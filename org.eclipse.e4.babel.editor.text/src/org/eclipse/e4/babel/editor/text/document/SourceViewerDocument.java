package org.eclipse.e4.babel.editor.text.document;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class SourceViewerDocument {

    private static final int DEFAULT_FILE_CAPACITY = 10 * 1024;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String TAG = SourceViewerDocument.class.getSimpleName();
    private final IFile file;

    private SourceViewerDocument(final IFile file) {
        this.file = file;
    }

    public static SourceViewerDocument create(final IFile file) {
        return new SourceViewerDocument(file);
    }

    public IDocument getDocument() {
        final InputStream content = getContent();
        if (content == null) {
            return null;
        }
        final IDocument document = new Document();
        try (InputStreamReader inputStreamReader = new InputStreamReader(content, getEncoding()); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            final StringBuffer buffer = new StringBuffer(DEFAULT_FILE_CAPACITY);
            final char[] readBuffer = new char[2048];
            int number = bufferedReader.read(readBuffer);
            while (number > 0) {
                buffer.append(readBuffer, 0, number);
                number = bufferedReader.read(readBuffer);
            }
            document.set(buffer.toString());
        } catch (final Exception exception) {
            Log.e(TAG, exception);
        }
        return document;
    }

    private InputStream getContent() {
        InputStream content = null;
        try {
            content = file.getContents();
        } catch (final CoreException e) {
            Log.e(TAG, e);
        }
        return content;
    }

    private String getEncoding() {
        String encoding = null;
        try {
            encoding = file.getCharset();
        } catch (final CoreException e) {
            Log.e(TAG, e);
        }

        if (null == encoding) {
            return DEFAULT_ENCODING;
        } else {
            return encoding;
        }
    }
}
