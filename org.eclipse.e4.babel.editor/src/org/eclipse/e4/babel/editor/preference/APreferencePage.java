package org.eclipse.e4.babel.editor.preference;


import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;


abstract class APreferencePage extends PreferencePage {

    protected final int indentPixels = 20;

    protected final Map<Text, String> errors = new HashMap<Text, String>();

    public APreferencePage(final String title) {
        super(title);
    }

    protected Composite createFieldComposite(Composite parent) {
        return createFieldComposite(parent, 0);
    }

    protected Composite createFieldComposite(Composite parent, int indent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = indent;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        composite.setLayout(gridLayout);
        return composite;
    }

    protected void setWidthInChars(Control field, int widthInChars) {
        GridData gd = new GridData();
        gd.widthHint = getWidthInChars(field, widthInChars);
        field.setLayoutData(gd);
    }

    public static int getWidthInChars(Control control, int numOfChars) {
        GC gc = new GC(control);
        Point extent = gc.textExtent("W");//$NON-NLS-1$
        gc.dispose();
        return numOfChars * extent.x;
    }

    protected class IntTextValidatorKeyListener extends KeyAdapter {

        private String errMsg = null;

        /**
         * Constructor.
         *
         * @param errMsg error message
         */
        public IntTextValidatorKeyListener(String errMsg) {
            super();
            this.errMsg = errMsg;
        }

        /**
         * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
         */
        @Override
        public void keyReleased(KeyEvent event) {
            Text text = (Text) event.widget;
            String value = text.getText();
            event.doit = value.matches("^\\d*$"); //$NON-NLS-1$
            if (event.doit) {
                errors.remove(text);
                if (errors.isEmpty()) {
                    setErrorMessage(null);
                    setValid(true);
                } else {
                    setErrorMessage(errors.values().iterator().next());
                }
            } else {
                errors.put(text, errMsg);
                setErrorMessage(errMsg);
                setValid(false);
            }
        }
    }

    protected class DoubleTextValidatorKeyListener extends KeyAdapter {

        private String errMsg;
        private double minValue;
        private double maxValue;

        public DoubleTextValidatorKeyListener(String errMsg) {
            super();
            this.errMsg = errMsg;
        }

        public DoubleTextValidatorKeyListener(String errMsg, double minValue, double maxValue) {
            super();
            this.errMsg = errMsg;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        @Override
        public void keyReleased(KeyEvent event) {
            Text text = (Text) event.widget;
            String value = text.getText();
            boolean valid = value.length() > 0;
            if (valid) {
                valid = value.matches("^\\d*\\.?\\d*$"); //$NON-NLS-1$
            }
            if (valid && minValue != maxValue) {
                double doubleValue = Double.parseDouble(value);
                valid = doubleValue >= minValue && doubleValue <= maxValue;
            }
            event.doit = valid;
            if (event.doit) {
                errors.remove(text);
                if (errors.isEmpty()) {
                    setErrorMessage(null);
                    setValid(true);
                } else {
                    setErrorMessage(errors.values().iterator().next());
                }
            } else {
                errors.put(text, errMsg);
                setErrorMessage(errMsg);
                setValid(false);
            }
        }
    }
}
