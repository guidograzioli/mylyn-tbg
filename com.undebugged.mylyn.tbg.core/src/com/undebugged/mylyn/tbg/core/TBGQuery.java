package com.undebugged.mylyn.tbg.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;

import com.undebugged.mylyn.tbg.core.model.TBGObject;

public class TBGQuery {

    private String[] states; // open, closed, all
    private String[] issueTypes;  // issueTypes
    private String assignee; // none, me, all, username 
    private String projectKey;
    
    
    public TBGQuery() {
    }
    
    /**
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String toQueryString(int offset) throws UnsupportedEncodingException  {
        StringBuilder query = new StringBuilder();
        query.append("/").append(projectKey).append("/list/issues/json/");
        for (String state : states) {
        	if (state.equals("open") || state.equals("closed"))
        		query.append("state/").append(state).append("/");
        }
        for (String kind : issueTypes) {
        	if (!kind.equals("all"))
        		query.append("issuetype/").append(TBGObject.generateKey(kind)).append("/");
        }
        if (StringUtils.isNotBlank(assignee)) {
        	if (!assignee.equals("all"))
        		query.append("assigned_to/").append(URLEncoder.encode(assignee, "UTF-8")).append("/");           
        }
        return query.toString();
    }
    
    public static TBGQuery get(IRepositoryQuery query) {
        TBGQuery tbgQuery = new TBGQuery();
        tbgQuery.states = getAttributes(query, TBGCorePlugin.TBG_QUERY_STATE);
        tbgQuery.issueTypes = getAttributes(query, TBGCorePlugin.TBG_QUERY_TYPE);
        tbgQuery.assignee = query.getAttribute(TBGCorePlugin.TBG_QUERY_ASSIGNEE);
        tbgQuery.projectKey = query.getAttribute(TBGCorePlugin.TBG_QUERY_PROJECT);

        return tbgQuery;
    }
    
    static String[] getAttributes(IRepositoryQuery query, String key) {
        String values = query.getAttribute(key);
        return values != null ? values.split(",") : new String[0];
    }

}