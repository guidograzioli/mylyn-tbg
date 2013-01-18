package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.undebugged.mylyn.tbg.core.TBGRepository;

public class TBGUser extends TBGObject {

	private String id;
	private String name;
	private String username;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Map<String, String> getParams() {
		return null;
	}

	@Override
	public String buildUrl(TBGRepository bbr) {
		return "";
	}

	@Override
	public String getObjectKey() {
		return "";
	}

	@Override
	public Type getListType() {
		return new TypeToken<List<TBGUser>>(){}.getType();
	}

}
