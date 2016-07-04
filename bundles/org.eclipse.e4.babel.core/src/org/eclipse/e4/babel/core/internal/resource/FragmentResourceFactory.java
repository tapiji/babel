/*
 * Copyright (C) 2007  Uwe Voigt
 * 
 * This file is part of Essiembre ResourceBundle Editor.
 * 
 * Essiembre ResourceBundle Editor is free software; you can redistribute it 
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * Essiembre ResourceBundle Editor is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Essiembre ResourceBundle Editor; if not, write to the 
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, 
 * Boston, MA  02111-1307  USA
 */
package org.eclipse.e4.babel.core.internal.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.babel.core.api.IResourceFactory;
import org.eclipse.e4.babel.core.internal.createfile.FragmentNLPropertiesFileCreator;
import org.eclipse.e4.babel.core.internal.createfile.FragmentPropertiesFileCreator;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.utils.PDEUtils;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;

/**
 * This is a resource factory responsible for creating editors from fragment
 * projects contributing translations.
 * 
 * @author Uwe Voigt
 * @author Christian Behon
 */
public class FragmentResourceFactory extends NLResourceFactory {

	/**
	 * {@inheritDoc}}
	 */
	@Override
    public void init(IFile file) throws CoreException {

		List<SourceEditor> editors = new ArrayList<>();
		loadEditors(editors, file, null);
		for (Iterator<SourceEditor> it = editors.iterator(); it.hasNext();) {
			SourceEditor editor = it.next();
			addSourceEditor(editor.getLocale(), editor);
		}
		setDisplayName(getDisplayName(file));
	}

	@Override
    protected void loadEditors(List<SourceEditor> editors, IFile file, IResource nlDir)
			throws CoreException {

		/*
		 * check again and load the fragment
		 */
		final IProject fragment = PDEUtils.lookupFragment(file.getProject());
		if (fragment == null)
			throw new CoreException(new Status(IStatus.ERROR, "", 0, "no fragment found", null));

		/*
		 * extract the path to the resource bundle
		 */
		final IPath resourceBundlePath = file.getParent().getProjectRelativePath();
		final String regex = getPropertiesFileRegEx(file);

		IResource folder = fragment.findMember(resourceBundlePath);
		// this may be the case if no translations exist to date
		if (folder == null) {
			folder = fragment.getFolder(resourceBundlePath);
		}

		// load editors from the nl-folder, if present
		nlDir = lookupNLDir(fragment);
		List<SourceEditor> nlEditors = new ArrayList<>();
		if (nlDir != null && nlDir.exists())
			super.loadEditors(nlEditors, file, nlDir);

		// load editors from the same folder as the base file
		List<SourceEditor> fragmentEditors = loadFragmentEditors(regex, folder);

		// Load root file, if exists.
		IProject hostProject = PDEUtils.getFragmentHost(fragment);
		SourceEditor sourceEditor = null;
		if (hostProject == null) {
			// create root file only if no host could be found, otherwise the 
			// factory for the host should find it.
			sourceEditor = createEditor(file, null);
		}
		if (sourceEditor != null) {
			editors.add(sourceEditor);
		}

		if (nlEditors.size() > 0 && fragmentEditors.size() <= 1) {
			// only nl-editors found, the one fragment editor is the file itself
			editors.addAll(nlEditors);
			setPropertiesFileCreator(new FragmentNLPropertiesFileCreator(fragment, file.getName()));
		} else if (nlEditors.size() > 0 && fragmentEditors.size() > 1) {
			/*
			 * if resource bundles have been found within both, the nl-folder 
			 * and the same folder as the base bundle folder, then ask how to 
			 * handle that
			 */
			if (hostProject != null || hostProject == null && shouldNLCreatorBeUsed(fragment)) {
				editors.addAll(nlEditors);
				setPropertiesFileCreator(new FragmentNLPropertiesFileCreator(fragment, file.getName()));
			}
		}

		//if (getPropertiesFileCreator() == null) {
		/*
		 * If the files creator is still null here, 
		 * only resources in the same folder as the file could be found.
		 */
		editors.addAll(fragmentEditors);
		setPropertiesFileCreator(new FragmentPropertiesFileCreator(fragment, resourceBundlePath.toString(),
				getBundleName(file), file.getFullPath().getFileExtension()));
		//  }

		/*
		 * load the resources of host plug-in
		 */
		hostProject = PDEUtils.getFragmentHost(fragment);
		if (hostProject != null) {
			if (!PropertyPreferences.getInstance().isLoadOnlyFragmentResources()) {
				IResourceFactory parentFactory = ResourceFactory
						.createParentFactory(hostProject.getFile(file.getProjectRelativePath()), this.getClass());
				if (parentFactory != null) {
					Set<Locale> fragmentLocales = getFragmentLocales(fragmentEditors);
					parentFactory.getSourceEditors().stream().forEach(editor->{
						if (!fragmentLocales.contains(editor.getLocale())) {
							editors.add(editor);
						}
					});
				}
			}
		}
	}

	private Set<Locale> getFragmentLocales(List<SourceEditor> fragmentEditors) {
		Set<Locale> locales = new HashSet<>();
		for (SourceEditor editor : fragmentEditors) {
			locales.add(editor.getLocale());
		}
		return locales;
	}

	private List<SourceEditor> loadFragmentEditors(final String regex, IResource folder)
			throws CoreException {
		List<SourceEditor> fragmentEditors = new ArrayList<>();
		if (folder.exists()) {
			IResource[] members = ((IContainer) folder).members();
			for (int j = 0; j < members.length; j++) {
				IResource resource = members[j];
				if (!(resource instanceof IFile) || !resource.getName().matches(regex))
					continue;
				Locale locale = parseBundleName(resource);
				SourceEditor editor = createEditor(resource, locale);
				if (editor != null) {
					fragmentEditors.add(editor);
				}
			}
		}
		return fragmentEditors;
	}

	private boolean shouldNLCreatorBeUsed(IProject fragment) {
		return true;
		// TODO this decision could be stored within the base file persistent 
		// properties 
		// TODO externalize/translate this message
		/* return MessageDialog.openQuestion(
		        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
		        "Translations", "Within the fragment project '" 
		      + fragment.getName()
		      + "' there is a 'nl'-folder containing matching resource "
		      + "bundles as well as nationalized resource bundles within the "
		      + "same folder as the base file. "
		      + "Press 'yes' to open the files from the 'nl'-folder and "
		      + "'no' to open the others.");*/
	}

	/**
	 * Checks whether the {@link FragmentResourceFactory} is responsible 
	 * to load resources for the given file.
	 * <p>
	 * This method will return true
	 * </p>
	 */
	@Override
    public boolean isResponsible(IFile file) {
		/*
		 * Check if NL is supported.
		 */
		if (!PropertyPreferences.getInstance().isSupportFragments()) {
			return false;
		}

		/*
		 * Check whether there is a fragment that extends this project
		 * or this project itself is a fragment
		 */
		if (PDEUtils.lookupFragment(file.getProject()) == null) {
			return false;
		}
		return true;
	}

}
