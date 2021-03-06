package org.fusesource.ide.server.karaf.ui.runtime;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.server.core.IRuntimeWorkingCopy;
import org.eclipse.wst.server.core.TaskModel;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;
import org.eclipse.wst.server.ui.wizard.WizardFragment;
import org.fusesource.ide.server.karaf.core.internal.KarafUtils;
import org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntime;
import org.fusesource.ide.server.karaf.core.internal.runtime.IKarafRuntimeWorkingCopy;


public abstract class  AbstractKarafRuntimeWizardFragment extends WizardFragment {

	protected AbstractKarafRuntimeComposite composite = null;
	protected final KarafWizardDataModel model = new KarafWizardDataModel();

	public AbstractKarafRuntimeWizardFragment() {
	}

	@Override
	public Composite createComposite(Composite parent, IWizardHandle handle) {
		getTaskModel().putObject(KarafWizardDataModel.KARAF_MODEL, model);
		populateModel();
		composite = getRuntimeComposite(parent,handle,model);
		composite.createContents();
		return composite;
	}

	@Override
	public boolean hasComposite() {
		return true;
	}
		
	
	@Override
	public void performFinish(IProgressMonitor monitor) throws CoreException {
		super.performFinish(monitor);
		if (composite != null)
			composite.performFinish();
		updateRuntime();
	}

	
	protected IRuntimeWorkingCopy getRuntimeWorkingCopy() {
		return (IRuntimeWorkingCopy) getTaskModel().getObject(
				TaskModel.TASK_RUNTIME);
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
				model.setKarafVersion(KarafUtils.getVersion(new File(model.getKarafInstallDir())));
			}
		}
	}
	
	/**
	 * This updates the runtime.
	 */
	private void updateRuntime() {
		IRuntimeWorkingCopy workingCopy = getRuntimeWorkingCopy();
		if (workingCopy != null) {
			// workCopy will be instance of ServerDelegate classs.
			// We need to get the params, so IKarafRuntime will be enough.
			IKarafRuntimeWorkingCopy karafRuntimeWorkingCopy = (IKarafRuntimeWorkingCopy) workingCopy
					.loadAdapter(IKarafRuntimeWorkingCopy.class, new NullProgressMonitor());
			if (karafRuntimeWorkingCopy != null) {
				String installDir = model.getKarafInstallDir();
				IPath path = new Path(installDir);
				workingCopy.setLocation(path);
// commented out the naming of the runtime as it seems to break server to runtime links
//				workingCopy.setName(path.toFile().getName());
				karafRuntimeWorkingCopy.setKarafInstallDir(path.removeTrailingSeparator().toOSString());
				karafRuntimeWorkingCopy.setKarafPropertiesFileLocation(model.getKarafPropertiesFileLocation());
				karafRuntimeWorkingCopy.setKarafVersion(model.getKarafVersion());
			}
		}
		try {
			workingCopy.save(true, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	protected abstract AbstractKarafRuntimeComposite getRuntimeComposite(Composite parent, IWizardHandle handle, KarafWizardDataModel model);

	@Override
	public boolean isComplete() {
		return composite != null ? composite.isValid() : true;
	}
}
