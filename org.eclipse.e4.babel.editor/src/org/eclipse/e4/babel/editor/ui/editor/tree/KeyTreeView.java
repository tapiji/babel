package org.eclipse.e4.babel.editor.ui.editor.tree;


import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeContract.Presenter;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class KeyTreeView extends Composite implements KeyTreeContract.View {

    private Presenter presenter;
    private IBabelResourceProvider resourceProvider;
    private TreeViewer treeViewer;
    private EMenuService menuService;
    private ESelectionService selectionService;
    private static final String TAG = KeyTreeView.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    public static KeyTreeView create(final Composite sashForm, EMenuService menuService, IBabelResourceProvider resourceProvider, KeyTree keyTree,
                    ESelectionService selectionService) {
        KeyTreeView treevIew = new KeyTreeView(sashForm, menuService, resourceProvider, selectionService);
        KeyTreeContract.Presenter pres = KeyTreePresenter.create(treevIew, keyTree, resourceProvider);
        return treevIew;
    }

    private KeyTreeView(final Composite parent, EMenuService menuService, IBabelResourceProvider resourceProvider, ESelectionService selectionService) {
        super(parent, SWT.BORDER);
        this.menuService = menuService;
        this.selectionService = selectionService;
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
        treeViewer.setUseHashlookup(true);
        treeViewer.getTree()
                  .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 0, 0));
        treeViewer.addSelectionChangedListener((event) -> {
            selectionService.setSelection(((IStructuredSelection) treeViewer.getSelection()).getFirstElement());
            Log.d(TAG, "Selection:" + ((IStructuredSelection) treeViewer.getSelection()).getFirstElement());
        });
        
        treeViewer.getTree().addMouseListener(new MouseAdapter() {
            public void mouseDoubleClick(MouseEvent event) {
                final Object element = selectionService.getSelection();
                if (treeViewer.isExpandable(element)) {
                    if (treeViewer.getExpandedState(element)) {
                        treeViewer.collapseToLevel(element, 1);
                    } else {
                        treeViewer.expandToLevel(element, 1);
                    }
                }
                System.out.println("AGAIN: "+selectionService.getSelection());
            }
        });
        
        menuService.registerContextMenu(treeViewer.getTree(), TREE_VIEWER_MENU_ID);
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
        btnAddKey.setEnabled(false);
        btnAddKey.setToolTipText("Add new key");
        btnAddKey.addListener(SWT.Selection, (e) -> presenter.addKey(textBox.getText()));

        textBox.addListener(SWT.KeyUp, (e) -> {
            if (e.character == SWT.CR && btnAddKey.isEnabled()) {
                presenter.addKey(textBox.getText());
            }
        });
        textBox.addModifyListener((e) -> btnAddKey.setEnabled(presenter.isNewKey(textBox.getText())));
    }

    public TreeViewer getTreeViewer() {
        return this.treeViewer;
    }

    public void treeViewVisibility(boolean visible) {
        setVisible(visible);
    }

    @Override
    public void updateKeyTree(KeyTree keyTree) {
        treeViewer.setInput(keyTree);

    }

    @Override
    public void setTreeViewerContentProvider(KeyTreeContentProvider provider) {
        treeViewer.setContentProvider(provider);
    }

    @Override
    public void setTreeViewerLabelProvider(KeyTreeLabelProvider provider) {
        treeViewer.setLabelProvider(provider);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        presenter.init();
    }
}
