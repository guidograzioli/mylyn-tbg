package com.undebugged.mylyn.tbg.core.model;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.undebugged.mylyn.tbg.core.TBGRepository;

/**
 * Container of The Bug Genie issues.
 */
public class TBGIssues extends TBGObject {

    private final Map<Integer,TBGIssue> issues = new HashMap<Integer,TBGIssue>();
    
    private int count = 0;
    
    public int getCount() {
        return count;
    }

    public TBGIssue get(int idx) {
        return issues.get(idx);
    }
    
    public Map<Integer,TBGIssue> getIssues() {
    	return issues;
    }
    
    public Collection<TBGIssue> getIssuesList() {
    	return issues.values();
    }
    
    public Set<Integer> getIssuesId() {
    	return issues.keySet();
    }
    
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("TBGIssues[");
        str.append("count=").append(count);
        str.append(", issues=[");
        for (Iterator<TBGIssue> iter = issues.values().iterator(); iter.hasNext();) {
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
    public String buildUrl(TBGRepository r) {
        //return TBGRepository.API_BITBUCKET + TBGRepository.REPO_PART + bbr.getUsername() + "/" + bbr.getRepoSlug() + "/" + "issues/";
    	return r.getUrl();
    }

	public void addMoreIssues(Map<Integer, TBGIssue> moreIssues) {
		issues.putAll(moreIssues);
	}

}