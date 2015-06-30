package org.eclipse.e4.babel.editor.preference;


import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;


public class PreferenceDialogPage extends PreferencePage {


    private static final String TAG = PreferenceDialogPage.class.getSimpleName();


    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);

        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        composite.setLayout(gridLayout);


        Button showGeneratedBy = new Button(composite, SWT.CHECK);
        new Label(composite, SWT.NONE).setText("sdfdfsaf");

        return composite;
    }


}
