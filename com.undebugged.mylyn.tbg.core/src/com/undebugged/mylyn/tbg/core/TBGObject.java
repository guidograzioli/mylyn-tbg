package com.undebugged.mylyn.tbg.core;

import java.lang.reflect.Type;
import java.util.Map;

public abstract class TBGObject {
    
    public abstract Map<String, String> getParams();
    
    public abstract String buildUrl(TBGRepository bbr);
    
    public abstract String getKey();
    
    public abstract Type getListType();

}