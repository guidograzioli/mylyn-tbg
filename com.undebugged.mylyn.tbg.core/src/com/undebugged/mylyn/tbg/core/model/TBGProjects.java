package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.undebugged.mylyn.tbg.core.TBGRepository;

public class TBGProjects extends TBGObject {

	// project_key: project_name
	private Map<String,String> projects = new HashMap<String,String>();

	public TBGProjects() {}
	
	public TBGProjects(Map<String,String> elements) {
		this.projects = elements;
	}
	
	
	public Set<String> getProjectKeys() {
		return projects.keySet();
	}
	
	public String getProjectName(String key) {
		return projects.get(key);
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
	
	public class TBGProjectsDeserializer implements JsonDeserializer<TBGProjects> {
		public TBGProjects deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			Set<Entry<String, JsonElement>> elements = json.getAsJsonObject().entrySet();
			Map<String,String> projects = new HashMap<String,String>();
			for (Entry<String, JsonElement> entry : elements) {
				projects.put(entry.getKey(), entry.getValue().getAsString());
			}
			return new TBGProjects(projects);
		}
	}

	public Set<TBGProject> getAsProjectSet() {
		Set<TBGProject> set = new HashSet<TBGProject>();
		for (Entry<String,String> e : projects.entrySet()) {
			set.add(new TBGProject(e.getKey(), e.getValue()));
		}
		return set;
	}

}
