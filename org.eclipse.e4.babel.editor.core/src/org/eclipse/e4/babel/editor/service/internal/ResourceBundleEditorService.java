package org.eclipse.e4.babel.editor.service.internal;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.eclipse.e4.babel.editor.model.IResourceBundleEditorService;
import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.model.updater.GroupedKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class ResourceBundleEditorService implements IResourceBundleEditorService {

    private static final String TAG = ResourceBundleEditorService.class.getSimpleName();
    private KeyTree keyTree;


    public ResourceBundleEditorService() {
        init();
    }

    public void init() {
        KeyTreeUpdater updater = new GroupedKeyTreeUpdater(".");
        BundleGroup bundleGroupe = BundleGroup.create();
        Bundle bundle = Bundle.create();
        BundleEntry entry = BundleEntry.create("blavla.dsd");
        bundle.addBundleEntry(entry);
        entry = BundleEntry.create("chris");
        bundle.addBundleEntry(entry);
        entry = BundleEntry.create("chris", "wahh", "comment", true);
        bundle.addBundleEntry(entry);
        bundleGroupe.addBundle(new Locale("de", "DE"), bundle);
        keyTree = new KeyTree(bundleGroupe, updater);
    }

    @Override
    public void addNewKey(String newKey) {
        Log.d(TAG, "addNewKey " + newKey);
        BundleGroup bundleGroup = keyTree.getBundleGroup();
        if (!bundleGroup.containsKey(newKey)) {
            bundleGroup.addBundleEntryKey(newKey);
        }
    }

    @Override
    public void removeKey(final KeyTreeItem keyTreeItem, String key) {
        Log.d(TAG, "removeKey " + key);
        final List<KeyTreeItem> items = new ArrayList<>();
        items.add(keyTreeItem);
        items.addAll(keyTreeItem.getNestedChildren());
        items.stream().forEach((item)->{
            keyTree.getBundleGroup().removeBundleEntryKey(item.getId());
        });
    }

    @Override
    public void renameKey(final KeyTreeItem keyTreeItem, final String newKey) {
        Log.d(TAG, "renameKey " + newKey);
        final List<KeyTreeItem> items = new ArrayList<>();
        items.add(keyTreeItem);
        items.addAll(keyTreeItem.getNestedChildren());
        items.stream()
             .forEach((item) -> {
                 String oldItemKey = item.getId();
                 if (oldItemKey.startsWith(keyTreeItem.getId())) {
                     String newItemKey = newKey + oldItemKey.substring(keyTreeItem.getId().length());
                     keyTree.getBundleGroup().renameBundleEntryKey(oldItemKey, newItemKey);
                 }
             });
    }

    @Override
    public KeyTree getKeyTree() {
        return keyTree;
    }
}
