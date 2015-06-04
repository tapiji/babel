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

    @Inject
    private ITapijiResourceProvider resourceProvider;

    @PostConstruct
    public void createControl(final Composite parent, final Shell shell, final EPartService partService) {
        Log.d(TAG, "treeViewerPart");

        this.tabFolder = createTabFolder(parent);

        final SashForm sashForm = new SashForm(tabFolder, SWT.SMOOTH);

        TreeViewerComposite.create(sashForm, menuService);
        I18nComposite.create(sashForm, resourceProvider);

        sashForm.setWeights(new int[] {25, 75});
        createFirstTab(sashForm);

        createTabs();

        tabFolder.setSelection(0);
    }

    private void createFirstTab(final SashForm sashForm) {
        final CTabItem firstTabItem = createItem(0, sashForm);
        firstTabItem.setText("Eigenschaften");
        firstTabItem.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_RESOURCE_BUNDLE));
    }

    private void createTabs() {
        for (int i = 1; i < 4; i++) {
            final CTabItem tabItem = createItem(i, new BundleTextEditorComposite(tabFolder));
            tabItem.setText("Chinesisch");
            tabItem.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_RESOURCE_PROPERTY));
        }
    }

    private CTabFolder createTabFolder(final Composite parent) {
        final CTabFolder tabFolder = new CTabFolder(parent, SWT.BOTTOM | SWT.FLAT);
        return tabFolder;
    }

    private CTabItem createItem(final int index, final Control control) {
        final CTabItem item = new CTabItem(tabFolder, SWT.NONE, index);
        item.setControl(control);
        return item;
    }
}
