package com.undebugged.mylyn.tbg.core.model;

public enum TBGIssueType {
    
    BUG("Bug report"),
    ENHANCEMENT("Enhancement"),
    PROPOSAL("Feature request"),
    TASK("Task"),
    USERSTORY("User story"),
    DOCREQ("Documentation request");
    
    TBGIssueType(final String kind) {
        this.type = kind;
    }
    
    private final String type;

    public String getType() {
        return type;
    }
    
    public static String[] asArray() {
        return new String[]{BUG.getType(), ENHANCEMENT.getType(), PROPOSAL.getType(), TASK.getType(), USERSTORY.getType(), DOCREQ.getType()};
    }
    public static String[] asArrayForMultiSelect() {
        return new String[]{"All",BUG.getType(), ENHANCEMENT.getType(), PROPOSAL.getType(), TASK.getType(), USERSTORY.getType(), DOCREQ.getType()};
    }
}