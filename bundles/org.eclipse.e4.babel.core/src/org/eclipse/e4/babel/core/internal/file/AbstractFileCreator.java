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
package org.eclipse.e4.babel.core.internal.file;


import java.io.IOException;
import java.util.Locale;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.internal.generator.PropertiesGenerator;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyFileResource;
import org.eclipse.e4.babel.editor.text.file.PropertyIFileResource;
import org.eclipse.jdt.annotation.Nullable;


/**
 * Creates a new property file.
 * 
 * @author Christian Behon
 */
public abstract class AbstractFileCreator {

    /**
     * Creates an external or internal file resource.
     * An external file {@link PropertyFileResource} is a part of the
     * FileSystem and an internal file
     * {@link PropertyIFileResource} is a part
     * of the current workspace.
     * 
     * @param locale locale representing properties file
     * @param isExternal external or internal resource
     * @return IPropertyResource the properties file
     * @throws CoreException when a problem occurs
     * @throws IOException when a problem during file creation happens
     **/
    @Nullable
    public IPropertyResource createPropertyFile(final Locale locale, boolean isExternal) throws IOException, CoreException {
        return createPropertyResource(locale, generatedBy());
    }


    /**
     * Creates generated by string 
     * if it is activated in the settings.
     * 
     * @return String
     */
    private String generatedBy() {
        String content = "";
        if (PropertyPreferences.getInstance().isGeneratedByEnabled()) {
            content = PropertiesGenerator.GENERATED_BY;
        }
        return content;
    }

    /**
     * Creates a new file
     * 
     * @param locale locale locale representing properties file
     * @param content to write file
     * @return IPropertyResource instance of {@link PropertyFileResource} or {@link PropertyIFileResource}
     * @throws IOException when a problem during file creation happens
     * @throws CoreException when a problem occurs
     */
    protected abstract IPropertyResource createPropertyResource(Locale locale, String content) throws CoreException, IOException;
}
