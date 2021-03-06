/*******************************************************************************
 * Copyright (c) 2007 Jeff Mesnil
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Benjamin Walstrum (issue #24)
 *******************************************************************************/

package org.fusesource.ide.jmx.ui.extensions;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

public interface IAttributeControlFactory {

    Control createControl(Composite parent, FormToolkit toolkit, 
    		boolean writable, String type, Object value, 
    		IWritableAttributeHandler handler);
	
}
