/*
 * Copyright (C) 2003-2014 Pascal Essiembre
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.e4.babel.core.internal.resource;


import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.babel.core.api.IResourceFactory;


/**
 * The extension point descriptor for the resource factory extension point.
 * 
 * @author Uwe Voigt
 * @author Christian Behon
 */
class ResourceFactoryDescriptor {

    private static String RESOURCE_FACTORY_EXTENSION_POINT_ID = "org.eclipse.e4.babel.core.api.resourceFactory";
    private static final String TAG_FACTORY = "resource_factory";
    private IConfigurationElement fElement;

    private ResourceFactoryDescriptor(IConfigurationElement element) {
        fElement = element;
    }


    /**
     * Returns new instances of the contributed resource factories order by
     * their contributed order value.
     * 
     * @return
     * @throws CoreException
     */
    public static IResourceFactory[] getContributedResourceFactories() throws CoreException {
        ResourceFactoryDescriptor[] descriptors = getResourceFactories().toArray(new ResourceFactoryDescriptor[getResourceFactories().size()]);
        SortedMap<Integer, Object> factories = new TreeMap<>();
        for (int i = 0, lastOrder = 0; i < descriptors.length; i++) {
            Object factory = descriptors[i].fElement.createExecutableExtension("class");
            String attribute = descriptors[i].fElement.getAttribute("order");
            Integer order = null;
            try {
                order = new Integer(attribute);
            } catch (Exception e) {
                order = new Integer(++lastOrder);
            }
            while (factories.containsKey(order))
                order = new Integer(lastOrder = order.intValue());
            factories.put(order, factory);
        }
        return factories.values().toArray(new IResourceFactory[factories.values().size()]);
    }

    private static List<ResourceFactoryDescriptor> getResourceFactories() {
        return findFactoreiesByTag(Platform.getExtensionRegistry().getConfigurationElementsFor(RESOURCE_FACTORY_EXTENSION_POINT_ID));
    }

    private static List<ResourceFactoryDescriptor> findFactoreiesByTag(final IConfigurationElement[] elements) {
        return Stream.of(elements).filter(element -> TAG_FACTORY.equals(element.getName())).map(ResourceFactoryDescriptor::new).collect(Collectors.toList());
    }
}
