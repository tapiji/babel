package org.eclipse.e4.babel.editor.ui.editor;


import java.util.Random;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.resource.ITapijiResourceProvider;


public class ResourceBundleEditor {


    private static final String TAG = ResourceBundleEditor.class.getSimpleName();
    private static final String BOTTOM_MENU_ID = "org.eclipse.e4.babel.editor.toolbar.toolbar";
    private static final String TREE_VIEWER_MENU_ID = "org.eclipse.e4.babel.editor.popupmenu.treePopupMenu";

    private TreeViewer treeViewer;

    @Inject
    private EMenuService menuService;

    @Inject
    private MPart part;

    @Inject
    private ITapijiResourceProvider resourceProvider;

    private String stackId;

    @Inject
    MApplication application;

    @Inject
    EModelService modelService;

    @Inject
    EPartService partService;


    private void setMinimumCharacters() {
        MPartStack stacki = (MPartStack) modelService.find(stackId, application);
        // if (!(changedElement instanceof MPartStack) || !(changedElement.getWidget() instanceof CTabFolder)) return;

        CTabFolder ctf = (CTabFolder) stacki.getWidget();
        ctf.setMinimumCharacters(40);
    }

    @PostConstruct
    public void createControl(final Composite parent, final Shell shell, MWindow window) {
        Log.d(TAG, "treeViewerPart");

        IEditorInput file = (IEditorInput) part.getTransientData().get("FILE");
        stackId = (String) part.getTransientData().get("STACK");

        //     stack.Log.d(TAG, "Stack: " + stack.toString());

        setMinimumCharacters();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();
        addNewLocale();


        Log.d(TAG, "Stack: " + part);

        partService.showPart(part, PartState.ACTIVATE);

        //     Log.d(TAG, "WAHHH" + file.toString());

        // this.tabFolder = createTabFolder(parent);


        //        final SashForm sashForm = new SashForm(tabFolder, SWT.SMOOTH);
        //
        //        TreeViewerComposite.create(sashForm, menuService);
        //        I18nComposite.create(sashForm, resourceProvider);
        //
        //        sashForm.setWeights(new int[] {25, 75});
        //createFirstTab(sashForm);

        //createTabs(partService);

        //  tabFolder.setSelection(0);
    }

    private void addNewLocale() {
        MPartStack stacki = (MPartStack) modelService.find(stackId, application);
        MPart part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
        part.setLabel("Locale: " + new Random().nextInt(55));

        stacki.getChildren().add(part);
    }

    private void createFirstTab(final SashForm sashForm) {
        //  final CTabItem firstTabItem = createItem(0, sashForm);
        // firstTabItem.setText("Eigenschaften");
        //   firstTabItem.setImage(resourceProvider.loadImage(TapijiResourceConstants.IMG_RESOURCE_BUNDLE));
    }
}
