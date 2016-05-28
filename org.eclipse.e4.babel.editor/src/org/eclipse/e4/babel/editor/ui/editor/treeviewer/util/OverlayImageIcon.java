package org.eclipse.e4.babel.editor.ui.editor.treeviewer.util;


/*******************************************************************************
 * Copyright (c) 2007 Pascal Essiembre.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pascal Essiembre - initial API and implementation
 *    Christian Behon
 ******************************************************************************/


import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;


/**
 * @author Pascal Essiembre
 * @author Christian Behon
 */
public class OverlayImageIcon extends CompositeImageDescriptor {

    /** Constant for overlaying icon in top left corner. */
    public enum Position {
        TOP_LEFT(0),
        TOP_RIGHT(1),
        BOTTOM_LEFT(2),
        BOTTOM_RIGHT(3);
        
        private int id;

        Position(int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
    }

    private final Image baseImage;
    private final Image overlayImage;
    private final Position location;
    private final Point imgSize;

    private OverlayImageIcon(final Image baseImage, final Image overlayImage, final Position location) {
        super();
        this.baseImage = baseImage;
        this.overlayImage = overlayImage;
        this.location = location;
        this.imgSize = new Point(baseImage.getImageData().width, baseImage.getImageData().height);
    }

    /**
     * @see org.eclipse.jface.resource.CompositeImageDescriptor #drawCompositeImage(int, int)
     */
    @Override
    protected void drawCompositeImage(final int width, final int height) {
        drawImage(baseImage.getImageData(), 0, 0);
        final ImageData imageData = overlayImage.getImageData();
        switch (Position.valueOf(location.toString())) {
            case TOP_LEFT:
                drawImage(imageData, 0, 0);
                return;
            case TOP_RIGHT:
                drawImage(imageData, imgSize.x - imageData.width, 0);
                return;
            case BOTTOM_LEFT:
                drawImage(imageData, 0, imgSize.y - imageData.height);
                return;
            case BOTTOM_RIGHT:
                drawImage(imageData, imgSize.x - imageData.width, imgSize.y - imageData.height);
                return;
        }
    }

    /**
     * @see org.eclipse.jface.resource.CompositeImageDescriptor#getSize()
     */
    @Override
    protected Point getSize() {
        return imgSize;
    }

    /**
     * Creates new instance of OverlayImageIcon
     *
     * @param baseImage background image
     * @param overlayImage the image to put on top of background image
     * @param location in which corner to put the icon
     */
    public static OverlayImageIcon create(final Image baseImage, final Image overlayImage, final Position location) {
        return new OverlayImageIcon(baseImage, overlayImage, location);
    }
}
