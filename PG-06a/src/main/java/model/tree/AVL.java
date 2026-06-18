package model.tree;

public class AVL<T extends Comparable<T>> extends BST<T> {

    public AVL() {
        super();
    }

    @Override
    public void add(T element) {
        this.root = add(root, element);
    }

    private BTreeNode<T> add(BTreeNode<T> node, T element) {
        if (node == null) {
            return new BTreeNode<>(element);
        }

        if (compareElements(element, node.data) < 0) {
            node.left = add(node.left, element);
        } else if (compareElements(element, node.data) > 0) {
            node.right = add(node.right, element);
        } else {
            // Element already exists, do nothing or handle as per requirements
            return node;
        }

        // Update height of the current node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor
        int balance = getBalanceFactor(node);

        // Perform rotations if unbalanced

        // Left Left Case
        if (balance > 1 && compareElements(element, node.left.data) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && compareElements(element, node.right.data) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && compareElements(element, node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && compareElements(element, node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    @Override
    public void remove(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Tree is empty");
        this.root = remove(root, element);
    }

    private BTreeNode<T> remove(BTreeNode<T> node, T element) {
        if (node == null) {
            return node;
        }

        if (compareElements(element, node.data) < 0) {
            node.left = remove(node.left, element);
        } else if (compareElements(element, node.data) > 0) {
            node.right = remove(node.right, element);
        } else {
            // Node with only one child or no child
            if (node.left == null || node.right == null) {
                BTreeNode<T> temp = null;
                if (temp == node.left) {
                    temp = node.right;
                } else {
                    temp = node.left;
                }

                // No child case
                if (temp == null) {
                    node = null;
                } else { // One child case
                    node = temp; // Copy the contents of the non-empty child
                }
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                BTreeNode<T> temp = minValueNode(node.right);

                // Copy the inorder successor's data to this node
                node.data = temp.data;

                // Delete the inorder successor
                node.right = remove(node.right, temp.data);
            }
        }

        if (node == null) {
            return node;
        }

        // Update height of the current node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor
        int balance = getBalanceFactor(node);

        // Perform rotations if unbalanced

        // Left Left Case
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right Case
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left Case
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Helper method to get height of a node (handles null nodes)
    private int height(BTreeNode<T> node) {
        return (node == null) ? 0 : node.height;
    }

    // Helper method to get balance factor of a node
    private int getBalanceFactor(BTreeNode<T> node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Right rotate
    private BTreeNode<T> rightRotate(BTreeNode<T> y) {
        BTreeNode<T> x = y.left;
        BTreeNode<T> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    // Left rotate
    private BTreeNode<T> leftRotate(BTreeNode<T> x) {
        BTreeNode<T> y = x.right;
        BTreeNode<T> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    // Helper to find the node with the minimum value in a subtree
    private BTreeNode<T> minValueNode(BTreeNode<T> node) {
        BTreeNode<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Override height() from BTree to use AVL's height calculation
    @Override
    public int height() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Tree is empty");
        return height(root);
    }

    // Override height(T element) from BTree to use AVL's height calculation
    @Override
    public int height(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Tree is empty");
        BTreeNode<T> node = findNode(root, element); // Need a findNode method that returns BTreeNode
        if (node == null) throw new TreeException("Element not found in AVL Tree");
        return height(node);
    }

    // Helper method to find a node by element (similar to BTree's findNode)
    private BTreeNode<T> findNode(BTreeNode<T> node, T element) {
        if (node == null) return null;
        if (compareElements(element, node.data) == 0) return node;
        if (compareElements(element, node.data) < 0) return findNode(node.left, element);
        return findNode(node.right, element);
    }

    // Method to check if the tree is balanced (as per rubric)
    public boolean isBalanced() throws TreeException {
        if (isEmpty()) return true; // An empty tree is balanced
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode<T> node) {
        if (node == null) {
            return true;
        }
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }
}
