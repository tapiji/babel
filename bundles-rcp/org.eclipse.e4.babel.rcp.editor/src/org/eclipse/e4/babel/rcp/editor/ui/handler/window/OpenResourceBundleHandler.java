package org.eclipse.e4.babel.rcp.editor.ui.handler.window;


import org.eclipse.e4.babel.core.utils.BabelUtils;
import org.eclipse.e4.babel.editor.ui.handler.window.AOpenResourceBundleHandler;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;


public class OpenResourceBundleHandler extends AOpenResourceBundleHandler {

    @Override
    protected String[] recentlyOpenedFiles(Shell shell) {
        return BabelUtils.queryFileName(shell, "Open Resource-Bundle", SWT.OPEN, BabelUtils.PROPERTY_FILE_ENDINGS);
    }

    @Override
    @Execute
    public void execute() {
        super.execute();
    }
}
