package com.undebugged.mylyn.tbg.core.model;


public enum TBGResolution {
	
	CANT_REPRODUCE("CAN'T REPRODUCE"),
	WONT_FIX("WON'T FIX"),
	NOT_AN_ISSUE("NOT AN ISSUE"),
	POSTPONED("POSTPONED"),
	RESOLVED("RESOLVED"),
	CANT_FIX("CAN'T FIX"),
	DUPLICATE("DUPLICATE");
	
	private String resolution;

	TBGResolution(final String resolution) {
        this.resolution = resolution;
    }
    

    public String getResolution() {
        return resolution;
    }
    
    public static String[] asArray() {
        return new String[] {
        		CANT_REPRODUCE.getResolution(), WONT_FIX.getResolution(), NOT_AN_ISSUE.getResolution(),
        		POSTPONED.getResolution(), RESOLVED.getResolution(), CANT_FIX.getResolution(),
        		DUPLICATE.getResolution()
        };
    }

}
