package org.eclipse.e4.babel.editor.text.document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

public final class FileResource implements IFileDocument {
    protected static final int DEFAULT_FILE_CAPACITY = 10 * 1024;
    protected static final String DEFAULT_ENCODING = "UTF-8";
    protected static final String TAG = IFileDocument.class.getSimpleName();

    protected Document document;
    public File file;

    private FileResource(final File file) {
        super();
        this.file = file;
    }

    @Override
    public void saveDocument() {
        // TODO Auto-generated method stub
    }

    @Override
    public IDocument getDocument() {
        if (null != document) {
            return document;
            
        }
        document = new Document();
        try {
            document.set(readFile());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return document;

    }


    private String readFile() throws IOException {
      return  new String(Files.readAllBytes(file.toPath()));
    }

    private void writeFile(final String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }
    
    
    @Override
    public String getEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getModificationTimeStamp(String format) {
        String date = "";
        if (format != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            if (null != document) {
                date = simpleDateFormat.format(new Date(document.getModificationStamp()));
            }
        }
        return date;
    }

    @Override
    public int getNumberOfLines() {
        return 0;
    }

    @Override
    public FileType getFileType() {
        return FileType.FILE;
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }

    public static IFileDocument create(File file) {
        return new FileResource(file);
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public String getFileExtension() {
        return "properties";
    }  

    @Override
    public IFile getIFile() {
        String path = file.getAbsolutePath(); // P:\Workspace\AST\TEST\src\messages\Messages_de.properties
        int index = path.indexOf("src");
        String pathBeforeSrc = path.substring(0, index - 1);
        int lastIndexOf = pathBeforeSrc.lastIndexOf(File.separatorChar);
        String projectName = path.substring(lastIndexOf + 1, index - 1);
        String relativeFilePath = path.substring(index, path.length());

        return ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName).getFile(relativeFilePath);
    }

    @Override
    public String getName() {
        return file.getName();
    }

}
