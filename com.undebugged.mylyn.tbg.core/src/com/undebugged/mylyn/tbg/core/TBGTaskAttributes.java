package com.undebugged.mylyn.tbg.core;

import java.text.SimpleDateFormat;

import org.eclipse.mylyn.tasks.core.data.TaskAttribute;

import com.undebugged.mylyn.tbg.core.model.TBGIssue;
import com.undebugged.mylyn.tbg.core.model.TBGIssueType;
import com.undebugged.mylyn.tbg.core.model.TBGPriority;
import com.undebugged.mylyn.tbg.core.model.TBGStatus;

/**
 * The Bug Genie task attribute enumeration.
 * Each enum represents an attribute of an Issue in TBG.
 * The values provided in the constructors and  the overrided methods are used to either:
 *     - map between Mylyn's TaskData object and TBG's Issue object
 *          For instance TBG's IssueId needs to be mapped to the the TASK_KEY attribute
 *            so that Mylyn would know to send that key when searching for an issue)
 *     - define how a certain attributes behaves and looks.
 *          For instance the Status Attribute should appear as a Option list in the editor page.
 */
public enum TBGTaskAttributes {
	
    STATUS(TaskAttribute.STATUS, TaskAttribute.TYPE_SHORT_TEXT,"Status", new EnumOptionProvider(TBGStatus.asArray())) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getStatus();
        }
        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setStatus(value);
        }

    },
    TASK_KEY(TaskAttribute.TASK_KEY, TaskAttribute.TYPE_LONG,"id") {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getId();
        }
        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setId(value);
        }

    },
    SUMMARY(TaskAttribute.SUMMARY, TaskAttribute.TYPE_SHORT_TEXT,"title", BuilderFlag.IS_SMALL_EDITABLE_TEXT) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getTitle();
        }
        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setTitle(value);
        }

    },
    DESCRIPTION(TaskAttribute.DESCRIPTION, TaskAttribute.TYPE_LONG_RICH_TEXT,"Description", BuilderFlag.IS_OPTIONAL,BuilderFlag.LARGE_EDITABLE_TEXT) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getDescription();
        }

        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setDescription(value);
        }

    },
    KIND(TaskAttribute.TASK_KIND, TaskAttribute.TYPE_SHORT_TEXT, "Issue Type", new EnumOptionProvider(TBGIssueType.asArray())) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getIssuetype();
        }
        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setIssuetype(value);
        }

        
    },
    PRIORITY(TaskAttribute.PRIORITY, TaskAttribute.TYPE_SINGLE_SELECT, "Priority", new EnumOptionProvider(TBGPriority.asArray())) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getPriority();
        }

        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setPriority(value);
        }
//    },
//    VERSION(TaskAttribute.VERSION, "Version", new BBGetListOptionProvider(new BBVersion())) {
//
//        @Override
//        public String getValueFromIssue(BBIssue issue) {
//            return issue.getMetadata().getVersion();
//        }
//
//        @Override
//        public void setValueInIssue(BBIssue issue, String value) {
//            issue.getMetadata().setVersion(value);
//        }
//
//    },
//    MILESTONE(TaskAttribute.PRODUCT, "Milestone", new BBGetListOptionProvider(new BBMilestone())) {
//
//        @Override
//        public String getValueFromIssue(BBIssue issue) {
//            return issue.getMetadata().getMilestone();
//        }
//
//        @Override
//        public void setValueInIssue(BBIssue issue, String value) {
//            issue.getMetadata().setMilestone(value);
//        }
//
//    },
//    COMPONENT(TaskAttribute.COMPONENT, "Component", new BBGetListOptionProvider(new BBComponent())) {
//
//        @Override
//        public String getValueFromIssue(BBIssue issue) {
//            return issue.getMetadata().getComponent();
//        }
//
//        @Override
//        public void setValueInIssue(BBIssue issue, String value) {
//            issue.getMetadata().setComponent(value);
//        }
//
    },
    CREATED_AT(TaskAttribute.DATE_CREATION, TaskAttribute.TYPE_DATETIME, "Created on") {

		@Override
		public String getValueFromIssue(TBGIssue issue) {
			return SimpleDateFormat.getDateInstance().format(issue.getCreatedAt());
		}
		@Override
		public void setValueInIssue(TBGIssue issue, String value) {
			//readonly
		}
    },
    LAST_UPDATED(TaskAttribute.DATE_MODIFICATION, TaskAttribute.TYPE_DATETIME, "Updated on") {

		@Override
		public String getValueFromIssue(TBGIssue issue) {
			if (issue.getLastUpdated() == null) return null;
			return SimpleDateFormat.getDateInstance().format(issue.getLastUpdated());
		}
		@Override
		public void setValueInIssue(TBGIssue issue, String value) {
			//readonly
		}
    },    
    USER_REPORTER(TaskAttribute.USER_REPORTER, TaskAttribute.TYPE_PERSON, "Posted by") {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getPostedBy() == null ? "" : issue.getPostedBy();
        }
        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            // we don't want to populate this yet.
        }

    },
    USER_ASSIGNED(TaskAttribute.USER_ASSIGNED, TaskAttribute.TYPE_PERSON, "Assigned to") {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getAssignedTo() == null ? "" : issue.getAssignedTo();
        }
        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            // we don't want to populate this yet. 
        }

    };
    private TaskAttributeBuilder builder;

    /**
     * @param id - the Mylyn Id that matches the attribute, such as Status or Priority.
     * @param label - the label to show in the editor page.
     * @param flags - flags that represent how the attribute behaves in the editor page.
     *                valid values are either an OptionProvider instance (if the attribute is composed
     *                of a list of options) or any BuilderFlag.
     */
    private TBGTaskAttributes(String id, String type, String label, Object... flags) {
    	this.key = id;
        builder = new TaskAttributeBuilder();
        builder.withId(id).withLabel(label);
        for (Object flag : flags) {
            processFlag(flag);
        }
    }
    
    private String key;
    
    public String getKey() {
    	return key;
    }

    private void processFlag(Object flag) {
        if (flag instanceof OptionProvider) {
            builder.isOptionList((OptionProvider) flag);
        } else {
            switch ((BuilderFlag) flag) {
            case IS_OPTIONAL:
                builder.isOptional();
                break;
            case IS_SMALL_EDITABLE_TEXT:
                builder.isSmallEditableText();
                break;
            case LARGE_EDITABLE_TEXT:
                builder.isLongText();
                break;
            }
        }
    }

    public abstract String getValueFromIssue(TBGIssue issue);
    
    public abstract void setValueInIssue(TBGIssue issue, String value);

    public TaskAttributeBuilder getBuilder() {
        return builder;
    }
    
    enum BuilderFlag {
        IS_OPTIONAL, IS_SMALL_EDITABLE_TEXT, LARGE_EDITABLE_TEXT;
    }

}