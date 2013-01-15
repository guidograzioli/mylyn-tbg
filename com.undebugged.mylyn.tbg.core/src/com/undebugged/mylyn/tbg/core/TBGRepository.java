package com.undebugged.mylyn.tbg.core;


public class TBGRepository {

	private String url;
	private String projectId;
	
	public TBGRepository(String url) {
		this.url = url;
    }
	
	public static TBGRepository createFromUrl(String url) {
        return new TBGRepository(url);
    }
	
	/**
	 * Req format: url/project/"list"/"issues"/"json"
	 * @param projectId
	 * @return
	 */
	public String getIssueListForProjectUrl(String projectId) {
        return getUrl() + "/" + projectId + "/list/issues/json";
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Req format: url/project/"issues"/issueId/"format"/"json"
	 * @param taskId
	 * @return
	 */
	public String getIssueUrl(String taskId) {
		return getUrl() + "/" + projectId + "/issues/"+taskId+"/format/json";
	}
	
}
