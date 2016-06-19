package org.eclipse.e4.babel.resource;


import org.eclipse.swt.graphics.Image;


public interface IBabelResourceProvider {

    public Image loadImage(String path);

    public void dispose();
}
