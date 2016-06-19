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
package org.eclipse.babel.tapiji.tools.core.model;

public interface IResourceDescriptor {

    void setProjectName(String projName);

    void setRelativePath(String relPath);

    void setAbsolutePath(String absPath);

    void setBundleId(String bundleId);

    String getProjectName();

    String getRelativePath();

    String getAbsolutePath();

    String getBundleId();

}
