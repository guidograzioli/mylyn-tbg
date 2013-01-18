package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.undebugged.mylyn.tbg.core.TBGRepository;

public class TBGProject extends TBGObject implements Comparable<TBGProject> {

	// project_key: project_name
	@SerializedName("key")
	private String projectKey;
	@SerializedName("name")
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
	public String getObjectKey() {
		return "";
	}

	@Override
	public Type getListType() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TBGProject)) return false;
		return getProjectKey().equals(((TBGProject)obj).getProjectKey());
	}

	@Override
	public int compareTo(TBGProject o) {
		if (o == null || o.getProjectName() == null) return -1;
		return getProjectName().compareTo(o.getProjectName());
	}
}
