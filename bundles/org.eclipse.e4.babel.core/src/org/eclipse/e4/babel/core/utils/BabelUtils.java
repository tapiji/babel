/*******************************************************************************
 * Copyright (c) 2012 Martin Reiterer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * @author Martin Reiterer
 * @author Christian Behon
 * @author Pascal Essiembre
 * @since 0.0.1
 ******************************************************************************/
package org.eclipse.e4.babel.core.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public final class BabelUtils {

    private BabelUtils() {
        // Only static access
    }

    public static final String[] XML_FILE_ENDINGS = new String[] {"*.xml"};

    public static final String ENCODING_TYPE_UTF_16 = "UTF-16";
    
    /** Token to replace in a regular expression with a bundle name. */
    private static final String TOKEN_BUNDLE_NAME = "BUNDLENAME";

    /** Token to replace in a regular expression with a file extension. */
    private static final String TOKEN_FILE_EXTENSION = "FILEEXTENSION";

    /** Regex to match a properties file. */
    private static final String PROPERTIES_FILE_REGEX = "^(" + TOKEN_BUNDLE_NAME + ")" + "((_[a-z]{2,3})|(_[a-z]{2,3}_[A-Z]{2})" + "|(_[a-z]{2,3}_[A-Z]{2}_\\w*))?(\\." + TOKEN_FILE_EXTENSION + ")$";

    /** External project name **/
    public static final String EXTERNAL_RB_PROJECT_NAME = "ExternalResourceBundles";

    /** Allowed property file endings **/
    public static final String[] PROPERTY_FILE_ENDINGS = new String[] {"*.properties"};


    /**
     * TODO
     * @param fileDocument
     * @return
     */
    public static String getBundleName(IPropertyResource fileDocument) {
        final String regex = "^(.*?)((_[a-z]{2,3})|(_[a-z]{2,3}_[A-Z]{2})|(_[a-z]{2,3}_[A-Z]{2}_\\w*))?(\\." + fileDocument.getFileExtension() + ")$";
        return fileDocument.getName().replaceFirst(regex, "$1");
    }

    /**
     * Creates the name for the Resource Bundle Editor
     * TODO
     * @param fileDocument
     * @return String 
     */
    public static String getShortDisplayName(IPropertyResource fileDocument) {
        return getBundleName(fileDocument) + "[...]." + fileDocument.getFileExtension();
    }
 
    /**
     * TODO
     * @param fileDocument
     * @return
     */
    public static String getPropertiesFileRegEx(IPropertyResource fileDocument) {
        return PROPERTIES_FILE_REGEX.replaceFirst(TOKEN_BUNDLE_NAME, getBundleName(fileDocument)).replaceFirst(TOKEN_FILE_EXTENSION, fileDocument.getFileExtension());
    }

    
    /**
     * Parses the specified bundle name and returns the locale.
     * TODO
     * @param resource the resource
     * @return the locale or null if none
     */
    public static Locale parseBundleName(IPropertyResource fileDocument) {
        // Build local title
        String regex = getPropertiesFileRegEx(fileDocument);
        String localeText = fileDocument.getName().replaceFirst(regex, "$2");
        StringTokenizer tokens = new StringTokenizer(localeText, "_");
        List<String> localeSections = new ArrayList<>();
        while (tokens.hasMoreTokens()) {
            localeSections.add(tokens.nextToken());
        }
        Locale locale = null;
        switch (localeSections.size()) {
            case 1:
                locale = new Locale(localeSections.get(0));
                break;
            case 2:
                locale = new Locale(localeSections.get(0), localeSections.get(1));
                break;
            case 3:
                locale = new Locale(localeSections.get(0), localeSections.get(1), localeSections.get(2));
                break;
            default:
                break;
        }
        return locale;
    }
    
    public static boolean isResourceBundle(final String fileName) {
        return fileName.toLowerCase().endsWith(".properties");
    }
  
    public static String[] queryFileName(final Shell shell, final String title, final int dialogOptions, final String[] endings) {
        final FileDialog dialog = new FileDialog(shell, dialogOptions);
        dialog.setText(title);
        dialog.setFilterExtensions(endings);

        final String filepath = dialog.open();

        // if single option, return path
        if ((dialogOptions & SWT.SINGLE) == SWT.SINGLE) {
            return new String[] {filepath};
        } else {
            // [RAP] In RAP1.5 getFilterPath is always empty!!!
            final String path = dialog.getFilterPath();
            // [RAP] In RAP1.5 getFileNames returns full filename (+ path)!!!
            final String[] filenames = dialog.getFileNames();

            // append filenames to path
            if (!path.isEmpty()) {
                for (int i = 0; i < filenames.length; i++) {
                    filenames[i] = path + File.separator + filenames[i];
                }

            }
            if (filenames.length > 0) {
                return filenames;
            }
            return null;
        }
    }
}
