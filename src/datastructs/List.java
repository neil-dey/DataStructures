package datastructs;
/**
 * Represents the List abstract data type
 * 
 * @author Neil Dey
 *
 * @param <E>
 *            Represents the generic type for elements
 */
public interface List<E> extends Iterable<E> {
    /**
     * Returns the element at the specified index of the ArrayList.
     * 
     * @param index
     *            The index to retrieve the element from
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException
     *             if the index is outside the bounds of the ArrayList.
     */
    E get(int index);

    /**
     * Adds the specified element to the list at the given index
     * 
     * @param index
     *            The index to insert the element at
     * @param element
     *            The element to insert
     * @throws IndexOutOfBoundsException
     *             if the index is outside the bounds of the ArrayList.
     */
    void add(int index, E element);

    /**
     * Adds the specified element to the end of the list
     * 
     * @param element
     *            The element to be added
     * @throws IndexOutOfBoundsException
     *             if the index is outside the bounds of the ArrayList.
     */
    void add(E element);

    /**
     * Sets the value at the specified index to the given element.
     * 
     * @param index
     *            The index of the element to replace
     * @param element
     *            The element to be placed at the given index
     * @return The replaced element
     * @throws IndexOutOfBoundsException
     *             if the specified index is outside the bounds of the ArrayList.
     */
    E set(int index, E element);

    /**
     * Removes the element at the specified index
     * 
     * @param index
     *            The index of the element to remove
     * @return The removed element
     * @throws IndexOutOfBoundsException
     *             if the specified index is outside the bounds of the ArrayList.
     */
    E remove(int index);

    /**
     * Removes the first instance of the specified element from the list.
     * 
     * @param element
     *            The element to remove
     * @return True if the element was removed; false if the element was not in the
     *         list
     */
    boolean remove(E element);

    /**
     * Returns the size of the list
     * 
     * @return The size of the list
     */
    int size();
}
