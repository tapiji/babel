package org.eclipse.e4.babel.editor.model.tree.filter;


import java.util.Collection;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;


public final class MissingKeyFilter implements ITreeFilter {


    private boolean oneMissingChild = false;
    
    @Override
    public boolean doFilter(KeyTreeItem keyTreeItem) {
        boolean isMissingValue = isItemMissingValue(keyTreeItem);
     

        if (isMissingValue) {
            keyTreeItem.setVisible(false);
        } else {
           
            for (KeyTreeItem childItem : keyTreeItem.getNestedChildren()) {
                if (isItemMissingValue(childItem)) {
                    childItem.setVisible(false);
                    
                } else {
                    oneMissingChild = true;
                    childItem.setVisible(true);
                }
            }

               keyTreeItem.setVisible(false); 
  
        }
return false;
    }


    private boolean isItemMissingValue(KeyTreeItem item) {
        String key = item.getId();
        BundleGroup bundleGroup = item.getKeyTree().getBundleGroup();
        if (bundleGroup.isKey(key)) {
            Collection<BundleEntry> entries = bundleGroup.getBundleEntries(key);
            if (entries.size() != bundleGroup.getSize()) {
                return true;
            }
            for (BundleEntry entry : entries) {
                if (entry == null || entry.getValue().length() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
