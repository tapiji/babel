package org.eclipse.e4.babel.editor.model.tree.filter;

import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;

public interface ITreeFilter {

    boolean doFilter(KeyTreeItem keyTreeItem);

}
