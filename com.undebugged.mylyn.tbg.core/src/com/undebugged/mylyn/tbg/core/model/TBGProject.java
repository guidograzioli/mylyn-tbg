package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.undebugged.mylyn.tbg.core.TBGRepository;

public class TBGProject extends TBGObject {

	// project_key: project_name
	private String projectKey;
	private String projectName;

	public TBGProject(String key, String name) {
		this.projectKey = key;
		this.projectName = name;
	}
	
	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public Map<String, String> getParams() {
		return new HashMap<String, String>();
	}

	@Override
	public String buildUrl(TBGRepository r) {
		return r.getUrl()+"/list/projects/json";
	}

	@Override
	public String getKey() {
		return "";
	}

	@Override
	public Type getListType() {
		return null;
	}

}
