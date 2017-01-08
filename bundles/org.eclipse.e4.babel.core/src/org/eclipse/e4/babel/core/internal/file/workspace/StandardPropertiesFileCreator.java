/*
 * Copyright (C) 2003-2014 Pascal Essiembre
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.e4.babel.core.internal.file.workspace;


import java.io.IOException;
import java.util.Locale;
import org.eclipse.core.resources.IFile;
//import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.babel.core.internal.file.AbstractFileCreator;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyIFileResource;
import org.eclipse.e4.babel.logger.Log;


/**
 * Creates a standard properties file.
 * 
 * @author Pascal Essiembre
 */
public final class StandardPropertiesFileCreator extends AbstractFileCreator {

    private static final String TAG = StandardPropertiesFileCreator.class.getSimpleName();
    private final String dir;
    private final String baseFileName;
    private final String extension;

    /**
     * Creates new property file
     * 
     * @param dir directory in which to create the file
     * @param baseFileName base name of file to create
     * @param extension file extension
     */
    public StandardPropertiesFileCreator(final String dir, final String baseFileName, final String extension) {
        super();
        this.dir = dir;
        this.baseFileName = baseFileName;
        this.extension = extension;
        Log.d(TAG, "Directory: " + dir + " BaseFilename: " + baseFileName + " Extesnion: " + extension);
    }


    /*
     * (non-Javadoc)
     * @see org.eclipse.e4.babel.core.internal.file.AbstractFileCreator#createPropertyResource(java.util.Locale,
     * java.lang.String)
     */
    @Override
    protected IPropertyResource createPropertyResource(final Locale locale, final String content) throws IOException {
        IPath path = new Path(dir);
        path = path.append(baseFileName);
        if (locale != null) {
            path = new Path(path.toString() + "_" + locale.toString());
        }
        path.addFileExtension(extension);
       // final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
       // if (file.exists()) {
        //    throw new IOException("File already exists: " + file.getName());
       // }
       // Log.d(TAG, "FILE: " + file.toString());
        return null;//PropertyIFileResource.create(file);
    }
}
