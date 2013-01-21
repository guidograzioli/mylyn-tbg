package com.undebugged.mylyn.tbg.core;

import java.util.*;

import org.eclipse.mylyn.tasks.core.ITaskAttachment;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;



/**
 * Provides a mapping from Mylyn task keys to TBG issues keys.
 * 
 * @author Guido Grazioli
 */
public class TBGAttributeMapper extends TaskAttributeMapper {
    
	public TBGAttributeMapper(TaskRepository taskRepository) {
        super(taskRepository);
    }
    
    private Map<String, TBGTaskAttributes> taskAttributeToTBGAttribute = new HashMap<String, TBGTaskAttributes>();

    
    {
        taskAttributeToTBGAttribute.put(TaskAttribute.PRODUCT, TBGTaskAttributes.PROJECT);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.COMPONENT, TBGTaskAttributes.CATEGORY);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.COMMENT_NEW, Attribute.NEW_COMMENT);
    	taskAttributeToTBGAttribute.put(TaskAttribute.DESCRIPTION, TBGTaskAttributes.DESCRIPTION);
    	taskAttributeToTBGAttribute.put(TaskAttribute.DATE_MODIFICATION, TBGTaskAttributes.LAST_UPDATED);
    	taskAttributeToTBGAttribute.put(TaskAttribute.SUMMARY, TBGTaskAttributes.SUMMARY);
    	taskAttributeToTBGAttribute.put(TaskAttribute.DATE_CREATION, TBGTaskAttributes.CREATED_AT);
    	taskAttributeToTBGAttribute.put(TaskAttribute.USER_REPORTER, TBGTaskAttributes.USER_REPORTER);
    	taskAttributeToTBGAttribute.put(TaskAttribute.USER_ASSIGNED, TBGTaskAttributes.USER_ASSIGNED);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.ATTACHMENT_ID, TBGTaskAttributes.ATTACHID);
    	taskAttributeToTBGAttribute.put(TaskAttribute.TASK_KEY, TBGTaskAttributes.TASK_KEY);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.USER_CC, TBGTaskAttributes.MONITORS);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.ADD_SELF_CC, TBGTaskAttributes.ADD_SELF_TO_MONITORS);
    	taskAttributeToTBGAttribute.put(TaskAttribute.STATUS, TBGTaskAttributes.STATUS);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.RESOLUTION, TBGTaskAttributes.RESOLUTION);
    	taskAttributeToTBGAttribute.put(TaskAttribute.PRIORITY, TBGTaskAttributes.PRIORITY);
    	taskAttributeToTBGAttribute.put(TaskAttribute.TASK_KIND, TBGTaskAttributes.KIND);
    	taskAttributeToTBGAttribute.put(TaskAttribute.RESOLUTION, TBGTaskAttributes.RESOLUTION);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.VERSION, TBGTaskAttributes.VERSION);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.SEVERITY, TBGTaskAttributes.SEVERITY);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.DATE_DUE, TBGTaskAttributes.DUE_DATE);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.DATE_COMPLETION, TBGTaskAttributes.COMPLETION_DATE);
//    	taskAttributeToTBGAttribute.put(TaskAttribute.KEYWORDS, TBGTaskAttributes.TAGS);
    }

    @Override
    public String mapToRepositoryKey(TaskAttribute parent, String key) {
    	
    	TBGTaskAttributes mapped = taskAttributeToTBGAttribute.get(key);
    	if ( mapped != null)
    		return mapped.getKey();
    	
        return super.mapToRepositoryKey(parent, key);
    }

    @Override
    public void updateTaskAttachment(ITaskAttachment taskAttachment, TaskAttribute taskAttribute) {

        super.updateTaskAttachment(taskAttachment, taskAttribute);

//        if ( taskAttachment.getFileName() != null && taskAttachment.getFileName().startsWith(MantisAttachmentHandler.CONTEXT_DESCRIPTION))
//            taskAttachment.setDescription(MantisAttachmentHandler.CONTEXT_DESCRIPTION);

    }
    
}