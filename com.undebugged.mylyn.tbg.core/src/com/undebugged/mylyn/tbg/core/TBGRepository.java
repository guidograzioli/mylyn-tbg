package com.undebugged.mylyn.tbg.core;

import java.util.regex.Matcher;

public class TBGRepository {

	public TBGRepository() {
    }
	
	public static TBGRepository createFromUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid url: " + url);
        }
        return new TBGRepository();
    }
	
	public String getIssueUrl(String issueId) {
        return HTTPS_BITBUCKET_ORG + username + "/" + repoSlug + "/issue/" + issueId + "/";
    }
}
