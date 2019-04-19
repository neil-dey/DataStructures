package datastructs;

import java.util.Iterator;

/**
 * A hash table that uses separate chaining and Fibonnaci hash compression
 * 
 * @author Neil Dey
 *
 * @param <K>
 *            The generic type for keys
 * @param <V>
 *            The generic type for values
 */
@SuppressWarnings("rawtypes")
public class HashTable<K, V> implements Dictionary<K, V>, Iterable<HashTable.Node> {

    /** The load factor at which the hash table resizes **/
    protected static final double RESIZE_THRESHOLD = 1.00;
    /** The initial capacity of the hash table **/
    protected static final int INIT_CAP = 19;
    /** The inverse of the golden ratio **/
    protected static final double INV_PHI = 2.0 / (1 + Math.sqrt(5));

    /** Determines whether or not to calculate primes **/
    protected static final boolean NEED_PRIMES = false;

    /** The list of chains in the hash table **/
    protected Node[] table;
    /** The number of elements in the hash table **/
    protected int numElements;
    /** The capacity of the hash table **/
    protected int capacity;
    /** The head of the iterable linked list of nodes **/
    protected Node head;
    /** The tail of the iterable linked list of nodes **/
    protected Node tail;

    /**
     * Creates a new hash table with the given capacity
     * 
     * @param capacity
     *            The capacity of the hash table
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        if (capacity % 2 == 0) {
            capacity++;
        }
        while (!isPrime(capacity)) {
            capacity += 2;
        }

        this.table = new HashTable.Node[capacity];
        this.capacity = capacity;
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        this.head.fullNext = this.tail;
        this.tail.fullPrev = this.head;
        this.numElements = 0;

    }

    /**
     * Creates a new hash table with the default initial capacity
     */
    public HashTable() {
        this(HashTable.INIT_CAP);
    }

    @Override
    public void insert(K key, V value) {
        if (this.numElements / this.capacity >= HashTable.RESIZE_THRESHOLD) {
            this.resize();
        }

        int index = this.compress(key.hashCode());
        Node n = new Node(key, value);
        n.next = this.table[index];

        n.fullNext = this.head.fullNext;
        n.fullPrev = this.head;
        this.head.fullNext = n;
        n.fullNext.fullPrev = n;

        this.table[index] = n;
        this.numElements++;
    }

