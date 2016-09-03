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
package org.eclipse.e4.babel.core.internal.resource;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.api.IResourceFactory;
import org.eclipse.e4.babel.core.internal.file.AbstractFileCreator;
import org.eclipse.e4.babel.core.internal.resource.external.ExternalResourceFactory;
import org.eclipse.e4.babel.core.utils.BabelUtils;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyFileType;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;


/**
 * Responsible for creating resources related to a given file structure.
 * <p>
 * This class is also the abstract base class for implementations of a
 * {@link ResourceFactory} as well as static entry point to access the
 * responsible one.
 * </p>
 * 
 * @author Pascal Essiembre
 * @author cuhiodtick
 * @author Christian Behon
 */
public abstract class ResourceFactory implements IResourceFactory {

    private final static String TAG = ResourceFactory.class.getSimpleName();

    public ResourceFactory() {
        super();
    }

    /**
     * A sorted map of {@link SourceEditor}s. Sorted by key (Locale).
     */
    private Map<Locale, SourceEditor> sourceEditors = new TreeMap<>(new Comparator<Locale>() {

        @Override
        public int compare(final Locale locale1, final Locale locale2) {
            return UIUtils.getDisplayName(locale1).compareToIgnoreCase(UIUtils.getDisplayName(locale2));
        }
    });

    /**
     * The {@link AbstractFileCreator} used to create new files.
     */
    private AbstractFileCreator propertiesFileCreator;

    /**
     * The short displayname
     */
    private String displayName;
    
    /**
     * The short displayname
     */
    private String resourceLocation;


    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the editor display name of this factory.
     * 
     * @param displayName
     *        The display name to set.
     * @see #getDisplayName()
     */
    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String getResourceLocation() {
        return resourceLocation;
    }

    /**
     * Sets the editor display name of this factory.
     * 
     * @param displayName
     *        The display name to set.
     * @see #getDisplayName()
     */
    protected void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    public List<SourceEditor> getSourceEditors() {
        List<SourceEditor> editors = new ArrayList<>(sourceEditors.values().size());
        sourceEditors.values().stream().forEach(editor -> editors.add(editor));
        return editors;
    }

    @Override
    public SourceEditor addResource(IPropertyResource fileDocument, Locale locale) {
        if (sourceEditors.containsKey(locale)) {
            throw new IllegalArgumentException("ResourceFactory already contains a resource for locale " + locale);
        }
        SourceEditor editor = createEditor(fileDocument, locale);
        addSourceEditor(editor.getLocale(), editor);
        return editor;
    }

    protected void addSourceEditor(Locale locale, SourceEditor sourceEditor) {
        sourceEditors.put(locale, sourceEditor);
    }

    @Override
    public AbstractFileCreator getPropertiesFileCreator() {
        return propertiesFileCreator;
    }

    protected void setPropertiesFileCreator(AbstractFileCreator fileCreator) {
        this.propertiesFileCreator = fileCreator;
    }

    @Override
    public abstract boolean isResponsible(IPropertyResource fileDocument) throws CoreException;

    @Override
    public abstract void init(IPropertyResource fileDocument) throws CoreException, IOException;

    /**
     * Creates a resource factory based on given arguments.
     * 
     * @param site
     *        eclipse editor site
     * @param file
     *        file used to create factory
     * @return An initialized resource factory, or <code>null</code> if no
     *         responsible one could be found
     * @throws CoreException
     *         problem creating factory
     * @throws IOException
     */
    public static IResourceFactory createFactory(IPropertyResource fileDocument) throws CoreException, IOException {

        if (fileDocument.getFileType().equals(PropertyFileType.IFILE)) {
            IResourceFactory[] factories = ResourceFactoryDescriptor.getContributedResourceFactories();

            if (fileDocument.getFileType().equals(PropertyFileType.IFILE)) {
                for (int i = 0; i < factories.length; i++) {
                    IResourceFactory factory = factories[i];
                    if (factory.isResponsible(fileDocument)) {
                        factory.init(fileDocument);
                        return factory;
                    }
                }
            }
            return null;
        } else {
            ExternalResourceFactory factory = new ExternalResourceFactory();
            factory.init(fileDocument);
            return factory;
        }
    }

    /**
     * Creates a resource factory based on given arguments and excluding
     * factories of the given class.
     * <p>
     * This might be used to get the {@link SourceEditor}s from other factories
     * while initializing an other factory.
     * </p>
     * 
     * @param site
     *        eclipse editor site
     * @param file
     *        file used to create factory
     * @param childFactoryClass
     *        The class of factory to exclude.
     * @return An initialized resource factory, or <code>null</code> if no
     *         responsible one could be found
     * @throws CoreException
     *         problem creating factory
     * @throws IOException
     */
    public static IResourceFactory createParentFactory(IPropertyResource fileDocument, Class<?> childFactoryClass) throws CoreException, IOException {
        IResourceFactory[] factories = ResourceFactoryDescriptor.getContributedResourceFactories();
        for (int i = 0; i < factories.length; i++) {
            IResourceFactory factory = factories[i];
            if (!factory.getClass().equals(childFactoryClass) && factory.isResponsible(fileDocument)) {
                factory.init(fileDocument);
                return factory;
            }
        }
        return null;
    }

    protected SourceEditor createEditor(IPropertyResource fileDocument, Locale locale) {
        return SourceEditor.create(fileDocument, locale);
    }

    /**
     * Returns the resource bundle file resources that match the specified file
     * name.
     * 
     * @param file
     *        the file to match
     * @return array of file resources, empty if none matches
     * @throws CoreException
     */
    protected static IFile[] getResources(IPropertyResource file) throws CoreException {
        String regex = BabelUtils.getPropertiesFileRegEx(file);
        final Collection<IResource> validResources = new ArrayList<>();
        Stream.of(file.getIFile().getParent().members()).forEach(resource -> {
            String resourceName = resource.getName();
            if (resource instanceof IFile && resourceName.matches(regex)) {
                validResources.add(resource);
            }
        });
        return validResources.toArray(new IFile[] {});
    }

    @Override
    public boolean isExternal() {
        return false;
    }
}
