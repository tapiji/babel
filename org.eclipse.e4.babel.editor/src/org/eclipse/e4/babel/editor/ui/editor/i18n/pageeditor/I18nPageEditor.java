package org.eclipse.e4.babel.editor.ui.editor.i18n.pageeditor;


import org.eclipse.e4.babel.editor.text.PropertiesTextEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class I18nPageEditor extends Composite {

    private static final int VERTICAL_RULER_WIDTH = 12;

    public I18nPageEditor(final CTabFolder tabFolder) {
        super(tabFolder, SWT.NONE);
        createControl(tabFolder);
    }


    public void createControl(final Composite sashForm) {

        //      Composite parent = new Composite(this, SWT.NONE);
        setLayout(new FillLayout());


        new PropertiesTextEditor(this, null);

    }
}
