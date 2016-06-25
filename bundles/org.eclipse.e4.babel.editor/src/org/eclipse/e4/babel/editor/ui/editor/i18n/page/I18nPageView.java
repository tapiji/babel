package org.eclipse.e4.babel.editor.ui.editor.i18n.page;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleChangeAdapter;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.bundle.listener.IBundleChangeListener;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract.View;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract.Presenter;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryView;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public final class I18nPageView extends ScrolledComposite implements I18nPageContract.View {

    private static final String TAG = I18nPageView.class.getSimpleName();
    private final List<I18nPageEntryContract.View> pageEntries = new ArrayList<>();
    private final LocalBehaviour localBehaviour = new LocalBehaviour();
    private I18nPageEntryContract.View activeBundleEntry;
    private Composite i18nEntryComposite;
	private IResourceManager resourceManager;
	private View editorView;
	private I18nPageEntryContract.View activeEntry;
	private I18nPageEntryContract.View lastActiveEntry;
	private IBabelResourceProvider resourceProvider;

    public static I18nPageView create(final Composite sashForm, ResourceBundleEditorContract.View editorView, final IBabelResourceProvider resourceProvider, IResourceManager resourceManager) {
        return new I18nPageView(sashForm, resourceProvider,resourceManager,editorView, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nPageView(final Composite sashForm, final IBabelResourceProvider resourceProvider, IResourceManager resourceManager, View editorView, final int style) {
        super(sashForm, style);
        this.resourceManager = resourceManager;
        this.editorView = editorView;
        this.resourceProvider = resourceProvider;
        i18nEntryComposite = new Composite(this, SWT.BORDER);
        i18nEntryComposite.setLayout(new GridLayout(1, false));
        
        editorView.getKeyTreeView().getTreeViewer().addSelectionChangedListener(localBehaviour);
        createEditingPart();

        this.setContent(i18nEntryComposite);
        this.setMinSize(i18nEntryComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setShowFocusedControl(true);
    }
    
    
    
    private void createEditingPart() {
        Control[] children = i18nEntryComposite.getChildren();
        for (int i = 0; i < children.length; i++) {
            children[i].dispose();
        }
     
        
        resourceManager.getLocales().stream().forEach(locale -> {
            final I18nPageEntryContract.View entry = I18nPageEntryView.create(this,locale);
            entry.addLocalListener(localBehaviour);
            pageEntries.add(entry);	
        });
        // TODO
        
      /*  Composite _rightComposite = new Composite(parent, SWT.BORDER);
        parent.setContent(_rightComposite);
//        if (!RBEPreferences.getAutoAdjust()) {
            parent.setMinSize(_rightComposite.computeSize(
                    SWT.DEFAULT,
                    resourceMediator.getLocales().size()
                            * RBEPreferences.getMinHeight()));
//        }
        _rightComposite.setLayout(new GridLayout(1, false));
        entryComposites.clear();
        for (Iterator<Locale> iter = resourceMediator.getLocales().iterator(); 
                iter.hasNext();) {
            Locale locale = (Locale) iter.next();
            BundleEntryComposite entryComposite = new BundleEntryComposite(
                    _rightComposite, resourceMediator, locale, this);
            entryComposite.addFocusListener(localBehaviour);
            entryComposites.add(entryComposite);
        }*/
        
    }
    @Override
    public Composite getI18NPage() {
    	return i18nEntryComposite;
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
    public void refreshLayout() {
        this.setMinSize(i18nEntryComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT)); 
        this.layout(true, true);
    }
    
    public void redrawEditorSize() {
       pageEntries.stream().forEach((entry)->entry.updateEditorHeight());
    }

    @Override
    public void onLocaleClick() {

    }
    
    /**
     * Refreshes all value-holding text boxes in this page.
     */
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
    public void setNextFocusDown() {
        if (null != activeBundleEntry) {
            final int index = pageEntries.indexOf(activeBundleEntry);
            if ((index >= 0) && (index != (pageEntries.size() - 1))) {
                setFocusForNextComposite(pageEntries.get(index + 1));
            } else if (index == (pageEntries.size() - 1)) {
                setFocusForNextComposite(pageEntries.get(0));
            }
        }
    }

    @Override
    public void setNextFocusUp() {
        if (null != activeBundleEntry) {
            final int index = pageEntries.indexOf(activeBundleEntry);
            if (index > 0) {
                setFocusForNextComposite(pageEntries.get(index - 1));
            } else if (index == 0) {
                setFocusForNextComposite(pageEntries.get(pageEntries.size() - 1));
            }
        }
    }

    private void setFocusForNextComposite(final I18nPageEntryContract.View nextFocusComposite) {
        nextFocusComposite.setFocusTextView();
        setOrigin(getOrigin().x, nextFocusComposite.getCoordinateY());
    }

    @Override
    public void onFocusChange(final I18nPageEntryView bundleEntry) {
        activeBundleEntry = bundleEntry;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Refreshes the editor associated with the active text box (if any) if it
     * has changed.
     */
    public void refreshEditorOnChanges() {
        if (activeEntry != null) {
            activeEntry.getPresenter().updateBundleOnChanges();
        }
    }
    
    public class LocalBehaviour extends BundleChangeAdapter implements FocusListener,
    ISelectionChangedListener {



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

	@Override
	public ResourceBundleEditorContract.View getResourceBundleEditor() {
		return editorView;
	}

	@Override
	public void selectNextTreeEntry() {
        activeEntry.getPresenter().updateBundleOnChanges();
        String nextKey = resourceManager.getBundleGroup().getNextKey(getSelectedKey());
        if (nextKey == null)
            return;

        Locale currentLocale = activeEntry.getPresenter().getLocale();
        resourceManager.getKeyTree().selectKey(nextKey);
        focusBundleEntryComposite(currentLocale);
	}

	@Override
	public void selectPreviousTreeEntry() {
	    activeEntry.getPresenter().updateBundleOnChanges();
        String prevKey = resourceManager.getBundleGroup().getPreviousKey(
                getSelectedKey());
        if (prevKey == null)
            return;

        Locale currentLocale = activeEntry.getPresenter().getLocale();
        resourceManager.getKeyTree().selectKey(prevKey);
        focusBundleEntryComposite(currentLocale);		
	}
	
	
    /**
     * Refreshes the tree and recreates the editing part.
     */
    public void refreshPage() {
        if (editorView != null) {
        	editorView.getKeyTreeView().getTreeViewer().refresh(true);
        }
       createEditingPart();
       i18nEntryComposite.layout(true, true);
       layout(true, true);
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
     * Focusses the previous {@link BundleEntryComposite}.
     */
    public void focusPreviousBundleEntryComposite() {
        int index = pageEntries.indexOf(activeEntry);
        I18nPageEntryContract.View nextComposite;
        if (index > 0)
            nextComposite = pageEntries.get(--index);
        else
            nextComposite = pageEntries.get(pageEntries.size() - 1);

        if (nextComposite != null)
            focusComposite(nextComposite);
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
     * Focusses the given {@link BundleEntryComposite} and scrolls the
     * surrounding {@link ScrolledComposite} in order to make it visible.
     * 
     * @param comp
     *            The {@link BundleEntryComposite} to be focussed.
     */
    private void focusComposite(I18nPageEntryContract.View comp) {
        Point compPos =((I18nPageEntryView) comp).getLocation();
        Point compSize = ((I18nPageEntryView) comp).getSize();
        Point size = (I18nPageView.this).getSize();
        Point origin = (I18nPageView.this).getOrigin();
        if (compPos.y + compSize.y > size.y + origin.y)
        	(I18nPageView.this).setOrigin(origin.x, origin.y
                    + (compPos.y + compSize.y) - (origin.y + size.y) + 5);
        else if (compPos.y < origin.y)
        	(I18nPageView.this).setOrigin(origin.x, compPos.y);
       comp.focusTextBox();
    }
    public void dispose() {
     ////      keysComposite.dispose();
      //  }
        pageEntries.forEach(entry->entry.dispose());
        super.dispose();
    }
    
}
