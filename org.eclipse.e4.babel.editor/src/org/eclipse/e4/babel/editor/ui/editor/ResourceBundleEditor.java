package org.eclipse.e4.babel.editor.ui.editor;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.babel.i18n.Messages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.model.IResourceBundleEditorService;
import org.eclipse.e4.babel.editor.preference.APreferencePage;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageView;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageeditor.I18nPageEditorView;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeView;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
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


public class ResourceBundleEditor extends CTabFolder implements ResourceBundleEditorContract.View {
    
    public static final String TOPIC_TREE_VIEW_VISIBILITY = "TOPIC_GUI/TREE_VIEW_VISIBILITY";
    private static final String TAG = ResourceBundleEditor.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    @Inject
    private EMenuService menuService;

    @Inject
    private MPart part;

    @Inject
    private IBabelResourceProvider resourceProvider;

    @Inject
    private ECommandService commandService;

    @Inject
    private EHandlerService handlerService;
    
    @Inject
    private IResourceBundleEditorService editor;
    
    @Inject
    private ESelectionService selectionService;

    @Translation
    Messages translation;
    private I18nPageView i18nPage;
    private SashForm sashForm;

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
        IEditorInput file = (IEditorInput) part.getTransientData()
                                               .get("FILE");

        sashForm = new SashForm(this, SWT.SMOOTH);
        
        KeyTreeView view = KeyTreeView.create(sashForm, (ResourceBundleEditorContract.View) this);
        

        i18nPage = I18nPageView.create(sashForm, resourceProvider);
        sashForm.setWeights(new int[] {25, 75});
        createTab(sashForm, "Properties", BabelResourceConstants.IMG_RESOURCE_BUNDLE);

        for (int i = 0; i < 10; i++) {
            createTab(new I18nPageEditorView(this), "TAB" + i, BabelResourceConstants.IMG_RESOURCE_PROPERTY);
        }

        setSelection(0);
    }

    @Inject
    @Optional
    private void onTreeViewVisibilityChange(@UIEventTopic(TOPIC_TREE_VIEW_VISIBILITY) final boolean visibility) {
        if (sashForm.getMaximizedControl() == null) {
            sashForm.setMaximizedControl(i18nPage);
        } else {
            sashForm.setMaximizedControl(null);
        }
    }

    @Inject
    @Optional
    public void redrawLayout(@UIEventTopic(APreferencePage.TOPIC_REFRESH_LAYOUT) final String visibility) {
        Log.d(TAG, "INPUT: UPDATE UI" + visibility);
        i18nPage.redrawEditorSize();
        i18nPage.refreshLayout();
        System.out.println("New user: " + PropertyPreferences.getInstance().isEditorTreeExpanded());
    }

    private void createTab(final Control control, final String title, String image) {
        final CTabItem tab = new CTabItem(this, SWT.NONE);
        tab.setText(title);
        tab.setImage(resourceProvider.loadImage(image));
        tab.setControl(control);
    }

    @Override
    public ECommandService getCommandService() {
        return commandService;
    }

    @Override
    public EHandlerService getHandlerService() {
        return handlerService;
    }

    @Override
    public EMenuService getMenuService() {
        return menuService;
    }

    @Override
    public IBabelResourceProvider getResourceProvider() {
        return resourceProvider;
    }
    
    @Override
    public IResourceBundleEditorService getEditorService() {
        return editor;
    }

    @Override
    public ESelectionService getSelectionService() {
        return selectionService;
    }
}
