package org.fusesource.ide.server.karaf.ui.runtime.smx4x;

import java.io.File;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;
import org.fusesource.ide.server.karaf.core.internal.ServiceMixUtils;
import org.fusesource.ide.server.karaf.ui.Messages;
import org.fusesource.ide.server.karaf.ui.runtime.KarafWizardDataModel;
import org.fusesource.ide.server.karaf.ui.runtime.v2x.KarafRuntimeComposite2x;


/**
 * @author lhein
 */
public class ServiceMixRuntimeComposite4x extends KarafRuntimeComposite2x {

	private static final String LIB_SERVICEMIX_VERSION_JAR = String.format("%s%s%s", LIB_FOLDER, SEPARATOR, "servicemix-version.jar");
	
	/**
	 * constructor 
	 * 
	 * @param parent
	 * @param wizardHandle
	 * @param model
	 */
	public ServiceMixRuntimeComposite4x(Composite parent, IWizardHandle wizardHandle, KarafWizardDataModel model) {
		super(parent, wizardHandle, model);
		wizardHandle.setTitle(Messages.ServiceMixRuntimeComposite_wizard_tite);
		wizardHandle.setDescription(Messages.ServiceMixRuntimeComposite_wizard_desc);
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.ui.runtime.AbstractKarafRuntimeComposite#doClassPathEntiresExist(java.lang.String)
	 */
	@Override
	protected boolean doClassPathEntiresExist(String karafInstallDir) {
		File libServiceMixVersionJar = new File(String.format("%s%s%s", karafInstallDir, SEPARATOR, LIB_SERVICEMIX_VERSION_JAR));
		return super.doClassPathEntiresExist(karafInstallDir) && libServiceMixVersionJar.exists();
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.ui.runtime.AbstractKarafRuntimeComposite#validate()
	 */
	@Override
	public boolean validate() {
		valid = super.validate();
		
		if (valid) {
			String dirLocation = txtKarafDir.getText().trim();
			if (dirLocation != null && !"".equals(dirLocation)) { 
				File file = new File(dirLocation);
				if (!file.exists()) {
					wizardHandle.setMessage(Messages.AbstractKarafRuntimeComposite_no_dir,
							IMessageProvider.ERROR);
				} else if (!file.isDirectory()) {
					wizardHandle.setMessage(Messages.AbstractKarafRuntimeComposite_not_a_dir,
							IMessageProvider.ERROR);
				} else{
					File binSMX = new File(dirLocation + SEPARATOR + Messages.AbstractKarafRuntimeComposite_bin_karaf); 
					File binSMXBat = new File(dirLocation + SEPARATOR + Messages.AbstractKarafRuntimeComposite_bin_karaf_bat); 
					if (binSMX.exists() || binSMXBat.exists() ) {
						valid = true;
						wizardHandle.setMessage("", IMessageProvider.NONE); //$NON-NLS-1$
					} else {
						wizardHandle.setMessage(Messages.AbstractKarafRuntimeComposite_invalid_dir, IMessageProvider.ERROR); //$NON-NLS-1$
					}
				}
			} else {
				wizardHandle.setMessage(Messages.AbstractKarafRuntimeComposite_wizard_help_msg, IMessageProvider.NONE); //$NON-NLS-1$
			}	
		}
		
		return valid;
	}
	
	public void handleEvent(Event event) {
		boolean valid = false;
		if (event.type == SWT.FocusIn){
			handleFocusEvent(event);
		} else{
			if (event.widget == txtKarafDir) {
				valid = validate();
				if (valid){
					String installDir = txtKarafDir.getText();
					model.setKarafInstallDir(installDir);
					model.setKarafPropertiesFileLocation(getKarafPropFileLocation(installDir));
					model.setKarafVersion(ServiceMixUtils.getVersion(new File(installDir)));
				}
			}
		}
		
		wizardHandle.update();
	}
}
