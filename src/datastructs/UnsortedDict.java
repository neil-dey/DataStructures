package datastructs;

//Yes, the unsorted dictionary extends Comparable. Deal with it. Otherwise you can't mergesort the tuples.
/**
 * An unsorted dictionary that uses the move-to-front heuristic. This dictionary
 * does not support duplicate keys.
 * 
 * @author Neil Dey
 *
 * @param <K>
 *            The generic type for keys. Keys must implement Comparable.
 * @param <V>
 *            The generic type for values. Values must implement Comparable.
 */
public class UnsortedDict<K extends Comparable<K>, V extends Comparable<V>> implements Dictionary<K, V> {
    /** The dummy head of the dictionary that contains nothing **/
    private Node head;

    /**
     * Initializes the dictionary with a single dummy head
     */
    public UnsortedDict() {
        this.head = new Node(null, null);
    }

    /**
     * Returns the value of the entry in the dictionary matching the given key
     * 
     * @param key
     *            The key of the value to search for
     * @return The value of the entry in the dictionary matching the given key, or
     *         null if the key doesn't exit.
     */
    public V lookUp(K key) {
        Node p = this.head;
        while (p.next != null) {
            if (p.next.key.equals(key)) {
                Node temp = p.next;
                p.next = p.next.next;
                temp.next = this.head.next;
                this.head.next = temp;
                return this.head.next.value;
            }
            p = p.next;
        }
        return null;
    }

    /**
     * Inserts the given key and value to the end of the list, or updates the value
     * if the key already exists
     * 
     * @param key
     *            The key of the entry
     * @param value
     *            The value of the entry
     */
    public void insert(K key, V value) {
        Node p = this.head;
        while (p.next != null) {
            p = p.next;
            if (p.key.equals(key)) {
                p.value = value;
                return;
            }
        }
        p.next = new Node(key, value);
    }

    public V update(K key, V value) {
        Node p = this.head;
        while (p.next != null) {
            p = p.next;
            if (p.key.equals(key)) {
                V temp = p.value;
                p.value = value;
                return temp;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a list of tuples (2-entry arrays) consisting of the key/value pairs
     * in the dictionary
     * 
     * @return A list of tuples in the dictionary
     */
    public ArrayList<Object[]> getAllTuples() {
        Node p = this.head;
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        while (p.next != null) {
            p = p.next;
            list.add(new Comparable[] { p.key, p.value });
        }
        return list;
    }

    // Based on slides by Dr. King
    /**
     * Sorts tuples from high to low
     * 
     * @param tupleList
     *            The list of tuples
     * @param lower
     *            The lower index to start sorting from
     * @param upper
     *            The upper index to stop sorting at
     * @return A sorted list of tuples
     */
    @SuppressWarnings("rawtypes")
    public static ArrayList<Comparable[]> mergeSortTuples(ArrayList<Comparable[]> tupleList, int lower, int upper) {
        if (tupleList.size() > 1) {
            int middle = tupleList.size() / 2;
            ArrayList<Comparable[]> l = copy(tupleList, 0, middle - 1);
            ArrayList<Comparable[]> r = copy(tupleList, middle, tupleList.size() - 1);
            mergeSortTuples(l, 0, l.size() - 1);
            mergeSortTuples(r, 0, r.size() - 1);
            tupleList = merge(tupleList, l, r);
        }
        return tupleList;
    }

    /**
     * Merges three lists together
     * 
     * @param tupleList
     *            The list to have values merged into
     * @param l
     *            One of the lists to merge into the T list
     * @param r
     *            Another of the lists to merge into the T list
     * @return T as a sorted merged list from the tuples in L and R
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static ArrayList<Comparable[]> merge(ArrayList<Comparable[]> tupleList, ArrayList<Comparable[]> l,
            ArrayList<Comparable[]> r) {
        int lIndex = 0;
        int rIndex = 0;
        for (int i = 0; i < tupleList.size(); i++) {
            // If: There's nothing more in the right list
            // OR: There's stuff in the left list and the left frequency is bigger
            // OR: There's stuff in the left list, both have the same frequency, and left's
            // actRes is first alphabetically
            if (rIndex >= r.size() || (lIndex < l.size() && l.get(lIndex)[1].compareTo(r.get(rIndex)[1]) > 0)
                    || (lIndex < l.size() && l.get(lIndex)[1].compareTo(r.get(rIndex)[1]) == 0
                            && l.get(lIndex)[0].compareTo(r.get(rIndex)[0]) < 0)) {
                tupleList.set(i, l.get(lIndex));
                lIndex++;
            } else {
                tupleList.set(i, r.get(rIndex));
                rIndex++;
            }
        }
        return tupleList;
    }

    /**
     * Returns a list of tuples from the input, starting at the lower bound and
     * ending at the upper bound.
     * 
     * @param tupleList
     *            The list to copy tuples from
     * @param lower
     *            The lower bound to start copying at (inclusive)
     * @param upper
     *            The upper bound to stop copying at (inclusive)
     * @return A list of tuples from T between the lower and upper bounds
     */
    @SuppressWarnings("rawtypes")
    private static ArrayList<Comparable[]> copy(ArrayList<Comparable[]> tupleList, int lower, int upper) {
        ArrayList<Comparable[]> l = new ArrayList<Comparable[]>();
        for (int i = lower; i <= upper; i++) {
            l.add((Comparable[]) tupleList.get(i));
        }
        return l;
    }

    // Necessary for debugging only
    // public String toString() {
    // if (this.head.next == null) {
    // return "[]";
    // }
    // Node p = this.head.next;
    // StringBuilder sb = new StringBuilder();
    // sb.append("[(" + p.key + ", " + p.value + ")");
    // while (p.next != null) {
    // p = p.next;
    // sb.append(", (" + p.key + ", " + p.value + ")");
    // }
    // sb.append("]");
    // return sb.toString();
    // }

    /**
     * An inner class that holds the entries of the dictionary
     * 
     * @author Neil Dey
     *
     */
    private class Node {
        /** The key of the entry **/
        private K key;
        /** The value of the entry **/
        private V value;
        /** The next node in the dictionary **/
        private Node next;

        /**
         * Initializes the node with the given key, value, and next node
         * 
         * @param key
         *            The key of the entry
         * @param value
         *            The value of the entry
         * @param next
         *            The next node in the list
         */
        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Initializes the node with the given key and value without a next node
         * 
         * @param key
         *            The key of the entry
         * @param value
         *            The value of the entry
         */
        public Node(K key, V value) {
            this(key, value, null);
        }

        // For debugging purposes only
        // /**
        // * Returns a string representation of the node
        // *
        // * @return A string of the format "Key: [key] Value: [value]"
        // */
        // public String toString() {
        // return "Key: " + this.key + " Value: " + this.value;
        // }
    }

}
