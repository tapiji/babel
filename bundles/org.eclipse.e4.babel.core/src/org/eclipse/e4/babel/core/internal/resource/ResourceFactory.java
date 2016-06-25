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
package org.eclipse.e4.babel.core.internal.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Builder;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.api.IResourceFactory;
import org.eclipse.e4.babel.core.internal.createfile.PropertiesFileCreator;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.text.document.SourceViewerDocument;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PartInitException;
import org.eclipselabs.e4.tapiji.logger.Log;

/**
 * Responsible for creating resources related to a given file structure.
 * <p>
 * This class is also the abstract base class for implementations of a
 * {@link ResourceFactory} as well as static entry point to access the
 * responsible one.
 * </p>
 * 
 * @author Pascal Essiembre
 * @author cuhiodtick
 * @author Christian Behon
 */
abstract class ResourceFactory implements IResourceFactory {


	private final static String TAG = ResourceFactory.class.getSimpleName();
	
	/** Class name of Properties file editor (Eclipse 3.1). */
	protected static final String PROPERTIES_EDITOR_CLASS_NAME = "org.eclipse.jdt.internal.ui.propertiesfileeditor."
			+ "PropertiesFileEditor";

	/** Token to replace in a regular expression with a bundle name. */
	private static final String TOKEN_BUNDLE_NAME = "BUNDLENAME";
	/** Token to replace in a regular expression with a file extension. */
	private static final String TOKEN_FILE_EXTENSION = "FILEEXTENSION";
	/** Regex to match a properties file. */
	private static final String PROPERTIES_FILE_REGEX = "^(" + TOKEN_BUNDLE_NAME + ")"
			+ "((_[a-z]{2,3})|(_[a-z]{2,3}_[A-Z]{2})" + "|(_[a-z]{2,3}_[A-Z]{2}_\\w*))?(\\." + TOKEN_FILE_EXTENSION
			+ ")$";

	/*
	 * Common members of ResourceFactories
	 */

	/**
	 * A sorted map of {@link SourceEditor}s. Sorted by key (Locale).
	 */
	private Map<Locale, SourceEditor> sourceEditors = new TreeMap<>(new Comparator<Locale>() {
		@Override
		public int compare(final Locale locale1, final Locale locale2) {
			return UIUtils.getDisplayName(locale1).compareToIgnoreCase(UIUtils.getDisplayName(locale2));
		}
	});

	/**
	 * The {@link PropertiesFileCreator} used to create new files.
	 */
	private PropertiesFileCreator propertiesFileCreator;
	/**
	 * The associated editor site.
	 */
	/**
	 * The displayname
	 */
	private String displayName;


	@Override
	public String getEditorDisplayName() {
		return displayName;
	}

	/**
	 * Sets the editor display name of this factory.
	 * 
	 * @param displayName
	 *            The display name to set.
	 * @see #getEditorDisplayName()
	 */
	protected void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public List<SourceEditor> getSourceEditors() {
		List<SourceEditor> editors = new ArrayList<>(sourceEditors.values().size());
		sourceEditors.values().stream().forEach(editor -> editors.add(editor));
		return editors;
	}

	@Override
	public SourceEditor addResource(IResource resource, Locale locale) throws PartInitException {
		if (sourceEditors.containsKey(locale)) {
			throw new IllegalArgumentException("ResourceFactory already contains a resource for locale " + locale);
		}
		SourceEditor editor = createEditor(resource, locale);
		addSourceEditor(editor.getLocale(), editor);
		return editor;
	}

	protected void addSourceEditor(Locale locale, SourceEditor sourceEditor) {
		sourceEditors.put(locale, sourceEditor);
	}

	@Override
	public PropertiesFileCreator getPropertiesFileCreator() {
		return propertiesFileCreator;
	}

	protected void setPropertiesFileCreator(PropertiesFileCreator fileCreator) {
		this.propertiesFileCreator = fileCreator;
	}

	@Override
	public abstract boolean isResponsible(IFile file) throws CoreException;

	@Override
	public abstract void init(IFile file) throws CoreException;

