package model.tree;

/**
 * Árbol AVL (Adelson-Velsky & Landis, 1962).

 * Árbol BST auto-balanceado que mantiene el Factor de Equilibrio (FE)
 * de cada nodo dentro del rango {−1, 0, +1}.

 * FE(nodo) = altura(izquierdo) − altura(derecho)

 * Si |FE| > 1 tras una inserción/eliminación se aplica:
 *   LL → Rotación simple derecha
 *   RR → Rotación simple izquierda
 *   LR → Rotación doble: rotateLeft(y) + rotateRight(z)
 *   RL → Rotación doble: rotateRight(y) + rotateLeft(z)

 * Todas las operaciones garantizan O(log n).
 *
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class AVL<T extends Comparable<T>> extends BST<T> {

    @Override
    public void add(T element) {
        this.root = add(root, element, "root");
    }

    private BTreeNode<T> add(BTreeNode<T> node, T element, String path) {
        if (node == null) {
            node = new BTreeNode<>(element, path);
        } else if (compareElements(element, node.data) < 0)
            node.left = add(node.left, element, path + "/left");
        else if (compareElements(element, node.data) > 0)
            node.right = add(node.right, element, path + "/right");

        //------------------- Luego de insertar, se debe rebalancear ---------------//
        //obtenemos el factor de balanceo
        int balance = getBalanceFactor(node);
        // Caso 1: Left Left Case - LL
        if (balance > 1 && compareElements(element, node.left.data) < 0) {
            node.path = path + "/→LL-Simple Right Rotate (Rebalancing by insertion)";
            return rightRotate(node);
        }
        // Caso 2: Right Right Case - RR
        if (balance < -1 && compareElements(element, node.right.data) > 0) {
            node.path = path + "/→RR-Simple Left Rotate (Rebalancing by insertion)";
            return leftRotate(node);
        }
        // Caso 3: Left Right Case - LR
        if (balance > 1 && compareElements(element, node.left.data) > 0) {
            node.path = path + "/→LR-Double Left-Right Rotate (Rebalancing by insertion)";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Caso 4: Right Left Case - RL
        if (balance < -1 && compareElements(element, node.right.data) < 0) {
            node.path = path + "/→RL-Double Right-Left Rotate (Rebalancing by insertion)";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node; //en todos los casos retorna un nuevo nodo
    }

    //get balance factor for node
    public int getBalanceFactor(BTreeNode<T> node) {
        if (node == null) {
            return 0;
        } else {
            return height(node.left) - height(node.right); //clase BTree: public int height(BTreeNode<T> node)
        }
    }

    private BTreeNode<T> leftRotate(BTreeNode<T> node) {
        BTreeNode<T> node1 = node.right;
        BTreeNode<T> node2 = node1.left;
        //se realiza la rotacion (perform rotation)
        node1.left = node;
        node.right = node2;
        return node1;
    }

    private BTreeNode<T> rightRotate(BTreeNode<T> node) {
        BTreeNode<T> node1 = node.left;
        BTreeNode<T> node2 = node1.right;
        //se realiza la rotacion (perform rotation)
        node1.right = node;
        node.left = node2;
        return node1;
    }

    @Override
    public void remove(T element) throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        root = remove(root, element);
    }

    //método interno
    private BTreeNode<T> remove(BTreeNode<T> node, T element) throws TreeException {
        if (node != null) {
            if (compareElements(element, node.data) < 0)
                node.left = remove(node.left, element);
            else if (compareElements(element, node.data) > 0)
                node.right = remove(node.right, element);
            else if (equals(element, node.data)) { //ya lo encontro
                //Caso1. El nodo a suprimir no tiene hijos
                if (node.left == null && node.right == null) return null;
                    //Caso2. El nodo a suprimir solo tiene un hijo
                    //en este caso, el nodo es reemplazado por su hijo
                else if (node.left != null && node.right == null) return node.left;
                else if (node.left == null && node.right != null) return node.right;
                else { //if(node.left!=null && node.right!=null){
                    //Caso 3: El nodo tiene 2 hijos
                    //buscamos el valor min del subárbol der
                    //se reemplaza la data del nodo con ese valor
                    //luego se suprime el valor min del subárbol der
                    T minValue = min(node.right);
                    node.data = minValue;
                    node.right = remove(node.right, minValue);
                }//fin de if(node.left!=null && node.right!=null)
            }//fin de if(node.data==element)
        }//fin de if(node!=null)

        if (node == null) return null;

        //------------------- Luego de eliminar, se debe rebalancear ---------------//
        int balance = getBalanceFactor(node);
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            node.path += "/→LL-Simple Right Rotate (Rebalancing by elimination)";
            return rightRotate(node);
        }
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.path += "/→LR-Double Left-Right Rotate (Rebalancing by elimination)";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            node.path += "/→RR-Simple Left Rotate (Rebalancing by elimination)";
            return leftRotate(node);
        }
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.path += "/→RL-Double Right-Left Rotate (Rebalancing by elimination)";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    /**
     * Verifica si el árbol AVL está balanceado.
     * Un árbol AVL siempre debería estar balanceado (|FE| <= 1 en todo nodo).
     * Este método lo comprueba de forma explícita recorriendo el árbol.
     *
     * @return true si el árbol está balanceado, false en caso contrario
     * @throws TreeException si el árbol está vacío
     */
    public boolean isBalanced() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty");
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode<T> node) {
        if (node == null) return true;
        int bf = getBalanceFactor(node);
        if (Math.abs(bf) > 1) return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    /**
     * Muestra la altura de cada elemento del árbol en recorrido preOrder (N-L-R).
     * Indica además el Factor de Equilibrio (FE) de cada nodo.
     *
     * @return cadena con el resultado del recorrido preOrder con alturas y FE
     * @throws TreeException si el árbol está vacío
     */
    @Override
    public String nodeHeight() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty");
        StringBuilder sb = new StringBuilder();
        nodeHeightPreOrder(root, sb);
        return sb.toString();
    }

    private void nodeHeightPreOrder(BTreeNode<T> node, StringBuilder sb) {
        if (node == null) return;
        int fe = getBalanceFactor(node);
        sb.append("Nodo [").append(node.data)
                .append("] | Altura: ").append(height(node))
                .append(" | FE: ").append(fe).append("\n");
        nodeHeightPreOrder(node.left, sb);
        nodeHeightPreOrder(node.right, sb);
    }

    public String getRebalancingInfo() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary AVL Tree is empty");
        return getRebalancingInfo(root);
    }

    //Recorrido: N-L-R
    private String getRebalancingInfo(BTreeNode<T> node) throws TreeException {
        String result = "";
        if (node != null) {
            result = "Node Rebalancing Info [" + node.data + "]: " + node.path + "\n";
            result += getRebalancingInfo(node.left);
            result += getRebalancingInfo(node.right);
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "AVL Tree is empty";
        String result = "AVL Tree Tour\n";
        try {
            result += "PreOrder (N-L-R): " + preOrder() + "\n";
            result += "InOrder (L-N-R): " + inOrder() + "\n";
            result += "PostOrder (L-R-N): " + postOrder() + "\n";
        } catch (TreeException e) {
            result += "Error: " + e.getMessage();
        }
        return result;
    }

}