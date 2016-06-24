package org.eclipse.e4.babel.editor.ui.editor.i18n.pageeditor;


import org.eclipse.e4.babel.editor.text.PropertiesTextEditor;
import org.eclipse.e4.babel.editor.text.SourceEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class I18nPageEditorView extends Composite {

  private static final int VERTICAL_RULER_WIDTH = 12;
//private SourceViewerDocument sourceViewerDocument;

  public I18nPageEditorView(final CTabFolder tabFolder, SourceEditor editor) {
    super(tabFolder, SWT.NONE);
    createControl(tabFolder, editor);
  //  this.sourceViewerDocument = sourceViewerDocument;

  }


  public void createControl(final Composite sashForm, SourceEditor editor) {

    //      Composite parent = new Composite(this, SWT.NONE);
    setLayout(new FillLayout());


    new PropertiesTextEditor(this, editor.getEditor());

  }
}