	/**
	 * Creates a resource factory based on given arguments.
	 * 
	 * @param site
	 *            eclipse editor site
	 * @param file
	 *            file used to create factory
	 * @return An initialized resource factory, or <code>null</code> if no
	 *         responsible one could be found
	 * @throws CoreException
	 *             problem creating factory
	 */
	public static IResourceFactory createFactory(IFile file) throws CoreException {
		IResourceFactory[] factories = ResourceFactoryDescriptor.getContributedResourceFactories();
		for (int i = 0; i < factories.length; i++) {
			IResourceFactory factory = factories[i];
			if (factory.isResponsible(file)) {
				factory.init(file);
				return factory;
			}
		}
		return new StandardResourceFactory();
	}

	/**
	 * Creates a resource factory based on given arguments and excluding
	 * factories of the given class.
	 * <p>
	 * This might be used to get the {@link SourceEditor}s from other factories
	 * while initializing an other factory.
	 * </p>
	 * 
	 * @param site
	 *            eclipse editor site
	 * @param file
	 *            file used to create factory
	 * @param childFactoryClass
	 *            The class of factory to exclude.
	 * @return An initialized resource factory, or <code>null</code> if no
	 *         responsible one could be found
	 * @throws CoreException
	 *             problem creating factory
	 */
	public static IResourceFactory createParentFactory(IFile file, Class<?> childFactoryClass) throws CoreException {		
		IResourceFactory[] factories = ResourceFactoryDescriptor.getContributedResourceFactories();
		for (int i = 0; i < factories.length; i++) {
			IResourceFactory factory = factories[i];
			if (!factory.getClass().equals(childFactoryClass) && factory.isResponsible(file)) {
				factory.init(file);
				return factory;
			}
		}
		return null;
	}

	/**
	 * Parses the specified bundle name and returns the locale.
	 * 
	 * @param resource the resource
	 * @return the locale or null if none
	 */
	protected static Locale parseBundleName(IResource resource) {
		// Build local title
		String regex = ResourceFactory.getPropertiesFileRegEx(resource);
		String localeText = resource.getName().replaceFirst(regex, "$2");
		StringTokenizer tokens = new StringTokenizer(localeText, "_");
		List<String> localeSections = new ArrayList<>();
		while (tokens.hasMoreTokens()) {
			localeSections.add(tokens.nextToken());
		}
		Locale locale = null;
		switch (localeSections.size()) {
		case 1:
			locale = new Locale(localeSections.get(0), localeSections.get(0).toUpperCase());
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
	
	protected SourceEditor createEditor(IResource resource, Locale locale) {
		return SourceEditor.create(SourceViewerDocument.create((IFile) resource),locale, (IFile) resource);
	}

	protected static String getBundleName(IResource file) {
		String name = file.getName();
		String regex = "^(.*?)" + "((_[a-z]{2,3})|(_[a-z]{2,3}_[A-Z]{2})" + "|(_[a-z]{2,3}_[A-Z]{2}_\\w*))?(\\."
				+ file.getFileExtension() + ")$";
		return name.replaceFirst(regex, "$1");
	}

	protected static String getDisplayName(IResource file) {
		if (file instanceof IFile)
			return getBundleName(file) + "[...]." + file.getFileExtension();
		else
			return getBundleName(file);
	}

	protected static String getPropertiesFileRegEx(IResource file) {
		String bundleName = getBundleName(file);
		return PROPERTIES_FILE_REGEX.replaceFirst(TOKEN_BUNDLE_NAME, bundleName).replaceFirst(TOKEN_FILE_EXTENSION,
				file.getFileExtension());
	}

	/**
	 * Returns the resource bundle file resources that match the specified file
	 * name.
	 * 
	 * @param file
	 *            the file to match
	 * @return array of file resources, empty if none matches
	 * @throws CoreException
	 */
	protected static IFile[] getResources(IFile file) throws CoreException {
		String regex = ResourceFactory.getPropertiesFileRegEx(file);
		final Collection<IResource> validResources = new ArrayList<>();
		Stream.of(file.getParent().members()).forEach(resource -> {
			String resourceName = resource.getName();
			if (resource instanceof IFile && resourceName.matches(regex)) {
				validResources.add(resource);
			}
		});
		return (IFile[]) validResources.toArray(new IFile[] {});
	}

}
