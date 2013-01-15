package com.undebugged.mylyn.tbg.core;

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
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;
import org.eclipse.mylyn.tasks.core.sync.ISynchronizationSession;

public class TBGRepositoryConnector extends AbstractRepositoryConnector {

	private final TBGTaskDataHandler taskDataHandler;
	
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
	public TaskData getTaskData(TaskRepository taskRepository, String taskId,
			IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IStatus performQuery(TaskRepository repository,
			IRepositoryQuery query, TaskDataCollector collector,
			ISynchronizationSession session, IProgressMonitor monitor) {
		monitor.beginTask("Querying repository ...", 1);
        try {
            TBGIssues issues = TBGService.get(repository).searchIssues(TBGQuery.get(query));
            // collect task data
            if (issues == null) return Status.OK_STATUS;
            for (TBGIssue issue : issues.getIssues()) {
                //addCommentsToIssue(repository,issue);
                TaskData taskData = taskDataHandler.toTaskData(repository, issue);
                collector.accept(taskData);
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
