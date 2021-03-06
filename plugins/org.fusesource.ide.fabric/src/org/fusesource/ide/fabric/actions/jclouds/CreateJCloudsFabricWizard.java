/*******************************************************************************
 * Copyright (c) 2010 JVM Monitor project. All rights reserved.
 * 
 * This code is distributed under the terms of the Eclipse Public License v1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.fusesource.ide.fabric.actions.jclouds;


import java.net.URI;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.fusesource.fabric.api.CreateJCloudsContainerMetadata;
import org.fusesource.fabric.api.CreateJCloudsContainerOptions;
import org.fusesource.fabric.api.CreationStateListener;
import org.fusesource.fabric.service.jclouds.JcloudsContainerProvider;
import org.fusesource.fabric.service.jclouds.firewall.internal.Ec2FirewallSupport;
import org.fusesource.fabric.service.jclouds.firewall.internal.FirewallManagerFactoryImpl;
import org.fusesource.ide.commons.Viewers;
import org.fusesource.ide.commons.jobs.Jobs;
import org.fusesource.ide.commons.util.Strings;
import org.fusesource.ide.fabric.FabricPlugin;
import org.fusesource.ide.fabric.actions.Messages;
import org.fusesource.ide.fabric.navigator.Fabrics;


/**
 * The wizard for creating agents via jclouds
 */
public class CreateJCloudsFabricWizard extends Wizard {
	private final Fabrics fabrics;
	private final String defaultAgentName;
	private IStructuredSelection selection;
	private CloudDetailsWizardPage page1;
	private CloudFabricDetailsWizardPage page2;

	public CreateJCloudsFabricWizard(Fabrics fabrics, String defaultAgentName) {
		this.fabrics = fabrics;
		this.defaultAgentName = defaultAgentName;
		super.setWindowTitle(Messages.createJCloudsFabricTitle);
	}


	public String getDefaultAgentName() {
		return defaultAgentName;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}


	public CloudDetailsWizardPage getPage1() {
		return page1;
	}


	public CloudFabricDetailsWizardPage getPage2() {
		return page2;
	}


	@Override
	public void addPages() {
		page1 = new CloudDetailsWizardPage();
		addPage(page1);


		page2 = new CloudFabricDetailsWizardPage(this);
		addPage(page2);
	}


	@Override
	public boolean performFinish() {
		final CloudFabricDetailsForm form = getPage2().getForm();
		form.saveSettings();
		
		Jobs.schedule(new Job("Create container") {

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				final String fabricName = form.getFabricName();
				try {
					String agentName = form.getAgentName();
					CreateJCloudsContainerOptions args = form.getCreateCloudArguments();
					args.setName(agentName);
					args.setEnsembleServer(true);
					args.adminAccess(true);
					//args.setResolver(ZkDefs.PUBLIC_IP);
					String proxyUri = Fabrics.DEFAULT_MAVEN_PROXY_URI;
					if (form instanceof CloudFabricDetailsForm) {
						CloudFabricDetailsForm fabricForm = form;
						proxyUri = fabricForm.getProxyUri();
					}
					if (!Strings.isBlank(proxyUri)) {
						args.setProxyUri(new URI(proxyUri));
					}
					System.out.println("============ proxy URI: " + args.getProxyUri());
					args.setCreationStateListener(new CreationStateListener() {

						@Override
						public void onStateChange(String message) {
							monitor.subTask(message);
						}
					});

					System.out.println("Create cloud fabric: " + fabricName + " container: " + agentName);


					JcloudsContainerProvider provider = new JcloudsContainerProvider();
					FirewallManagerFactoryImpl firewallManagerFactory = new FirewallManagerFactoryImpl();
					String providerName = args.getProviderName();
					System.out.println("Creating Jclouds provider type: " + providerName);
					if ("aws-ec2".equals(providerName)) {
						firewallManagerFactory.bind(new Ec2FirewallSupport());
					}
					provider.setFirewallManagerFactory(firewallManagerFactory);

					Set<CreateJCloudsContainerMetadata> metadatas = provider.create(args);

					final StringBuilder urisBuilder = new StringBuilder();

					for (CreateJCloudsContainerMetadata metadata : metadatas) {
						Throwable failure = metadata.getFailure();
						if (failure != null) {
							return new Status(Status.ERROR, FabricPlugin.PLUGIN_ID, "Failed to create Fabric: " + fabricName, failure);
						}
						for(String address:metadata.getPublicAddresses()) {
							urisBuilder.append(address).append(",");
						}
					}
					Viewers.async(new Runnable() {

						@Override
						public void run() {
							String uris = urisBuilder.toString();
							if(uris.endsWith(",")) {
								uris = uris.substring(0, uris.length() - 1);
							}
							System.out.println("Creating fabric with uris: " + uris);
							fabrics.addFabric(fabricName, uris);
						}});
					return Status.OK_STATUS;
				} catch (Throwable e) {
					return new Status(Status.ERROR, FabricPlugin.PLUGIN_ID, "Failed to create Fabric: " + fabricName, e);
				}
			}
		});
		return true;
	}

	public CloudDetails getSelectedCloud() {
		return page1.getSelectedCloud();
	}


}