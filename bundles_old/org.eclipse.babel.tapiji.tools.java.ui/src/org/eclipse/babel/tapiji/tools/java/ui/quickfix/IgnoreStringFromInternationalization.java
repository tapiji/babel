/*******************************************************************************
 * Copyright (c) 2012 Martin Reiterer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Reiterer - initial API and implementation
 ******************************************************************************/
package org.eclipse.babel.tapiji.tools.java.ui.quickfix;

import org.eclipse.babel.tapiji.tools.core.Logger;
import org.eclipse.babel.tapiji.tools.java.ui.util.ASTutilsUI;
import org.eclipse.babel.tapiji.tools.java.util.ASTutils;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;

public class IgnoreStringFromInternationalization implements IMarkerResolution2 {

    @Override
    public String getLabel() {
        return "Ignore String";
    }

    @Override
    public void run(IMarker marker) {
        IResource resource = marker.getResource();

        CompilationUnit cu = ASTutilsUI.getAstRoot(ASTutilsUI
                .getCompilationUnit(resource));

        ITextFileBufferManager bufferManager = FileBuffers
                .getTextFileBufferManager();
        IPath path = resource.getRawLocation();

        try {
            bufferManager.connect(path, LocationKind.NORMALIZE, null);
            ITextFileBuffer textFileBuffer = bufferManager.getTextFileBuffer(
                    path, LocationKind.NORMALIZE);
            IDocument document = textFileBuffer.getDocument();

            int position = marker.getAttribute(IMarker.CHAR_START, 0);

            ASTutils.createReplaceNonInternationalisationComment(cu, document,
                    position, 1);
            textFileBuffer.commit(null, false);

        } catch (JavaModelException e) {
            Logger.logError(e);
        } catch (CoreException e) {
            Logger.logError(e);
        }

    }

    @Override
    public String getDescription() {
        return "Ignore String from Internationalization";
    }

    @Override
    public Image getImage() {
        // TODO Auto-generated method stub
        return null;
    }

}
