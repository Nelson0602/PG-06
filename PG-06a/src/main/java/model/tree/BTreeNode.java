package model.tree;


public class BTreeNode<T> {
    public T data;
    public BTreeNode<T> left, right;
    public String path; //ruta de inserción. Ejemplo: root/left/right
    public int height; // Added for AVL tree balancing

    public BTreeNode(T data) {
        this.data = data;
        this.left = right = null;
        this.height = 1; // New nodes are leaves, so height is 1
    }

    public BTreeNode(T data, String path) {
        this.data = data;
        this.path = path;
        this.left = right = null;
        this.height = 1; // New nodes are leaves, so height is 1
    }

}