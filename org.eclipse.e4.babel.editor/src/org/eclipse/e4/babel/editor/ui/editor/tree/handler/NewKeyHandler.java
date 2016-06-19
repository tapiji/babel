package org.eclipse.e4.babel.editor.ui.editor.tree.handler;


import javax.inject.Named;
import org.eclipse.e4.babel.editor.model.IResourceBundleEditorService;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;


public final class NewKeyHandler {

  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) KeyTreeItem item, @Named(IServiceConstants.ACTIVE_SHELL) final Shell shell, IResourceBundleEditorService editorService) {
    if (item != null) {
      System.out.println("sadadsd " + item.toString());
      InputDialog dialog = new InputDialog(shell, "Add new key", "Below is the key that will be newly created:", item.getId(), new IInputValidator() {

        public String isValid(String key) {
          if (editorService.containsKey(key)) {
            return "Key already exists";
          }
          return null;
        }
      });
      dialog.open();
      if (dialog.getReturnCode() == Window.OK) {
        editorService.addNewKey(dialog.getValue());
      }
    }
  }
}
