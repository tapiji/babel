package org.eclipse.e4.babel.editor.ui.editor.tree;


import java.util.Collections;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract.View;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeContract.Presenter;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;
import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class KeyTreeView extends Composite implements KeyTreeContract.View {

  private static final String TAG = KeyTreeView.class.getSimpleName();
  private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";
  private static final String COMMAND_DELETE_KEY = "org.eclipse.e4.babel.editor.command.deleteKey";

  private final View rbeView;
  private Presenter presenter;
  private TreeViewer treeViewer;
  private Text addKeyTextBox;

  private Cursor waitCursor;
  private Cursor defaultCursor;

  public static KeyTreeView create(final Composite sashForm, ResourceBundleEditorContract.View resourceBundleEditor) {
    KeyTreeView treevIew = new KeyTreeView(sashForm, resourceBundleEditor);
    KeyTreePresenter.create(treevIew, resourceBundleEditor);
    return treevIew;
  }

  private KeyTreeView(final Composite parent, final ResourceBundleEditorContract.View resourceBundleEditor) {
    super(parent, SWT.BORDER);
    this.rbeView = resourceBundleEditor;
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

    final Button btnHierarchical = new Button(composite, SWT.TOGGLE);
    final Button btnFlat = new Button(composite, SWT.TOGGLE);
    btnFlat.setImage(rbeView.getResourceProvider()
                            .loadImage(BabelResourceConstants.IMG_TREE_FLAT));
    btnFlat.setToolTipText("Tree");
    btnFlat.addListener(SWT.Selection, (e) -> {
      if (btnFlat.getSelection()) {
        btnHierarchical.setSelection(false);
        btnHierarchical.setEnabled(true);
        btnFlat.setEnabled(false);
        setCursor(waitCursor);
        setVisible(false);
        presenter.changeToFlatTree();
        setSelectedKeyTreeItem(presenter.getKeyTree()
                                        .getKeyTreeItem(addKeyTextBox.getText()));
        setVisible(true);
        setCursor(defaultCursor);
        treeViewer.refresh(true);

      }
    });


    btnHierarchical.setImage(rbeView.getResourceProvider()
                                    .loadImage(BabelResourceConstants.IMG_TREE_HIERARCHICAL));
    btnHierarchical.setToolTipText("Tree");
    btnHierarchical.addListener(SWT.Selection, (e) -> {
      if (btnHierarchical.getSelection()) {
        btnFlat.setSelection(false);
        btnFlat.setEnabled(true);
        btnHierarchical.setEnabled(false);
        setCursor(waitCursor);
        setVisible(false);
        if (PropertyPreferences.getInstance()
                               .isEditorTreeExpanded()) {
          treeViewer.getControl()
                    .setRedraw(false);
          treeViewer.expandAll();
          treeViewer.getControl()
                    .setRedraw(true);
        }
        presenter.changeToHierarchicalTree();
        setSelectedKeyTreeItem(presenter.getKeyTree()
                                        .getKeyTreeItem(addKeyTextBox.getText()));
        setVisible(true);
        setCursor(defaultCursor);
        treeViewer.refresh(true);
      }

    });

    Label separator = new Label(composite, SWT.VERTICAL | SWT.SEPARATOR);
    GridData layoutData = new GridData();
    layoutData.heightHint = btnHierarchical.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
    separator.setLayoutData(layoutData);

    final Button btnExpandAll = new Button(composite, SWT.PUSH | SWT.FLAT);
    btnExpandAll.setImage(rbeView.getResourceProvider()
                                 .loadImage(BabelResourceConstants.IMG_EXPAND_ALL));
    btnExpandAll.addListener(SWT.Selection, (e) -> expandAll());
    btnExpandAll.setToolTipText("Expand all");

    final Button btnCollapseAll = new Button(composite, SWT.PUSH | SWT.FLAT);
    btnCollapseAll.setToolTipText("Collapse all");
    btnCollapseAll.setImage(rbeView.getResourceProvider()
                                   .loadImage(BabelResourceConstants.IMG_COLLAPSE_ALL));
    btnCollapseAll.addListener(SWT.Selection, (e) -> collapseAll());

    if (PropertyPreferences.getInstance()
                           .isEditorTreeHierachical()) {
      btnHierarchical.setSelection(true);
      btnHierarchical.setEnabled(false);
    } else {
      btnFlat.setSelection(true);
      btnFlat.setEnabled(false);
    }
  }

  private void middleView() {
    treeViewer = new TreeViewer(this, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    treeViewer.setUseHashlookup(true);
    treeViewer.getTree()
              .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 0, 0));
    if (PropertyPreferences.getInstance()
                           .isEditorTreeExpanded()) {
      treeViewer.expandAll();
    }
    treeViewer.addSelectionChangedListener((event) -> {
      IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
      rbeView.getSelectionService()
             .setSelection(presenter.getSelection(selection));
      String selectedKey = presenter.getSelectedKeyFromSelection(selection);
      if (selectedKey != null) {
        addKeyTextBox.setText(selectedKey);
        presenter.getKeyTree()
                 .selectKey(selectedKey);
      }
      Log.d(TAG, "Selection:" + ((IStructuredSelection) treeViewer.getSelection()).getFirstElement());
    });

    treeViewer.getTree()
              .addKeyListener(new KeyAdapter() {

                @SuppressWarnings("restriction")
                public void keyReleased(KeyEvent event) {
                  if (event.character == SWT.DEL) {
                    rbeView.getHandlerService()
                           .executeHandler((ParameterizedCommand) rbeView.getCommandService()
                                                                         .createCommand(COMMAND_DELETE_KEY, Collections.emptyMap()));
                  }
                }
              });

    treeViewer.getTree()
              .addMouseListener(new MouseAdapter() {

                public void mouseDoubleClick(MouseEvent event) {
                  final Object element = rbeView.getSelectionService()
                                                .getSelection();
                  if (treeViewer.isExpandable(element)) {
                    if (treeViewer.getExpandedState(element)) {
                      treeViewer.collapseToLevel(element, 1);
                    } else {
                      treeViewer.expandToLevel(element, 1);
                    }
                  }
                }
              });

    rbeView.getMenuService()
           .registerContextMenu(treeViewer.getTree(), TREE_VIEWER_MENU_ID);
  }

  private void bottomView() {
    final Composite composite = new Composite(this, SWT.NONE);
    final GridLayout gridLayout = new GridLayout(2, false);
    composite.setLayout(gridLayout);
    composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 0, 0));

    addKeyTextBox = new Text(composite, SWT.BORDER);
    addKeyTextBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));


    final Button btnAddKey = new Button(composite, SWT.PUSH);
    btnAddKey.setText("Add");
    btnAddKey.setEnabled(false);
    btnAddKey.setToolTipText("Add new key");
    btnAddKey.addListener(SWT.Selection, (e) -> presenter.addKey(addKeyTextBox.getText()));

    addKeyTextBox.addListener(SWT.KeyUp, (e) -> {
      if (e.character == SWT.CR && btnAddKey.isEnabled()) {
        presenter.addKey(addKeyTextBox.getText());
      }
    });
    addKeyTextBox.addModifyListener((e) -> btnAddKey.setEnabled(presenter.isNewKey(addKeyTextBox.getText())));
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
  public KeyTree getKeyTree() {
    return presenter.getKeyTree();
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    presenter.init();
  }

  @Override
  public void setSelectedKeyTreeItem(KeyTreeItem item) {
    if (item != null) {
      treeViewer.setSelection(new StructuredSelection(item), true);
    }
  }

  @Override
  public void expandAll() {
    treeViewer.expandAll();
    treeViewer.getTree()
              .forceFocus();
  }

  @Override
  public void collapseAll() {
    treeViewer.collapseAll();
    treeViewer.getTree()
              .forceFocus();
  }

  public void dispose() {
    super.dispose();

    waitCursor.dispose();
    defaultCursor.dispose();

    addKeyTextBox.dispose();
    presenter.dispose();
  }
}
