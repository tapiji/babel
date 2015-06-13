package org.eclipse.e4.babel.editor.text.prop;


import org.eclipse.jface.text.source.SourceViewerConfiguration;


public class PropertyConfiguration extends SourceViewerConfiguration {


    private ColorManager colorManager;


    private PropertyConfiguration() {
        this.colorManager = ColorManager.create();
    }


    public static PropertyConfiguration create() {
        return new PropertyConfiguration();
    }
}
