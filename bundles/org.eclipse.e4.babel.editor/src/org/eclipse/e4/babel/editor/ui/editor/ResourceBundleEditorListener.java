package org.eclipse.e4.babel.editor.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.babel.logger.Log;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;

public final class ResourceBundleEditorListener implements IPartListener {

    private static String TAG = ResourceBundleEditorListener.class.getSimpleName();
    private static final List<ResourceBundleEditor> availaibleResourceBundleEditors = new ArrayList<ResourceBundleEditor>();
    private static final List<ResourceBundleEditor> hiddenResourceBundleEditors = new ArrayList<ResourceBundleEditor>();

    @Override
    public void partVisible(MPart part) {
	final Object obj = part.getObject();
	if (obj instanceof ResourceBundleEditor) {
	    Log.d(TAG, "------------------------------ partVisible " + part);

	    if (!availaibleResourceBundleEditors.contains(obj)) {
		availaibleResourceBundleEditors.add((ResourceBundleEditor) obj);
	    }
	   // Log.d(TAG, "Manager Size: " + availaibleResourceBundleEditors.size());
	}

    }

    @Override
    public void partHidden(MPart part) {
	Object obj = part.getObject();
	if (obj instanceof ResourceBundleEditor) {
	    Log.d(TAG, "------------------------------ partHidden " + part);
	    if (availaibleResourceBundleEditors.contains(obj)) {
		availaibleResourceBundleEditors.remove(obj);
		//Log.d(TAG, "Available Manager Size: " + availaibleResourceBundleEditors.size());
	    }

	    if (!hiddenResourceBundleEditors.contains(obj)) {
		hiddenResourceBundleEditors.add((ResourceBundleEditor) obj);
		Log.d(TAG, "Hidden Manager Size: " + hiddenResourceBundleEditors.size());
	    }

	}
    }

    @Override
    public void partDeactivated(MPart part) {
	final Object obj = part.getObject();
	if (obj instanceof ResourceBundleEditor) {
	    Log.d(TAG, "------------------------------ partDeactivated " + part);
	    if (availaibleResourceBundleEditors.contains(obj)) {
		availaibleResourceBundleEditors.remove(obj);
	    }
	  //  Log.d(TAG, "Manager Size: " + availaibleResourceBundleEditors.size());
	}
    }

    @Override
    public void partBroughtToTop(MPart part) {
	// no-op
    }

    @Override
    public void partActivated(MPart part) {
	Object obj = part.getObject();
	if (obj instanceof ResourceBundleEditor) {
	    Log.d(TAG, "------------------------------ partActivated " + part);
	    if (!availaibleResourceBundleEditors.contains(obj)) {
		availaibleResourceBundleEditors.add((ResourceBundleEditor) obj);
		//Log.d(TAG, "Available Manager Size: " + availaibleResourceBundleEditors.size());
	    }

	    if (hiddenResourceBundleEditors.contains(obj)) {
		hiddenResourceBundleEditors.remove(obj);
		Log.d(TAG, "Hidden Manager Size: " + hiddenResourceBundleEditors.size());
	    }
	}
    }

    public int getAvailableEditorSize() {
	return availaibleResourceBundleEditors.size();
    }
    
    public int getHiddenEditorsSize() {
	return availaibleResourceBundleEditors.size();
    }

    public void dispose() {
	availaibleResourceBundleEditors.clear();
    }
}
