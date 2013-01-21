package com.undebugged.mylyn.tbg.core.mapping;

import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;

/**
 * a helper class meant to make working with Mylyn TaskAttributes easier.
 * It is configured once, and after configuration can be used to create new TaskAttributes directly.
 * It has methods that define specific TaskAttribute behavior based on abstract ideas-
 *      for instance you can define that the built attribute would be an option select box in the Editor.
 * It also provides useful defaults.
 */
public class TaskAttributeBuilder {

    private String label;
    private String id;
    private OptionProvider optionProvider = null;
    private String type = TaskAttribute.TYPE_SHORT_TEXT;
    private String kind = null;
    private boolean required = true;
    private boolean readOnly = true;
    
    public TaskAttributeBuilder() {
        
    }

    public TaskAttribute build(TaskData data,TaskRepository repository) {
        Assert.isNotNull(label);
        Assert.isNotNull(id);
        TaskAttribute attr = data.getRoot().createAttribute(id);
        attr.getMetaData()
                .defaults()
                .setLabel(label)
                .setType(type)
                .setKind(kind)
                .setReadOnly(readOnly);
        if (optionProvider != null) buildOptionList(attr,repository);
        return attr;
    }
    private void buildOptionList(TaskAttribute attr,TaskRepository repository) {
        for (Entry<String,String> option: optionProvider.getOptions(repository)) {
            attr.putOption(option.getKey(),option.getValue());
        }       
    }
    
    public TaskAttributeBuilder isOptionList(OptionProvider provider) {
        this.optionProvider = provider;
        kind = TaskAttribute.KIND_DEFAULT;
        type = TaskAttribute.TYPE_SINGLE_SELECT;
        readOnly = false;
        return this;
    }

    public TaskAttributeBuilder isLongText() {
        this.type = TaskAttribute.TYPE_LONG_RICH_TEXT;
        readOnly = false;
        
        return this;
    }
    public TaskAttributeBuilder isSmallEditableText(){
        readOnly = false;
        return this;
    }
    public TaskAttributeBuilder withId(String id) {
        this.id = id;
        return this;
    }
    public TaskAttributeBuilder withLabel(String label) {
        this.label = label;
        return this;
    }
    public TaskAttributeBuilder isOptional() {
        this.required = false;
        return this;
    }

    public boolean isRequired() {
        return required;
    }
    
    public String getAttributeId() {
        return id;
    }
    
}