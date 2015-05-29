package org.eclipse.e4.babel.editor.ui.editor;


import javax.annotation.PostConstruct;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public class MPartEditor {

    private static final String TAG = MPartEditor.class.getSimpleName();
    private ColumnViewer viewer;

    @PostConstruct
    public void createPartControl(Composite parent) {
        viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        viewer.setLabelProvider(new LabelProvider());
        viewer.setInput(new String[] {"One", "Two", "Three"});
    }

    @Focus
    public void setFocus() {
        viewer.getControl().setFocus();
    }
}
