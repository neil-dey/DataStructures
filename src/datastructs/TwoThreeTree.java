package datastructs;

public class TwoThreeTree<K extends Comparable<K>, V> implements Dictionary<K, V> {
    /** The root of the search tree **/
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
        if (n.leftKey.compareTo(key) == 0) {
            V temp = n.leftValue;
            n.leftValue = value;
            return temp;
        } else if (n.rightKey != null && n.rightKey.compareTo(key) == 0) {
            V temp = n.rightValue;
            n.rightValue = value;
            return temp;
        } else {
            return null;
        }
    }

    public void insert(K key, V value) {
        if (this.root == null) {
            this.root = new Node(key, value);
            return;
        }

        Node n = this.getKey(key);

        // Is a 2-node
        if (n.rightKey == null) {
            if (key.compareTo(n.leftKey) < 0) {
                n.rightKey = n.leftKey;
                n.rightValue = n.leftValue;
                n.leftKey = key;
                n.leftValue = value;
            } else {
                n.rightKey = key;
                n.rightValue = value;
            }
            return;
        }

        // Is a 3-node. T-T
        if (key.compareTo(n.leftKey) < 0) {
            n.overFlowKey = n.rightKey;
            n.overFlowValue = n.rightValue;
            n.rightKey = n.leftKey;
            n.rightValue = n.leftValue;
            n.leftKey = key;
            n.leftValue = value;
        } else if (key.compareTo(n.rightKey) < 0) {
            n.overFlowKey = n.rightKey;
            n.overFlowValue = n.rightValue;
            n.rightKey = key;
            n.rightValue = value;
        } else {
            n.overFlowKey = key;
            n.overFlowValue = value;
        }
        split(n);
    }

    private void split(Node n) {
        // This is the root node
        if (n.parent == null) {
            Node r = new Node(n.rightKey, n.rightValue);
            r.left = new Node(n.leftKey, n.leftValue, null, null, n.left, null, n.middle, r);
            r.right = new Node(n.overFlowKey, n.overFlowValue, null, null, n.right, null, n.overFlowNode, r);
            TwoThreeTree.this.root = r;
        }
        // The parent is a 2-node
        else if (n.parent.rightKey == null) {
            int kComp = n.parent.leftKey.compareTo(n.rightKey);
            if (kComp < 0) {
                n.parent.rightKey = n.rightKey;
                n.parent.rightValue = n.rightValue;
            } else {
                n.parent.rightKey = n.parent.leftKey;
                n.parent.rightValue = n.parent.leftValue;

                n.parent.leftKey = n.rightKey;
                n.parent.leftValue = n.rightValue;
            }
            // Split n into two 2-nodes
            // n is the left node
            if (n == n.parent.left) {
                n.parent.left = new Node(n.leftKey, n.leftValue, null, null, n.left, null, n.middle, n.parent);
                n.parent.middle = new Node(n.overFlowKey, n.overFlowValue, null, null, n.right, null, n.overFlowNode,
                        n.parent);
            }
            // n is the right node
            else {
                n.parent.middle = new Node(n.leftKey, n.leftValue, null, null, n.left, null, n.middle, n.parent);
                n.parent.right = new Node(n.overFlowKey, n.overFlowValue, null, null, n.right, null, n.overFlowNode,
                        n.parent);
            }
        }
        // The parent is a 3-node
        else {
            int kComp = n.rightKey.compareTo(n.parent.leftKey);
            if (kComp < 0) {
                n.parent.overFlowKey = n.parent.rightKey;
                n.parent.overFlowValue = n.parent.rightValue;
                n.parent.rightKey = n.parent.leftKey;
                n.parent.rightValue = n.parent.leftValue;
                n.parent.leftKey = n.rightKey;
                n.parent.leftValue = n.rightValue;
            } else {
                kComp = n.rightKey.compareTo(n.parent.rightKey);
                if (kComp < 0) {
                    n.parent.overFlowKey = n.parent.rightKey;
                    n.parent.overFlowValue = n.parent.rightValue;
                    n.parent.rightKey = n.rightKey;
                    n.parent.rightValue = n.rightValue;
                } else {
                    n.parent.overFlowKey = n.rightKey;
                    n.parent.overFlowValue = n.rightValue;
                }
            }
            // This is the left child
            if (n.parent.left == n) {
                n.parent.overFlowNode = n.parent.right;
                n.parent.right = n.parent.middle;
                n.parent.middle = new Node(n.overFlowKey, n.overFlowValue, null, null, n.right, null, n.overFlowNode,
                        n.parent);
                n.parent.left = new Node(n.leftKey, n.leftValue, null, null, n.left, null, n.middle, n.parent);
            }
            // This is the middle child
            else if (n.parent.middle == n) {
                n.parent.overFlowNode = n.parent.right;
                n.parent.middle = new Node(n.leftKey, n.leftValue, null, null, n.left, null, n.middle, n.parent);
                n.parent.right = new Node(n.overFlowKey, n.overFlowValue, null, null, n.right, null, n.overFlowNode,
                        n.parent);
            }
            // This is the right child
            else {
                n.parent.right = new Node(n.leftKey, n.leftValue, null, null, n.left, null, n.middle, n.parent);
                n.parent.overFlowNode = new Node(n.overFlowKey, n.overFlowValue, null, null, n.right, null,
                        n.overFlowNode, n.parent);
            }
            this.split(n.parent);
        }
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
        if (key.compareTo(n.leftKey) == 0) {
            return n.leftValue;
        } else if (n.rightKey != null && key.compareTo(n.rightKey) == 0) {
            return n.rightValue;
        }
        return null;
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
     * A node of the search tree
     * 
     * @author Neil Dey
     */
    private class Node {
        public K leftKey;
        public K rightKey;
        public V leftValue;
        public V rightValue;

        public Node parent;
        public Node left;
        public Node middle;
        public Node right;

        public K overFlowKey;
        public V overFlowValue;
        public Node overFlowNode;

        public Node(K key1, V value1, K key2, V value2, Node left, Node middle, Node right, Node parent) {
            this.leftKey = key1;
            this.rightKey = key2;
            this.leftValue = value1;
            this.rightValue = value2;
            this.left = left;
            this.middle = middle;
            this.right = right;
            this.parent = parent;

            if (left != null) {
                left.parent = this;
            }
            if (middle != null) {
                middle.parent = this;
            }
            if (right != null) {
                right.parent = this;
            }
        }

        // public Node(K key, V value, Node left, Node right) {
        // this(key, value, null, null, left, null, right, null);
        // }

        public Node(K key, V value) {
            this(key, value, null, null, null, null, null, null);
        }

        // public Node(K key1, V value1, K key2, V value2) {
        // this(key1, value1, key2, value2, null, null, null, null);
        // }

        public Node getKey(K key) {
            // 2-node
            if (this.rightKey == null) {
                int temp = this.leftKey.compareTo(key);
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
            // 3-node
            int lcomp = this.leftKey.compareTo(key);
            if (lcomp == 0) {
                return this;
            }
            if (lcomp > 0) {
                if (this.left == null) {
                    return this;
                }
                return this.left.getKey(key);
            }
            int rcomp = this.rightKey.compareTo(key);
            if (rcomp == 0) {
                return this;
            }
            if (rcomp < 0) {
                if (this.right == null) {
                    return this;
                }
                return this.right.getKey(key);
            }
            if (this.middle == null) {
                return this;
            }
            return this.middle.getKey(key);
        }

        // For debugging purposes only
        // public String toString() {
        // return "(" + this.leftKey + ", " + this.leftValue + ") (" + this.rightKey +
        // ", " + this.rightValue + ")";
        // }
    }
}
