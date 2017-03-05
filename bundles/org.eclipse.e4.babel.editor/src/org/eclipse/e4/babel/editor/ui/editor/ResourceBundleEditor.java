package org.eclipse.e4.babel.editor.ui.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.BabelExtensionManager;
import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.preference.APreferencePage;
import org.eclipse.e4.babel.editor.text.BundleTextEditor;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageView;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeView;
import org.eclipse.e4.babel.editor.ui.handler.window.AOpenResourceBundleHandler;
import org.eclipse.e4.babel.logger.Log;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ResourceBundleEditor extends CTabFolder implements ResourceBundleEditorContract.View, SelectionListener {

    public static final String TOPIC_TREE_VIEW_VISIBILITY = "TOPIC_GUI/TREE_VIEW_VISIBILITY";
    private static final String TAG = ResourceBundleEditor.class.getSimpleName();

    // private ResourceChangeListener resourceChangeListener = new
    // ResourceChangeListener();

    @Inject
    private EMenuService menuService;

    @Inject
    private MPart part;

    @Inject
    private IBabelResourceProvider resourceProvider;

    @Inject
    private ESelectionService selectionService;

    @Inject
    private MDirtyable dirty;

    @Inject
    private IEclipseContext context;

    @Inject
    private EModelService modelService;

    // TODO RAP and RCP
    // @Inject
    // private IWorkspace workspace;

    @Inject
    UISynchronize sync;

    @Inject
    private EPartService partService;

    private I18nPageContract.View i18nPage;
    private SashForm sashForm;
    private KeyTreeView keyTreeView;
    private IResourceManager resourceManager;
    private SourceEditor lastEditor;
    private ResourceBundleEditorListener resourceBundleEditorListener = new ResourceBundleEditorListener();
    private List<BundleTextEditor> editors = new ArrayList<>();
    private SourceViewer lastSourceViewer;
    private MWindow window;

    @Inject
    public ResourceBundleEditor(Composite parent) {
	super(parent, SWT.BOTTOM);
    }

    @PostConstruct
    public void onCreate(final Composite parent, final Shell shell, MWindow window, BabelExtensionManager manager) {
	try {
	    partService.addPartListener(resourceBundleEditorListener);
	    this.window = window;
	    Log.d(TAG, "Create ResourceBundleEditor");
	    this.resourceManager = manager.getResourceManager().get();
	    setMinimumCharacters(40);

	    Object object = part.getTransientData().get(AOpenResourceBundleHandler.KEY_FILE_DOCUMENT);

	    if (object instanceof IPropertyResource) {
		IPropertyResource file = (IPropertyResource) object;

		toolbarVisibility();

		// TODO RCP and RAP
		// this.workspace.addResourceChangeListener(resourceChangeListener,
		// IResourceChangeEvent.POST_CHANGE);

		addSelectionListener(this);

		sashForm = new SashForm(this, SWT.SMOOTH);
		keyTreeView = KeyTreeView.create(sashForm, this, context);
		i18nPage = I18nPageView.create(sashForm, this, resourceManager, context);
		sashForm.setWeights(new int[] { 25, 75 });

		createTab(sashForm, "Properties", BabelResourceConstants.IMG_RESOURCE_BUNDLE);

		setSelection(0);
		part.setDescription("Editor f\u00FCr ResourceBundle:");

		this.resourceManager.init(file).whenComplete((result, exception) -> {
		    Log.d(TAG, "Create ResourceBundleEditor");
		    sync.syncExec(() -> {

			createTabs();
			part.setTooltip(resourceManager.getResourceLocation());
			part.setLabel(resourceManager.getDisplayName());
			keyTreeView.getPresenter().createTreeView();
			i18nPage.getPresenter().setChangeListener();
			i18nPage.refreshView();
		    });
		});

	    } else {
		throw new ClassCastException("Can not cast object to IFileDocument");
	    }

	} catch (ClassCastException | CoreException | IOException exception) {
	    MessageDialog.openError(shell, "Cannot open Resource", exception.getMessage());
	    Log.e("onCreate(): ", exception);
	}
	// catch (CoreException exception) {
	// MessageDialog.openError(shell, "Cannot open Resource",
	// exception.getMessage());
	// Log.e("onCreate(Can not initialize resource)", exception);
	// }
    }

    private void toolbarVisibility() {
	MToolBar trimStack = (MToolBar) modelService.find("org.eclipse.e4.babel.editor.toolbar.main", window);
	MPartStack mainStack = (MPartStack) modelService.find("org.eclipse.e4.babel.editor.partstack.editorPartStack", window);
	trimStack.setToBeRendered(mainStack.getChildren().size() > 0);
    }

    /**
     * Change current tab based on locale. If there is no editors associated
     * with current locale, do nothing.
     *
     * @param locale
     *            Locale used to identify the tab to change to
     */
    @Override
    public void setActiveTab(Locale locale) {
	final List<SourceEditor> editors = resourceManager.getSourceEditors();
	for (int i = 0; i < editors.size(); i++) {
	    Locale editorLocale = editors.get(i).getLocale();
	    if (editorLocale != null && editorLocale.equals(locale) || editorLocale == null && locale == null) {
		setSelection(i + 1);
		break;
	    }
	}
    }

    /**
     * Change the visibility of tree view.
     *
     * @param visibility
     *            True hide tree view otherwise show tree view
     */
    @Inject
    @Optional
    private void onTreeViewVisibilityChange(@UIEventTopic(TOPIC_TREE_VIEW_VISIBILITY) final boolean visibility) {
	if (sashForm.getMaximizedControl() == null) {
	    sashForm.setMaximizedControl((I18nPageView) i18nPage);
	} else {
	    sashForm.setMaximizedControl(null);
	}
    }

    @Inject
    @Optional
    public void redrawLayout(@UIEventTopic(APreferencePage.TOPIC_REFRESH_LAYOUT) final String visibility) {
	Log.d(TAG, "INPUT: UPDATE UI" + visibility);
	i18nPage.getPresenter().redrawEditorSize();
	i18nPage.refreshLayout();

	resourceManager.getBundleGroup().getBundles().values().forEach(bundle -> {
	    bundle.setSortKeys(PropertyPreferences.getInstance().isSortKeys());
	});
    }

    private void createTab(final Control control, final String title, String image) {
	final CTabItem tab = new CTabItem(this, SWT.NONE);
	tab.setText(title);
	tab.setImage(resourceProvider.loadImage(image));
	tab.setControl(control);
    }

    @Override
    public IResourceManager getResourceManager() {
	return resourceManager;
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
    public KeyTreeView getKeyTreeView() {
	return keyTreeView;
    }

    @Override
    public void addResource(IPropertyResource fileDocument, Locale locale) {
	resourceManager.addSourceEditor(fileDocument, locale);
	disposeTabs();
	createTabs();
	i18nPage.refreshView();
	setSelection(0);
	updateDirtyState(true);
    }

    private void disposeTabs() {
	Stream.of(getItems()).skip(1).forEach(tab -> tab.dispose());
    }

    private void createTabs() {
	editors.clear();
	resourceManager.getSourceEditors().forEach(editor -> {
	    final BundleTextEditor textEditor = new BundleTextEditor(this, dirty, editor.getDocument());
	    editors.add(textEditor);
	    createTab(textEditor, UIUtils.getDisplayName(editor.getLocale()), BabelResourceConstants.IMG_RESOURCE_PROPERTY);
	});
    }

    // TODO RAP and RCP
    /*
     * private class ResourceChangeListener implements IResourceChangeListener {
     *
     * @Override public void resourceChanged(IResourceChangeEvent event) {
     * resourceManager.reloadProperties();
     * i18nPage.getPresenter().refreshTextBoxes(); } }
     */

    @Override
    public I18nPageContract.View getI18nPage() {
	return i18nPage;
    }

    @Override
    public void updateDirtyState(boolean isDirty) {
	if (!dirty.isDirty()) {
	    dirty.setDirty(isDirty);
	}
    }

    @Override
    public boolean getDirtyState() {
	return dirty.isDirty();
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
	Log.i(TAG, "widgetSelected");
	KeyTree keyTree = resourceManager.getKeyTree();
	int index = getSelectionIndex();

	if (lastEditor != null) {
	    if (lastEditor.getCurrentKey(lastSourceViewer) != null) {
		keyTree.selectKey(lastEditor.getCurrentKey(lastSourceViewer));
	    }
	}

	if (index == 0) {
	    resourceManager.reloadProperties();
	    i18nPage.getPresenter().refreshTextBoxes();
	    lastEditor = null;
	    lastSourceViewer = null;
	    return;
	}

	if (index == getTabList().length - 1) // switched to last page
	    return;

	int editorIndex = index - 1; // adjust because first page is tree page
	if (editorIndex >= 0 && editorIndex < resourceManager.getSourceEditors().size()) {
	    lastSourceViewer = editors.get(editorIndex).getSourceViewer();
	    lastEditor = resourceManager.getSourceEditors().get(editorIndex);
	    if (keyTree.getSelectedKey() != null)
		lastEditor.selectKey(keyTree.getSelectedKey(), lastSourceViewer);
	}
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
	Log.i(TAG, "widgetDefaultSelected" + e);

    }

    @Focus
    public void requestFocus() {
	keyTreeView.setFocus();
    }

    @PreDestroy
    public void preDestroy() {
	Log.d(TAG, "AVAILABLE RESOURCE BUNDLES: " + resourceBundleEditorListener.getAvailableEditorSize());
	if (resourceBundleEditorListener.getAvailableEditorSize() <= 0) {
	    ((MToolBar) modelService.find("org.eclipse.e4.babel.editor.toolbar.main", window)).setToBeRendered(false);
	}
    }

    @Override
    public ESelectionService getSelectionService() {
	return selectionService;
    }

    @Persist
    public void doSave() {
	Log.d(TAG, "DO SAVE");
	KeyTree keyTree = resourceManager.getKeyTree();
	String key = keyTree.getSelectedKey();

	i18nPage.getPresenter().refreshEditorOnChanges();
	resourceManager.save();

	keyTree.setUpdater(keyTree.getKeyTreeUpdater());
	if (key != null) {
	    keyTree.selectKey(key);
	}
	updateDirtyState(false);
    }

    @Override
    public void dispose() {
	i18nPage.dispose();
	List<SourceEditor> sourceEditors = resourceManager.getSourceEditors();
	sourceEditors.forEach(editor -> {
	    editor.getDocument().dispose();
	});
	partService.removePartListener(resourceBundleEditorListener);
	resourceBundleEditorListener.dispose();
	// TODO RAP and RCP
	// workspace.removeResourceChangeListener(resourceChangeListener);
	super.dispose();
    }
}
