package org.eclipse.e4.babel.editor.text.document;


import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;


public interface IFileDocument {

    public void saveDocument();
    
    public IDocument getDocument();

    public String getEncoding();

    public String getModificationTimeStamp(final String format);

    public int getNumberOfLines();

    public FileType getFileType();
    
    public String getFileName();
    
    public String getFileExtension();

    public void dispose();

    public IFile getIFile();

    public String getName();
}
