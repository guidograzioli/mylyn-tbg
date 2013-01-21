package com.undebugged.mylyn.tbg.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.RepositoryResponse;
import org.eclipse.mylyn.tasks.core.RepositoryResponse.ResponseKind;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskDataHandler;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;

import com.undebugged.mylyn.tbg.core.mapping.SimpleAttributeDataMapper;
import com.undebugged.mylyn.tbg.core.mapping.IssueToTaskDataMapper;
import com.undebugged.mylyn.tbg.core.model.TBGIssue;

public class TBGTaskDataHandler extends AbstractTaskDataHandler {

    private static final String DATA_VERSION = "1";
    private List<IssueToTaskDataMapper> mappers = new ArrayList<IssueToTaskDataMapper>();
 
    public TBGTaskDataHandler() {
        mappers.add(new SimpleAttributeDataMapper());
    }

    @Override
    public TaskAttributeMapper getAttributeMapper(TaskRepository taskRepository) {
        return new TBGAttributeMapper(taskRepository);
    }

    @Override
    public boolean initializeTaskData(TaskRepository repository, TaskData data, ITaskMapping initializationData,
            IProgressMonitor monitor) throws CoreException {
        data.setVersion(DATA_VERSION);
        for (IssueToTaskDataMapper mapper : mappers) {
            mapper.addAttributesToTaskData(data, repository);
        }
        return true;
    }
    
    // This populates from the server
    public TaskData toTaskData(TaskRepository repository, TBGIssue issue) throws TBGServiceException {
        
        TaskData data = new TaskData(getAttributeMapper(repository), TBGCorePlugin.CONNECTOR_KIND,
                repository.getRepositoryUrl(), issue.getId());
        data.setVersion(DATA_VERSION);
        for (IssueToTaskDataMapper mapper : mappers) {
            mapper.applyToTaskData(issue, data, repository);
        }
        data.setPartial(hasNullValueInRequiredAttributes(data));
        
        return data;
    }

    
    // return true if data has null value of required attribute 
    private boolean hasNullValueInRequiredAttributes(TaskData data) {
        for (IssueToTaskDataMapper mapper : mappers) {
            if (!mapper.isValid(data)) return true;
        }
        return false;
    }

    @Override
    public RepositoryResponse postTaskData(TaskRepository repository, TaskData taskData,
            Set<TaskAttribute> oldAttributes, IProgressMonitor monitor) throws CoreException {
        try {
            TBGIssue issue = new TBGIssue();
            for (IssueToTaskDataMapper mapper : mappers) {
                mapper.applyToIssue(taskData, issue);
            }
            TBGIssue newIssue = null;
            if (taskData.isNew()) {
                newIssue = TBGService.get(repository).doPost(issue);
                newIssue.setProjectKey(issue.getProjectKey());
            } else {
//                // TODO handle update not supported Operation
//                newIssue = TBGService.get(repository).doPut(issue);
//                TaskAttribute newCommentAttribute = taskData.getRoot().getMappedAttribute(TaskAttribute.COMMENT_NEW);
//                if (newCommentAttribute != null && StringUtils.isNotBlank(newCommentAttribute.getValue())) {
//                    TBGService.get(repository).doPost(comment);
//                }
            }
            ResponseKind responseKind = taskData.isNew() ? ResponseKind.TASK_CREATED : ResponseKind.TASK_UPDATED;
            return new RepositoryResponse(responseKind, newIssue.getId());
        } catch (TBGServiceException e) {
            throw new CoreException(TBGConnectorStatus.newErrorStatus(e));
        } catch (NullPointerException e) {
        	e.printStackTrace();
            throw new CoreException(TBGConnectorStatus.newErrorStatus("Error occurred during POST method", e));
        }
    }

}