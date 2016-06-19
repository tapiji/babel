/*******************************************************************************
 * Copyright (c) 2012 Michael Gasser.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Gasser - initial API and implementation
 ******************************************************************************/
package org.eclipse.babel.tapiji.tools.rbmanager.viewer.filters;

import java.util.List;

import org.eclipse.babel.tapiji.tools.core.ui.utils.EditorUtils;
import org.eclipse.babel.tapiji.tools.core.ui.utils.ResourceUtils;
import org.eclipse.babel.tapiji.tools.core.util.FragmentProjectUtils;
import org.eclipse.babel.tapiji.tools.core.util.RBFileUtils;
import org.eclipse.babel.tapiji.tools.rbmanager.model.VirtualResourceBundle;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ProblematicResourceBundleFilter extends ViewerFilter {

    /**
     * Shows only IContainer and VirtualResourcebundles with all his
     * properties-files, which have RB_Marker.
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof IFile) {
            return true;
        }
        if (element instanceof VirtualResourceBundle) {
            for (IResource f : ((VirtualResourceBundle) element).getFiles()) {
                if (RBFileUtils.hasResourceBundleMarker(f)) {
                    return true;
                }
            }
        }
        if (element instanceof IContainer) {
            try {
                IMarker[] ms = null;
                if ((ms = ((IContainer) element).findMarkers(
                        EditorUtils.RB_MARKER_ID, true,
                        IResource.DEPTH_INFINITE)).length > 0) {
                    return true;
                }

                List<IContainer> fragmentContainer = ResourceUtils
                        .getCorrespondingFolders((IContainer) element,
                                FragmentProjectUtils
                                        .getFragments(((IContainer) element)
                                                .getProject()));

                IMarker[] fragment_ms;
                for (IContainer c : fragmentContainer) {
                    try {
                        if (c.exists()) {
                            fragment_ms = c.findMarkers(
                                    EditorUtils.RB_MARKER_ID, false,
                                    IResource.DEPTH_INFINITE);
                            ms = org.eclipse.babel.tapiji.tools.core.util.EditorUtils
                                    .concatMarkerArray(ms, fragment_ms);
                        }
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
                if (ms.length > 0) {
                    return true;
                }

            } catch (CoreException e) {
            }
        }
        return false;
    }
}
