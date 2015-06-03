package org.eclipse.e4.babel.editor.ui.editor.composite;


import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class BundleEntryComposite extends ScrolledComposite {

    private static final int UNDO_LEVEL = 20;

    public static BundleEntryComposite create(final Composite sashForm) {
        return new BundleEntryComposite(sashForm, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private BundleEntryComposite(final Composite sashForm, final int style) {
        super(sashForm, style);

        final Composite comp = new Composite(this, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));

        for (int i = 0; i < 10; i++) {
            final Composite composite = new Composite(comp, SWT.NONE);
            composite.setLayout(new GridLayout(2, false));

            final Label lblNewLabel = new Label(composite, SWT.NONE);
            lblNewLabel.setBounds(0, 0, 62, 15);
            lblNewLabel.setText("New Label" + i);

            final Label lblNewLabel_2 = new Label(composite, SWT.NONE);
            lblNewLabel_2.setText("New Label" + i);

            final Composite composite_1 = new Composite(comp, SWT.NONE);
            composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

            final Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
            lblNewLabel_1.setBounds(0, 0, 62, 15);
            lblNewLabel_1.setText("New Label" + i);

            final TextViewer textViewer = new TextViewer(comp, SWT.V_SCROLL | SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
            textViewer.setDocument(new Document());


            TextViewerUndoManager undoManager = new TextViewerUndoManager(UNDO_LEVEL);
            textViewer.setUndoManager(undoManager);


            final StyledText styledText = textViewer.getTextWidget();

            styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 4));


            styledText.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent event) {
                    Log.d("sdsd", "sdsdsd");
                    if (isKeyCombination(event, SWT.CTRL, 'z')) {
                        undoManager.undo();
                    } else if (isKeyCombination(event, SWT.CTRL, 'y')) {
                        undoManager.redo();
                    } else if (isKeyCombination(event, SWT.CTRL, 'a')) {
                        textViewer.setSelectedRange(0, textViewer.getDocument().getLength());
                    } else {
                        /*
                         * StyledText eventBox = (StyledText) event.widget;
                         * final ITextEditor editor = resourceManager.getSourceEditor(locale).getEditor();
                         * // Text field has changed: make editor dirty if not already
                         * if (textBeforeUpdate != null && !textBeforeUpdate.equals(eventBox.getText())) {
                         * // Make the editor dirty if not already. If it is,
                         * // we wait until field focus lost (or save) to
                         * // update it completely.
                         * if (!editor.isDirty()) {
                         * int caretPosition = eventBox.getCaretOffset();
                         * updateBundleOnChanges();
                         * eventBox.setSelection(caretPosition);
                         * }
                         * //utoDetectRequiredFont(eventBox.getText());
                         */

                    }
                }
            });

        }
        this.setContent(comp);
        this.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setShowFocusedControl(true);

    }

    private static boolean isKeyCombination(KeyEvent event, int modifier1, int keyCode) {
        return (event.keyCode == keyCode && event.stateMask == modifier1);
    }
}
