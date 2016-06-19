package org.eclipse.e4.babel.editor.text.document;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class SourceViewerDocument {

    private static final int DEFAULT_FILE_CAPACITY = 10 * 1024;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String TAG = SourceViewerDocument.class.getSimpleName();
    private final IFile file;
    private Document document;

    private SourceViewerDocument(final IFile file) {
        this.file = file;
    }

    public static SourceViewerDocument create(final IFile file) {
        return new SourceViewerDocument(file);
    }

    public void saveDocument() {
        final Charset charset = Charset.forName(getEncoding());
        final CharsetEncoder encoder = charset.newEncoder();

        try {
            final ByteBuffer byteBuffer = encoder.encode(CharBuffer.wrap(document.get()));
            byte[] bytes;
            if (byteBuffer.hasArray()) {
                bytes = byteBuffer.array();
            } else {
                bytes = new byte[byteBuffer.limit()];
                byteBuffer.get(bytes);
            }

            try (final ByteArrayInputStream stream = new ByteArrayInputStream(bytes, 0, byteBuffer.limit())) {
                file.setContents(stream, true, true, null);
            }
        } catch (final Exception e) {
            Log.e(TAG, e);
        }
    }

    @NonNull
    public IDocument getDocument() {
        if (null != document) {
            return document;
        }
        document = new Document();
        try (InputStream content = file.getContents();
                        InputStreamReader inputStreamReader = new InputStreamReader(content, getEncoding());
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

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

    private String getEncoding() {
        String encoding = null;
        try {
            encoding = file.getCharset();
        } catch (final CoreException e) {
            Log.e(TAG, e);
        }
        return (null == encoding) ? DEFAULT_ENCODING : encoding;
    }

    public int getNumberOfLines() {
        if (null != document) {
            return document.getNumberOfLines();
        } else {
            return 0;
        }
    }

    @NonNull
    public String getModificationTimeStamp(final String format) {
        String date = "";
        if (format != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            if (null != document) {
                date = simpleDateFormat.format(new Date(document.getModificationStamp()));
            }
        }
        return date;
    }

    public void dispose() {
        if (null != document) {
            document.set("");
        }
    }
}
