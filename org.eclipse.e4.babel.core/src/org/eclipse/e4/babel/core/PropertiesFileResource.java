package org.eclipse.e4.babel.core;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipselabs.e4.tapiji.logger.Log;


public class PropertiesFileResource {


    private static final String TAG = PropertiesFileResource.class.getSimpleName();


    public void getText(final IFile file) {
        InputStream content = getContent(file);
        if (content == null) {
            return;
        }

        try (InputStreamReader inputStreamReader = new InputStreamReader(content, getEncoding(file)); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            final StringBuffer buffer = new StringBuffer(15 * 1024);
            final char[] readBuffer = new char[2048];
            int n = bufferedReader.read(readBuffer);
            while (n > 0) {
                buffer.append(readBuffer, 0, n);
                n = bufferedReader.read(readBuffer);
            }
        } catch (final Exception e) {
            Log.e(TAG, e);
        }
    }

    private InputStream getContent(IFile file) {
        InputStream content = null;
        try {
            content = file.getContents();
        } catch (final CoreException e) {
            Log.e(TAG, e);
        }
        return content;
    }


    private String getEncoding(final IFile file) {
        String encoding = null;
        try {
            encoding = file.getCharset();
        } catch (final CoreException e) {
            Log.e(TAG, e);
        }

        if (null == encoding) {
            return "UTF-8";
        } else {
            return encoding;
        }
    }
}