    @Override
    public V lookUp(K key) {
        int index = this.compress(key.hashCode());
        Node n = this.table[index];
        while (n != null) {
            if (n.key.equals(key)) {
                return n.value;
            }
            n = n.next;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = this.compress(key.hashCode());
        Node n = this.table[index];
        if (n == null) {
            return null;
        }
        if (n.key.equals(key)) {
            this.table[index] = n.next;

            n.fullNext.fullPrev = n.fullPrev;
            n.fullPrev.fullNext = n.fullNext;

            this.numElements--;
            return n.value;
        }
        while (n.next != null) {
            if (n.next.key.equals(key)) {
                V temp = n.next.value;
                n.next = n.next.next;

                n.fullNext.fullPrev = n.fullPrev;
                n.fullPrev.fullNext = n.fullNext;

                this.numElements--;
                return temp;
            }
            n = n.next;
        }
        return null;
    }

    @Override
    public V update(K key, V value) {
        int index = this.compress(key.hashCode());
        Node n = this.table[index];
        while (n != null) {
            if (n.key.equals(key)) {
                V temp = n.value;
                n.value = value;
                return temp;
            }
            n = n.next;
        }
        return null;
    }

    /**
     * Compresses a hash into the capacity of the hash table using Fibonnaci hash
     * compression
     * 
     * @param hash
     *            The hash to compress
     * @return The compressed hash
     */
    protected int compress(int hash) {
        double temp = hash * HashTable.INV_PHI;
        temp = temp - (int) temp;
        return (int) Math.abs(this.capacity * temp);
    }

    /**
     * Resizes the hash table
     */
    @SuppressWarnings("unchecked")
    protected void resize() {
        if (HashTable.NEED_PRIMES) {
            if (this.capacity % 2 == 0) {
                this.capacity++;
            }
            while (!isPrime(this.capacity)) {
                this.capacity += 2;
            }
        } else {
            this.capacity = this.capacity * 2 + 1;
        }
        Node[] temp = new HashTable.Node[capacity];

        Node n = this.head.fullNext;
        while (n != this.tail) {
            int index = this.compress(n.key.hashCode());
            n.next = temp[index];

            temp[index] = n;
            n = n.fullNext;
        }
        this.table = temp;
    }

    /**
     * Returns (base^power) % mod
     * 
     * @param base
     *            The base of the exponent
     * @param power
     *            The exponent
     * @param mod
     *            The value to take the mod of
     * @return (base^power) % mod
     */
    protected int modPower(int base, int power, int mod) {
        long baseLong = base;
        baseLong = baseLong % mod;

        long result = 1;
        while (power > 0) {
            if (power % 2 == 1) {
                result = (result * baseLong) % mod;
            }
            power >>= 1;
            baseLong = (baseLong * baseLong) % mod;
        }
        return (int) result;
    }

    /**
     * The Miller Rabin Test for primes up to 2^32. The values checked will be a =
     * 2, 7, and 63.
     * 
     * To check primes up to 2^64, we must check far more values: a = 2, 3, 5, 7,
     * 11, 13, 17, 19, 23, 29, 31, and 37.
     * 
     * Thus, as written, this only guarantees primeness for 32-bit integers.
     * 
     * @author Neil Dey
     * 
     * 
     * @param n
     *            A 32-bit integer
     * @return Whether or not n is prime
     */
    protected boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        if (n == 2 || n == 3) {
            return true;
        }
        if(n % 6 != 1 && n % 6 != 5) {
            return false;
        }
        
        int d = n - 1;
        int r = 0;
        while (d % 2 == 0) {
            d >>= 1;
            r++;
        }
        int a = 2;
        outer: for (int i = 0; i < 3; i++) {
            if (i == 1) {
                a = 7;
            } else if (i == 2) {
                a = 63;
            }
            int x = modPower(a, d, n);
            if (x == 1 || x == n - 1) {
                continue outer;
            }
            for (int j = 0; j < r - 1; j++) {
                x = modPower(x, 2, n);
                if (x == 1) {
                    return false;
                }
                if (x == n - 1) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Node n = this.head.fullNext;
        StringBuilder s = new StringBuilder();
        s.append("{ ");
        while (n != this.tail) {
            s.append(n.toString() + " ");
            n = n.fullNext;
        }
        s.append("}");
        return s.toString();
    }

    /**
     * Gets the capacity of the hash table
     * 
     * @return The capacity of the hash table
     */
    public int getHashTableLength() {
        return this.table.length;
    }

    /**
     * Gets the number of elements in the hash table
     * 
     * @return The number of elements in the hash table
     */
    public int size() {
        return this.numElements;
    }

    @Override
    public Iterator<HashTable.Node> iterator() {
        Iterator<HashTable.Node> it = new Iterator<HashTable.Node>() {
            private Node curNode = HashTable.this.head;

            @Override
            public boolean hasNext() {
                return this.curNode.fullNext != HashTable.this.tail;
            }

            @Override
            public Node next() {
                this.curNode = this.curNode.fullNext;
                return this.curNode;
            }
        };
        return it;
    }

    /**
     * A node in a chain in the hash table
     * 
     * @author Neil Dey
     *
     */
    public class Node {
        /** The key held by the Node **/
        public K key;
        /** The value held by the Node **/
        public V value;
        /** The next node in a chain **/
        public Node next;
        /** The next node in the overall linked list **/
        public Node fullNext;
        /** The previous node in the overall linked list **/
        public Node fullPrev;

        /**
         * Creates a new Node with the given key and value
         * 
         * @param key
         *            The key held by the Node
         * @param value
         *            The value held by the Node
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + this.key.toString() + ", " + this.value.toString() + ")";
        }
    }
}
