/*******************************************************************************
 * Copyright (c) 2010 JVM Monitor project. All rights reserved.
 * 
 * This code is distributed under the terms of the Eclipse Public License v1.0
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.fusesource.ide.fabric.navigator;




import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.fusesource.fabric.api.Profile;
import org.fusesource.ide.commons.ui.ICanValidate;


/**
 * The form for adding or editing {@link Profile}
 */
public class ProfilesForm extends ProfilesFormSupport {
	private final ContainerNode node;
	public ProfilesForm(ICanValidate validator, ContainerNode node) {
		super(validator);
		this.node = node;
	}

	@Override
	public void createTextFields(Composite parent) {
		this.featuresList = null;
		Profile[] profiles = node.getContainer().getProfiles();
		for (final Profile profile : profiles) {
			String sectionTitle = profile.getId();

			Composite inner = createSectionComposite(sectionTitle, new GridData(GridData.FILL_BOTH));
			
			createProfileForm(profile, inner);
		}
	}

}