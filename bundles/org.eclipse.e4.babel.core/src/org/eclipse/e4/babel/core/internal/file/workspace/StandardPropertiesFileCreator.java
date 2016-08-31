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
package org.eclipse.e4.babel.core.internal.file.workspace;

import java.util.Locale;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Creates a standard properties file.
 * @author Pascal Essiembre
 */
public final class StandardPropertiesFileCreator extends AbstractIFileCreator {

	private final String dir;
	private final String baseFileName;
	private final String extension;

	/**
	 * Constructor
	 * 
	 * @param dir directory in which to create the file
	 * @param baseFileName base name of file to create
	 * @param extension file extension
	 */
	public StandardPropertiesFileCreator(final String dir, final String baseFileName, final String extension) {
		super();
		this.dir = dir;
		this.baseFileName = baseFileName;
		this.extension = extension;
	}

	@Override
	protected IPath buildFilePath(final Locale locale) {
		IPath path = new Path(dir);
		path = path.append(baseFileName);
		if (locale != null) {
			path = new Path(path.toString() + "_" + locale.toString());
		}
		return path.addFileExtension(extension);
	}
}
