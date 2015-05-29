package org.eclipse.e4.babel.editor.ui.editor;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.tools.services.IResourcePool;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipselabs.e4.tapiji.logger.Log;


public class ResourceBundleEditor {


    private static final String TAG = ResourceBundleEditor.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    private TreeViewer treeViewer;

    @Inject
    private EMenuService menuService;

    @Inject
    IResourcePool resourcePool;

    @PostConstruct
    public void createControl(Composite parent, Shell shell) {
        Log.d(TAG, "treeViewerPart");


        //        CTabFolder folder = new CTabFolder(parent, SWT.BOTTOM);
        //        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
        //        folder.setLayoutData(data);
        //
        //        CTabItem cTabItem1 = new CTabItem(folder, SWT.NONE);
        //        cTabItem1.setText("Tab1");
        //        CTabItem cTabItem2 = new CTabItem(folder, SWT.NONE);
        //        cTabItem2.setText("Tab2");
        //        CTabItem cTabItem3 = new CTabItem(folder, SWT.NONE);
        //        cTabItem3.setText("Tab3");
        //
        //        Text text = new Text(folder, SWT.BORDER);
        //        text.setText("Hello");
        //        cTabItem1.setControl(text);


        //Composite composite = new Composite(parent, SWT.NONE);

        //        CTabFolder tabFolder = new CTabFolder(parent, SWT.BORDER | SWT.BOTTOM);
        //        GridData gd_tabFolder = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        //        tabFolder.setLayoutData(gd_tabFolder);
        //        tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
        //
        //        CTabItem tbtmParameterOptions = new CTabItem(tabFolder, SWT.NONE);
        //        tbtmParameterOptions.setText("Manage Parameters");
        //
        //        CTabItem tbtmParameterOptions2 = new CTabItem(tabFolder, SWT.NONE);
        //        tbtmParameterOptions2.setText("Manage Parameters");
        //        CTabItem tbtmParameterOptions3 = new CTabItem(tabFolder, SWT.NONE);
        //        tbtmParameterOptions3.setText("Manage Parameters");
        //        CTabItem tbtmParameterOptions4 = new CTabItem(tabFolder, SWT.NONE);
        //        tbtmParameterOptions4.setText("Manage Parameters");
        //
        //        ToolBar parameterToolbar = new ToolBar(tabFolder, SWT.FLAT | SWT.RIGHT);
        //        tbtmParameterOptions.setControl(parameterToolbar);
        //
        //        ToolItem refreshItem = new ToolItem(parameterToolbar, SWT.PUSH);
        //        //refreshItem.setImage(NTImageCache.getImage("refresh.png"));
        //
        //        Text textArea = new Text(parent, SWT.MULTI | SWT.BORDER);
        //        textArea.setText("bla bla bla...[SWT.MULTI]");
        //
        //        refreshItem.setText("Refresh");
        //        refreshItem.setToolTipText("Refresh");
        //
        //        ToolItem editParameterItem = new ToolItem(parameterToolbar, SWT.PUSH);
        //        //editParameterItem.setImage(NTImageCache.getImage("edit_item.png"));
        //        editParameterItem.setText("Edit Parameter");
        //        editParameterItem.setToolTipText("Edit Parameter");
        //
        //        ToolItem deleteParameterItem = new ToolItem(parameterToolbar, SWT.PUSH);
        //        // deleteParameterItem.setImage(NTImageCache.getImage("delete_item.png"));
        //        deleteParameterItem.setText("Delete Parameter");
        //        deleteParameterItem.setToolTipText("Delete Parameter");
        //
        //        tabFolder.setSelection(tbtmParameterOptions);
        //        tabFolder.setSimple(true);
        //        GridLayout layout = new GridLayout();
        //        layout.marginWidth = 0;
        //        layout.marginHeight = 0;
        //        layout.verticalSpacing = 3;
        //        Composite composite = new Composite(parent, SWT.NONE);
        //        composite.setLayout(layout);
        //        //Create SWT ToolBar
        //        ToolBar toolbar = new ToolBar(composite, SWT.HORIZONTAL);
        //        toolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        //
        //        // Create SWT Text


        //        // Create SWT Text [SWT.MULTI]
        //        Text textArea = new Text(composite, SWT.MULTI | SWT.BORDER);
        //        textArea.setText("bla bla bla...[SWT.MULTI]");
        //        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        //        gridData.heightHint = 100;
        //        textArea.setLayoutData(gridData);
        //        // Create SWT Label
        //        Label label = new Label(composite, SWT.NONE);
        //        label.setText("bla bla bla...");
        //        label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        //        // Create Button
        //        Button button = new Button(composite, SWT.BORDER);
        //        button.setText("SWT Button");
        //        // Create Button [SWT.CHECK]
        //        Button checkbox = new Button(composite, SWT.CHECK);
        //        checkbox.setText("SWT Button [SWT.CHECK]");
        //        // Create Button [SWT.RADIO]
        //        Button radio = new Button(composite, SWT.RADIO);
        //        radio.setText("SWT Button [SWT.RADIO]");
        //        // Create Combo
        //        Combo combo = new Combo(composite, SWT.BORDER);
        //        combo.add("Item 1");
        //        combo.add("Item 2");
        //        combo.select(0);
        // Create CTabFolder
        CTabFolder tabFolder = new CTabFolder(parent, SWT.BOTTOM);
        tabFolder.setUnselectedCloseVisible(true);
        tabFolder.setUnselectedImageVisible(true);

        /*
         * Text textArea = new Text(tabFolder, SWT.NONE);
         * textArea.setText("bla bla bla...[SWT.MULTI]");
         * GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
         * gridData.heightHint = 100;
         * textArea.setLayoutData(gridData);
         */

        Text text = new Text(tabFolder, SWT.BORDER);
        text.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

        final StyledText styledText = new StyledText(tabFolder, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
        styledText.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(2, 1).create());


        CTabItem tabItem1 = new CTabItem(tabFolder, SWT.NONE);
        tabItem1.setText("Tab 1");
        tabItem1.setControl(text);

        CTabItem tabItem2 = new CTabItem(tabFolder, SWT.NONE);
        tabItem2.setText("Tab 2");
        tabItem2.setControl(text);
        tabFolder.setSelection(0);


        /*
         * TabFolder tabFolder = new TabFolder(parent, SWT.BOTTOM);
         * TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
         * tbtmNewItem.setText("New Item");
         * TabItem tbtmNewItem_1 = new TabItem(tabFolder, SWT.NONE);
         * tbtmNewItem_1.setText("New Item");
         * TabItem tbtmNewItem_2 = new TabItem(tabFolder, SWT.NONE);
         * tbtmNewItem_2.setText("New Item");
         */
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
