/*******************************************************************************
 * Copyright (c) 2001, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.babel.build.core.exceptions;

public class FailedDeletionException extends Exception {

    static final long serialVersionUID = 3L;

    public FailedDeletionException() {
    }

    public FailedDeletionException(String arg0) {
        super(arg0);
    }

    public FailedDeletionException(Throwable arg0) {
        super(arg0);
    }

    public FailedDeletionException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
