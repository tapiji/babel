package org.eclipse.e4.babel.editor.ui.editor.tree.provider;


import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.treeviewer.util.OverlayImageIcon.Position;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


public final class KeyTreeLabelProvider extends LabelProvider{
   
    
    private static final int KEY_DEFAULT = 1 << 1;
    private static final int KEY_COMMENTED = 1 << 2;
    private static final int KEY_NOT = 1 << 3;
    private static final int WARNING = 1 << 4;
    private static final int WARNING_GREY = 1 << 5;

    private static ImageRegistry imageRegistry = new ImageRegistry();


    @Override
    public String getText(Object element) {
        return ((KeyTreeItem) element).getName(); 
    }

    

    public Image getImage(Object element) {
        KeyTreeItem treeItem = ((KeyTreeItem) element);
        
        int iconFlags = 0;

        // Figure out background icon
        /*if (treeItem.getKeyTree().getBundleGroup().isKey(treeItem.getId())) {
            IsCommentedVisitor commentedVisitor = new IsCommentedVisitor();
            treeItem.accept(commentedVisitor, null);
            if (commentedVisitor.hasOneCommented()) {
                iconFlags += KEY_COMMENTED;
            } else {
                iconFlags += KEY_DEFAULT;
            }
        } else {
            iconFlags += KEY_NOT;
        }
        
        // Maybe add warning icon        
        if (RBEPreferences.getReportMissingValues()) {
            IsMissingValueVisitor misValVisitor = new IsMissingValueVisitor();
            treeItem.accept(misValVisitor, null);
            if (misValVisitor.isMissingValue()) {
                iconFlags += WARNING;
            } else if (misValVisitor.isMissingChildValueOnly()) {
                iconFlags += WARNING_GREY;
            }
        }*/

        return generateImage(iconFlags);
    }
    
    
    
    private Image generateImage(int iconFlags) {
        Image image = imageRegistry.get("" + iconFlags);
        if (image == null) {
            // Figure background image
            if ((iconFlags & KEY_COMMENTED) != 0) {
                image = getRegistryImage("keyCommented.gif");
            } else if ((iconFlags & KEY_NOT) != 0) {
                image = getRegistryImage("keyCommented.gif");
            } else {
                image = getRegistryImage("key.gif");
            }
            
            // Add warning icon
            if ((iconFlags & WARNING) != 0) {
                image = overlayImage(image, "warning.gif",
                        Position.BOTTOM_RIGHT, iconFlags);
            } else if ((iconFlags & WARNING_GREY) != 0) {
                image = overlayImage(image, "warningGrey.gif",
                        Position.BOTTOM_RIGHT, iconFlags);
            }
        }
        return image;
    }
    
    
    private Image overlayImage(Image baseImage, String imageName, Position location, int iconFlags) {
        /*
         * To obtain a unique key, we assume here that the baseImage and
         * location are always the same for each imageName and keyFlags
         * combination.
         */
        String imageKey = imageName + iconFlags;
        Image image = imageRegistry.get(imageKey);
        if (image == null) {
            //image = new OverlayImageIcon(baseImage, getRegistryImage(imageName), location).createImage();
            imageRegistry.put(imageKey, image);
        }
        return image;
    }

    private Image getRegistryImage(String imageName) {
        Image image = imageRegistry.get(imageName);
        if (image == null) {
           // image = RBEPlugin.getImageDescriptor(imageName).createImage();
            imageRegistry.put(imageName, image);
        }
        return image;
    }
}
