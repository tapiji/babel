package org.eclipse.e4.babel.editor.service.internal.context;


import org.eclipse.e4.babel.editor.model.IResourceBundleEditorService;
import org.eclipse.e4.babel.editor.service.internal.ResourceBundleEditorService;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;


public final class ResourceBundleEditorContextFunction extends ContextFunction {

    @Override
    public Object compute(final IEclipseContext context, final String contextKey) {
        final IResourceBundleEditorService resourceBundleEditorService = ContextInjectionFactory.make(ResourceBundleEditorService.class, context);
        final MApplication application = context.get(MApplication.class);
        final IEclipseContext applicationContext = application.getContext();
        applicationContext.set(IResourceBundleEditorService.class, resourceBundleEditorService);
        return resourceBundleEditorService;
    }
}
