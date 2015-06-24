package org.eclipse.e4.babel.resource.internal;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


public class ResourceLoader implements IBabelResourceProvider {

    private static final Map<String, Image> IMAGES = new HashMap<String, Image>();

    @Override
    public Image loadImage(final String path) {
        Image img = IMAGES.get(path);
        if (null == img) {
            final Bundle bundle = FrameworkUtil.getBundle(ResourceLoader.class);
            final URL url = FileLocator.find(bundle, new Path(path), null);
            final ImageDescriptor imageDescr = ImageDescriptor.createFromURL(url);
            img = imageDescr.createImage();
        }
        return img;
    }

    @Override
    public void dispose() {
        for (Image image : IMAGES.values()) {
            image.dispose();
        }
    }
}
