package org.eclipse.e4.babel.editor.ui.editor;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.babel.i18n.Messages;
import org.eclipse.e4.babel.editor.ui.editor.composite.BundleTextEditorComposite;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.resource.ITapijiResourceProvider;


public class ResourceBundleEditor extends CTabFolder {


    private static final String TAG = ResourceBundleEditor.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    private TreeViewer treeViewer;

    @Inject
    private EMenuService menuService;

    @Inject
    private MPart part;

    @Inject
    private ITapijiResourceProvider resourceProvider;

    @Inject
    private MApplication application;

    @Inject
    private EModelService modelService;

    @Inject
    private EPartService partService;

    @Translation
    Messages translation;

    @Inject
    public ResourceBundleEditor(Composite parent) {
        super(parent, SWT.BOTTOM);
    }

    @PostConstruct
    public void createControl(final Composite parent, final Shell shell, MWindow window) {
        Log.d(TAG, "treeViewerPart");

        setMinimumCharacters(40);
        IEditorInput file = (IEditorInput) part.getTransientData().get("FILE");


        BundleTextEditorComposite bund = new BundleTextEditorComposite(this);
        CTabItem tab = new CTabItem(this, SWT.NONE);
        tab.setControl(bund);

        //     stack.Log.d(TAG, "Stack: " + stack.toString());


        //     Log.d(TAG, "WAHHH" + file.toString());

        // this.tabFolder = createTabFolder(parent);


        //        final SashForm sashForm = new SashForm(tabFolder, SWT.SMOOTH);
        //
        //        TreeViewerComposite.create(sashForm, menuService);
        //        I18nComposite.create(sashForm, resourceProvider);
        //
        //        sashForm.setWeights(new int[] {25, 75});
        //createFirstTab(sashForm);

        //createTabs(partService);

        setSelection(0);
    }
}
