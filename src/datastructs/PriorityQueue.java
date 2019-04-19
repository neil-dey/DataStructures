package datastructs;

/**
 * Represents the priority queue abstract data type
 * 
 * @author Neil Dey
 *
 * @param <E>
 *            The generic type for elements
 */
public interface PriorityQueue<E extends Comparable<E>> {
    /**
     * Inserts an entry into the priority queue
     * 
     * @param entry
     *            The entry to insert
     */
    void enqueue(E entry);

    /**
     * Examines but does not remove the entry at the front of the priority queue
     * 
     * @return The first entry in the priority queue
     */
    E peek();

    /**
     * Removes the entry at the front of the priority queue
     * 
     * @return The first (removed) entry in the priority queue
     */
    E dequeue();

    /**
     * Returns whether or not the priority queue is empty
     * 
     * @return Whether or not the priority queue is empty
     */
    boolean isEmpty();

    /**
     * If the priority queue is adaptable, this updates the position of the given
     * data in the priority queue.
     * 
     * @param data
     *            The data to update
     */
    void update(LocationAware data);
}