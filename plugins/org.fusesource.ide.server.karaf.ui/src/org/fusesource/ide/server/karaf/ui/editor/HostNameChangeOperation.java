package org.fusesource.ide.server.karaf.ui.editor;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.fusesource.ide.server.karaf.core.internal.server.IServerConfigurationWorkingCopy;


/**
 * @author lhein
 */
public class HostNameChangeOperation extends AbstractOperation {
	
	private final IServerConfigurationWorkingCopy copy;
	private final String newHostName;
	private final String oldHostName;
	
	public HostNameChangeOperation(IServerConfigurationWorkingCopy copy, String newHostName, String label) {
		super(label);
		this.copy = copy;
		oldHostName = copy.getPassword();
		this.newHostName = newHostName;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		copy.setHostName(newHostName);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		copy.setHostName(oldHostName);
		return Status.OK_STATUS;
	}
}
