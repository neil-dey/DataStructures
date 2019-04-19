package datastructs;

/**
 * A skip list that allows duplicate values. This is an implementation of the
 * dictionary abstract data type.
 * 
 * @author Neil Dey
 * @param <K>
 *            The generic type for keys. Keys must implement Comparable.
 * @param <V>
 *            The generic type for values. Values must implement Comparable.
 * 
 */
public class SkipList<K extends Comparable<K>, V extends Comparable<V>> implements Dictionary<K, V> {
    /** Special constant for keys that represents the "largest" possible key **/
    private final K posInfty = null; // Special constant for keys
    /** Special constant for keys that represents the "smallest" possible key **/
    private final K negInfty = null; // Special constant for keys
    /** The node pointing to the top-level NEG_INFTY **/
    private Node head;
    /** The node pointing to the top-level POS_INFTY **/
    private Node tail;
    /** The height of the skip list **/
    private int height;

    /**
     * Initializes the skip list with a height of one and with the POS_INFTY and
     * NEG_INFTY entries
     */
    public SkipList() {
        this.head = new Node(negInfty, null);
        this.tail = new Node(posInfty, null);
        head.next = tail;
        tail.prev = head;
        this.height = 1;
    }

    /**
     * This method searches for the first node in the bottom-level of the skip list
     * that matches the inputed key. If the key is not found, it returns the node
     * that would be behind it if the key were in the dictionary.
     * 
     * @param key
     *            The key to search for
     * @return The node in the dictionary that has a key that is at most equal to
     *         the specified key
     */
    private Node getNodeAtMost(K key) {
        Node p = head;
        while (true) {
            if (p.next.key != posInfty && p.next.key.compareTo(key) < 0) {
                p = p.next;
            } else if (p.down == null) {
                if (p.next.key == posInfty || p.next.key.compareTo(key) > 0) {
                    return p;
                }
                return p.next;
            } else {
                p = p.down;
            }
        }
    }

    /**
     * Returns the value of the first entry in the dictionary matching the given key
     * 
     * @param key
     *            The key of the value to search for
     * @return The value of the first entry in the dictionary matching the given
     *         key, or null if the key doesn't exit.
     */
    public V lookUp(K key) {
        Node p = getNodeAtMost(key);
        if (p.key == negInfty) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            return p.value;
        }
        return null;
    }

    private Node getNodeAtMost(K key, V value) {
        Node p = head;
        while (true) {
            if (p.next.key != posInfty && p.next.key.compareTo(key) < 0) {
                p = p.next;
            } else if (p.down == null) {
                if (p.next.key == posInfty || p.next.key.compareTo(key) > 0) {
                    return p;
                }
                return p.next;
            } else {
                if (p.next.key != posInfty && p.next.key.compareTo(key) == 0 && p.next.value.compareTo(value) < 0) {
                    p = p.next;
                } else {
                    p = p.down;
                }
            }
        }
    }

    /**
     * Inserts the given key/value pair as an entry into the skip list
     * 
     * @param key
     *            The key of the entry
     * @param value
     *            The value that the key matches to
     */
    public void insert(K key, V value) {
        Node p = getNodeAtMost(key, value);
        if (p.key != negInfty && p.key.compareTo(key) == 0) {
            p = p.prev;
        }
        // This is the bottleneck
        while (p.next.key != posInfty && p.next.key.compareTo(key) == 0 && p.next.value.compareTo(value) < 0) {
            p = p.next;
        }

        Node n = new Node(key, value, null, null, p, p.next);
        p.next.prev = n;
        p.next = n;

        int heightSoFar = 1;
        p = n;
        Node r = n.next;
        while (Math.random() < 0.5) {
            // If we need to add another level to the skip list
            if (heightSoFar >= height) {
                Node temp = new Node(negInfty, null);
                temp.down = head;
                head.up = temp;
                head = temp;

                temp = new Node(posInfty, null);
                temp.down = tail;
                tail.up = temp;
                tail = temp;

                head.next = tail;
                tail.prev = head;

                temp = null;
                this.height++;
            }

            p.up = new Node(key, value);
            p.up.down = p;
            p = p.up;

            while (r.up == null) {
                r = r.next;
            }
            r = r.up;
            p.prev = r.prev;
            r.prev.next = p;
            p.next = r;
            r.prev = p;

            heightSoFar++;
        }
    }

    public V update(K key, V value) {
        Node p = this.getNodeAtMost(key);
        if (p.key == null) {
            return null;
        }
        V temp = p.value;
        while (p != null) {
            p.value = value;
            p = p.up;
        }
        return temp;
    }

    public V remove(K key) {
        // throw new UnsupportedOperationException();
        Node p = this.getNodeAtMost(key);
        if (p.key == null) {
            return null;
        }
        V temp = p.value;
        while (p != null) {
            p.down = null;
            p.next.prev = p.prev;
            p.prev.next = p.next;
            p = p.up;
        }
        return temp;
    }

    // Only necessary for debugging purposes
    public String printAll() {
        StringBuilder s = new StringBuilder();
        Node p = head;
        while (p.down != null) {
            p = p.down;
        }
        p = p.next;
        while (p.key != posInfty) {
            s.append(p.value + ", ");
            p = p.next;
        }
        return s.toString();
    }

    /**
     * An inner class that stores the keys and values for the skip list
     * 
     * @author Neil Dey
     *
     */
    private class Node {
        /** The key of the entry **/
        private K key;
        /** The value of the entry **/
        private V value;
        /** The node above this one **/
        private Node up;
        /** The node below this one **/
        private Node down;
        /** The node before this one **/
        private Node prev;
        /** The node after this one **/
        private Node next;

        /**
         * Initializes a node with the given key, value, and neighboring nodes
         * 
         * @param key
         *            The key for the entry
         * @param value
         *            The value for the entry
         * @param up
         *            The node above this one
         * @param down
         *            The node below this one
         * @param prev
         *            The node before this one
         * @param next
         *            The node after this one
         */
        public Node(K key, V value, Node up, Node down, Node prev, Node next) {
            this.key = key;
            this.value = value;
            this.up = up;
            this.down = down;
            this.prev = prev;
            this.next = next;
        }

        /**
         * Initializes the node with the given key and value. The node will have no
         * neighbors.
         * 
         * @param key
         *            The key for the entry
         * @param value
         *            The value for the entry
         */
        public Node(K key, V value) {
            this(key, value, null, null, null, null);
        }

        // Only necessary for debugging purposes
        // public String toString() {
        // return "Key: " + key + " Value: " + value;
        // }

    }

}
