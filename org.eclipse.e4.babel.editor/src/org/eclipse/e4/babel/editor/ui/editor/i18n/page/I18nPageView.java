package org.eclipse.e4.babel.editor.ui.editor.i18n.page;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract.Presenter;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntry;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public final class I18nPageView extends ScrolledComposite implements I18nPageContract.View {

    private static final String TAG = I18nPageView.class.getSimpleName();
    private final List<I18nPageEntryContract.View> bundleEntries = new ArrayList<>();

    private I18nPageEntryContract.View activeBundleEntry;
    private Composite i18nEntryComposite;

    public static I18nPageView create(final Composite sashForm, final IBabelResourceProvider resourceProvider) {
        return new I18nPageView(sashForm, resourceProvider, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nPageView(final Composite sashForm, final IBabelResourceProvider resourceProvider, final int style) {
        super(sashForm, style);
        i18nEntryComposite = new Composite(this, SWT.BORDER);
        i18nEntryComposite.setLayout(new GridLayout(1, false));
        for (int i = 0; i < 8; i++) {
            final I18nPageEntryContract.View entry = I18nPageEntry.create(i18nEntryComposite,(ScrolledComposite)this, resourceProvider);
            entry.addPageListener(this);
            bundleEntries.add(entry);
        }
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
       bundleEntries.stream().forEach((entry)->entry.updateEditorHeight());
    }

    @Override
    public void onLocaleClick() {

    }

    @Override
    public void setNextFocusDown() {
        if (null != activeBundleEntry) {
            final int index = bundleEntries.indexOf(activeBundleEntry);
            if ((index >= 0) && (index != (bundleEntries.size() - 1))) {
                setFocusForNextComposite(bundleEntries.get(index + 1));
            } else if (index == (bundleEntries.size() - 1)) {
                setFocusForNextComposite(bundleEntries.get(0));
            }
        }
    }

    @Override
    public void setNextFocusUp() {
        if (null != activeBundleEntry) {
            final int index = bundleEntries.indexOf(activeBundleEntry);
            if (index > 0) {
                setFocusForNextComposite(bundleEntries.get(index - 1));
            } else if (index == 0) {
                setFocusForNextComposite(bundleEntries.get(bundleEntries.size() - 1));
            }
        }
    }

    private void setFocusForNextComposite(final I18nPageEntryContract.View nextFocusComposite) {
        nextFocusComposite.setFocusTextView();
        setOrigin(getOrigin().x, nextFocusComposite.getCoordinateY());
    }

    @Override
    public void onFocusChange(final I18nPageEntry bundleEntry) {
        activeBundleEntry = bundleEntry;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        // TODO Auto-generated method stub
        
    }
}