package model.tree;

public class BST<T extends Comparable<T>> extends BTree<T>{


    @Override
    public void remove(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        this.root = remove(root, element);
    }
    private BTreeNode<T> remove(BTreeNode<T> node, T element) {
        if(node!=null){
            if (compareElements(element, node.data)<0)
                node.left = remove(node.left, element);
            else if (compareElements(element, node.data)>0)
                node.right = remove(node.right, element);
            else if(equals(element, node.data)){ //ya lo encontro
                //Caso 1. El nodo a suprimir no tiene hijos, es una hoja
                if (node.left == null && node.right == null)return null;
                    //Caso 2. El nodo a suprimir solo tiene un hijo
                    //En este caso, el nodo es reemplazado por su hijo
                else if (node.right!=null && node.left == null) return node.left;
                else if (node.left!=null && node.right == null) return node.right;
                    //Caso 3. nodo a suprimir tiene dos hijos
                else{
                    //se tiene el elemento menor al subarbol derecho
                    //se reemplaza la data del nodo por ese valor
                    //se elimina el valor
                    T minValue = min(node.right);
                    node.data = minValue;
                    node.right = remove(node.right, minValue);
                }
            }

        }
        return node;//retorna el arbol modificado, sin elemento suprimido
    }

    @Override
    public boolean contains(T element) throws TreeException {
        if(isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return binarySearch(this.root,element);
    }
    private boolean binarySearch(BTreeNode<T> node, T element) {
        if (node == null) return false;
        if(equals(node.data,element)) return true;
        else if(compareElements(element,node.data) < 0)
            return binarySearch(node.left,element);
        else return binarySearch(node.right,element);
    }

    @Override
    public void add(T element) {
        this.root = add(root,element);

    }//a
    private BTreeNode<T> add(BTreeNode<T> node, T element){
        if(node == null){
            node = new BTreeNode<>(element);

        }else if(compareElements(element,node.data) < 0)
            node.left = add(node.left,element);
        else if(compareElements(element,node.data) > 0)
            node.right = add(node.right,element);

        return node;
    }



    @Override
    public T min() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return min(root);

    }
    public T min(BTreeNode<T> node) {
        if (node.left != null) return min(node.left);
        return node.data;
    }

    @Override
    public T max() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return max(root);
    }
    private T max(BTreeNode<T> node) {
        if (node.right != null) return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return preOrder(root);
    }

    // Recorrido: N-L-R
    private String preOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = node.data + ", ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }
    @Override
    public String toString() {
        if (isEmpty()) return "Binary Tree is empty";
        String result = "Binary Tree Tour\n";
        try{
            result += "PreOrder (N-L-R): " + preOrder() + "\n";
            result += "InOrder (L-N-R): " + inOrder() + "\n";
            result += "PostOrder (L-R-N): " + postOrder() + "\n";
        }catch (TreeException e)

        {
            new RuntimeException("Error: " + e.getMessage());
        }
        return result;
    }

}