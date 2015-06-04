package org.eclipse.e4.babel.editor.ui.editor.composite;


import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipselabs.e4.tapiji.logger.Log;


public class TreeViewerComposite extends Composite {


    private Text text;


    private static final String TAG = TreeViewerComposite.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";


    public static TreeViewerComposite create(final Composite sashForm) {
        return new TreeViewerComposite(sashForm);
    }


    private TreeViewerComposite(final Composite sashForm) {
        super(sashForm, SWT.NONE);
        Log.d(TAG, "treeViewerPart");

        setLayout(new GridLayout(1, false));

        final Composite composite = new Composite(this, SWT.NONE);
        final GridLayout gl_composite = new GridLayout(6, false);
        gl_composite.marginWidth = 0;
        gl_composite.marginHeight = 0;
        gl_composite.horizontalSpacing = 0;
        composite.setLayout(gl_composite);
        composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));

        final Button btnNewButton = new Button(composite, SWT.FLAT | SWT.TOGGLE);
        btnNewButton.setText("New Button");

        final ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);

        //menuService.registerContextMenu(toolBar, "org.eclipse.e4.babel.editor.toolbar.myToolbar");
        /*
         * ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
         * tltmNewItem.setText("New Item");
         */

        final Button btnNewButton_1 = new Button(composite, SWT.FLAT | SWT.TOGGLE);
        btnNewButton_1.setText("New Button");

        final Label label = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);

        final GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_label.heightHint = 20;
        label.setLayoutData(gd_label);
        final Button btnNewButton_2 = new Button(composite, SWT.FLAT);
        btnNewButton_2.setText("New Button");

        final Button button = new Button(composite, SWT.FLAT);
        button.setText("New Button");

        final Composite composite_1 = new Composite(this, SWT.NONE);
        final GridLayout gl_composite_1 = new GridLayout(1, false);
        gl_composite_1.verticalSpacing = 0;
        gl_composite_1.marginWidth = 0;
        gl_composite_1.horizontalSpacing = 0;
        gl_composite_1.marginHeight = 0;
        composite_1.setLayout(gl_composite_1);
        composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        final TreeViewer treeViewer = new TreeViewer(composite_1, SWT.BORDER);
        final Tree tree = treeViewer.getTree();
        tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        tree.setBounds(0, 0, 83, 83);

        final Composite composite_2 = new Composite(this, SWT.NONE);
        final GridLayout gl_composite_2 = new GridLayout(3, false);
        gl_composite_2.horizontalSpacing = 0;
        gl_composite_2.marginWidth = 0;
        composite_2.setLayout(gl_composite_2);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        final Control text = new Text(composite_2, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(composite_2, SWT.NONE);

        final Button btnNewButton_3 = new Button(composite_2, SWT.NONE);
        final GridData gd_btnNewButton_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton_3.heightHint = 23;
        btnNewButton_3.setLayoutData(gd_btnNewButton_3);
        btnNewButton_3.setBounds(0, 0, 83, 29);
        btnNewButton_3.setText("New Button");


    }

}
