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
import org.eclipse.e4.core.di.extensions.Service;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
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
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public class ResourceBundleEditor extends CTabFolder implements ResourceBundleEditorContract.View, SelectionListener {

    private static final String PART_STACK_ID = "org.eclipse.e4.babel.editor.partstack.editorPartStack";
    public static final String TOPIC_TREE_VIEW_VISIBILITY = "TOPIC_GUI/TREE_VIEW_VISIBILITY";
    private static final String TAG = ResourceBundleEditor.class.getSimpleName();

    private EMenuService menuService;
    private EPartService partService;
    private I18nPageContract.View i18nPage;
    private IResourceManager resourceManager;
    private MApplication application;
    private IEclipseContext context;
    private EModelService modelService;
    private MDirtyable dirtyable;
    private ESelectionService selectionService;
    private IBabelResourceProvider resourceProvider;
    private MWindow window;

    private SourceViewer lastSourceViewer;
    private SourceEditor lastEditor;
    private SashForm sashForm;
    private KeyTreeView keyTreeView;
    private UISynchronize uiSync;
    private List<BundleTextEditor> editors = new ArrayList<>();

    @Inject
    public ResourceBundleEditor(Composite parent, ESelectionService selectionService, MApplication application, IEclipseContext context, EModelService modelService,
	    MDirtyable dirtyable, EMenuService menuService, IBabelResourceProvider resourceProvider, EPartService partService, UISynchronize uiSync, MWindow window) {
	super(parent, SWT.BOTTOM);
	this.application = application;
	this.context = context;
	this.modelService = modelService;
	this.dirtyable = dirtyable;
	this.selectionService = selectionService;
	this.menuService = menuService;
	this.resourceProvider = resourceProvider;
	this.partService = partService;
	this.uiSync = uiSync;
	this.window = window;

    }

    

    @PostConstruct
    public void onCreate(final Composite parent, final Shell shell, @Service IResourceManager  manager, MPart part) {
	Log.d(TAG, "Create ResourceBundleEditor");
	try {
	    this.resourceManager = manager;
	    setMinimumCharacters(40);

	    Object object = part.getTransientData().get(AOpenResourceBundleHandler.KEY_FILE_DOCUMENT);

	    if (object instanceof IPropertyResource) {
		IPropertyResource file = (IPropertyResource) object;
		closeIfAlreadyOpen(file);
		toolbarVisibility(window);

		addSelectionListener(this);

		sashForm = new SashForm(this, SWT.SMOOTH);
		keyTreeView = KeyTreeView.create(sashForm, this, context);
		i18nPage = I18nPageView.create(sashForm, this, resourceManager, context);
		sashForm.setWeights(new int[] { 25, 75 });

		createTab(sashForm, "Properties", BabelResourceConstants.IMG_RESOURCE_BUNDLE);

		setCursorWaitVisibility(true);
		this.resourceManager.init(file).whenComplete((result, exception) -> {
		    uiSync.syncExec(() -> {
			part.setDescription("Editor f\u00FCr ResourceBundle:");
			part.setTooltip(resourceManager.getResourceLocation());
			part.setLabel(resourceManager.getDisplayName());

			createTabs();

			keyTreeView.getPresenter().updateKeyTree();
			i18nPage.getPresenter().setChangeListener();
			i18nPage.refreshView();
			setCursorWaitVisibility(false);
			setSelection(0);
			notifyListeners(SWT.Selection, new Event());
		    });
		});

	    } else {
		throw new ClassCastException("Can not cast object to IFileDocument");
	    }

	} catch (ClassCastException | CoreException | IOException exception) {
	    MessageDialog.openError(shell, "Cannot open Resource", exception.getMessage());
	    Log.e("onCreate(): ", exception);
	}
    }

    public void setCursorWaitVisibility(boolean visibility) {
	uiSync.asyncExec(() -> {
	    if (visibility) {
		getParent().setCursor(new Cursor(getParent().getDisplay(), SWT.CURSOR_WAIT));
	    } else {
		getParent().setCursor(new Cursor(getParent().getDisplay(), SWT.CURSOR_ARROW));
	    }
	});
    }

    private void toolbarVisibility(MWindow window) {
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
	    final BundleTextEditor textEditor = new BundleTextEditor(this, dirtyable, editor.getResource());
	    editors.add(textEditor);
	    createTab(textEditor, UIUtils.getDisplayName(editor.getLocale()), BabelResourceConstants.IMG_RESOURCE_PROPERTY);
	});
    }

    @Override
    public I18nPageContract.View getI18nPage() {
	return i18nPage;
    }

    @Override
    public void updateDirtyState(boolean isDirty) {
	if (!dirtyable.isDirty()) {
	    dirtyable.setDirty(isDirty);
	}
    }

    @Override
    public boolean getDirtyState() {
	return dirtyable.isDirty();
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

    @Override
    public ESelectionService getSelectionService() {
	return selectionService;
    }

    private void closeIfAlreadyOpen(IPropertyResource resource) {
	MPartStack mainStack = (MPartStack) modelService.find(PART_STACK_ID, application);
	mainStack.getChildren().stream().filter(MPart.class::isInstance).map(MPart.class::cast).forEach(part -> {
	    if (part.getObject() instanceof ResourceBundleEditorContract.View) {
		if (((ResourceBundleEditorContract.View) part.getObject()).getResourceManager().containsResource(resource)) {
		    partService.hidePart(part, true);
		}
	    }
	});
    }

    @Persist
    public void persist() {
	Log.d(TAG, "persist() called");
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

    @PreDestroy
    public void preDestroy() {
	Log.d(TAG, "preDestroy() called");
	i18nPage.dispose();
	List<SourceEditor> sourceEditors = resourceManager.getSourceEditors();
	sourceEditors.forEach(editor -> {
	    editor.getResource().dispose();
	});
    }
}
