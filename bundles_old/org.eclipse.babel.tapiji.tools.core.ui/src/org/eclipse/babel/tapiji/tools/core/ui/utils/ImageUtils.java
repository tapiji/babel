/*******************************************************************************
 * Copyright (c) 2007 Pascal Essiembre.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pascal Essiembre - initial API and implementation
 *    Martin Reiterer - extracting image handling from UIUtils
 ******************************************************************************/
package org.eclipse.babel.tapiji.tools.core.ui.utils;

import org.eclipse.babel.tapiji.tools.core.ui.Activator;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public final class ImageUtils {

    /** Name of resource bundle image. */
    public static final String IMAGE_RESOURCE_BUNDLE = "icons/resourcebundle.gif"; //$NON-NLS-1$
    /** Name of properties file image. */
    public static final String IMAGE_PROPERTIES_FILE = "icons/propertiesfile.gif"; //$NON-NLS-1$
    /** Name of properties file entry image */
    public static final String IMAGE_PROPERTIES_FILE_ENTRY = "icons/key.gif";
    /** Name of new properties file image. */
    public static final String IMAGE_NEW_PROPERTIES_FILE = "icons/newpropertiesfile.gif"; //$NON-NLS-1$
    /** Name of hierarchical layout image. */
    public static final String IMAGE_LAYOUT_HIERARCHICAL = "icons/hierarchicalLayout.gif"; //$NON-NLS-1$
    /** Name of flat layout image. */
    public static final String IMAGE_LAYOUT_FLAT = "icons/flatLayout.gif"; //$NON-NLS-1$
    public static final String IMAGE_INCOMPLETE_ENTRIES = "icons/incomplete.gif"; //$NON-NLS-1$
    public static final String IMAGE_EXCLUDED_RESOURCE_ON = "icons/int.gif"; //$NON-NLS-1$
    public static final String IMAGE_EXCLUDED_RESOURCE_OFF = "icons/exclude.png"; //$NON-NLS-1$
    public static final String ICON_RESOURCE = "icons/Resource16_small.png";
    public static final String ICON_RESOURCE_INCOMPLETE = "icons/Resource16_warning_small.png";

    /** Image registry. */
    private static final ImageRegistry imageRegistry = new ImageRegistry();

    /**
     * Constructor.
     */
    private ImageUtils() {
        super();
    }

    /**
     * Gets an image.
     * 
     * @param imageName
     *            image name
     * @return image
     */
    public static Image getImage(String imageName) {
        Image image = imageRegistry.get(imageName);
        if (image == null) {
            image = Activator.getImageDescriptor(imageName).createImage();
            imageRegistry.put(imageName, image);
        }
        return image;
    }
}
