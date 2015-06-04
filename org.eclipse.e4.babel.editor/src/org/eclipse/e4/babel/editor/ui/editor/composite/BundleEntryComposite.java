package org.eclipse.e4.babel.editor.ui.editor.composite;


import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
import org.eclipselabs.e4.tapiji.resource.ITapijiResourceProvider;
import org.eclipselabs.e4.tapiji.resource.TapijiResourceConstants;


final class BundleEntryComposite extends Composite implements KeyListener, TraverseListener, SelectionListener, FocusListener, MouseListener {

    private static final int UNDO_LEVEL = 20;
    private static final String TAG = BundleEntryComposite.class.getSimpleName();
    private final TextViewerUndoManager undoManager = new TextViewerUndoManager(UNDO_LEVEL);
    private TextViewer textViewer;
    private IBundleEntryComposite listener;
    private boolean expanded;
    private GridData gd_styledText;
    private GridData data;
    private ITapijiResourceProvider resourceProvider;
    private Label expandIcon;

    public interface IBundleEntryComposite {

        void onLocaleClick();

        void setNextFocusDown();

        void setNextFocusUp();

        void onFocusChange(BundleEntryComposite bundleEntryComposite);

    }

    private BundleEntryComposite(final Composite parent, ITapijiResourceProvider resourceProvider, final int style) {
        super(parent, style);
        this.resourceProvider = resourceProvider;

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

        expandIcon = new Label(composite, SWT.FLAT);
        expandIcon.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_COLLAPSE));
        expandIcon.addMouseListener(this);


        Link localeName = new Link(composite, SWT.NONE);
        localeName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
        localeName.setText("<a>Albanien</a>");
        localeName.addSelectionListener(this);

        Label localeImage = new Label(composite, SWT.NONE);
        GridData localeImageDataLayout = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        localeImageDataLayout.horizontalIndent = 5;
        localeImageDataLayout.heightHint = 16;
        localeImage.setLayoutData(localeImageDataLayout);
        localeImage.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_FOLDER_FLAGS + "AT.png"));


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
        data = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 2);
        composite_2.setLayoutData(data);

        textViewer = new TextViewer(composite_2, SWT.BORDER);
        StyledText styledText = textViewer.getTextWidget();
        gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
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

    public static BundleEntryComposite create(final Composite parent, ITapijiResourceProvider resourceProvider) {
        return new BundleEntryComposite(parent, resourceProvider, SWT.NONE);
    }

    private void expandTextView(boolean expand) {
        if (expand) {
            textViewer.getTextWidget().setVisible(false);
            expandIcon.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_EXPAND));
        } else {
            textViewer.getTextWidget().setVisible(true);
            expandIcon.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_COLLAPSE));
        }
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

    public void setFocusTextView() {
        if (null != textViewer) {
            StyledText styledText = textViewer.getTextWidget();
            styledText.setFocus();
        }
    }

    @Override
    public void mouseDoubleClick(MouseEvent e) {

    }

    @Override
    public void mouseDown(MouseEvent e) {
    }

    @Override
    public void mouseUp(MouseEvent e) {
        expandTextView(textViewer.getTextWidget().isVisible());
    }
}
