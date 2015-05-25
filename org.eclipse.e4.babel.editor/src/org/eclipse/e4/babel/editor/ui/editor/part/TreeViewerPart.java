package org.eclipse.e4.babel.editor.ui.editor.part;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.tools.services.IResourcePool;
import org.eclipse.e4.ui.services.EMenuService;
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


public class TreeViewerPart {


    private static final String TAG = TreeViewerPart.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    private TreeViewer treeViewer;

    @Inject
    private EMenuService menuService;

    @Inject
    IResourcePool resourcePool;

    @PostConstruct
    public void createControl(Composite parent) {
        Log.d(TAG, "treeViewerPart");


        parent.setLayout(new GridLayout(1, false));

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gl_composite = new GridLayout(6, false);
        gl_composite.marginWidth = 0;
        gl_composite.marginHeight = 0;
        gl_composite.horizontalSpacing = 0;
        composite.setLayout(gl_composite);
        composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));

        Button btnNewButton = new Button(composite, SWT.FLAT | SWT.TOGGLE);
        btnNewButton.setText("New Button");

        ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);

        menuService.registerContextMenu(toolBar, "org.eclipse.e4.babel.editor.toolbar.myToolbar");
        /*
         * ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
         * tltmNewItem.setText("New Item");
         */

        Button btnNewButton_1 = new Button(composite, SWT.FLAT | SWT.TOGGLE);
        btnNewButton_1.setText("New Button");

        Label label = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);

        GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_label.heightHint = 20;
        label.setLayoutData(gd_label);
        Button btnNewButton_2 = new Button(composite, SWT.FLAT);
        btnNewButton_2.setText("New Button");

        Button button = new Button(composite, SWT.FLAT);
        button.setText("New Button");

        Composite composite_1 = new Composite(parent, SWT.NONE);
        GridLayout gl_composite_1 = new GridLayout(1, false);
        gl_composite_1.verticalSpacing = 0;
        gl_composite_1.marginWidth = 0;
        gl_composite_1.horizontalSpacing = 0;
        gl_composite_1.marginHeight = 0;
        composite_1.setLayout(gl_composite_1);
        composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        TreeViewer treeViewer = new TreeViewer(composite_1, SWT.BORDER);
        Tree tree = treeViewer.getTree();
        tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        tree.setBounds(0, 0, 83, 83);

        Composite composite_2 = new Composite(parent, SWT.NONE);
        GridLayout gl_composite_2 = new GridLayout(3, false);
        gl_composite_2.horizontalSpacing = 0;
        gl_composite_2.marginWidth = 0;
        composite_2.setLayout(gl_composite_2);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        Control text = new Text(composite_2, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(composite_2, SWT.NONE);

        Button btnNewButton_3 = new Button(composite_2, SWT.NONE);
        GridData gd_btnNewButton_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton_3.heightHint = 23;
        btnNewButton_3.setLayoutData(gd_btnNewButton_3);
        btnNewButton_3.setBounds(0, 0, 83, 29);
        btnNewButton_3.setText("New Button");

        /*
         * btnNewButton.setImage(resourcePool.getImageUnchecked(TapijiResourceProvider.IMG_LAYOUT_HIERARCHICAL));
         * btnNewButton_1.setImage(resourcePool.getImageUnchecked(TapijiResourceProvider.IMG_LAYOUT_FLAT));
         * btnNewButton_2.setImage(resourcePool.getImageUnchecked(TapijiResourceProvider.IMG_EXPAND_ALL));
         * button.setImage(resourcePool.getImageUnchecked(TapijiResourceProvider.IMG_COLLAPSE_ALL));
         */


        //GridData gridData = new GridData();
        // gridData.verticalAlignment = GridData.FILL;
        //gridData.grabExcessVerticalSpace = true;
        // gridData.horizontalAlignment = GridData.FILL;
        // gridData.grabExcessHorizontalSpace = true;


        // menuService.registerContextMenu(treeViewer.getControl(), TREE_VIEWER_MENU_ID);

        /*
         * treeViewer.setContentProvider(new KeyTreeContentProvider());
         * labelProvider = new KeyTreeLabelProvider();
         * treeViewer.setLabelProvider(labelProvider);
         * treeViewer.setUseHashlookup(true);
         * treeViewer.setInput(keyTree);
         */

    }
}
