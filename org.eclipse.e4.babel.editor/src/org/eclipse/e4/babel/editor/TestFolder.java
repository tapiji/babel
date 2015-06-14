package org.eclipse.e4.babel.editor;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipselabs.e4.tapiji.logger.Log;


public class TestFolder extends CTabFolder {


    @Inject
    public TestFolder(Composite parent) {
        super(parent, SWT.BOTTOM);
        Log.d("WAHH", "TESTFOLDER");
    }

    @PostConstruct
    public void postConstruct(Composite parent, EPartService partService) {
        Log.d("WAHH", "postConstruct");

        new TestItem(this);
        new TestItem(this);
        new TestItem(this);
        new TestItem(this);
        new TestItem(this);
        new TestItem(this);
        new TestItem(this);
        new TestItem(this);
        new TestItem(this);
        new TestItem(this);


    }

}
