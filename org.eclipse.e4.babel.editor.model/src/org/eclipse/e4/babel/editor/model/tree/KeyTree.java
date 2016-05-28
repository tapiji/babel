package org.eclipse.e4.babel.editor.model.tree;


import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;


public class KeyTree {

    private final Map<String, KeyTreeItem> keyItemsCache = new TreeMap<String, KeyTreeItem>();
    private final Set<KeyTreeItem> rootKeyItems = new TreeSet<KeyTreeItem>();

    private String selectedKey;
    private KeyTreeUpdater keyTreeUpdater;
    private BundleGroup bundleGroup;

    public KeyTree(final BundleGroup bundleGroup, KeyTreeUpdater keyTreeUpdater) {
        super();
        this.keyTreeUpdater = keyTreeUpdater;
        this.bundleGroup = bundleGroup;
        loadKeys();
    }

    public KeyTreeItem getKeyTreeItem(String key) {
        return keyItemsCache.get(key);
    }

    public String getSelectedKey() {
        return selectedKey;
    }

    public Map<String, KeyTreeItem> getKeyItemsCache() {
        return keyItemsCache;
    }

    public Set<KeyTreeItem> getRootKeyItems() {
        return rootKeyItems;
    }

    public void addKey(String key) {
        keyTreeUpdater.addKey(this, key);
        System.out.println("Add key: " + key);
    }

    public void removeKey(String key) {
        keyTreeUpdater.removeKey(this, key);
        System.out.println("Remove key: " + key);
    }

    public void modifyKey(String key) {
        System.out.println("Modify key: " + key);
    }

    public void selectKey(String key) {
        if (key == null) return;

        Object item = keyItemsCache.get(key);
        if ((selectedKey == null) || (!selectedKey.equals(key))) {
            selectedKey = key;
        }
    }

    public void setUpdater(KeyTreeUpdater keyTreeUpdater) {
        this.keyTreeUpdater = keyTreeUpdater;
        keyItemsCache.clear();
        rootKeyItems.clear();
        loadKeys();
    }

    private void loadKeys() {
        bundleGroup.getKeys()
                   .stream()
                   .forEach((key) -> keyTreeUpdater.addKey(this, key));
    }

    public BundleGroup getBundleGroup() {
        return bundleGroup;
    }

    @Override
    public String toString() {
        return "KeyTree [keyItemsCache=, rootKeyItems=, selectedKey=" + selectedKey + ", keyTreeUpdater=" + keyTreeUpdater + ", bundleGroup=" + bundleGroup + "]";
    }


}
