package org.eclipse.e4.babel.rap.editor.ui.handler.window;


import org.eclipse.e4.babel.editor.ui.handler.window.AOpenResourceBundleHandler;
import org.eclipse.e4.tapiji.rap.utils.UploadFileUtils;
import org.eclipse.e4.tapiji.utils.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class UploadResourceBundleHandler extends AOpenResourceBundleHandler {
	@Override
	protected String[] recentlyOpenedFiles(Shell shell) {
		return UploadFileUtils.uploadFiles(shell, "Open Glossary", SWT.OPEN, FileUtils.XML_FILE_ENDINGS);
	}
}
