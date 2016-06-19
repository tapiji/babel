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
package org.eclipse.babel.tapiji.tools.core.model;

import java.io.Serializable;

import org.eclipse.babel.tapiji.tools.core.extensions.ILocation;
import org.eclipse.core.resources.IFile;

public class SLLocation implements Serializable, ILocation {

    private static final long serialVersionUID = 1L;
    private IFile file = null;
    private int startPos = -1;
    private int endPos = -1;
    private final String literal;
    private Serializable data;

    public SLLocation(final IFile file, final int startPos, final int endPos, final String literal) {
        super();
        this.file = file;
        this.startPos = startPos;
        this.endPos = endPos;
        this.literal = literal;
    }

    public IFile getFile() {
        return file;
    }

    public void setFile(final IFile file) {
        this.file = file;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(final int startPos) {
        this.startPos = startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(final int endPos) {
        this.endPos = endPos;
    }

    public String getLiteral() {
        return literal;
    }

    public Serializable getData() {
        return data;
    }

    public void setData(final Serializable data) {
        this.data = data;
    }

}
