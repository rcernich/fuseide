package org.fusesource.ide.deployment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.fusesource.ide.commons.ui.Selections;
import org.fusesource.ide.commons.ui.Workbenches;


/**
 * This hack is a work around from being able to access views which are part of perspectives which are not visible
 * e.g. if you are on the Java perspective and want to get the JMX Explorer which may only be on the Fuse Integration perspective.
 * 
 * The usual workbench active view stuff only shows things in the current perspective.
 *
 */
public class DeployViews {
	private static Map<String,IViewPart> viewMap =new ConcurrentHashMap<String,IViewPart>();
	private static ISelectionListener selectionListener;
	private static IProject activeProject;

	public static void registerView(String id, IViewPart view) {
		viewMap.put(id, view);

		IWorkbench workbench = Workbenches.getActiveWorkbench();
		if (workbench != null) {
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			if (window != null) {
				if (selectionListener == null) {
					selectionListener = new ISelectionListener() {

						@Override
						public void selectionChanged(IWorkbenchPart part, ISelection selection) {
							IProject project = selectionToProject(selection);
							if (project != null) {
								activeProject = project;
							}
						}
					};
					window.getSelectionService().addSelectionListener(selectionListener);
				}
			}
		}
	}

	public static IViewPart findView(String id) {
		IViewPart answer = Workbenches.findView(id);
		if (answer == null) {
			answer = viewMap.get(id);
		}
		return answer;
	}

	public static IProject getActiveProject() {
		return activeProject;
	}

	public static IProject selectionToProject(ISelection selection) {
		Object element = Selections.getFirstSelection(selection);
		IProject project = null;
		if (element instanceof IResource) {
			project = ((IResource)element).getProject();
		} else if (element instanceof PackageFragmentRootContainer) {
			IJavaProject jProject =
					((PackageFragmentRootContainer)element).getJavaProject();
			project = jProject.getProject();
		} else if (element instanceof IJavaElement) {
			IJavaProject jProject= ((IJavaElement)element).getJavaProject();
			project = jProject.getProject();
		}
		/*
		else if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			if (sourcePart instanceof JavaEditor) {
				IJavaElement javaElement = SelectionConverter.resolveEnclosingElement(sourcePart, selection);
				project = javaElement.getJavaProject().getProject();
			}
		}
		 */
		return project;
	}

}
