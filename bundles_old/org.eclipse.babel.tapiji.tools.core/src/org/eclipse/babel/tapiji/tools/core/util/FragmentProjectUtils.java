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
package org.eclipse.babel.tapiji.tools.core.util;

import java.util.List;

import org.eclipse.babel.core.util.PDEUtils;
import org.eclipse.core.resources.IProject;

public final class FragmentProjectUtils {

	private FragmentProjectUtils() {

	}

	public static String getPluginId(final IProject project) {
		return PDEUtils.getPluginId(project);
	}

	public static IProject[] lookupFragment(final IProject pluginProject) {
		return PDEUtils.lookupFragment(pluginProject);
	}

	public static boolean isFragment(final IProject pluginProject) {
		return PDEUtils.isFragment(pluginProject);
	}

	public static List<IProject> getFragments(final IProject hostProject) {
		return PDEUtils.getFragments(hostProject);
	}

	public static String getFragmentId(final IProject project,
			final String hostPluginId) {
		return PDEUtils.getFragmentId(project, hostPluginId);
	}

	public static IProject getFragmentHost(final IProject fragment) {
		return PDEUtils.getFragmentHost(fragment);
	}

}
