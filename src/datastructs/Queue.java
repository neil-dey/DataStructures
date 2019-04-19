package datastructs;

/**
 * A linked-list based implementation of the Queue abstract data type
 * 
 * @author Neil Dey
 *
 * @param <E>
 *            The generic type for entries
 */
public class Queue<E> {
    /** The head of the queue **/
    private Node head;
    /** The tail of the queue **/
    private Node tail;
    /** The number of elements in the queue **/
    private int size;

    /**
     * Constructs a new queue
     */
    public Queue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Inserts a new element into the queue
     * 
     * @param data
     *            The element to insert
     */
    public void enqueue(E data) {
        this.size++;
        if (size == 1) {
            this.head = new Node(data);
            this.tail = head;
            return;
        }
        this.tail.next = new Node(data);
        this.tail = this.tail.next;
    }

    /**
     * Removes the element at the front of the queue
     * 
     * @return The element at the front of the queue
     */
    public E dequeue() {
        E temp = this.head.data;
        this.head = this.head.next;
        if (this.size == 1) {
            this.tail = this.tail.next;
        }
        this.size--;
        return temp;
    }

    /**
     * Returns the element at the front of the queue without removing it
     * 
     * @return The element at the front of the queue
     */
    public E peek() {
        return this.head.data;
    }

    /**
     * Returns whether or not the queue is empty
     * 
     * @return True if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the size of the queue
     * 
     * @return The size of the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * Represents a node in the linked list that the queue is based off of
     * 
     * @author Neil Dey
     */
    private class Node {
        /** The data held in the node **/
        public E data;
        /** The next node in the queue **/
        public Node next;

        /**
         * Creates a new node with the given data and next node
         * 
         * @param data
         *            The data held in the node
         * @param next
         *            The next node in the list
         */
        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Creates a new node without a successor node
         * 
         * @param data
         *            The data held in the node
         */
        public Node(E data) {
            this(data, null);
        }

        public String toString() {
            return this.data.toString();
        }
    }

}
