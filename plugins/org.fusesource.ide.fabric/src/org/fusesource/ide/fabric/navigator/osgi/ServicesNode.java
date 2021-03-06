package org.fusesource.ide.fabric.navigator.osgi;

import java.util.ArrayList;
import java.util.List;

import javax.management.openmbean.TabularData;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.fusesource.ide.commons.tree.Node;
import org.fusesource.ide.commons.tree.RefreshableCollectionNode;
import org.fusesource.ide.commons.ui.ImageProvider;
import org.fusesource.ide.commons.ui.propsrc.PropertySourceTableSheetPage;
import org.fusesource.ide.fabric.FabricPlugin;


public class ServicesNode extends RefreshableCollectionNode implements ImageProvider {
	private final OsgiFacade facade;

	public ServicesNode(Node parent, OsgiFacade facade) {
		super(parent);
		this.facade = facade;
	}

	@Override
	public String toString() {
		return "Services";
	}


	@Override
	public Image getImage() {
		return FabricPlugin.getDefault().getImage("container.png");
	}

	@Override
	protected void loadChildren() {
	}


	public OsgiFacade getFacade() {
		return facade;
	}

	@Override
	protected PropertySourceTableSheetPage createPropertySourceTableSheetPage() {
		return new ServicesTableSheetPage(this);
	}


	@Override
	public List<IPropertySource> getPropertySourceList() {
		List<IPropertySource> answer = new ArrayList<IPropertySource>();
		try {
			final TabularData tabularData = facade.listServices();
			return TabularDataHelper.toPropertySources(tabularData);
		} catch (Exception e) {
			FabricPlugin.getLogger().error("Failed to fetch bundle state: " + e, e);
		}
		return answer;
	}


}
