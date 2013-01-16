package com.undebugged.mylyn.tbg.core;

import org.eclipse.mylyn.tasks.core.data.TaskAttribute;

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
    STATUS(TaskAttribute.STATUS, "Status", new EnumOptionProvider(TBGStatus.asArray())) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getStatus();
        }

        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setStatus(value);
        }

    },
    TASK_KEY(TaskAttribute.TASK_KEY, "id") {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getId();
        }

        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setId(value);
        }

    },
    SUMMARY(TaskAttribute.SUMMARY, "Title", BuilderFlag.IS_SMALL_EDITABLE_TEXT) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getTitle();
        }

        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setTitle(value);
        }

//    },
//    DESCRIPTION(TaskAttribute.DESCRIPTION, "Content", BuilderFlag.IS_OPTIONAL,BuilderFlag.LARGE_EDITABLE_TEXT) {
//
//        @Override
//        public String getValueFromIssue(TBGIssue issue) {
//            return issue.getContent();
//        }
//
//        @Override
//        public void setValueInIssue(TBGIssue issue, String value) {
//            issue.setContent(value);
//        }

    },
    KIND(TaskAttribute.TASK_KIND, "IssueType", new EnumOptionProvider(TBGIssueType.asArray())) {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getIssueType();
        }

        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            issue.setIssueType(value);
        }

//    },
//    PRIORITY(TaskAttribute.PRIORITY, "Priority", new EnumOptionProvider(BBPriority.asArray())) {
//
//        @Override
//        public String getValueFromIssue(TBGIssue issue) {
//            return issue.getPriority();
//        }
//
//        @Override
//        public void setValueInIssue(TBGIssue issue, String value) {
//            issue.setPriority(value);
//        }
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
    USER_REPORTER(TaskAttribute.USER_REPORTER, "Posted by") {

        @Override
        public String getValueFromIssue(TBGIssue issue) {
            return issue.getPostedBy() == null ? "" : issue.getPostedBy();
        }

        @Override
        public void setValueInIssue(TBGIssue issue, String value) {
            // we don't want to populate this yet.
        }

    },
    USER_ASSIGNED(TaskAttribute.USER_ASSIGNED, "Assigned to") {

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
    private TBGTaskAttributes(String id, String label, Object... flags) {
        builder = new TaskAttributeBuilder();
        builder.withId(id).withLabel(label);
        for (Object flag : flags) {
            processFlag(flag);
        }
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