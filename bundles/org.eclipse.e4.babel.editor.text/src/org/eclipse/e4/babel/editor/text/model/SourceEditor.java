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

import org.eclipse.core.resources.IFile;
import org.eclipse.e4.babel.editor.text.document.SourceViewerDocument;

/**
 * Wrapper around a properties file text editor providing extra founctionality.
 * 
 * @author Pascal Essiembre
 * @author Tobias Langner
 */
public class SourceEditor {

	private final Locale locale;
	private final IFile file;
	private final SourceViewerDocument document;
	private String contentCache;

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            text editor
	 * @param locale
	 *            a locale
	 * @param file
	 *            properties file
	 */
	private SourceEditor(SourceViewerDocument document, Locale locale, IFile file) {
		super();
		this.document = document;
		this.locale = locale;
		this.file = file;
		this.contentCache = getContent();
	}

	public static SourceEditor create(SourceViewerDocument document, Locale locale, IFile file) {
		return new SourceEditor(document, locale, file);
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
	 * Gets the file associated with this source editor.
	 * 
	 * @return properties file
	 */
	public IFile getFile() {
		return file;
	}

	/**
	 * Gets the text editor associated with this source editor.
	 * 
	 * @return text editor
	 */
	public SourceViewerDocument getDocument() {
		return document;
	}

	/**
	 * Checks whether the underlying file content differs from the cached source
	 * editor content.
	 * 
	 * @return <code>true</code> if dirty
	 */
	public boolean isCacheDirty() {
		return !getContent().equals(contentCache);
	}

	/**
	 * Resets the source editor cache.
	 */
	public void resetCache() {
		contentCache = getContent();
	}

	/**
	 * Gets the content of this source editor.
	 * 
	 * @return content
	 */
	public String getContent() {
		return document.getDocument().get();
	}

	/**
	 * Sets the content of this source editor (replacing existing content).
	 * 
	 * @param content
	 *            new content
	 */
	public void setContent(String content) {
		document.getDocument().set(content);
		contentCache = content;
	}

	/**
	 * Checks whether this source editor is read-only.
	 * 
	 * @return <code>true</code> if read-only.
	 */
	public boolean isReadOnly() {
		return false;// ((ISourceViewer) editor).isEditorInputReadOnly();
	}

	public void selectKey(String key) {
		if (key != null) {
			String editorContent = getContent();
			Pattern pattern = Pattern.compile("^" + Pattern.quote(key) + ".*$", Pattern.MULTILINE);
			Matcher matcher = pattern.matcher(editorContent);
			if (matcher.find()) {
				int start = matcher.start();
				// textEditor.selectAndReveal(start, 0);
			}

		}
	}

	public String getCurrentKey() {
/*
        if (textEditor.getSelectionProvider().getSelection() 
                instanceof TextSelection) {
            TextSelection selection = (TextSelection) 
                    textEditor.getSelectionProvider().getSelection();
            int selectionStart = selection.getOffset();
            String content = getContent();
            int start = 0, end = 0;
            
            // Extract the bounds of the line containing the selection
            for (start = selectionStart; start > 0 
                    && content.charAt(start-1) != '\n'; start--);
            for (end = start; end < content.length()-1 
                    && content.charAt(end+1) != '=' 
                    && content.charAt(end+1) != '\n'; end++);
            String line = content.substring(start, end+1).trim();
            return line;
        }*/
        return null;
	}

	// TODO add save and revertToSave here (spawning a thread)
}
