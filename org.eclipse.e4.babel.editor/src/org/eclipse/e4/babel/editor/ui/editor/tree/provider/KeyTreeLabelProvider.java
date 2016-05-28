package org.eclipse.e4.babel.editor.ui.editor.tree.provider;


import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.model.tree.visitor.IsCommentedVisitor;
import org.eclipse.e4.babel.editor.model.tree.visitor.IsMissingValueVisitor;
import org.eclipse.e4.babel.editor.ui.editor.treeviewer.util.OverlayImageIcon;
import org.eclipse.e4.babel.editor.ui.editor.treeviewer.util.OverlayImageIcon.Position;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;


public final class KeyTreeLabelProvider extends LabelProvider implements IFontProvider, IColorProvider {

    private static final int KEY_DEFAULT = 1 << 1;
    private static final int KEY_COMMENTED = 1 << 2;
    private static final int KEY_NOT = 1 << 3;
    private static final int WARNING = 1 << 4;
    private static final int WARNING_GREY = 1 << 5;

    private Font keyFont = UIUtils.createFont(SWT.NORMAL);
    private Font groupFontKey = UIUtils.createFont(SWT.NORMAL);
    private Font groupFontNoKey = UIUtils.createFont(SWT.NORMAL);

    private Color colorCommented = UIUtils.getSystemColor(SWT.COLOR_GRAY);

    private IBabelResourceProvider resourceProvider;
    //    private static ImageRegistry imageRegistry = new ImageRegistry();


    public KeyTreeLabelProvider(IBabelResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }


    @Override
    public String getText(Object element) {
        return ((KeyTreeItem) element).getName();
    }


    public Image getImage(Object element) {
        KeyTreeItem treeItem = ((KeyTreeItem) element);
        int iconFlags = 0;

        // Figure out background icon
        if (treeItem.getKeyTree()
                    .getBundleGroup()
                    .isKey(treeItem.getId())) {
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
        if (PropertyPreferences.getInstance()
                               .isReportMissingValues()) {
            IsMissingValueVisitor misValVisitor = new IsMissingValueVisitor();
            treeItem.accept(misValVisitor, null);
            if (misValVisitor.isMissingValue()) {
                iconFlags += WARNING;
            } else if (misValVisitor.isMissingChildValueOnly()) {
                iconFlags += WARNING_GREY;
            }
        }
        return generateImage(iconFlags);
    }


    @Override
    public Font getFont(Object element) {
        KeyTreeItem item = (KeyTreeItem) element;
        if (item.getChildren()
                .size() > 0) {
            if (item.getKeyTree()
                    .getBundleGroup()
                    .isKey(item.getId())) {
                return groupFontKey;
            }
            return groupFontNoKey;
        }
        return keyFont;
    }


    private Image generateImage(int iconFlags) {
        Image image;
        if ((iconFlags & KEY_COMMENTED) != 0 || (iconFlags & KEY_NOT) != 0) {
            image = resourceProvider.loadImage(BabelResourceConstants.IMG_KEY_COMMENTED);
        } else {
            image = resourceProvider.loadImage(BabelResourceConstants.IMG_KEY_DEFAULT);
        }
        return addOverlayImage(image, iconFlags);
    }

    private Image addOverlayImage(Image image, int iconFlags) {
        if ((iconFlags & WARNING) != 0) {
            return overlayImage(image, resourceProvider.loadImage(BabelResourceConstants.IMG_OVERLAY_WARNING), Position.BOTTOM_RIGHT, iconFlags);
        } else if ((iconFlags & WARNING_GREY) != 0) {
            return overlayImage(image, resourceProvider.loadImage(BabelResourceConstants.IMG_OVERLAY_WARNING_GREY), Position.BOTTOM_RIGHT, iconFlags);
        }
        return image;
    }

    private Image overlayImage(Image baseImage, Image imageName, Position location, int iconFlags) {
        return OverlayImageIcon.create(baseImage, imageName, location)
                               .createImage();
    }

    @Override
    public void dispose() {
        groupFontKey.dispose();
        groupFontNoKey.dispose();
        keyFont.dispose();
    }


    @Override
    public Color getForeground(Object element) {
        KeyTreeItem treeItem = (KeyTreeItem) element;

        IsCommentedVisitor commentedVisitor = new IsCommentedVisitor();
        treeItem.accept(commentedVisitor, null);
        if (commentedVisitor.hasOneCommented()) {
            return colorCommented;
        }
        return null;
    }


    @Override
    public Color getBackground(Object element) {
        return null;
    };


}
