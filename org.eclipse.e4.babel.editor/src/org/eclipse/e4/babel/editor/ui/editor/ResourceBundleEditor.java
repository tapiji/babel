package org.eclipse.e4.babel.editor.ui.editor;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.babel.editor.ui.editor.composite.BundleEntryComposite;
import org.eclipse.e4.babel.editor.ui.editor.composite.BundleTextEditorComposite;
import org.eclipse.e4.babel.editor.ui.editor.composite.TreeViewerComposite;
import org.eclipse.e4.tools.services.IResourcePool;
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


public class ResourceBundleEditor {


    private static final String TAG = ResourceBundleEditor.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    private TreeViewer treeViewer;

    @Inject
    private EMenuService menuService;

    @Inject
    private MPart part;

    @Inject
    IResourcePool resourcePool;
    private CTabFolder tabFolder;

    @PostConstruct
    public void createControl(Composite parent, Shell shell, EPartService partService) {
        Log.d(TAG, "treeViewerPart");

        tabFolder = createTabFolder(parent);

        SashForm sashForm = new SashForm(tabFolder, SWT.NONE);

        TreeViewerComposite.create(sashForm);
        BundleEntryComposite.create(sashForm);

        sashForm.setWeights(new int[] {25, 75});


        createItem(0, sashForm);


        CTabItem item = createItem(0, sashForm);
        item.setText("sddada");
        tabFolder.setSelection(0);
        for (int i = 1; i < 4; i++) {
            CTabItem items = createItem(i, new BundleTextEditorComposite(tabFolder));
            items.setText("dsadasda");
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
