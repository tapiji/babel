package org.eclipse.e4.babel.core;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
public class BabelExtensionManager {

	private static String RESOURCE_MANAGER_EXTENSION_POINT_ID = "org.eclipse.e4.babel.core.api.resourceManager";

	public static String RESOURCE_FACTORY_EXTENSION_POINT_ID = "org.eclipse.e4.babel.core.api.resourceFactory";

	private IExtensionRegistry registry;

	@PostConstruct
	public void onCreate(IExtensionRegistry registry) {
		this.registry = registry;
	}

	private <T> Optional<T> getConfigurationFor(final String extensionPointId, Class<T> type) {
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(extensionPointId);
		if (elements.length == 1) {
			try {
				final Object obj = elements[0].createExecutableExtension("class");
				if (type.isInstance(obj)) {
					return Optional.of(type.cast(obj));
				}
			} catch (CoreException exception) {
				exception.printStackTrace();
			}
		}
		return Optional.ofNullable(null);
	}

	public Optional<IResourceManager> getResourceManager() {
		return getConfigurationFor(RESOURCE_MANAGER_EXTENSION_POINT_ID, IResourceManager.class);
	}
}
