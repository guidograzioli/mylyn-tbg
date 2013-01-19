package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.undebugged.mylyn.tbg.core.TBGRepository;

public class TBGIssue extends TBGObject {

    private String id;
    private String title;
    private String content;
    @SerializedName("created_at")
    private Date createdAt;
    private String status;
    private String issueNo;
    private String priority;
    private Date lastUpdated;
    private String assignedTo;
    private String postedBy;
    private String issuetype;
    private String resourceUri;
    private String projectKey;
    private String severity;
    private String category;
    private String build;
    private String edition;
    private String resolution;
    private String milestone;
    private String description;
    private String reproductionSteps;
    
    
    public TBGIssue() {}
    
    public TBGIssue(String taskId) {
    	String[] parts = taskId.split("\\|");
    	this.issueNo = parts[1];
    	this.projectKey = parts[0];
    }
    
    //respoinsible
    
    public TBGIssue(String title, String content, String priority, String status, String kind) {
        super();
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.status = status;
    }

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getIssuetype() {
		return issuetype;
	}

	public void setIssuetype(String issuetype) {
		this.issuetype = issuetype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReproductionSteps() {
		return reproductionSteps;
	}

	public void setReproductionSteps(String reproductionSteps) {
		this.reproductionSteps = reproductionSteps;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getLocalId() {
        return id;
    }

    public void setLocalId(String localId) {
        this.id = localId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

	@Override
    public String toString() {
        return "TBGIssue [id=" + id + ", title=" + title + ", description=" + description + ",steps="+ reproductionSteps +
        		", createdAt=" + createdAt + ", status=" + status + ", priority=" + priority +  
                ", issuetype=" + issuetype + "]";
    }
    

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", getTitle());
        params.put("content", getContent());
        params.put("status", getStatus());
        params.put("priority", getPriority());
        params.put("issueType", getIssuetype());
        return params;
    }

    @Override
    public String buildUrl(TBGRepository r) {
        return r.getUrl() + "/" + getProjectKey() + "/issues/" + getIssueNo().replaceAll("[^0-9]","") + "/format/json/";
    }
    
    
    
    public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	@Override
    public String getObjectKey() {
        return "";
    }

    public Type getListType() {
        return new TypeToken<List<TBGIssue>>(){}.getType();
    }

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}



}