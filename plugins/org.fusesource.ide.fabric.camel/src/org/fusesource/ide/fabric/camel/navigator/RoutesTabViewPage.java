package org.fusesource.ide.fabric.camel.navigator;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.fusesource.ide.commons.ui.views.TabFolderSupport2;


public class RoutesTabViewPage extends TabFolderSupport2 {
	private final RoutesNode node;

	public RoutesTabViewPage(RoutesNode node) {
		super(node.getClass().getName(), true);
		this.node = node;
	}

	/*
	@Override
	protected void createTabItems() {
		PropertySourceTableView processorsTableView = new PropertySourceTableView(RoutesNode.class.getName());
		List<IPropertySource> propertySourceList = node.getPropertySourceList();
		processorsTableView.setPropertySources(propertySourceList);

		addPage("Routes", processorsTableView);

		propertySourceList = node.getAllProcessorsPropertySourceList();
		ProcessorTabViewPage.addProcessorsTab(this, propertySourceList);

		ProcessorCallView treeView = new ProcessorCallView(node);
		addPage("Profile", treeView);
	}
	 */


	@Override
	protected ITabDescriptor[] getTabDescriptors() {
		final List<IPropertySource> propertySourceList = node.getAllProcessorsPropertySourceList();
		return new ITabDescriptor[] {
				/*
				new PropertiesPageTabDescriptor(node),
				 */
				new ProcessorsPageTabDescriptor("Processors", propertySourceList),
				new ProcessorCallViewTabDescriptor("Profile", node),
		};
	}

}
