package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.Map;

import com.undebugged.mylyn.tbg.core.TBGRepository;

public abstract class TBGObject {
    
    public abstract Map<String, String> getParams();
    
    public abstract String buildUrl(TBGRepository bbr);
    
    public abstract String getKey();
    
    public abstract Type getListType();

}