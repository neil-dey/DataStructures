package datastructs;

import java.util.Iterator;

/**
 * An array-based implementation of the List abstract data type.
 * 
 * @author Neil Dey
 *
 * @param <E>
 *            The generic type for entries
 */
public class ArrayList<E> implements List<E> {
    /** Default initial capacity of the array **/
    private static final int INIT_CAP = 10;
    /** An array holding the data **/
    private E[] list;
    /** The size of the list **/
    private int size;

    /**
     * Initializes the ArrayList object with a size of 0 and with the specified
     * initial capacity.
     * 
     * @param capacity
     *            The initial capacity of the ArrayList
     * @throws IllegalArgumentException
     *             if the specified capacity is nonpositive.
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        list = (E[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * Initializes the ArrayList object with a size of 0 and with the default
     * initial capacity.
     */
    public ArrayList() {
        this(INIT_CAP);
    }

    @Override
    public E get(int index) {
        return list[index];
    }

    @Override
    public void add(int index, E e) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        if (this.size == list.length) {
            this.growArray();
        }
        for (int i = size; i > index; i--) {
            this.list[i] = this.list[i - 1];
        }
        list[index] = e;
        this.size++;
    }

    @Override
    public void add(E e) {
        this.add(size, e);
    }

    @Override
    public E set(int index, E e) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        E temp = this.list[index];
        this.list[index] = e;
        return temp;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        E temp = list[index];
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        size--;
        return temp;
    }

    @Override
    public boolean remove(E e) {
        for (int i = 0; i < size; i++) {
            if (this.list[i] == e) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * Doubles the capacity of the ArrayList
     */
    @SuppressWarnings("unchecked")
    private void growArray() {
        E[] temp = (E[]) new Object[this.list.length * 2];
        for (int i = 0; i < this.list.length; i++) {
            temp[i] = this.list[i];
        }
        this.list = temp;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            private int current = -1;

            @Override
            public boolean hasNext() {
                return current < ArrayList.this.size - 1;
            }

            @Override
            public E next() {
                this.current++;
                return ArrayList.this.list[current];
            }
        };
        return it;
    }

    @Override
    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        StringBuilder s = new StringBuilder();
        s.append("[");
        s.append(this.list[0]);
        for (int i = 1; i < this.size; i++) {
            s.append(", " + this.list[i]);
        }
        s.append("]");
        return s.toString();
    }

    // Implemented how Java implemented equals() for AbstractList
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        if (((List<?>) o).size() != this.size) {
            return false;
        }

        Iterator<E> it1 = this.iterator();
        Iterator<?> it2 = ((List<?>) o).iterator();
        while (it1.hasNext()) {
            E e1 = it1.next();
            Object e2 = it2.next();
            if (!(e1 == null ? e2 == null : e1.equals(e2))) {
                return false;
            }
        }
        return true;
    }

    // As specified by the Java documentation for List
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    /**
     * Performs a binary search of the list with the given key. If a match isn't
     * found, it returns the element before where it would be in the list.
     * 
     * @param key
     *            The key to search for
     * @return The matching element of the list, or the element before where it
     *         would be in the list
     */
    public E binarySearch(E key) {
        return binarySearch(key, 0, this.size - 1);
    }

    @SuppressWarnings("unchecked")
    private E binarySearch(E key, int low, int high) {
        if (high >= low) {
            int middle = (high + low) / 2;
            if (this.list[middle] == key) {
                return list[middle];
            }
            if (((Comparable<E>) this.list[middle]).compareTo(key) > 0) {
                return binarySearch(key, low, middle - 1);
            }
            return binarySearch(key, middle + 1, high);
        }
        return this.list[high];
    }

    /**
     * Quicksorts the contents of the array
     */
    public void sort() {
        this.list = this.quickSort(this.list, this.size);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private E[] quickSort(E[] array, int length) {

        if (length <= 1) {
            return array;
        }

        E[] less = (E[]) new Object[length];
        E[] more = (E[]) new Object[length];
        E[] equal = (E[]) new Object[length];

        int lessIndex = 0;
        int equalIndex = 0;
        int moreIndex = 0;

        E pivot = array[(int) (length * Math.random())];
        for (int i = 0; i < length; i++) {
            if (((Comparable) array[i]).compareTo(pivot) < 0) {
                less[lessIndex] = array[i];
                lessIndex++;
            } else if (((Comparable) array[i]).compareTo(pivot) == 0) {
                equal[equalIndex] = array[i];
                equalIndex++;
            } else {
                more[moreIndex] = array[i];
                moreIndex++;
            }
        }

        less = this.quickSort(less, lessIndex);
        more = this.quickSort(more, moreIndex);

        int index = 0;
        for (int i = 0; i < lessIndex; i++) {
            array[index] = less[i];
            index++;
        }
        for (int i = 0; i < equalIndex; i++) {
            array[index] = equal[i];
            index++;
        }
        for (int i = 0; i < moreIndex; i++) {
            array[index] = more[i];
            index++;
        }
        return array;
    }
}
