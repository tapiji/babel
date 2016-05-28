package org.eclipse.e4.babel.editor.ui.editor.tree;


import java.util.Locale;
import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.updater.GroupedKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.custom.SashForm;


public class KeyTreePage {

    private KeyTreeContract.View view;
    private KeyTreeContract.Presenter presenter;

    private KeyTreePage(SashForm sashForm, EMenuService menuService, IBabelResourceProvider resourceProvider) {


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


        KeyTree keyTree = new KeyTree(bundleGroupe,updater);
        
        view = KeyTreeView.create(sashForm, menuService, resourceProvider, keyTree);
        

       
    }

    public KeyTreeContract.View getView() {
        return view;
    }

    public KeyTreeContract.Presenter getPresenter() {
        return presenter;
    }

    public static KeyTreePage create(SashForm sashForm, EMenuService menuService, IBabelResourceProvider resourceProvider) {
        return new KeyTreePage(sashForm, menuService, resourceProvider);
    }
}
