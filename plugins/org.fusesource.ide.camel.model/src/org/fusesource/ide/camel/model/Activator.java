package org.fusesource.ide.camel.model;

import java.net.URL;

import org.fusesource.camel.tooling.util.CamelNamespaces;
import org.fusesource.camel.tooling.util.SchemaFinder;
import org.fusesource.camel.tooling.util.XsdDetails;
import org.fusesource.ide.commons.logging.RiderLogFacade;
import org.fusesource.ide.commons.ui.ImagesActivatorSupport;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;


/**
 * @author lhein
 */
public class Activator extends ImagesActivatorSupport {

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		CamelNamespaces.loadSchemasWith(new SchemaFinder() {

			@Override
			public URL findSchema(XsdDetails xsd) {
				String path = xsd.path();
				URL answer = null;
				Bundle[] bundles = Activator.getDefault().getBundle().getBundleContext().getBundles();
				for (Bundle bundle : bundles) {
					answer = bundle.getResource(path);
					if (answer != null) {
						break;
					}
				}
				//System.out.println("for path: " + path + " xsd " + xsd + " found: " + answer);
				return answer;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static RiderLogFacade getLogger() {
		return RiderLogFacade.getLog(getDefault().getLog());
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
