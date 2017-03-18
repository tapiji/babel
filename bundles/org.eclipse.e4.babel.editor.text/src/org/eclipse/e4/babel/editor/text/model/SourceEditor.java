package org.eclipse.e4.babel.editor.text.model;
/*
 * Copyright (C) 2003-2014  Pascal Essiembre
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyFileResource;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.SourceViewer;


/**
 * Wrapper around a properties file text editor
 * providing extra functionality.
 *
 * @author Pascal Essiembre
 * @author Tobias Langner
 * @author Christian Behon
 */
public class SourceEditor {

    private final Locale locale;
    private final IPropertyResource resource;
    private String cache;

    /**
     * Constructor
     *
     * @param editor text editor
     * @param locale a locale
     * @param file properties file
     */
    private SourceEditor(final IPropertyResource resource, final Locale locale) {
        super();
        this.resource = resource;
        this.locale = locale;
        this.cache = resource.getDocument().get();
    }

    /**
     * Gets the locale associated with this source editor.
     *
     * @return locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Checks if the resource associated with this source editor
     *
     * @return <code>true</code> if resource is the same
     */
    public boolean isResource(IPropertyResource propertyResource) {
        if (propertyResource instanceof PropertyFileResource) {
            return resource.getFile().equals(propertyResource.getFile());
        } else {
            return resource.getIFile().equals(propertyResource.getIFile());
        }
    }

    /**
     * Gets the resource associated with this source editor.
     *
     * @return text editor
     */
    public IPropertyResource getResource() {
        return resource;
    }

    /**
     * Checks whether the underlying file content differs from the cached source
     * editor content.
     *
     * @return <code>true</code> if dirty
     */
    public boolean isCacheDirty() {
        return !this.resource.getDocument().get().equals(this.cache);
    }

    /**
     * Resets the source editor cache.
     */
    public void resetCache() {
        this.cache = this.resource.getDocument().get();
    }

    /**
     * Gets the content of this source editor.
     *
     * @return content
     */
    public String getContent() {
        return this.resource.getDocument().get();
    }

    /**
     * Sets the content of this source editor (replacing existing content).
     *
     * @param content new content
     */
    public void setContent(String content) {
        this.resource.getDocument().set(content);
        this.cache = content;
    }

    /**
     * Save content to file
     */
    public void saveDocument() {
        this.resource.saveDocument();
    }

    /**
     * Checks whether this source editor is read-only.
     *
     * @return <code>true</code> if read-only.
     */
    public boolean isReadOnly() {
        return false;// ((ISourceViewer) editor).isEditorInputReadOnly();
    }

    public void selectKey(String key, SourceViewer sourceViewer) {
        if (key != null) {
            String editorContent = getContent();
            Pattern pattern = Pattern.compile("^" + Pattern.quote(key) + ".*$", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(editorContent);
            if (matcher.find()) {
                int start = matcher.start();
                sourceViewer.revealRange(start, 0);
                sourceViewer.setSelectedRange(start, 0);
            }
        }
    }

    public String getCurrentKey(SourceViewer sourceViewer) {
        if (sourceViewer.getSelectionProvider().getSelection() instanceof TextSelection) {
            TextSelection selection = (TextSelection) sourceViewer.getSelectionProvider().getSelection();
            int selectionStart = selection.getOffset();
            String content = getContent();
            int start = 0, end = 0;

            // Extract the bounds of the line containing the selection
            for (start = selectionStart; start > 0 && content.charAt(start - 1) != '\n'; start--);
            for (end = start; end < content.length() - 1 && content.charAt(end + 1) != '=' && content.charAt(end + 1) != '\n'; end++);
            String line = content.substring(start, end).trim();
            return line;
        }
        return null;
    }

    /**
     * Create new instance of {@link SourceEditor}
     *
     * @param document Document to get and save content
     * @param locale Locale
     * @param file File
     * @return SourceEditor
     */
    public static SourceEditor create(final IPropertyResource resource, final Locale locale) {
        return new SourceEditor(resource, locale);
    }
}
