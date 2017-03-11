package org.eclipse.e4.babel.editor.preference;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public abstract class APreferencePage extends PreferencePage {

    public static final String TOPIC_REFRESH_LAYOUT = "TOPIC_GUI/REFRESH_LAYOUT";
    protected final int indentPixels = 20;
    @Inject
    IEventBroker eventBroker;
    private boolean addToRooot;

    public APreferencePage(final String title) {
	super(title);
    }

    protected Composite createFieldComposite(final Composite parent) {
	return createFieldComposite(parent, 0);
    }

    protected Composite createFieldComposite(final Composite parent, final int indent) {
	final Composite composite = new Composite(parent, SWT.NONE);
	final GridLayout gridLayout = new GridLayout(2, false);
	gridLayout.marginWidth = indent;
	gridLayout.marginHeight = 0;
	gridLayout.verticalSpacing = 0;
	composite.setLayout(gridLayout);
	return composite;
    }

    protected void createLabel(final Composite parent, final String text) {
	new Label(parent, SWT.NONE).setText(text);
    }

    protected void redrawLayout() {
	eventBroker.post(TOPIC_REFRESH_LAYOUT, "refresh");
    }

    protected void addToRoot(boolean addToRoot) {
	this.addToRooot = addToRoot;
    }

    public boolean isAddToRooot() {
	return addToRooot;
    }

    public static int getWidthInChars(final Control control, final int numOfChars) {
	final GC gc = new GC(control);
	final Point extent = gc.textExtent("W");
	gc.dispose();
	return numOfChars * extent.x;
    }

    protected void setWidthInChars(final Control field, final int widthInChars) {
	final GridData gridData = new GridData();
	gridData.widthHint = getWidthInChars(field, widthInChars);
	field.setLayoutData(gridData);
    }
}
