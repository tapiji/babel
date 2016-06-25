package org.eclipse.e4.babel.editor.model.tree;


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.bundle.BundleObject;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleChangeAdapter;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;
import org.eclipselabs.e4.tapiji.logger.Log;


public class KeyTree extends BundleObject {

    private static String TAG = KeyTree.class.getSimpleName();
    private final Map<String, KeyTreeItem> keyItemsCache = new TreeMap<String, KeyTreeItem>();
    private final Set<KeyTreeItem> rootKeyItems = new TreeSet<KeyTreeItem>();

    private String selectedKey;
    private KeyTreeUpdater keyTreeUpdater;
    private BundleGroup bundleGroup;

    public KeyTree(final BundleGroup bundleGroup, KeyTreeUpdater keyTreeUpdater) {
        super();
        this.keyTreeUpdater = keyTreeUpdater;
        this.bundleGroup = bundleGroup;
        initBundleGroup(bundleGroup);
        loadKeys();
    }
    
    private void initBundleGroup(final BundleGroup bundleGroup) {
        bundleGroup.addChangeLIstener(new BundleChangeAdapter() {

            @Override
            public <T> void add(BundleEvent<T> event) {
                initBundle((Bundle) event.data());
            }
        });
        bundleGroup.getBundles().values().stream().forEach(bundle->initBundle(bundle));
    }

    protected void initBundle(final Bundle bundle) {
        bundle.addChangeLIstener(new BundleChangeAdapter() {

            @Override
            public <T> void add(BundleEvent<T> event) {
                String key = ((BundleEntry) event.data()).getKey();
                addKey(key);
            }

            @Override
            public <T> void remove(BundleEvent<T> event) {
                String key = ((BundleEntry) event.data()).getKey();
                Collection<BundleEntry> entries = bundleGroup.getBundleEntries(key);
                if (entries.size() == 0) {
                    removeKey(((BundleEntry) event.data()).getKey());
                }
            }

            @Override
            public <T> void modify(BundleEvent<T> event) {
                String key = ((BundleEntry) event.data()).getKey();
                modifyKey(key);
            }
        });
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
        fireAdd(keyItemsCache.get(key));
        Log.d(TAG, "Add key: " + key);
    }

    public void removeKey(String key) {
        Object item = keyItemsCache.get(key);
        keyTreeUpdater.removeKey(this, key);
        fireRemove(item);
        Log.d(TAG, "Remove key: " + key);
    }

    public void modifyKey(String key) {
        Object item = keyItemsCache.get(key);
        fireModify(item);
        Log.d(TAG, "Modify key: " + key);
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
        this.keyItemsCache.clear();
        this.rootKeyItems.clear();
        loadKeys();
    }

    private void loadKeys() {
        bundleGroup.getKeys()
                   .stream()
                   .forEach((key) -> keyTreeUpdater.addKey(this, key));
        fireAdd(this);
    }

    public BundleGroup getBundleGroup() {
        return bundleGroup;
    }
    
    public void dispose() {
        
    }

    @Override
    public String toString() {
        return "KeyTree [keyItemsCache=, rootKeyItems=, selectedKey=" + selectedKey + ", keyTreeUpdater=" + keyTreeUpdater + ", bundleGroup=" + bundleGroup + "]";
    }

	public KeyTreeUpdater getUpdater() {
		return keyTreeUpdater;
	}


}
