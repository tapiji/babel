package org.eclipse.e4.babel.editor.ui.editor.treeviewer;


import org.eclipse.e4.babel.editor.ui.editor.treeviewer.KeyTreeContract.Presenter;
import org.eclipse.e4.babel.editor.ui.editor.treeviewer.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.treeviewer.provider.KeyTreeLabelProvider;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipselabs.e4.tapiji.logger.Log;


public class TreeViewerView extends Composite implements KeyTreeContract.View {

    private Presenter presenter;
    private IBabelResourceProvider resourceProvider;
    private TreeViewer treeViewer;
    private static final String TAG = TreeViewerView.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    public static TreeViewerView create(final Composite sashForm, EMenuService menuService, IBabelResourceProvider resourceProvider) {
        return new TreeViewerView(sashForm, menuService, resourceProvider);
    }

    private TreeViewerView(final Composite parent, EMenuService menuService, IBabelResourceProvider resourceProvider) {
        super(parent, SWT.BORDER);
        this.resourceProvider = resourceProvider;
        Log.d(TAG, "treeViewerPart");
        setLayout(new GridLayout(1, false));
        createView();
    }

    private void createView() {
        topView();
        middleView();
        bottomView();
    }

    private void topView() {
        final Composite composite = new Composite(this, SWT.NONE);
        final GridLayout gridLayout = new GridLayout(5, false);
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 0, 0));

        final Button btnFlat = new Button(composite, SWT.TOGGLE);
        btnFlat.setImage(resourceProvider.loadImage(BabelResourceConstants.IMG_TREE_FLAT));
        btnFlat.addListener(SWT.Selection, (e) -> System.out.println("Button click " + e));

        final Button btnHierarchical = new Button(composite, SWT.TOGGLE);
        btnHierarchical.setImage(resourceProvider.loadImage(BabelResourceConstants.IMG_TREE_HIERARCHICAL));
        btnHierarchical.addListener(SWT.Selection, (e) -> System.out.println("Button click " + e));

        Label separator = new Label(composite, SWT.VERTICAL | SWT.SEPARATOR);
        GridData layoutData = new GridData();
        layoutData.heightHint = btnHierarchical.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
        separator.setLayoutData(layoutData);

        final Button btnExpandAll = new Button(composite, SWT.PUSH);
        btnExpandAll.setImage(resourceProvider.loadImage(BabelResourceConstants.IMG_EXPAND_ALL));
        btnExpandAll.addListener(SWT.Selection, (e) -> System.out.println("Button click " + e));

        final Button btnCollapseAll = new Button(composite, SWT.PUSH);
        btnCollapseAll.setImage(resourceProvider.loadImage(BabelResourceConstants.IMG_COLLAPSE_ALL));
        btnCollapseAll.addListener(SWT.Selection, (e) -> System.out.println("Button click " + e));
    }

    private void middleView() {
        treeViewer = new TreeViewer(this, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        treeViewer.setContentProvider(new KeyTreeContentProvider());
        treeViewer.setLabelProvider(new KeyTreeLabelProvider());
        treeViewer.setUseHashlookup(true);
        treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 0, 0));
    }

    private void bottomView() {
        final Composite composite = new Composite(this, SWT.NONE);
        final GridLayout gridLayout = new GridLayout(2, false);
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 0, 0));

        Text textBox = new Text(composite, SWT.BORDER);
        textBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        final Button btnAddKey = new Button(composite, SWT.PUSH);
        btnAddKey.setText("Add");
        btnAddKey.setToolTipText("Add new key");
        btnAddKey.addListener(SWT.Selection, (e) -> System.out.println("Button click " + e));
    }

    public TreeViewer getTreeViewer() {
        return this.treeViewer;
    }

    public void treeViewVisibility(boolean visible) {
        setVisible(visible);

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
