package com.undebugged.mylyn.tbg.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.tasks.core.AbstractRepositoryConnector;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;
import org.eclipse.mylyn.tasks.core.sync.ISynchronizationSession;

import com.undebugged.mylyn.tbg.core.model.TBGIssue;
import com.undebugged.mylyn.tbg.core.model.TBGIssues;

public class TBGRepositoryConnector extends AbstractRepositoryConnector {

	private final TBGTaskDataHandler taskDataHandler;
	// issue ID , projectKey|issueNo
	private final Map<String,String> cache = new HashMap<String,String>();
	
	public TBGRepositoryConnector() {
		this.taskDataHandler = new TBGTaskDataHandler();
	}

	@Override
	public boolean canCreateNewTask(TaskRepository repository) {
		return true;
	}

	@Override
	public boolean canCreateTaskFromKey(TaskRepository repository) {
		return false;
	}

	@Override
	public boolean canDeleteTask(TaskRepository repository, ITask task) {
		return false;
	}
	
	@Override
	public String getConnectorKind() {
		return TBGCorePlugin.CONNECTOR_KIND;
	}

	@Override
	public String getLabel() {
		return "The Bug Genie (JSON API)";
	}

	@Override
	public String getRepositoryUrlFromTaskUrl(String taskFullUrl) {
		if (taskFullUrl == null) return null;
		return taskFullUrl.substring(taskFullUrl.indexOf("/issues/"));
	}

	@Override
	public TaskData getTaskData(TaskRepository repository, String taskId,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Querying repository ...", 100);
        try {
        	if (cache.get(taskId) != null) { 
	            TBGIssue issue = TBGService.get(repository).doGet(new TBGIssue(cache.get(taskId)));
	            monitor.worked(60);
	            TaskData taskData = taskDataHandler.toTaskData(repository, issue);
	            taskData.setPartial(false);
	            return taskData;
        	}
        } catch (TBGServiceException e) {
        	e.printStackTrace();
            System.err.println(e.getMessage());
        } finally {
            monitor.done();
        }
		return null;
	}

	@Override
	public String getTaskIdFromTaskUrl(String taskFullUrl) {
		Pattern p = Pattern.compile("issues/(\\\\d+)/format");
		if (taskFullUrl.contains("/issues/")) {
			Matcher m = p.matcher(taskFullUrl);
			if (m.groupCount() > 1) return m.group(1);
		}
		return null;
	}

	@Override
	public String getTaskUrl(String repositoryUrl, String taskId) {
		return TBGRepository.createFromUrl(repositoryUrl).getIssueUrl(taskId);
	}

	@Override
	public boolean hasTaskChanged(TaskRepository taskRepository, ITask task,
			TaskData taskData) {
		
		TaskAttribute attrModification = taskData.getRoot().getMappedAttribute(TaskAttribute.DATE_MODIFICATION);

		if (attrModification == null || attrModification.getValue() == null
                || attrModification.getValue().length() > 0)
            return false;
        
        // detect if any of the tasks has and old version 
        Date lastKnownUpdated = task.getModificationDate();
        
        Date modified = taskData.getAttributeMapper().getDateValue(attrModification);
        
        return lastKnownUpdated.after(modified) || lastKnownUpdated.before(modified);
	}

	@Override
	public IStatus performQuery(TaskRepository repository,
			IRepositoryQuery query, TaskDataCollector collector,
			ISynchronizationSession session, IProgressMonitor monitor) {
		monitor.beginTask("Querying repository ...", 100);
        try {
            TBGIssues issues = TBGService.get(repository).searchIssues(TBGQuery.get(query));
            monitor.worked(60);
            // collect task data
            if (issues == null) return Status.OK_STATUS;
            for (TBGIssue issue : issues.getIssues()) {
                //addCommentsToIssue(repository,issue);
                TaskData taskData = taskDataHandler.toTaskData(repository, issue);
                taskData.setPartial(true);
                collector.accept(taskData);
                monitor.worked(40/issues.getCount());
                cache.put(issue.getId(), query.getAttribute(TBGCorePlugin.TBG_QUERY_PROJECT)+"|"+issue.getIssueNo());
            }
            return Status.OK_STATUS;
        } catch (TBGServiceException e) {
            return TBGConnectorStatus.newErrorStatus(e);
        } finally {
            monitor.done();
        }
	}

	@Override
	public void updateRepositoryConfiguration(TaskRepository taskRepository,
			IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTaskFromTaskData(TaskRepository taskRepository,
			ITask task, TaskData taskData) {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
