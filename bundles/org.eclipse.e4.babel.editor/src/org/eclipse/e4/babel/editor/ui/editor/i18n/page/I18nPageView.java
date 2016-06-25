package org.eclipse.e4.babel.editor.ui.editor.i18n.page;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public final class I18nPageView extends ScrolledComposite implements I18nPageContract.View {

    private static final String TAG = I18nPageView.class.getSimpleName();
    private final List<I18nPageEntryContract.View> pageEntries = new ArrayList<>();
    private final LocalBehaviour localBehaviour = new LocalBehaviour();
    private I18nPageEntryContract.View activeBundleEntry;
    private Composite i18nEntryComposite;
	private IResourceManager resourceManager;
	private View editorView;

    public static I18nPageView create(final Composite sashForm, ResourceBundleEditorContract.View editorView, final IBabelResourceProvider resourceProvider, IResourceManager resourceManager) {
        return new I18nPageView(sashForm, resourceProvider,resourceManager,editorView, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nPageView(final Composite sashForm, final IBabelResourceProvider resourceProvider, IResourceManager resourceManager, View editorView, final int style) {
        super(sashForm, style);
        this.resourceManager = resourceManager;
        this.editorView = editorView;
        i18nEntryComposite = new Composite(this, SWT.BORDER);
        i18nEntryComposite.setLayout(new GridLayout(1, false));
        
        editorView.getKeyTreeView().getTreeViewer().addSelectionChangedListener(localBehaviour);
        resourceManager.getLocales().stream().forEach(locale -> {
            final I18nPageEntryContract.View entry = I18nPageEntryView.create(i18nEntryComposite,(ScrolledComposite)this, resourceProvider, resourceManager, locale, this);
            pageEntries.add(entry);	
        });

        this.setContent(i18nEntryComposite);
        this.setMinSize(i18nEntryComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setShowFocusedControl(true);
    }
    
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
    
    private class LocalBehaviour extends BundleChangeAdapter implements FocusListener,
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
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    }

	@Override
	public ResourceBundleEditorContract.View getResourceBundleEditor() {
		return editorView;
	}

	@Override
	public void selectNextTreeEntry() {
      /*  activeEntry.updateBundleOnChanges();
        String nextKey = resourceMediator.getBundleGroup().getNextKey(
                getSelectedKey());
        if (nextKey == null)
            return;

        Locale currentLocale = activeEntry.getLocale();
        resourceMediator.getKeyTree().selectKey(nextKey);
        focusBundleEntryComposite(currentLocale);*/
	}

	@Override
	public void selectPreviousTreeEntry() {
		// TODO Auto-generated method stub
		
	}
    
    
}
