package com.undebugged.mylyn.tbg.core;

import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;

public interface TBGIssueToTaskDataMapper {

    /**
     * populates the provided TaskData with empty attributes - creating a new Issue Editor. 
     */
    public abstract void addAttributesToTaskData(TaskData data,TaskRepository repository);
    /**
     * populates the TaskData with attributes taken from the TBGIssue object provided
     */
    public abstract void applyToTaskData(TBGIssue issue, TaskData data, TaskRepository repository);
    /**
     * populates the provided TBGIssue from the TaskData. 
     */
    public abstract void applyToIssue(TaskData data,TBGIssue issue);
    
    /**
     * Check whether the TaskData isValid to update in TBG in regards to the section this mapper handles.
     */
    public abstract boolean isValid(TaskData data);

}