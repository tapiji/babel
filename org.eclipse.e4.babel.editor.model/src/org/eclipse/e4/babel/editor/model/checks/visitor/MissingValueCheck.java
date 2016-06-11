/*******************************************************************************
 * Copyright (c) 2007 Pascal Essiembre.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pascal Essiembre - initial API and implementation
 ******************************************************************************/
package org.eclipse.e4.babel.editor.model.checks.visitor;

import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleVisitorAdapter;

/**
 * Visitor for finding if a key has at least one corresponding bundle entry with
 * a missing value.
 * 
 * @author Pascal Essiembre (pascal@essiembre.com)
 */
public class MissingValueCheck extends BundleVisitorAdapter {

    /** The singleton */
    public static MissingValueCheck MISSING_KEY = new MissingValueCheck();

    /**
     * Constructor.
     */
    private MissingValueCheck() {
        super();
    }

    @Override
    public void visitBundleEntry(BundleEntry entry, Object passAlongArgument) {
        // TODO Auto-generated method stub
        super.visitBundleEntry(entry, passAlongArgument);
    }
    
    
    /**
     * @see org.eclipse.babel.core.message.internal.checks.IMessageCheck#checkKey(org.eclipse.babel.core.message.internal.MessagesBundleGroup,
     *      org.eclipse.babel.core.message.internal.Message)
     
    public boolean checkKey(IMessagesBundleGroup messagesBundleGroup,
            IMessage message) {
        if (message == null || message.getValue() == null
                || message.getValue().length() == 0) {
            return true;
        }
        return false;
    }*/
}
