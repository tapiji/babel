package org.eclipse.e4.babel.resource;


import org.eclipse.swt.graphics.Image;


public interface BabelResourceProvider {

    public Image loadImage(String path);

    public void dispose();
}
