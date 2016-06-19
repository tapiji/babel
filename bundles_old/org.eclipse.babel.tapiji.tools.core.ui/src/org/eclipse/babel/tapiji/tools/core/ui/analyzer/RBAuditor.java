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
package org.eclipse.babel.tapiji.tools.core.ui.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.babel.tapiji.tools.core.extensions.ILocation;
import org.eclipse.babel.tapiji.tools.core.ui.ResourceBundleManager;
import org.eclipse.babel.tapiji.tools.core.ui.extensions.I18nResourceAuditor;
import org.eclipse.babel.tapiji.tools.core.ui.utils.RBFileUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IMarkerResolution;

public class RBAuditor extends I18nResourceAuditor {

    @Override
    public void audit(IResource resource) {
        if (RBFileUtils.isResourceBundleFile(resource)) {
            ResourceBundleManager.getManager(resource.getProject())
                    .addBundleResource(resource);
        }
    }

    @Override
    public String[] getFileEndings() {
        return new String[] { "properties" };
    }

    @Override
    public List<ILocation> getConstantStringLiterals() {
        return new ArrayList<ILocation>();
    }

    @Override
    public List<ILocation> getBrokenResourceReferences() {
        return new ArrayList<ILocation>();
    }

    @Override
    public List<ILocation> getBrokenBundleReferences() {
        return new ArrayList<ILocation>();
    }

    @Override
    public String getContextId() {
        return "resource_bundle";
    }

    @Override
    public List<IMarkerResolution> getMarkerResolutions(IMarker marker) {
        return null;
    }
    
    @Override
    public void reset() {
        // there is nothing todo
    }

}
