package org.fusesource.ide.camel.editor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.fusesource.ide.camel.editor.editor.RiderDesignEditor;
import org.fusesource.ide.camel.editor.editor.RiderEditor;
import org.fusesource.ide.camel.model.generated.Route;


/**
 * @author lhein
 */
public class AddNewRouteHandler extends AbstractHandler {

	/**
	 * 
	 */
	public AddNewRouteHandler() {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		if (window != null) {
			IWorkbenchPage activePage = window.getActivePage();
			if (activePage != null) {
				IEditorPart activeEditor = activePage.getActiveEditor();
				if (activeEditor instanceof RiderEditor) {
					RiderEditor editor = (RiderEditor) activeEditor;
					RiderDesignEditor designEditor = editor.getDesignEditor();
					if (designEditor != null) {
						Route route = new Route();
						designEditor.getModel().addChild(route);
						designEditor.setSelectedRoute(route);
					}
				}
			}
		}
		return null;
	}
}
