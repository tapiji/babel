package org.eclipse.e4.babel.editor.preference.validator;


import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Text;


public final class NumberTextKeyListener extends ATextKeyListener {

    private static final String REGEX_DIGIT = "^\\d*$";

    public NumberTextKeyListener(final String errorMessage, final PreferencePage preferencePage) {
        super(preferencePage, errorMessage);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        final Text text = (Text) event.widget;
        final String value = text.getText();
        event.doit = value.matches(REGEX_DIGIT);
        handleError(event.doit, text);
    }
}
