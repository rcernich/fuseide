package org.fusesource.ide.camel.editor.features.custom;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.fusesource.ide.camel.editor.utils.DiagramUtils;


/**
 * @author lhein
 *
 */
public class SetGridVisibilityFeature extends AbstractCustomFeature {

	private static boolean gridVisible = true;
	
	public SetGridVisibilityFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.custom.ICustomFeature#execute(org.eclipse.graphiti.features.context.ICustomContext)
	 */
	@Override
	public void execute(ICustomContext context) {
		gridVisible = !gridVisible;
		DiagramUtils.setGridVisible(gridVisible);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.AbstractFeature#getName()
	 */
	@Override
	public String getName() {
		if (gridVisible) {
			return "Hide Grid";
		} else {
			return "Show Grid";			
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.custom.AbstractCustomFeature#getDescription()
	 */
	@Override
	public String getDescription() {
		if (gridVisible) {
			return "Hides the grid...";
		} else {
			return "Shows the grid...";			
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.custom.AbstractCustomFeature#canExecute(org.eclipse.graphiti.features.context.ICustomContext)
	 */
	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}
}
