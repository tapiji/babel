package org.eclipse.e4.babel.editor.ui.editor;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.babel.i18n.Messages;
import org.eclipse.core.resources.IFile;
import org.eclipse.e4.babel.editor.ui.editor.i18n.I18nPageEditor;
import org.eclipse.e4.babel.editor.ui.editor.treeviewer.TreeViewerComposite;
import org.eclipse.e4.babel.editor.ui.editor.i18n.I18nPage;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.di.annotations.Optional;
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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipselabs.e4.tapiji.logger.Log;


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
    private IBabelResourceProvider resourceProvider;

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

    @Inject
    @Optional
    public void setPartInput(@Named("input") Object input) {
        IFile file = ((IFileEditorInput) input).getFile();
        Log.d(TAG, "INPUT: " + file.getProject());
    }

    @PostConstruct
    public void createControl(final Composite parent, final Shell shell, MWindow window) {
        Log.d(TAG, "treeViewerPart");

        setMinimumCharacters(40);
        IEditorInput file = (IEditorInput) part.getTransientData().get("FILE");

        final SashForm sashForm = new SashForm(this, SWT.SMOOTH);
        TreeViewerComposite tree = TreeViewerComposite.create(sashForm, menuService, resourceProvider);
        I18nPage.create(sashForm, resourceProvider);
        sashForm.setWeights(new int[] {25, 75});
        createTab(sashForm, "Eigenschaften");

        for (int i = 0; i < 10; i++) {
            createTab(new I18nPageEditor(this), "TAB" + i);
        }

        setSelection(0);
    }

    private void createTab(final Control control, final String title) {
        CTabItem tab = new CTabItem(this, SWT.NONE);
        tab.setText("asdasd");
        //tab.setImage(resourceProvider.loadImage(""));
        tab.setControl(control);
    }
}
