package org.eclipse.e4.babel.editor.service.internal;

import java.util.Locale;
import org.eclipse.e4.babel.editor.model.IResourceBundleEditorService;
import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.updater.GroupedKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;

public class ResourceBundleEditorService implements IResourceBundleEditorService{

    
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
        
        entry = BundleEntry.create("chris", "wahh", "comment",true);
        bundle.addBundleEntry(entry);
        
        bundleGroupe.addBundle(new Locale("de", "DE"), bundle);


        keyTree = new KeyTree(bundleGroupe,updater);
    }
    
    @Override
    public void addNewKey(String key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeKey(String key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void renameKey(String key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public KeyTree getKeyTree() {
        return keyTree;
    }

}
