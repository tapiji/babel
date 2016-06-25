package org.eclipse.e4.babel.editor.ui.editor;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.BabelExtensionManager;
import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.model.bundle.listener.IBundleChangeListener;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.preference.APreferencePage;
import org.eclipse.e4.babel.editor.text.PropertiesTextEditor;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageView;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeView;
import org.eclipse.e4.babel.editor.ui.handler.window.OpenResourceBundleHandler;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
	private ESelectionService selectionService;

	private I18nPageView i18nPage;
	private SashForm sashForm;

	LocalBehaviour local = new LocalBehaviour();

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
	public void createControl(final Composite parent, final Shell shell, MWindow window,
			BabelExtensionManager manager) {
		Log.d(TAG, "Create ResourceBundleEditor");
		this.resourceManager = manager.getResourceManager().get();
		setMinimumCharacters(40);

		IFile file = (IFile) part.getTransientData().get(OpenResourceBundleHandler.KEY_FILE);
		try {
			resourceManager.init(file);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		Log.d(TAG, "KEYTREE: " + resourceManager.getKeyTree().toString());

		sashForm = new SashForm(this, SWT.SMOOTH);

		keyTreeView = KeyTreeView.create(sashForm, (ResourceBundleEditorContract.View) this);
		keyTreeView.getTreeViewer().addSelectionChangedListener(local);
		keyTreeView.getKeyTree().addChangeLIstener(local);

		i18nPage = I18nPageView.create(sashForm,  (ResourceBundleEditorContract.View) this ,resourceProvider, resourceManager);
		
		sashForm.setWeights(new int[] { 25, 75 });
		
		createTab(sashForm, "Properties", BabelResourceConstants.IMG_RESOURCE_BUNDLE);

		resourceManager.getSourceEditors().stream().forEach(editor -> {
			final PropertiesTextEditor textEditor = new PropertiesTextEditor(this, editor.getDocument());
			createTab(textEditor, UIUtils.getDisplayName(editor.getLocale()),
					BabelResourceConstants.IMG_RESOURCE_PROPERTY);

		});
		setSelection(0);
	}

	/**
	 * Change current tab based on locale. If there is no editors associated
     * with current locale, do nothing.
	 * 
	 * @param locale Locale used to identify the tab to change to
	 */
	@Override
	public void setActiveTab(Locale locale) {
		final List<SourceEditor> editors = resourceManager.getSourceEditors();
		for (int i = 0; i < editors.size(); i++) {
			Locale editorLocale = editors.get(i).getLocale();
			if (editorLocale != null && editorLocale.equals(locale) || editorLocale == null && locale == null) {
				setSelection(i+1);
				break;
			}
		}
	}

	/**
	 * Change the visibility of tree view.
	 * 
	 * @param visibility True hide tree view otherwise show tree view
	 */
	@Inject
	@Optional
	private void onTreeViewVisibilityChange(@UIEventTopic(TOPIC_TREE_VIEW_VISIBILITY) final boolean visibility) {
		if (sashForm.getMaximizedControl() == null) {
			sashForm.setMaximizedControl(i18nPage);
		} else {
			sashForm.setMaximizedControl(null);
		}
	}

	/**
	 * Refresh layout
	 * @param visibility
	 */
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

	private class LocalBehaviour implements FocusListener, IBundleChangeListener, ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void add(BundleEvent<T> event) {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void remove(BundleEvent<T> event) {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void modify(BundleEvent<T> event) {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void select(BundleEvent<T> event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
