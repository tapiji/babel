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
package org.eclipse.e4.babel.core.internal.resource.workspace;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.internal.file.AbstractFileCreator;
import org.eclipse.e4.babel.core.internal.file.workspace.StandardPropertiesFileCreator;
import org.eclipse.e4.babel.core.internal.resource.ResourceFactory;
import org.eclipse.e4.babel.core.utils.FileUtils;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyIFileResource;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;


/**
 * Responsible for creating resources related to a standard
 * directory structure.
 * 
 * @author Pascal Essiembre
 * @author Alexander Bieber
 */
public class StandardResourceFactory extends ResourceFactory {

    private AbstractFileCreator fileCreator;

    public StandardResourceFactory() {
        super();
    }

    @Override
    public boolean isResponsible(IPropertyResource fileDocument) throws CoreException {
        return true;
    }

    /**
     * Constructor.
     * 
     * @param site editor site
     * @param file file used to open all related files
     * @throws CoreException problem creating factory
     */
    @Override
    public void init(IPropertyResource fileDocument) throws CoreException {
        IFile file = fileDocument.getIFile();

        IFile[] resources = getResources(fileDocument);
        for (int i = 0; i < resources.length; i++) {
            IPropertyResource document = PropertyIFileResource.create(resources[i]);
            Locale locale = FileUtils.parseBundleName(document);
            SourceEditor sourceEditor = createEditor(document, locale);
            if (sourceEditor != null) {
                addSourceEditor(sourceEditor.getLocale(), sourceEditor);
            }
        }

        fileCreator = new StandardPropertiesFileCreator(file.getParent().getFullPath().toString(), FileUtils.getBundleName(fileDocument), file.getFileExtension());
        setDisplayName(FileUtils.getDisplayName(fileDocument));
    }

    /**
     * @see org.eclipse.e4.babel.core.resource.essiembre.eclipse.rbe.ui.editor.resources.ResourceFactory
     *      #getPropertiesFileCreator()
     */
    @Override
    public AbstractFileCreator getPropertiesFileCreator() {
        return fileCreator;
    }

    protected static IFile[] getResources(IPropertyResource fileDocument) {
        String regex = FileUtils.getPropertiesFileRegEx(fileDocument);
        List<IResource> resources = new ArrayList<>();
        try {
            resources = Arrays.asList(fileDocument.getIFile().getParent().members());
        } catch (CoreException e) {

            e.printStackTrace();
        }

        List<IResource> validResources = new ArrayList<>();
        resources.stream().forEach(resource -> {
            String resourceName = resource.getName();
            if (resource instanceof IFile && resourceName.matches(regex)) {
                validResources.add(resource);
            }
        });
        return validResources.toArray(new IFile[] {});
    }

}
