package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.Map;

import com.undebugged.mylyn.tbg.core.TBGRepository;

public abstract class TBGObject {
    
    public abstract Map<String, String> getParams();
    
    public abstract String buildUrl(TBGRepository bbr);
    
    public abstract String getObjectKey();
    
    public abstract Type getListType();

    public static String generateKey(String name) {
    	if (name == null) return null;
    	return name.toLowerCase().replaceAll("[^0-9a-z]", "");
    }
}