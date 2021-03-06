package org.fusesource.ide.camel.editor.commands;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.fusesource.ide.camel.editor.Activator;
import org.fusesource.ide.camel.editor.editor.CamelModelLoader;
import org.fusesource.ide.camel.editor.editor.RiderDesignEditor;
import org.fusesource.ide.camel.model.RouteSupport;


public class ImportCamelContextElementsCommand extends RecordingCommand {

	private final RiderDesignEditor designEditor;
	private TransactionalEditingDomain editingDomain;
	private Resource createdResource;
	private Diagram diagram;
	private IFeatureProvider featureProvider;

	public ImportCamelContextElementsCommand(RiderDesignEditor designEditor, TransactionalEditingDomain editingDomain, Diagram diagram) {
		super(editingDomain);
		this.diagram = diagram;
		this.designEditor = designEditor;
		this.editingDomain = editingDomain;
	}

	@Override
	protected void doExecute() {
		// lets use the route ID in the URI so we can switch between route diagrams without caching issues
		RouteSupport selectedRoute = designEditor.getSelectedRoute();
		/*
		String id = selectedRoute.getId();
		if (id == null || id.length() == 0) {
			id = "#" + selectedRoute.hashCode();
		}
		URI uri = URI.createPlatformResourceURI(designEditor.getCamelContextFile().getFullPath().toString() + "_" + id, true);
		 */

		URI uri = URI.createPlatformResourceURI(designEditor.getCamelContextURI(), true);

		createdResource = editingDomain.getResourceSet().createResource(uri);
		createdResource.getContents().add(diagram);

		// the following method doesn't seem to get the EditingDomain setup as well
		IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram, "org.fusesource.ide.camel.editor.dtp.id");
		/*
		IDiagramTypeProvider dtp2 = GraphitiInternal.getEmfService().getDTPForDiagram(getDiagram());
		System.out.println("dtp:  " + dtp.getDiagramEditor().getEditingDomain());
		System.out.println("dtp2: " + dtp2.getDiagramEditor().getEditingDomain());
		 */

		featureProvider = dtp.getFeatureProvider();
		designEditor.setFeatureProvider(featureProvider);

		CamelModelLoader bpmnFileReader = new CamelModelLoader(diagram, featureProvider);
		System.out.println("Loading diagram: " + diagram + " with route: " + selectedRoute + " # " + System.identityHashCode(selectedRoute));
		try {
			bpmnFileReader.loadModel(selectedRoute);
		} catch (RuntimeException e) {
			Activator.getLogger().error("Failed to load model: " + e, e);
			//throw e;
		}
	}

	public IFeatureProvider getFeatureProvider() {
		return featureProvider;
	}

	/**
	 * @return the createdResource
	 */
	public Resource getCreatedResource() {
		return createdResource;
	}

	public Diagram getDiagram() {
		return diagram;
	}
}
