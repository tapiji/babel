package org.eclipse.e4.babel.editor.preference;


import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipselabs.e4.tapiji.logger.Log;


public class PreferenceDialogPage extends FieldEditorPreferencePage {


    private static final String TAG = PreferenceDialogPage.class.getSimpleName();

    public PreferenceDialogPage() {
        super("sadd", FLAT);
    }

    @Override
    protected void createFieldEditors() {
        //  BooleanFieldEditor bfe = new BooleanFieldEditor("myBoolean", "Boolean", getFieldEditorParent());
        //7 addField(bfe);


        // Add a color field
        ColorFieldEditor cfe = new ColorFieldEditor("myColor", "Color:", getFieldEditorParent());

        addField(cfe);

        // Add a directory field
        //  DirectoryFieldEditor dfe = new DirectoryFieldEditor("myDirectory", "Directory:", getFieldEditorParent());
        // addField(dfe);

        // Add a file field
        //FileFieldEditor ffe = new FileFieldEditor("myFile", "File:", getFieldEditorParent());
        // addField(ffe);

        // Add a font field
        // FontFieldEditor fontFe = new FontFieldEditor("myFont", "Font:", getFieldEditorParent());
        //addField(fontFe);

        // Add a radio group field
        // RadioGroupFieldEditor rfe = new RadioGroupFieldEditor("myRadioGroup", "Radio Group", 2, new String[][] { {"First Value", "first"}, {"Second Value", "second"},
        //         {"Third Value", "third"}, {"Fourth Value", "fourth"}}, getFieldEditorParent(), true);
        // addField(rfe);

        // Add a path field
        //PathEditor pe = new PathEditor("myPath", "Path:", "Choose a Path", getFieldEditorParent());
        //addField(pe);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
        Log.d(TAG, "PROPERYT CHANGED: " + event);
    }
}
