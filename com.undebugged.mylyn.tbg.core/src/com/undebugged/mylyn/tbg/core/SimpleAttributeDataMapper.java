package com.undebugged.mylyn.tbg.core;

import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;

/**
 *  provides mapping of simple attributes (those attributes that are part of the TBGIssue domain model in Bug Genie).
 *  It primarily uses the TBGTaskAttributes enum to perform mapping.
 */
public class SimpleAttributeDataMapper implements TBGIssueToTaskDataMapper {


    public SimpleAttributeDataMapper() {
    }

    /* (non-Javadoc)
     * @see org.eclipse.mylyn.bitbucket.internal.mapping.BBIssueToTaskDataMapper#applyToTaskData(org.eclipse.mylyn.bitbucket.internal.model.BBIssue, org.eclipse.mylyn.tasks.core.data.TaskData, org.eclipse.mylyn.tasks.core.TaskRepository)
     */

    @Override
    public void addAttributesToTaskData(TaskData data, TaskRepository repository) {
        for (TBGTaskAttributes bbAttr : TBGTaskAttributes.values()) {
            bbAttr.getBuilder().build(data, repository);
        }   
    }
    
    @Override
    public void applyToTaskData(TBGIssue issue, TaskData data, TaskRepository repository) {
        addAttributesToTaskData(data, repository);
        for (TBGTaskAttributes bbAttr : TBGTaskAttributes.values()) {
            TaskAttribute attr = data.getRoot().getAttribute(bbAttr.getBuilder().getAttributeId());
            String value = bbAttr.getValueFromIssue(issue);
            if (value != null) attr.setValue(value);
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.mylyn.bitbucket.internal.mapping.BBIssueToTaskDataMapper#applyToIssue(org.eclipse.mylyn.tasks.core.data.TaskData, org.eclipse.mylyn.bitbucket.internal.model.BBIssue)
     */
    @Override
    public void applyToIssue(TaskData data, TBGIssue issue) {
        for (TBGTaskAttributes bbAttr : TBGTaskAttributes.values()) {
            String value = getValue(bbAttr.getBuilder(),data);
            bbAttr.setValueInIssue(issue, value);
        }       
        issue.setLocalId(data.getTaskId());
    }

    @Override
    public boolean isValid(TaskData data) {
        for (TBGTaskAttributes bbAttr : TBGTaskAttributes.values()) {
            if (bbAttr.getBuilder().isRequired() && !hasAttribute(bbAttr.getBuilder(),data))
                return false;
        }
        return true;
    }
    
    private boolean hasAttribute(TaskAttributeBuilder builder,TaskData data) {
        return getValue(builder,data) == null;
    }
    private String getValue(TaskAttributeBuilder builder,TaskData data) {
        TaskAttribute attr = data.getRoot().getAttribute(builder.getAttributeId());
        return attr != null ? attr.getValue() : null;
    }

}