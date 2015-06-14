package org.eclipse.e4.babel.editor;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.resource.ITapijiResourceProvider;
import org.eclipselabs.e4.tapiji.resource.TapijiResourceConstants;


public class TestItem extends CTabItem {


    private ITapijiResourceProvider resourceProvider;
    private Label expandIcon;

    public TestItem(CTabFolder parent) {
        super(parent, SWT.NONE);
        Log.d("WAHH", "TestItem");

    }

    public void postConstruct(final ITapijiResourceProvider resourceProvider, final int style) {
        Log.d("WAHH", "postConstruct");
        this.resourceProvider = resourceProvider;

        Composite comp = new Composite(getParent(), SWT.NONE);

        comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        final GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.marginWidth = 0;
        comp.setLayout(gridLayout);

        final Composite composite = new Composite(comp, SWT.NONE);
        final GridLayout gl_composite = new GridLayout(3, false);
        gl_composite.verticalSpacing = 0;
        gl_composite.marginWidth = 0;
        gl_composite.marginHeight = 0;
        composite.setLayout(gl_composite);

        expandIcon = new Label(composite, SWT.FLAT);
        expandIcon.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_COLLAPSE));


        final Link localeName = new Link(composite, SWT.NONE);
        localeName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
        localeName.setText("<a>Albanien</a>");

    }

}
