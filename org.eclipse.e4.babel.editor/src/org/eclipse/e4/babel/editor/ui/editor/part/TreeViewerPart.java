package org.eclipse.e4.babel.editor.ui.editor.part;


import javax.annotation.PostConstruct;
import org.eclipse.swt.widgets.Composite;
import org.eclipselabs.e4.tapiji.logger.Log;


public class TreeViewerPart {


    private static final String TAG = TreeViewerPart.class.getSimpleName();

    @PostConstruct
    public void createControl(Composite parent) {
        Log.d(TAG, "treeViewerPart");
    }
}
