package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.undebugged.mylyn.tbg.core.TBGRepository;

public class TBGProjects extends TBGObject {

	private int count = 0;
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int c) {
    	count = c;
    }
    
	
	private List<TBGProject> projects = new ArrayList<TBGProject>();

	public TBGProjects() {}
	
	@Override
	public Map<String, String> getParams() {
		return new HashMap<String, String>();
	}

	@Override
	public String buildUrl(TBGRepository r) {
		return r.getUrl()+"/list/projects/json";
	}

	@Override
	public String getObjectKey() {
		return "";
	}

	@Override
	public Type getListType() {
		return null;
	}
	
	public List<TBGProject> getProjects() {
		Collections.sort(projects);
		return projects;
	}

	public void setProjects(List<TBGProject> list) {
		projects = list;
	}
}
