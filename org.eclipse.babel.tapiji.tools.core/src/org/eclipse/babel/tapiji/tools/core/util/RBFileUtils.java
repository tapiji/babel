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
package org.eclipse.babel.tapiji.tools.core.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public final class RBFileUtils {

	private RBFileUtils() {

	}

	/**
	 * Checks whether a RB-file has a problem-marker
	 */
	public static boolean hasResourceBundleMarker(final IResource resource) {
		boolean hasRBMarker;
		
		try {
			hasRBMarker = resource.findMarkers(EditorUtils.RB_MARKER_ID, true,
					IResource.DEPTH_INFINITE).length > 0;
		} catch (CoreException e) {
			hasRBMarker = false;
		}
		
		return hasRBMarker;
	}

}
