package org.eclipse.e4.babel.editor.ui.editor;


import javax.inject.Inject;
import org.eclipse.e4.babel.editor.ui.editor.interfaces.IDocumentInput;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class MessageBundleTextEditor {

    private final IDocumentInput input;

    @Inject
    public MessageBundleTextEditor(final Composite parent, final IDocumentInput input) {
        this.input = input;
        parent.setLayout(new FillLayout());

        final int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
        final TextViewer viewer = new TextViewer(parent, styles);
        viewer.setDocument(input.getDocument());
    }

    @Persist
    public void save() {
        input.save();
    }
}
