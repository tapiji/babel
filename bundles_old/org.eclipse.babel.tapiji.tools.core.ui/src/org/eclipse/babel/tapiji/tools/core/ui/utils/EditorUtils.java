package org.eclipse.babel.tapiji.tools.core.ui.utils;

import java.util.Iterator;

import org.eclipse.babel.editor.IMessagesEditor;
import org.eclipse.babel.tapiji.tools.core.Logger;
import org.eclipse.babel.tapiji.tools.core.extensions.ILocation;
import org.eclipse.babel.tapiji.tools.core.ui.Activator;
import org.eclipse.babel.tapiji.tools.core.ui.ResourceBundleManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;

public class EditorUtils {

    /** Marker constants **/
    public static final String MARKER_ID = Activator.PLUGIN_ID
            + ".StringLiteralAuditMarker";
    public static final String RB_MARKER_ID = Activator.PLUGIN_ID
            + ".ResourceBundleAuditMarker";

    /** Editor ids **/
    public static final String RESOURCE_BUNDLE_EDITOR = "com.essiembre.rbe.eclipse.editor.ResourceBundleEditor";

    public static IEditorPart openEditor(IWorkbenchPage page, IFile file,
            String editor) {
        // open the rb-editor for this file type
        try {
            return IDE.openEditor(page, file, editor);
        } catch (PartInitException e) {
            Logger.logError(e);
        }
        return null;
    }

    public static IEditorPart openEditor(IWorkbenchPage page, IFile file,
            String editor, String key) {
        // open the rb-editor for this file type and selects given msg key
        IEditorPart part = openEditor(page, file, editor);
        if (part instanceof IMessagesEditor) {
            IMessagesEditor msgEditor = (IMessagesEditor) part;
            msgEditor.setSelectedKey(key);
        }
        return part;
    }

    public static void updateMarker(IMarker marker) {
        FileEditorInput input = new FileEditorInput(
                (IFile) marker.getResource());

        AbstractMarkerAnnotationModel model = (AbstractMarkerAnnotationModel) getAnnotationModel(marker);
        IDocument doc = JavaUI.getDocumentProvider().getDocument(input);

        try {
            model.updateMarker(doc, marker, getCurPosition(marker, model));
        } catch (CoreException e) {
            Logger.logError(e);
        }
    }

    public static IAnnotationModel getAnnotationModel(IMarker marker) {
        FileEditorInput input = new FileEditorInput(
                (IFile) marker.getResource());

        return JavaUI.getDocumentProvider().getAnnotationModel(input);
    }

    private static Position getCurPosition(IMarker marker,
            IAnnotationModel model) {
        Iterator iter = model.getAnnotationIterator();
        Logger.logInfo("Updates Position!");
        while (iter.hasNext()) {
            Object curr = iter.next();
            if (curr instanceof SimpleMarkerAnnotation) {
                SimpleMarkerAnnotation annot = (SimpleMarkerAnnotation) curr;
                if (marker.equals(annot.getMarker())) {
                    return model.getPosition(annot);
                }
            }
        }
        return null;
    }

    public static boolean deleteAuditMarkersForResource(IResource resource) {
        try {
            if (resource != null && resource.exists()) {
                resource.deleteMarkers(MARKER_ID, false,
                        IResource.DEPTH_ZERO);
                deleteAllAuditRBMarkersFromRB(resource);
            }
        } catch (CoreException e) {
            Logger.logError(e);
            return false;
        }
        return true;
    }

    /*
     * Delete all RB_MARKER from the hole resourcebundle
     */
    private static boolean deleteAllAuditRBMarkersFromRB(IResource resource)
            throws CoreException {
        // if (resource.findMarkers(RB_MARKER_ID, false,
        // IResource.DEPTH_INFINITE).length > 0)
        if (RBFileUtils.isResourceBundleFile(resource)) {
            String rbId = RBFileUtils
                    .getCorrespondingResourceBundleId((IFile) resource);
            if (rbId == null) {
                return true; // file in no resourcebundle
            }

            ResourceBundleManager rbmanager = ResourceBundleManager
                    .getManager(resource.getProject());
            for (IResource r : rbmanager.getResourceBundles(rbId)) {
                r.deleteMarkers(RB_MARKER_ID, false, IResource.DEPTH_INFINITE);
            }
        }
        return true;
    }

    public static void reportToMarker(String string, ILocation problem,
            int cause, String key, ILocation data, String context) {
        try {
            IMarker marker = problem.getFile().createMarker(MARKER_ID);
            marker.setAttribute(IMarker.MESSAGE, string);
            marker.setAttribute(IMarker.CHAR_START, problem.getStartPos());
            marker.setAttribute(IMarker.CHAR_END, problem.getEndPos());
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
            marker.setAttribute("cause", cause);
            marker.setAttribute("key", key);
            marker.setAttribute("context", context);
            if (data != null) {
                marker.setAttribute("bundleName", data.getLiteral());
                marker.setAttribute("bundleStart", data.getStartPos());
                marker.setAttribute("bundleEnd", data.getEndPos());
            }

            // TODO: init attributes
            marker.setAttribute("stringLiteral", string);
        } catch (CoreException e) {
            Logger.logError(e);
            return;
        }

        Logger.logInfo(string);
    }

    public static void reportToRBMarker(String string, ILocation problem,
            int cause, String key, String problemPartnerFile, ILocation data,
            String context) {
        try {
            if (!problem.getFile().exists()) {
                return;
            }
            IMarker marker = problem.getFile().createMarker(RB_MARKER_ID);
            marker.setAttribute(IMarker.MESSAGE, string);
            marker.setAttribute(IMarker.LINE_NUMBER, problem.getStartPos()); // TODO
                                                                             // better-dirty
                                                                             // implementation
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
            marker.setAttribute("cause", cause);
            marker.setAttribute("key", key);
            marker.setAttribute("context", context);
            if (data != null) {
                marker.setAttribute("language", data.getLiteral());
                marker.setAttribute("bundleLine", data.getStartPos());
            }
            marker.setAttribute("stringLiteral", string);
            marker.setAttribute("problemPartner", problemPartnerFile);
        } catch (CoreException e) {
            Logger.logError(e);
            return;
        }

        Logger.logInfo(string);
    }

}
