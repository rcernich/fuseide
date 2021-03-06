package org.fusesource.ide.server.karaf.ui.runtime.v2x;

import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;
import org.fusesource.ide.server.karaf.ui.runtime.AbstractKarafRuntimeComposite;
import org.fusesource.ide.server.karaf.ui.runtime.KarafWizardDataModel;


/**
 * @author lhein
 */
public class KarafRuntimeComposite2x extends AbstractKarafRuntimeComposite {

	protected static final String CONF_FOLDER = "etc";
	protected static final String CONF_FILE_NAME = "org.apache.karaf.shell.cfg";
	public static final String CONF_FILE = String.format("%s%s%s", CONF_FOLDER, SEPARATOR, CONF_FILE_NAME);
	protected static final String LIB_FOLDER = "lib";
	protected static final String LIB_BIN_FOLDER = String.format("%s%s%s", LIB_FOLDER, SEPARATOR, "bin");
	protected static final String LIB_KARAF_JAR = String.format("%s%s%s", LIB_FOLDER, SEPARATOR, "karaf.jar");
	protected static final String LIB_KARAF_JAAS_JAR = String.format("%s%s%s", LIB_FOLDER, SEPARATOR, "karaf-jaas-boot.jar");
	protected static final String LIB_KARAF_CLIENT_JAR = String.format("%s%s%s", LIB_BIN_FOLDER, SEPARATOR, "karaf-client.jar");
	
	/**
	 * constructor 
	 * 
	 * @param parent
	 * @param wizardHandle
	 * @param model
	 */
	public KarafRuntimeComposite2x(Composite parent, IWizardHandle wizardHandle, KarafWizardDataModel model) {
		super(parent, wizardHandle, model);
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.ui.runtime.AbstractKarafRuntimeComposite#doClassPathEntiresExist(java.lang.String)
	 */
	@Override
	protected boolean doClassPathEntiresExist(String karafInstallDir) {
		File libKarafJar = new File(String.format("%s%s%s", karafInstallDir, SEPARATOR, LIB_KARAF_JAR));
		File libKarafjassJar = new File(String.format("%s%s%s", karafInstallDir, SEPARATOR, LIB_KARAF_JAAS_JAR));
		File libKarafClientJar = new File(String.format("%s%s%s", karafInstallDir, SEPARATOR, LIB_KARAF_CLIENT_JAR));
		File confDir = new File(String.format("%s%s%s", karafInstallDir, SEPARATOR, CONF_FOLDER));
		return libKarafClientJar.exists() && libKarafJar.exists() && libKarafjassJar.exists() && confDir.exists();
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.ui.runtime.AbstractKarafRuntimeComposite#getSmxPropFileLocation(java.lang.String)
	 */
	@Override
	protected String getKarafPropFileLocation(String karafInstallDir) {
		return String.format("%s%s%s", karafInstallDir, SEPARATOR, CONF_FILE); 
	}
}
