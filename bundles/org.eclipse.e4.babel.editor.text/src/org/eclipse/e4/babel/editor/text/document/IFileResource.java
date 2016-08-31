package org.eclipse.e4.babel.editor.text.document;


import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.logger.Log;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;


public final class IFileResource implements IFileDocument {
    protected static final int DEFAULT_FILE_CAPACITY = 10 * 1024;
    protected static final String DEFAULT_ENCODING = "UTF-8";
    protected static final String TAG = IFileDocument.class.getSimpleName();

    protected Document document;
    private IFile file;

    private IFileResource(IFile file) {
        super();
        this.file = file;
    }

    public IFile getFile() {
        return file;
    }

    public static IFileResource create(final IFile file) {
        return new IFileResource(file);
    }

    
    private String readFile() throws IOException {
        return  new String(Files.readAllBytes(file.getRawLocation().toFile().toPath()));
      }

      private void writeFile(final String content) throws IOException {
          Files.write(file.getRawLocation().toFile().toPath(), content.getBytes());
      }
    
    @Override
    public void saveDocument() {
        
        try {
            writeFile(document.get());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       /* final Charset charset = Charset.forName(getEncoding());
        final CharsetEncoder encoder = charset.newEncoder();

        ByteBuffer byteBuffer;
        try {
            byteBuffer = encoder.encode(CharBuffer.wrap(document.get()));

            byte[] bytes;
            if (byteBuffer.hasArray()) {
                bytes = byteBuffer.array();
            } else {
                bytes = new byte[byteBuffer.limit()];
                byteBuffer.get(bytes);
            }

            try (final ByteArrayInputStream stream = new ByteArrayInputStream(bytes, 0, byteBuffer.limit())) {
                file.setContents(stream, true, true, null);
            } catch (CoreException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    @NonNull
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
        /*try (InputStream content = file.getContents(); InputStreamReader inputStreamReader = new InputStreamReader(content, getEncoding()); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

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
        }*/
        return document;
    }

    @Override
    public int getNumberOfLines() {
        if (null != document) {
            return document.getNumberOfLines();
        } else {
            return 0;
        }
    }
    
    public IContainer getParent() {
        return file.getParent();
    }

    @Override
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

    @Override
    public void dispose() {
        if (null != document) {
            document.set("");
        }
    }

    @Override
    public String getEncoding() {
        String encoding = null;
        try {
            encoding = file.getCharset();
        } catch (final CoreException e) {
            Log.e(TAG, e);
        }
        return (null == encoding) ? DEFAULT_ENCODING : encoding;
    }


    @Override
    public FileType getFileType() {
        return FileType.IFILE;
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public String getFileExtension() {
        return file.getFileExtension();
    }

    @Override
    public IFile getIFile() {
        return file;
    }

    @Override
    public String getName() {
        return file.getName();
    }
    
   
}
