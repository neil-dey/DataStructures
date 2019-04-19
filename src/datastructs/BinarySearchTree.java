package datastructs;

/**
 * A binary search tree implementation of a dictionary.
 * 
 * @author Neil Dey
 *
 * @param <K>
 *            The generic type for keys
 * @param <V>
 *            The generic type for values
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements Dictionary<K, V> {
    /** The root of the binary search tree **/
    private Node root;

    /**
     * Gets Node with the specified key
     * 
     * @param key
     *            The key to search for
     * @return The node with the specified key
     */
    private Node getKey(K key) {
        return this.root.getKey(key);
    }

    /**
     * Updates the node with the given key to have the specified value
     * 
     * @param key
     *            The key of the node to update
     * @param value
     *            The new value of the node
     */
    public V update(K key, V value) {
        Node n = this.getKey(key);
        if (n == null || !n.key.equals(key)) {
            return null;
        }
        V temp = n.value;
        n.value = value;
        return temp;
    }

    /**
     * Inserts a new node into the tree with the given key and value. Duplicates
     * will be inserted into the right subtree. Note that duplicate keys cannot be
     * found via lookUp().
     * 
     * @param key
     *            The key of the new node
     * @param value
     *            The value of the new node
     */
    public void insert(K key, V value) {
        if (this.root == null) {
            this.root = new Node(key, value);
            return;
        }
        Node n = this.getKey(key);
        if (n.key.compareTo(key) > 0) {
            n.left = new Node(key, value);
            return;
        }
        n.right = new Node(key, value);
    }

    /**
     * Returns the value associated with the specified key
     * 
     * @param key
     *            The key to search for
     * @return The value associated with the key; null if the key is not in the tree
     */
    public V lookUp(K key) {
        Node n = this.getKey(key);
        if (n.key.compareTo(key) != 0) {
            return null;
        }
        return n.value;
    }

    /**
     * Unimplemented function; should remove keys from the dictionary
     * 
     * @param key
     *            The key of the node to remove
     */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * A node of the binary search tree
     * 
     * @author Neil Dey
     */
    private class Node {
        /** The key associated with the node **/
        public K key;
        /** The value associated with the node **/
        public V value;
        /** The left child **/
        public Node left;
        /** The right child **/
        public Node right;

        /**
         * Constructs a node with the given key, value, and children
         * 
         * @param key
         *            The key of the new node
         * @param value
         *            The value of the new node
         * @param left
         *            The left child of the node
         * @param right
         *            The right child of the node
         */
        public Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * Constructs a new node with the given key and value with no children.
         * 
         * @param key
         *            The key of the new node
         * @param value
         *            The value of the new node
         */
        public Node(K key, V value) {
            this(key, value, null, null);
        }

        /**
         * Gets Node with the specified key that occurs in the subtree with this as the
         * root
         * 
         * @param key
         *            The key to search for
         * @return The node with the specified key
         */
        public Node getKey(K key) {
            int temp = this.key.compareTo(key);
            if (temp == 0) {
                return this;
            }
            if (temp > 0) {
                if (this.left == null) {
                    return this;
                }
                return this.left.getKey(key);
            }
            if (this.right == null) {
                return this;
            }
            return this.right.getKey(key);
        }

        // public String toString() {
        // return this.key.toString() + ", " + this.value.toString();
        // }
    }
}
