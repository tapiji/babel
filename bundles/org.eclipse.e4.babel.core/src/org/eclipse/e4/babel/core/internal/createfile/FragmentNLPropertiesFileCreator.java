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
package org.eclipse.e4.babel.core.internal.createfile;

import java.util.Locale;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.e4.babel.core.internal.resource.NLResourceFactory;
import org.eclipse.e4.babel.core.utils.PDEUtils;

/**
 * PropertiesFileCreator used when loaded resources from an nl structure in
 * fragments.
 * 
 * @author Alexander Bieber
 * @author Christian Behon
 */
public final class FragmentNLPropertiesFileCreator extends NLPropertiesFileCreator {

	private final IProject fragment;
	private final String fragmentNlDir;
	private final String hostNlDir;

	/**
	 * Constructor
	 * 
	 * @param nlDir
	 * @param fileName
	 */
	public FragmentNLPropertiesFileCreator(final IProject fragment, final String fileName) {
		super(NLResourceFactory.lookupNLDir(fragment).toString(), fileName);
		this.fragment = fragment;
		this.fragmentNlDir = NLResourceFactory.lookupNLDir(fragment).getFullPath().toString();
		final IProject host = PDEUtils.getFragmentHost(fragment);
		this.hostNlDir = host == null ? null : NLResourceFactory.lookupNLDir(host).getFullPath().toString();
	}

	@Override
	protected IPath buildFilePath(final Locale locale) throws CoreException {
		if (FragmentPropertiesFileCreator.shouldFileBeCreatedInFragment(fragment)) {
			setNlDir(fragmentNlDir);
		} else {
			setNlDir(hostNlDir);
		}
		return super.buildFilePath(locale);
	}

}
