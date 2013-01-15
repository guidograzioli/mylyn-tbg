package com.undebugged.mylyn.tbg.core;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Container of The Bug Genie issues.
 */
public class TBGIssues extends TBGObject {

    private final List<TBGIssue> issues = new ArrayList<TBGIssue>();
    
    private int count = 0;
    
    public int getCount() {
        return count;
    }

    public TBGIssue get(int idx) {
        return issues.get(idx);
    }
    
    public List<TBGIssue> getIssues() {
        return issues;
    }
    
    public int addMoreIssues(List<TBGIssue> moreIssues) {
        issues.addAll(moreIssues);
        return issues.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("BitbucketIssues[");
        str.append("count=").append(count);
        str.append(", issues=[");
        for (Iterator<TBGIssue> iter = issues.iterator(); iter.hasNext();) {
            TBGIssue issue = iter.next();
            str.append(issue.getLocalId());
            if (iter.hasNext()) {
                str.append(",");
            }
        }
        str.append("]]");
        return str.toString();
    }

    
    public Map<String, String> getParams() {
        return new HashMap<String, String>();
    }

    @Override
    public String getKey() {
        return "";
    }
    
    public Type getListType() {
       //annoyingly this is a wrapper for issue and works differently to the others
        return null;
    }

    @Override
    public String buildUrl(TBGRepository bbr) {
        //return TBGRepository.API_BITBUCKET + TBGRepository.REPO_PART + bbr.getUsername() + "/" + bbr.getRepoSlug() + "/" + "issues/";
    	return bbr.getUrl();
    }

}