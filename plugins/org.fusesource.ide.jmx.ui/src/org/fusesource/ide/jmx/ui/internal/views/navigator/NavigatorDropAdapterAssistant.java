package org.fusesource.ide.jmx.ui.internal.views.navigator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class NavigatorDropAdapterAssistant extends CommonDropAdapterAssistant {

	public NavigatorDropAdapterAssistant() {
	}

	@Override
	public boolean isSupportedType(TransferData transferType) {
		return true;
	}

	@Override
	public IStatus validateDrop(Object target, int operation,
			TransferData transferType) {
		return null;
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter dropAdapter,
			DropTargetEvent event, Object target) {
		return null;
	}

	@Override
	public IStatus validatePluginTransferDrop(
			IStructuredSelection selection, Object target) {
		return super.validatePluginTransferDrop(selection, target);
	}

	@Override
	public IStatus handlePluginTransferDrop(
			IStructuredSelection selection, Object target) {
		return super.handlePluginTransferDrop(selection, target);
	}


	@Override
	public void setCommonDropAdapter(CommonDropAdapter dropAdapter) {
		super.setCommonDropAdapter(dropAdapter);

		Transfer[] supportedDropTransfers = dropAdapter.getSupportedDropTransfers();
		for (Transfer transfer : supportedDropTransfers) {
		}
	}
	
	

}
