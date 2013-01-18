package com.undebugged.mylyn.tbg.core.model;

public enum TBGPriority {
	
	CRITICAL("Critical"), // = 1
	NEEDSTOBEFIXED("Needs to be fixed"), // = 2;
	MUSTFIX("Must fix before next release"), // = 3;
	LOW("Low"), // = 4;
	NORMAL("Normal");

	TBGPriority(final String priority) {
        this.priority = priority;
    }
    
    private String priority;

    public String getPriority() {
        return priority;
    }
    
    public static String[] asArray() {
        return new String[] {
    		CRITICAL.getPriority(), NEEDSTOBEFIXED.getPriority(), MUSTFIX.getPriority(),
    		LOW.getPriority(), NORMAL.getPriority()
        };
    }
}
