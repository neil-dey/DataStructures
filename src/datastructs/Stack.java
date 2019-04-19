package datastructs;


/**
 * A linked-list implementation of the stack abstract data type
 * 
 * @author Neil Dey
 *
 * @param <E>
 *            The generic type for entries
 */
public class Stack<E> {
    /** The top of the stack **/
    private Node head;
    /** The number of elements in the stack **/
    private int size;

    /**
     * Initializes a new stack
     */
    public Stack() {
        this.head = new Node();
        this.size = 0;
    }

    /**
     * Returns whether or not the stack is empty
     * 
     * @return True if the stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the element at the top of the stack without removing it
     * 
     * @return The element at the top of the stack
     */
    public E peek() {
        if (this.size == 0) {
            return null;
        }
        return this.head.data;
    }

    /**
     * Adds an element to the top of the stack
     * 
     * @param data
     *            The element to add
     */
    public void push(E data) {
        this.head = new Node(data, this.head);
        this.size++;
    }

    /**
     * Removes an element from the top of the stack
     * 
     * @return The element that was at the top of the stack
     */
    public E pop() {
        if (this.size == 0) {
            return null;
        }
        E temp = this.head.data;
        this.head = this.head.next;
        this.size--;
        return temp;
    }

    /**
     * Represents a node in the linked-list that the stack is made of
     * 
     * @author Neil Dey
     */
    private class Node {
        /** The data the node contains **/
        public E data;
        /** The next node in the list **/
        public Node next;

        /**
         * Constructs a new node with the given data and successor node
         * 
         * @param data
         *            The element the node holds
         * @param next
         *            The next node in the list
         */
        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Constructs a new node with no data or successor node
         */
        public Node() {
            this(null, null);
        }
    }

}
