package org.eclipse.e4.babel.core.internal.resource.external;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.internal.resource.ResourceFactory;
import org.eclipse.e4.babel.core.utils.FileUtils;
import org.eclipse.e4.babel.editor.text.document.FileResource;
import org.eclipse.e4.babel.editor.text.document.IFileDocument;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;


public class ExternalResourceFactory extends ResourceFactory {

    @Override
    public boolean isResponsible(IFileDocument fileDocument) throws CoreException {
        return true;
    }

    @Override
    public void init(IFileDocument fileDocument) throws CoreException {
        FileResource fileRes = (FileResource) fileDocument;

        List<File> resources = getResources(fileRes.file);

        for (File file : resources) {
            IFileDocument document = FileResource.create(file);
            Locale locale = FileUtils.parseBundleName(document);
            SourceEditor sourceEditor = createEditor(document, locale);
            if (sourceEditor != null) {
                addSourceEditor(sourceEditor.getLocale(), sourceEditor);
            }
        }
        setDisplayName(FileUtils.getDisplayName(fileDocument));
    }

    private static List<File> getResources(File file) {
        File parentDir = file.getParentFile();
        List<File> resources = null;
        if (parentDir != null) {
            resources = Stream.of(parentDir.listFiles()).filter(item -> {
                System.out.println("Item: " + item.getName());
                return (item instanceof File);
            }).collect(Collectors.toCollection(ArrayList::new));

        }

        return resources;
    }

}
