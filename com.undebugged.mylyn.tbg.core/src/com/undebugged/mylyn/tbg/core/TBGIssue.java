package com.undebugged.mylyn.tbg.core;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

public class TBGIssue extends TBGObject {

    /** issue id */
    private String id;
    /** issue title */
    private String title;
    /** issue content */
    private String content;
    /** issue created on */
    private Date createdAt;
    private String status;
    private String issueNo;
    private String priority;
    private Date lastUpdated;
    private String assignedTo;
    private String postedBy;
    
    private String resourceUri;
    
    public TBGIssue() {}
    
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

    public Date getCreatedOn() {
        return createdAt;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdAt = createdOn;
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
        return "TBGIssue [id=" + id + ", title=" + title + ", content=" + content + ", createdOn="
                + createdAt + ", status=" + status + ", priority=" + priority +  "]";
    }
    

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", getTitle());
        params.put("content", getContent());
        params.put("status", getStatus());
        params.put("priority", getPriority());
        return params;
    }

    @Override
    public String buildUrl(TBGRepository bbr) {
        //return TBGRepository.API_BITBUCKET + TBGRepository.REPO_PART + bbr.getUsername() + "/" + bbr.getRepoSlug() + "/issues/";
    	return bbr.getUrl();
    }
    
    @Override
    public String getKey() {
        return this.getLocalId();
    }

    public Type getListType() {
        return new TypeToken<List<TBGIssue>>(){}.getType();
    }



}