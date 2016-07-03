package org.eclipse.e4.babel.editor.model.tree;


import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public final class KeyTreeItem implements Comparable<KeyTreeItem>, IKeyTreeVisitable {

    private final String id;
    private final KeyTree keyTree;
    private final String name;
    private Object parent;
    private boolean visible;

    private final SortedSet<KeyTreeItem> children = new TreeSet<KeyTreeItem>();

    private KeyTreeItem(final KeyTree keyTree, final String id, final String name) {
        super();
        this.keyTree = keyTree;
        this.id = id;
        this.name = name;
        this.visible = true;
    }

    public static KeyTreeItem create(final KeyTree keyTree, final String id, final String name) {
        return new KeyTreeItem(keyTree, id, name);
    }

    public String getId() {
        return id;
    }

    public KeyTree getKeyTree() {
        return keyTree;
    }

    public String getName() {
        return name;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(final Object parent) {
        this.parent = parent;
    }

    public SortedSet<KeyTreeItem> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public void addChild(KeyTreeItem keyTreeItem) {
        if (keyTreeItem != null) {
            children.add(keyTreeItem);
        }
    }

    public void removeChild(final KeyTreeItem keyTreeItem) {
        if (keyTreeItem != null) {
            children.remove(keyTreeItem);
        }
    }

    public Set<KeyTreeItem> getNestedChildren() {
        Set<KeyTreeItem> nestedChildren = new TreeSet<KeyTreeItem>();
        nestedChildren.addAll(children);
        for (KeyTreeItem item : children) {
            nestedChildren.addAll(item.getNestedChildren());
        }
        return nestedChildren;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public int compareTo(KeyTreeItem keyTreeItem) {
        return this.id.compareTo((keyTreeItem).getId());
    }

    public void dispose() {
        children.clear();
    }

    @Override
    public void accept(IKeyTreeVisitor visitor, Object passAlongArgument) {
        visitor.visitKeyTreeItem(this, passAlongArgument);
    }

    public void setVisible(boolean b) {
            visible = b;
        
    }


}
