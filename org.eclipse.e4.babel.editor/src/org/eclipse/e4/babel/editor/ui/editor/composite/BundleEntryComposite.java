package org.eclipse.e4.babel.editor.ui.editor.composite;


import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipselabs.e4.tapiji.logger.Log;


final class BundleEntryComposite extends Composite implements KeyListener, TraverseListener {

    private static final int UNDO_LEVEL = 20;
    private static final String TAG = BundleEntryComposite.class.getSimpleName();
    private final TextViewerUndoManager undoManager = new TextViewerUndoManager(UNDO_LEVEL);
    private TextViewer textViewer;
    private I18nComposite i18nComposite;

    private BundleEntryComposite(final Composite parent, final int style, I18nComposite i18nComposite) {
        super(parent, style);

        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        this.i18nComposite = i18nComposite;


        setLayout(new GridLayout(2, false));

        Composite composite = new Composite(this, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));

        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setText("New Label");

        Label lblNewLabel_2 = new Label(composite, SWT.NONE);
        lblNewLabel_2.setText("New Label");

        Composite composite_1 = new Composite(this, SWT.NONE);
        composite_1.setLayout(new GridLayout(2, false));
        composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

        Label lblNewLabel_3 = new Label(composite_1, SWT.NONE);
        lblNewLabel_3.setText("New Label");

        Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
        lblNewLabel_1.setText("New Label");

        Composite composite_2 = new Composite(this, SWT.NONE);
        GridLayout gl_composite_2 = new GridLayout(1, false);
        gl_composite_2.marginHeight = 0;
        gl_composite_2.marginWidth = 0;
        gl_composite_2.verticalSpacing = 0;
        composite_2.setLayout(gl_composite_2);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));

        TextViewer textViewer = new TextViewer(composite_2, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        StyledText styledText = textViewer.getTextWidget();
        GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_styledText.minimumHeight = 40;
        styledText.setLayoutData(gd_styledText);
    }

    private static boolean isKeyCombination(final KeyEvent event, final int mask, final int keyCode) {
        return ((event.keyCode == keyCode) && (event.stateMask == mask));
    }

    @Override
    public void keyPressed(final KeyEvent event) {
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        Log.d(TAG, "Event: " + event);
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
        if (event.character == SWT.TAB) {
            event.doit = true;
            event.detail = SWT.TRAVERSE_NONE;
        } else if (event.keyCode == SWT.ARROW_DOWN && event.stateMask == SWT.CTRL) {
            event.doit = true;
            event.detail = SWT.TRAVERSE_NONE;
        } else if (event.keyCode == SWT.ARROW_UP && event.stateMask == SWT.CTRL) {
            event.doit = true;
            event.detail = SWT.TRAVERSE_NONE;
        }
    }

    public static BundleEntryComposite create(final Composite parent, I18nComposite i18nComposite) {
        return new BundleEntryComposite(parent, SWT.BORDER, i18nComposite);
    }
}
