package org.fusesource.ide.server.karaf.core.internal.runtime;

import org.eclipse.wst.server.core.model.RuntimeDelegate;

/**
 * @author lhein
 */
public class KarafRuntimeDelegate extends RuntimeDelegate implements IKarafRuntimeWorkingCopy {

	/**
	 * empty default constructor
	 */
	public KarafRuntimeDelegate() {
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntime#getKarafInstallDir()
	 */
	public String getKarafInstallDir() {
		return getAttribute(IKarafRuntime.INSTALL_DIR, "");
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntime#getKarafPropertiesFileLocation()
	 */
	public String getKarafPropertiesFileLocation() {
		return getAttribute(IKarafRuntime.PROPERTIES_FILE_LOC, "");
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntime#getKarafVersion()
	 */
	@Override
	public String getKarafVersion() {
		return getAttribute(IKarafRuntime.VERSION, "");
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntimeWorkingCopy#setKarafInstallDir(java.lang.String)
	 */
	public void setKarafInstallDir(String installDir) {
		setAttribute(IKarafRuntime.INSTALL_DIR, installDir);
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntimeWorkingCopy#setKarafPropertiesFileLocation(java.lang.String)
	 */
	public void setKarafPropertiesFileLocation(String propFile) {
		setAttribute(IKarafRuntime.PROPERTIES_FILE_LOC, propFile);
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntimeWorkingCopy#setKarafVersion(java.lang.String)
	 */
	@Override
	public void setKarafVersion(String version) {
		setAttribute(IKarafRuntime.VERSION, version);
	}
}
