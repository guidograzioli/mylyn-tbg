package com.undebugged.mylyn.tbg.core;
public enum TBGStatus {

	NEW("New"),
    INVESTIGATING("Investigating"),
    CONFIRMED("Confirmed"),
    BEINGWORKEDON("Being worked on"),
    NEARCOMPLETION("Near completion"),
    READYFORTESTING("Ready for testing / QA"),
    TESTING("Testing / QA"),
    CLOSED("Closed"),
    POSTPONED("Postponed"),
    NOTABUG("Not a bug"),
    DONE("Done"),
    FIXED("Fixed");
    
    TBGStatus(final String status) {
        this.status = status;
    }
    
    private final String status;

    public String getStatus() {
        return status;
    }

    public static String[] asArray() {
        return new String[] {
    		NEW.getStatus(), INVESTIGATING.getStatus(), CONFIRMED.getStatus(), NEARCOMPLETION.getStatus(), 
    		BEINGWORKEDON.getStatus(), READYFORTESTING.getStatus(), TESTING.getStatus(), CLOSED.getStatus(),
    		POSTPONED.getStatus(), NOTABUG.getStatus(), DONE.getStatus(), FIXED.getStatus() 
        };
    }
    
}