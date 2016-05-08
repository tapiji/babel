package org.eclipse.e4.babel.editor.ui.editor.treeviewer;


public class KeyTreePresenter implements KeyTreeContract.Presenter {

    private KeyTreePresenter() {
        
    }

    public static KeyTreePresenter create(KeyTreeContract.View keyTreeView) {
        final KeyTreePresenter presenter = new KeyTreePresenter();
        keyTreeView.setPresenter(presenter);
        return presenter;
    }
}
