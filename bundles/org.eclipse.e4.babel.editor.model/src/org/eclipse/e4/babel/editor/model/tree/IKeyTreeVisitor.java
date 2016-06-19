package org.eclipse.e4.babel.editor.model.tree;


public interface IKeyTreeVisitor {

    public void visitKeyTree(KeyTree keyTree, Object passAlongArgument);

    public void visitKeyTreeItem(KeyTreeItem item, Object passAlongArgument);
}
