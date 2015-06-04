package org.eclipse.e4.babel.editor.ui.editor;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.babel.editor.ui.editor.composite.BundleTextEditorComposite;
import org.eclipse.e4.babel.editor.ui.editor.composite.I18nComposite;
import org.eclipse.e4.babel.editor.ui.editor.composite.TreeViewerComposite;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.resource.ITapijiResourceProvider;
import org.eclipselabs.e4.tapiji.resource.TapijiResourceConstants;


public class ResourceBundleEditor {


    private static final String TAG = ResourceBundleEditor.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    private TreeViewer treeViewer;

    @Inject
    private EMenuService menuService;

    @Inject
    private MPart part;

    private CTabFolder tabFolder;

    @PostConstruct
    public void createControl(Composite parent, Shell shell, EPartService partService, ITapijiResourceProvider resourceProvider) {
        Log.d(TAG, "treeViewerPart");

        tabFolder = createTabFolder(parent);


        SashForm sashForm = new SashForm(tabFolder, SWT.NONE);

        TreeViewerComposite.create(sashForm);
        I18nComposite.create(sashForm, resourceProvider);

        sashForm.setWeights(new int[] {25, 75});


        CTabItem firstTabItem = createItem(0, sashForm);
        firstTabItem.setText("Eigenschaften");
        firstTabItem.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_RESOURCE_BUNDLE));

        for (int i = 1; i < 4; i++) {
            CTabItem tabItem = createItem(i, new BundleTextEditorComposite(tabFolder));
            tabItem.setText("Chinesisch");
            tabItem.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_RESOURCE_PROPERTY));
        }
        tabFolder.setSelection(0);
    }

    private CTabFolder createTabFolder(Composite parent) {
        final CTabFolder tabFolder = new CTabFolder(parent, SWT.BOTTOM | SWT.FLAT);
        return tabFolder;
    }

    private CTabItem createItem(int index, Control control) {
        CTabItem item = new CTabItem(tabFolder, SWT.NONE, index);
        item.setControl(control);
        return item;
    }
}
