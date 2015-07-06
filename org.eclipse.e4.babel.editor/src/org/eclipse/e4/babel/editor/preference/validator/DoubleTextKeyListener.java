package org.eclipse.e4.babel.editor.preference.validator;


import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Text;


public final class DoubleTextKeyListener extends ATextKeyListener {

    private static final String REGEX_DOUBLE = "^\\d*\\.?\\d*$";
    private double minValue;
    private double maxValue;

    public DoubleTextKeyListener(final String errorMessage, final PreferencePage preferencePage) {
        super(preferencePage, errorMessage);
    }

    public DoubleTextKeyListener(final String errorMessage, final double minValue, final double maxValue, final PreferencePage preferencePage) {
        super(preferencePage, errorMessage);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        final Text text = (Text) event.widget;
        final String value = text.getText();
        boolean valid = value.length() > 0;
        if (valid) {
            valid = value.matches(REGEX_DOUBLE);
        }
        if (valid && (minValue != maxValue)) {
            final double doubleValue = Double.parseDouble(value);
            valid = (doubleValue >= minValue) && (doubleValue <= maxValue);
        }
        event.doit = valid;
        handleError(event.doit, text);
    }
}
