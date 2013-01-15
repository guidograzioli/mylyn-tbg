package com.undebugged.mylyn.tbg.core;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

public class TBGIssue extends TBGObject {

    /** issue id */
    private String localId;
    /** issue title */
    private String title;
    /** issue content */
    private String content;
    /** issue created on */
    private Date createdOn;
    /** */
    private Meta metadata = new Meta();
    
    private String status;
    
    private String priority;

    /** reporter */
    //private BBUser reportedBy;
    
    //private BBUser responsible;
    
    //private long commentCount;
    
    //private long followerCount;
    
    //private BBComment[] comments = new BBComment[]{};;
    
    private String resourceUri;
    
    //private boolean isSpam;
    
    public TBGIssue() {}
    
    //respoinsible
    
    public TBGIssue(String title, String content, String priority, String status, String kind) {
        super();
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.status = status;
        this.metadata.setKind(kind);
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Meta getMetadata() {
        return metadata;
    }

    public void setMetadata(Meta metadata) {
        this.metadata = metadata;
    }

    public String getKind() {
        if (metadata == null) return null;
        return metadata.getKind();
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
        return "BitbucketIssue [localId=" + localId + ", title=" + title + ", content=" + content + ", createdOn="
                + createdOn + ", metadata=" + metadata + ", status=" + status + ", priority=" + priority +  "]";
    }
    
    public static class Meta {
       
        private String kind;
        private String milestone;
        private String component;
        private String version;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getMilestone() {
            return milestone;
        }

        public void setMilestone(String milestone) {
            this.milestone = milestone;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "Meta [kind=" + kind + ", milestone=" + milestone + ", component=" + component + ", version="
                    + version + "]";
        }

    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", getTitle());
        params.put("content", getContent());
        params.put("kind", getKind());
        params.put("status", getStatus());
        params.put("priority", getPriority());
        if (getMetadata().getVersion() != null && !getMetadata().getVersion().isEmpty()) {
            params.put("version", getMetadata().getVersion());
        }
        if (getMetadata().getMilestone() != null && !getMetadata().getMilestone().isEmpty()) {
            params.put("milestone", getMetadata().getMilestone());
        }
        if (getMetadata().getComponent() != null && !getMetadata().getComponent().isEmpty()) {
            params.put("component", getMetadata().getComponent());
        }
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