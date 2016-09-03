package org.eclipse.e4.babel.editor.ui.editor.i18n.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleChangeAdapter;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract.View;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryView;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;

@Creatable
public class I18nPagePresenter implements I18nPageContract.Presenter {

    final LocalBehaviour localBehaviour = new LocalBehaviour();
    final List<I18nPageEntryContract.View> pageEntries = new ArrayList<>();

    @Inject
    private IBabelResourceProvider resourceProvider;
    private IResourceManager resourceManager;

    private I18nPageEntryContract.View activeEntry;
    private I18nPageEntryContract.View lastActiveEntry;

    private View i18nPageView;
    private ResourceBundleEditorContract.View editorView;

    private I18nPagePresenter(I18nPageContract.View i18nPageView, IResourceManager resourceManager, ResourceBundleEditorContract.View editorView) {
	this.resourceManager = resourceManager;
	this.i18nPageView = i18nPageView;
	this.editorView = editorView;
    }

    @PostConstruct
    public void onCreate() { // NO_UCD
	editorView.getKeyTreeView().getTreeViewer().addSelectionChangedListener(localBehaviour);
	editorView.getKeyTreeView().getKeyTree().addChangeListener(localBehaviour);
    }

    @Override
    public void init() {

    }

    @Override
    public void dispose() {
	pageEntries.forEach(entry -> entry.dispose());
	pageEntries.clear();
    }

    @Override
    public LocalBehaviour getLocalBehaviour() {
	return localBehaviour;
    }

    @Override
    public IResourceManager getResourceManager() {
	return resourceManager;
    }

    @Override
    public List<I18nPageEntryContract.View> getPageEntries() {
	return pageEntries;
    }

    @Override
    public I18nPageEntryContract.View getPageEntryByIndex(int position) {
	return pageEntries.get(position);
    }

    @Override
    public void createEditingPages() {
	resourceManager.getSortedLocales().stream().forEach(locale -> {
	    final I18nPageEntryContract.View entry = I18nPageEntryView.create((I18nPageView) i18nPageView, locale);
	    entry.addLocalListener(localBehaviour);
	    pageEntries.add(entry);
	});
    }

    @Override
    public void redrawEditorSize() {
	pageEntries.stream().forEach((entry) -> entry.updateEditorHeight());
    }

    /**
     * Refreshes all value-holding text boxes in this page.
     */
    @Override
    public void refreshTextBoxes() {
	String key = getSelectedKey();
	pageEntries.forEach(entry -> {
	    entry.getPresenter().updateDocument(key);
	});
    }

    private String getSelectedKey() {
	return resourceManager.getKeyTree().getSelectedKey();
    }

    @Override
    public void selectPreviousTreeEntry() {
	activeEntry.getPresenter().updateBundleOnChanges();
	String prevKey = resourceManager.getBundleGroup().getPreviousKey(getSelectedKey());
	if (prevKey == null) {
	    return;
	}

	Locale currentLocale = activeEntry.getPresenter().getLocale();
	resourceManager.getKeyTree().selectKey(prevKey);
	focusBundleEntryComposite(currentLocale);
    }

    /*
     * Focusses the previous {@link BundleEntryComposite}.
     */
    public void focusPreviousBundleEntryComposite() {
	int index = pageEntries.indexOf(activeEntry);
	I18nPageEntryContract.View nextComposite;
	if (index > 0) {
	    nextComposite = pageEntries.get(--index);
	} else {
	    nextComposite = pageEntries.get(pageEntries.size() - 1);
	}

	if (nextComposite != null) {
	    focusComposite(nextComposite);
	}
    }

