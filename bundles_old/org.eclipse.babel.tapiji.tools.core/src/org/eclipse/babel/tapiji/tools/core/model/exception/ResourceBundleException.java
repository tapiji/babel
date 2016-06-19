/*******************************************************************************
 * Copyright (c) 2012 Martin Reiterer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Martin Reiterer - initial API and implementation
 ******************************************************************************/
package org.eclipse.babel.tapiji.tools.core.model.exception;

public class ResourceBundleException extends Exception {

    private static final long serialVersionUID = -2039182473628481126L;

    public ResourceBundleException(final String msg) {
        super(msg);
    }

    public ResourceBundleException() {
        super();
    }

}
