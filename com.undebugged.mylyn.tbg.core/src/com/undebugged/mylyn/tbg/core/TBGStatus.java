package com.undebugged.mylyn.tbg.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class TBGStatus extends Status {

    public TBGStatus(int severity, String message, Throwable exception) {
        super(severity, TBGCorePlugin.ID_PLUGIN, message, exception);
    }

    public TBGStatus(int severity, String message) {
        super(severity, TBGCorePlugin.ID_PLUGIN, message);
    }

    public static IStatus newErrorStatus(String message) {
        return new TBGStatus(IStatus.ERROR, message);
    }

    public static IStatus newErrorStatus(Throwable t) {
        return new TBGStatus(IStatus.ERROR, t.getMessage(), t);
    }
    
    public static IStatus newErrorStatus(String message, Throwable t) {
        return new TBGStatus(IStatus.ERROR, message, t);
    }

}