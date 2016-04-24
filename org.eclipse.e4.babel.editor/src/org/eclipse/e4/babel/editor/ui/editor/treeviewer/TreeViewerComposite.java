package org.eclipse.e4.babel.editor.ui.editor.treeviewer;


import org.eclipse.e4.babel.resource.BabelResourceConstants;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipselabs.e4.tapiji.logger.Log;


public class TreeViewerComposite extends Composite {

	private IBabelResourceProvider resourceProvider;
    private static final String TAG = TreeViewerComposite.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    public static TreeViewerComposite create(final Composite sashForm, EMenuService menuService, IBabelResourceProvider resourceProvider) {
        return new TreeViewerComposite(sashForm, menuService, resourceProvider);
    }

    private TreeViewerComposite(final Composite parent, EMenuService menuService, IBabelResourceProvider resourceProvider) {
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
    	btnFlat.addSelectionListener(flatListener);
    	
    	final Button btnHierarchical = new Button(composite, SWT.TOGGLE);
    	btnHierarchical.setImage(resourceProvider.loadImage(BabelResourceConstants.IMG_TREE_HIERARCHICAL));
    	btnHierarchical.addSelectionListener(hierarchicalListener);
    	    	
    	Label separator = new Label(composite, SWT.VERTICAL | SWT.SEPARATOR);
    	GridData layoutData = new GridData();
    	layoutData.heightHint = btnHierarchical.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
    	separator.setLayoutData(layoutData);
    	
    	final Button btnExpandAll = new Button(composite, SWT.PUSH);
    	btnExpandAll.setImage(resourceProvider.loadImage(BabelResourceConstants.IMG_EXPAND_ALL));
    	btnExpandAll.addSelectionListener(expandAllListener);
    	
    	final Button btnCollapseAll = new Button(composite, SWT.PUSH);
    	btnCollapseAll.setImage(resourceProvider.loadImage(BabelResourceConstants.IMG_COLLAPSE_ALL));
    	btnCollapseAll.addSelectionListener(collapseAllListener);
    }
    
    private void middleView() {
        TreeViewer treeViewer = new TreeViewer(this, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        treeViewer.setContentProvider(new KeyTreeContentProvider());
        KeyTreeLabelProvider labelProvider = new KeyTreeLabelProvider();
        treeViewer.setLabelProvider(labelProvider);
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
    	btnAddKey.addSelectionListener(addKeyListener);
    }

    
    private SelectionAdapter hierarchicalListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			System.out.println("HierarchicalListener");
		}
	};
	
	private SelectionAdapter flatListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			System.out.println("FlatListener");
		}
	};
	
	private SelectionAdapter expandAllListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			System.out.println("ExpandAllListener");
		}
	};
	
	private SelectionAdapter collapseAllListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			System.out.println("CollapseAllListener");
		}
	};
	
	private SelectionAdapter addKeyListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			System.out.println("AddKeyListener");
		}
	};
}
