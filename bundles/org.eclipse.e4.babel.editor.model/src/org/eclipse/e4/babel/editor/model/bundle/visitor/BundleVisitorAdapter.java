/*
 * Copyright (C) 2003-2014 Pascal Essiembre
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.e4.babel.editor.model.bundle.visitor;


import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;


/**
 * Convenience implementation of <code>IBundleVisitor</code> allowing to
 * override only required methods.
 * 
 * @author Pascal Essiembre
 */
public class BundleVisitorAdapter implements IBundleVisitor {

    /**
     * Constructor.
     */
    public BundleVisitorAdapter() {
        super();
    }

    @Override
    public void visitBundleGroup(BundleGroup group, Object passAlongArgument) {
        // noop 
    }

    @Override
    public void visitBundle(Bundle bundle, Object passAlongArgument) {
        // noop 
    }

    @Override
    public void visitBundleEntry(BundleEntry entry, Object passAlongArgument) {
        // noop 
    }
}
