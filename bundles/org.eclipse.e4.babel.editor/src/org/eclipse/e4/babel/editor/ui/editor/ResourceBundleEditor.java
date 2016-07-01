package org.eclipse.e4.babel.editor.ui.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.e4.babel.core.BabelExtensionManager;
import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.preference.APreferencePage;
import org.eclipse.e4.babel.editor.text.PropertiesTextEditor;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageView;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeView;
import org.eclipse.e4.babel.editor.ui.handler.window.OpenResourceBundleHandler;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IFileEditorInput;
import org.eclipselabs.e4.tapiji.logger.Log;

public class ResourceBundleEditor extends CTabFolder implements ResourceBundleEditorContract.View {

    public static final String TOPIC_TREE_VIEW_VISIBILITY = "TOPIC_GUI/TREE_VIEW_VISIBILITY";
    private static final String TAG = ResourceBundleEditor.class.getSimpleName();

    private List<IPath> paths = new ArrayList<IPath>();
    private ResourceChangeListener resourceChangeListener = new ResourceChangeListener();
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
    private ESelectionService selectionService;

    @Inject
    private MDirtyable dirty;

    private I18nPageContract.View i18nPage;
    private SashForm sashForm;

    private KeyTreeView keyTreeView;
    private IResourceManager resourceManager;

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
    public void createControl(final Composite parent, final Shell shell, MWindow window, BabelExtensionManager manager) {
	Log.d(TAG, "Create ResourceBundleEditor");
	this.resourceManager = manager.getResourceManager().get();
	setMinimumCharacters(40);

	IFile file = (IFile) part.getTransientData().get(OpenResourceBundleHandler.KEY_FILE);
	try {
	    resourceManager.init(file);
	} catch (CoreException e) {
	    e.printStackTrace();
	}

	ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE);

	Log.d(TAG, "KEYTREE: " + resourceManager.getKeyTree().toString());

	sashForm = new SashForm(this, SWT.SMOOTH);
	keyTreeView = KeyTreeView.create(sashForm, this);
	i18nPage = I18nPageView.create(sashForm, this, resourceProvider, resourceManager);
	sashForm.setWeights(new int[] { 25, 75 });

	createTab(sashForm, "Properties", BabelResourceConstants.IMG_RESOURCE_BUNDLE);

	resourceManager.getSourceEditors().stream().forEach(editor -> {
	    final PropertiesTextEditor textEditor = new PropertiesTextEditor(this, editor.getDocument());
	    createTab(textEditor, UIUtils.getDisplayName(editor.getLocale()), BabelResourceConstants.IMG_RESOURCE_PROPERTY);

	});
	setSelection(0);
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

    /**
     * Refresh layout
     * 
     * @param visibility
     */
    @Inject
    @Optional
    public void redrawLayout(@UIEventTopic(APreferencePage.TOPIC_REFRESH_LAYOUT) final String visibility) {
	Log.d(TAG, "INPUT: UPDATE UI" + visibility);
	i18nPage.getPresenter().redrawEditorSize();
	i18nPage.refreshLayout();
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
    public ESelectionService getSelectionService() {
	return selectionService;
    }

    @Override
    public KeyTreeView getKeyTreeView() {
	return keyTreeView;
    }

    @Override
    public void addResource(IFile resource, Locale locale) {
	SourceEditor editor = resourceManager.addSourceEditor(resource, locale);
	final PropertiesTextEditor textEditor = new PropertiesTextEditor(this, editor.getDocument());
	createTab(textEditor, UIUtils.getDisplayName(editor.getLocale()), BabelResourceConstants.IMG_RESOURCE_PROPERTY);
	i18nPage.refreshView();
	setSelection(0);
	dirty.setDirty(true);
    }

    @Persist
    public void doSave() {
	Log.d(TAG, "DO SAVE");
	KeyTree keyTree = resourceManager.getKeyTree();
	String key = keyTree.getSelectedKey();

	i18nPage.getPresenter().refreshEditorOnChanges();
	resourceManager.save();

	keyTree.setUpdater(keyTree.getUpdater());
	if (key != null) {
	    keyTree.selectKey(key);
	}
	dirty.setDirty(false);
    }

    private class ResourceChangeListener implements IResourceChangeListener {
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
	    boolean deltaFound = false;
	    for (IPath path : paths) {
		IResourceDelta delta = event.getDelta().findMember(path);
		deltaFound |= delta != null;
	    }
	    if (deltaFound) {
		resourceManager.reloadProperties();
		i18nPage.getPresenter().refreshTextBoxes();
	    }
	}
    }

    @Override
    public void dispose() {
	i18nPage.dispose();
	List<SourceEditor> sourceEditors = resourceManager.getSourceEditors();
	sourceEditors.forEach(editor -> {
	    editor.getDocument().dispose();
	});
	ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
	super.dispose();
    }

    @Override
    public void updateDirtyState(boolean isDirty) {
	if (!dirty.isDirty()) {
	    dirty.setDirty(isDirty);
	}
    }
}
