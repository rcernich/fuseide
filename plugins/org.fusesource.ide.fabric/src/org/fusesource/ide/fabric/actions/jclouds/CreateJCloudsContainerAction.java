package org.fusesource.ide.fabric.actions.jclouds;

import org.fusesource.ide.commons.ui.Wizards;
import org.fusesource.ide.commons.ui.actions.ActionSupport;
import org.fusesource.ide.fabric.FabricPlugin;
import org.fusesource.ide.fabric.actions.Messages;
import org.fusesource.ide.fabric.navigator.ContainerNode;
import org.fusesource.ide.fabric.navigator.Fabric;
import org.fusesource.ide.fabric.navigator.ProfileNode;
import org.fusesource.ide.fabric.navigator.VersionNode;


public class CreateJCloudsContainerAction extends ActionSupport {
	public static final boolean createLocalAgents = true;

	private VersionNode versionNode;
	private ContainerNode agentNode;
	private ProfileNode profileNode;
	private Fabric fabric;

	public CreateJCloudsContainerAction(Fabric fabric) {
		super(Messages.createJCloudsAgentMenuLabel, Messages.createJCloudsAgentToolTip, FabricPlugin.getDefault().getImageDescriptor("new_cloud_container.png"));
		this.fabric = fabric;
	}

	public CreateJCloudsContainerAction(VersionNode versionNode, ContainerNode agentNode, ProfileNode selectedProfile) {
		this(null);
		this.versionNode = versionNode;
		this.agentNode = agentNode;
		this.profileNode = selectedProfile;
	}

	@Override
	public void run() {
		showCreateAgentDialog();
	}

	public ProfileNode getProfileNode() {
		return profileNode;
	}

	public void setProfileNode(ProfileNode profileNode) {
		this.profileNode = profileNode;
	}

	protected void showCreateAgentDialog() {
		if (versionNode == null && fabric != null) {
			versionNode = fabric.getDefaultVersionNode();
		}
		CreateJCloudsContainerWizard wizard = new CreateJCloudsContainerWizard(versionNode, agentNode, versionNode.getFabric().getNewAgentName(), profileNode);

		/*
		IWorkbenchPartSite site = Workbenches.getActiveWorkbenchPartSite();
		wizard.init(Workbenches.getActiveWorkbench(), Selections.getStructuredSelection(site));
		 */

		Wizards.openWizardDialog(wizard);
	}



}
