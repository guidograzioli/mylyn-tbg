package com.undebugged.mylyn.tbg.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class TBGCorePlugin extends Plugin {

	public static final String ID_PLUGIN = "com.undebugged.mylyn.tbg.core"; //$NON-NLS-1$
	public static final String CONNECTOR_KIND = "TheBugGenie";//$NON-NLS-1$;
	public static final String TBG_QUERY_TYPE = "issuetype";//$NON-NLS-1$;
	public static final String TBG_QUERY_ASSIGNEE = "assigned_to";//$NON-NLS-1$;
	public static final String TBG_QUERY_STATE = "state";//$NON-NLS-1$;
	public static final String TBG_QUERY_TITLE = "title";//$NON-NLS-1$;
	public static final String TBG_QUERY_PROJECT = "projectKey";//$NON-NLS-1$;
	public static final String PROPERTY_SECURITYKEY = "securityKey"; //$NON-NLS-1$;

	
	private static TBGCorePlugin plugin;
	private TBGRepositoryConnector connector;
	
	public TBGCorePlugin() {}
	
	public static TBGCorePlugin getDefault() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (connector != null) {
			connector.stop();
			connector = null;
		}

		plugin = null;
		super.stop(context);
	}

	public TBGRepositoryConnector getConnector() {
		return connector;
	}

	public void setConnector(TBGRepositoryConnector connector) {
		this.connector = connector;
	}
	
}
