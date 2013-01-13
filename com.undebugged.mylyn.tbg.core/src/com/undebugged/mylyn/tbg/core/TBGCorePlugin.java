package com.undebugged.mylyn.tbg.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class TBGCorePlugin extends Plugin {

	public static final String ID_PLUGIN = "org.eclipse.mylyn.trac.core"; //$NON-NLS-1$
	public static final String CONNECTOR_KIND = "TheBugGenie";
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
