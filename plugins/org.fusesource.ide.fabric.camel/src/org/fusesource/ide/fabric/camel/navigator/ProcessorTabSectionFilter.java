/******************************************************************************* 
 * Copyright (c) 2013 Red Hat, Inc. 
 *  All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *
 ******************************************************************************/
package org.fusesource.ide.fabric.camel.navigator;

import org.eclipse.jface.viewers.IFilter;

public class ProcessorTabSectionFilter implements IFilter {

    @Override
    public boolean select(Object toTest) {
        return toTest instanceof ProcessorNodeSupport;
    }

}
