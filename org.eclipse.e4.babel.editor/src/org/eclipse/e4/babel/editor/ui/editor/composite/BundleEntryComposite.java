package org.eclipse.e4.babel.editor.ui.editor.composite;


import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipselabs.e4.tapiji.logger.Log;


final class BundleEntryComposite extends Composite implements KeyListener, TraverseListener, SelectionListener, FocusListener {

    private static final int UNDO_LEVEL = 20;
    private static final String TAG = BundleEntryComposite.class.getSimpleName();
    private final TextViewerUndoManager undoManager = new TextViewerUndoManager(UNDO_LEVEL);
    private TextViewer textViewer;
    private IBundleEntryComposite listener;

    public interface IBundleEntryComposite {

        void onLocaleClick();

        void setNextFocusDown();

        void setNextFocusUp();

        void onFocusChange(BundleEntryComposite bundleEntryComposite);

    }

    private BundleEntryComposite(final Composite parent, final int style) {
        super(parent, style);

        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.marginWidth = 0;
        setLayout(gridLayout);

        Composite composite = new Composite(this, SWT.NONE);
        GridLayout gl_composite = new GridLayout(3, false);
        gl_composite.verticalSpacing = 0;
        gl_composite.marginWidth = 0;
        gl_composite.marginHeight = 0;
        composite.setLayout(gl_composite);

        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setText("New Label");


        Link localeName = new Link(composite, SWT.NONE);
        GridData localeNameLayoutData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        localeNameLayoutData.horizontalIndent = 5;
        localeName.setLayoutData(localeNameLayoutData);
        localeName.setText("<a>Albanien</a>");
        localeName.addSelectionListener(this);


        Label lblNewLabel_2 = new Label(composite, SWT.NONE);
        GridData gd_lblNewLabel_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_lblNewLabel_2.horizontalIndent = 5;
        lblNewLabel_2.setLayoutData(gd_lblNewLabel_2);
        lblNewLabel_2.setText("New Label");
        new Label(this, SWT.NONE);

        Composite composite_1 = new Composite(this, SWT.NONE);
        GridLayout gl_composite_1 = new GridLayout(2, false);
        gl_composite_1.marginHeight = 0;
        gl_composite_1.marginWidth = 0;
        composite_1.setLayout(gl_composite_1);
        composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

        Label lblNewLabel_3 = new Label(composite_1, SWT.NONE);
        lblNewLabel_3.setText("New Label");

        Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
        GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_lblNewLabel_1.horizontalIndent = 5;
        lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
        lblNewLabel_1.setText("New Label");

        Composite composite_2 = new Composite(this, SWT.NONE);
        GridLayout gl_composite_2 = new GridLayout(1, false);
        gl_composite_2.marginHeight = 0;
        gl_composite_2.marginWidth = 0;
        gl_composite_2.verticalSpacing = 0;
        composite_2.setLayout(gl_composite_2);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 2));

        TextViewer textViewer = new TextViewer(composite_2, SWT.BORDER);
        StyledText styledText = textViewer.getTextWidget();
        GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_styledText.minimumHeight = 40;
        styledText.setLayoutData(gd_styledText);
        styledText.addFocusListener(this);
        styledText.addTraverseListener(this);
        styledText.addKeyListener(this);
    }

    private static boolean isKeyCombination(final KeyEvent event, final int mask, final int keyCode) {
        return ((event.keyCode == keyCode) && (event.stateMask == mask));
    }

    @Override
    public void keyPressed(final KeyEvent event) {
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        Log.d(TAG, "keyReleased: " + event);
        if (isKeyCombination(event, SWT.CTRL, 'z')) {
            undoManager.undo();
        } else if (isKeyCombination(event, SWT.CTRL, 'y')) {
            undoManager.redo();
        } else if (isKeyCombination(event, SWT.CTRL, 'a')) {
            textViewer.setSelectedRange(0, textViewer.getDocument().getLength());
        } else {

        }
    }

    @Override
    public void keyTraversed(TraverseEvent event) {
        Log.d(TAG, "keyTraversed: " + event.toString());
        if (listener != null) {
            if (event.character == SWT.TAB) {
                event.doit = true;
                event.detail = SWT.TRAVERSE_NONE;
                if (event.stateMask == 0) {
                    listener.setNextFocusDown();
                } else if (event.stateMask == SWT.SHIFT) {
                    listener.setNextFocusUp();
                }
            } else if (event.keyCode == SWT.ARROW_DOWN && event.stateMask == SWT.CTRL) {
                event.doit = true;
                event.detail = SWT.TRAVERSE_NONE;
            } else if (event.keyCode == SWT.ARROW_UP && event.stateMask == SWT.CTRL) {
                event.doit = true;
                event.detail = SWT.TRAVERSE_NONE;
            }
        }
    }

    public static BundleEntryComposite create(final Composite parent) {
        return new BundleEntryComposite(parent, SWT.BORDER);
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
        Log.d(TAG, "widgetSelected: " + event.toString());
        if (null != listener) {
            listener.onLocaleClick();
        }
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
    }

    public void addListener(IBundleEntryComposite entryListener) {
        if (null != entryListener) {
            listener = entryListener;
        }
    }

    @Override
    public void focusGained(FocusEvent event) {
        Log.d(TAG, "focusGained: " + event.toString());
        if (null != listener) {
            listener.onFocusChange(this);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
}
