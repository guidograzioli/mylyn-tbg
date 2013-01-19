package com.undebugged.mylyn.tbg.core.model;

import org.eclipse.mylyn.tasks.core.ITask.PriorityLevel;

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
    
    public static PriorityLevel fromPriorityId(String priority) {
    	if (CRITICAL.getPriority().equals(priority)) return PriorityLevel.P1;
    	if (NEEDSTOBEFIXED.getPriority().equals(priority)
    			|| MUSTFIX.getPriority().equals(priority)) return PriorityLevel.P2;
    	if (NORMAL.getPriority().equals(priority)) return PriorityLevel.P3;
    	if (LOW.getPriority().equals(priority)) return PriorityLevel.P4;
    	return PriorityLevel.P5; //none
    }
}
