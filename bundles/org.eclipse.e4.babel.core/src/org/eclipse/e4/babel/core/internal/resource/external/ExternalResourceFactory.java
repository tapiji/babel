package org.eclipse.e4.babel.core.internal.resource.external;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.internal.file.AbstractFileCreator;
import org.eclipse.e4.babel.core.internal.file.external.ExternalFileCreator;
import org.eclipse.e4.babel.core.internal.resource.ResourceFactory;
import org.eclipse.e4.babel.core.utils.BabelUtils;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyFileResource;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;


public final class ExternalResourceFactory extends ResourceFactory {

    private ExternalFileCreator fileCreator;

    private ExternalResourceFactory() {
        super();
    }

    @Override
    public boolean isResponsible(IPropertyResource fileDocument) throws CoreException {
        return true;
    }

    @Override
    public void init(IPropertyResource fileDocument) throws CoreException, IOException {
        PropertyFileResource fileRes = (PropertyFileResource) fileDocument;

        List<File> resources = getResources(fileRes.file);

        for (File file : resources) {
            IPropertyResource document = PropertyFileResource.create(file);
            Locale locale = BabelUtils.parseBundleName(document);
            SourceEditor sourceEditor = createEditor(document, locale);
            if (sourceEditor != null) {
                addSourceEditor(sourceEditor.getLocale(), sourceEditor);
            }
        }
        String absolutePath = fileRes.file.getAbsolutePath();
        fileCreator = new ExternalFileCreator(absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)), BabelUtils.getBundleName(fileDocument), fileRes.getFileExtension());
        setDisplayName(BabelUtils.getShortDisplayName(fileDocument));
        setResourceLocation(absolutePath);
    }

    /**
     * Gets file creator
     */
    @Override
    public AbstractFileCreator getPropertiesFileCreator() {
        return fileCreator;
    }

    /**
     * Gets resources from directory
     *
     * @param file
     * @return List<File>
     */
    private static List<File> getResources(File file) {
        File parentDir = file.getParentFile();
        List<File> resources = null;
        if (parentDir != null) {
            resources = Stream.of(parentDir.listFiles()).filter(isResourceBundle).collect(Collectors.toCollection(ArrayList::new));
        }
        return resources;
    }

    /**
     * Checks if file is a resource bundle
     */
    private static Predicate<File> isResourceBundle = file -> file instanceof File && BabelUtils.isResourceBundle(file.getName());

    @Override
    public boolean isExternal() {
        return true;
    }

    /**
     * @param fileDocument
     * @return
     * @throws CoreException
     * @throws IOException
     */
    public static ExternalResourceFactory create(IPropertyResource fileDocument) throws CoreException, IOException {
        ExternalResourceFactory factory = new ExternalResourceFactory();
        factory.init(fileDocument);
        return factory;
    }
}
