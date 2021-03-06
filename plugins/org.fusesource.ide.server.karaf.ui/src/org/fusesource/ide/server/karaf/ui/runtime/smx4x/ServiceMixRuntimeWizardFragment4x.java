package org.fusesource.ide.server.karaf.ui.runtime.smx4x;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.server.core.IRuntimeWorkingCopy;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;
import org.fusesource.ide.server.karaf.core.internal.ServiceMixUtils;
import org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntime;
import org.fusesource.ide.server.karaf.ui.runtime.AbstractKarafRuntimeComposite;
import org.fusesource.ide.server.karaf.ui.runtime.KarafWizardDataModel;
import org.fusesource.ide.server.karaf.ui.runtime.v2x.KarafRuntimeWizardFragment2x;


/**
 * @author lhein
 */
public class ServiceMixRuntimeWizardFragment4x extends
		KarafRuntimeWizardFragment2x {
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.server.karaf.ui.runtime.AbstractKarafRuntimeWizardFragment#getRuntimeComposite(org.eclipse.swt.widgets.Composite, org.eclipse.wst.server.ui.wizard.IWizardHandle, org.fusesource.ide.server.karaf.ui.runtime.KarafWizardDataModel)
	 */
	@Override
	protected AbstractKarafRuntimeComposite getRuntimeComposite(
			Composite parent, IWizardHandle handle, KarafWizardDataModel model) {
		return new ServiceMixRuntimeComposite4x(parent, handle, model);
	}
	
	/**
	 * updates the model from runtime.
	 */
	protected void populateModel() {
		IRuntimeWorkingCopy workingCopy = getRuntimeWorkingCopy();
		if (workingCopy != null) {
			// workCopy will be instance of ServerDelegate classs.
			// We need to get the params, so IFuseESBRuntime will be enough.
			IKarafRuntime karafRuntime = (IKarafRuntime) workingCopy
					.loadAdapter(IKarafRuntime.class,
							new NullProgressMonitor());
			if (karafRuntime != null) {
				model.setKarafInstallDir(karafRuntime.getKarafInstallDir());
				model.setKarafVersion(ServiceMixUtils.getVersion(new File(model.getKarafInstallDir())));
			}
		}
	}
}