    /**
     * Focusses the given {@link BundleEntryComposite} and scrolls the
     * surrounding {@link ScrolledComposite} in order to make it visible.
     * 
     * @param comp
     *            The {@link BundleEntryComposite} to be focussed.
     */
    private void focusComposite(I18nPageEntryContract.View comp) {

	Point compPos = ((I18nPageEntryView) comp).getLocation();
	Point compSize = ((I18nPageEntryView) comp).getSize();
	Point size = ((I18nPageView) i18nPageView).getSize();
	Point origin = ((I18nPageView) i18nPageView).getOrigin();
	if (compPos.y + compSize.y > size.y + origin.y) {
	    ((I18nPageView) i18nPageView).setOrigin(origin.x, origin.y + (compPos.y + compSize.y) - (origin.y + size.y) + 5);
	} else if (compPos.y < origin.y) {
	    ((I18nPageView) i18nPageView).setOrigin(origin.x, compPos.y);
	}
	comp.focusTextBox();
    }

    @Override
    public void selectNextTreeEntry() {
	activeEntry.getPresenter().updateBundleOnChanges();
	String nextKey = resourceManager.getBundleGroup().getNextKey(getSelectedKey());
	if (nextKey == null) {
	    return;
	}

	Locale currentLocale = activeEntry.getPresenter().getLocale();
	resourceManager.getKeyTree().selectKey(nextKey);
	focusBundleEntryComposite(currentLocale);
    }

    /**
     * Focusses the next {@link BundleEntryComposite}.
     */
    public void focusNextBundleEntryComposite() {
	int index = pageEntries.indexOf(activeEntry);
	I18nPageEntryContract.View nextComposite;
	if (index < pageEntries.size() - 1)
	    nextComposite = pageEntries.get(++index);
	else
	    nextComposite = pageEntries.get(0);

	if (nextComposite != null)
	    focusComposite(nextComposite);
    }

    /**
     * This method focusses the {@link BundleEntryComposite} corresponding to
     * the given {@link Locale}. If no such composite exists or the locale is
     * null, nothing happens.
     * 
     * @param locale
     *            The locale whose {@link BundleEntryComposite} is to be
     *            focussed.
     */
    public void focusBundleEntryComposite(Locale locale) {
	for (I18nPageEntryContract.View entry : pageEntries) {
	    Locale currentLocale = entry.getPresenter().getLocale();
	    if ((currentLocale == null) && (locale == null) || (locale != null && locale.equals(currentLocale))) {
		entry.focusTextBox();
	    }
	}
    }

    /**
     * Refreshes the editor associated with the active text box (if any) if it
     * has changed.
     */
    @Override
    public void refreshEditorOnChanges() {
	if (activeEntry != null) {
	    activeEntry.getPresenter().updateBundleOnChanges();
	}
    }

    @Override
    public I18nPageEntryContract.View getActiveEntry() {
	return activeEntry;
    }

    @Override
    public IBabelResourceProvider getResourceProvider() {
	return resourceProvider;
    }

    @Override
    public ResourceBundleEditorContract.View getResourceBundleEditor() {
	return editorView;
    }

    public class LocalBehaviour extends BundleChangeAdapter implements FocusListener, ISelectionChangedListener {

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
	    refreshTextBoxes();
	    String selected = getSelectedKey();
	    if (selected != null) {
		resourceManager.getKeyTree().selectKey(selected);
	    }

	}

	@Override
	public <T> void select(BundleEvent<T> event) {
	    KeyTreeItem item = (KeyTreeItem) event.data();
	    if (editorView != null) {
		if (item != null) {
		    editorView.getKeyTreeView().getTreeViewer().setSelection(new StructuredSelection(item));
		}
	    } else {
		refreshTextBoxes();
	    }
	}

	@Override
	public void focusGained(FocusEvent event) {
	    activeEntry = (I18nPageEntryView) event.widget;
	    lastActiveEntry = activeEntry;
	}

	@Override
	public void focusLost(FocusEvent arg0) {
	    activeEntry = null;
	}
    }

    public static I18nPagePresenter create(I18nPageContract.View i18nPageView, IResourceManager resourceManager, ResourceBundleEditorContract.View editorView) {
	return new I18nPagePresenter(i18nPageView, resourceManager, editorView);
    }

    @Override
    public void refreshKeyTree() {
	if (editorView != null) {
	    editorView.getKeyTreeView().getTreeViewer().refresh(true);
	}
    }

}
