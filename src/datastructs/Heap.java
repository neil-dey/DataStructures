package datastructs;

/**
 * Represents a heap data structure
 * 
 * @author Neil Dey
 *
 * @param <E>
 *            The generic type for elements
 */
public class Heap<E extends Comparable<E>> implements PriorityQueue<E> {
    /**
     * The list of elements in the heap
     */
    ArrayList<E> list;

    /**
     * Whether or not the heap is a maximum heap
     */
    boolean isMaxHeap;

    /**
     * Creates a heap
     * 
     * @param isMaxHeap
     *            Whether or not the heap is a max-heap
     */
    public Heap(boolean isMaxHeap) {
        this.list = new ArrayList<E>();
        this.isMaxHeap = isMaxHeap;
    }

    /**
     * Creates a min-heap
     */
    public Heap() {
        this(false);
    }

    @Override
    public void enqueue(E entry) {
        this.list.add(entry);
        if (entry instanceof LocationAware) {
            ((LocationAware) entry).setLocation(this.list.size() - 1);
        }
        this.upHeap(this.list.size() - 1);
    }

    @Override
    public E peek() {
        return this.list.get(0);
    }

    @Override
    public E dequeue() {
        E temp = this.list.get(0);
        this.list.set(0, this.list.get(this.list.size() - 1));
        this.list.remove(this.list.size() - 1);
        this.downHeap(0);
        return temp;
    }

    private void upHeap(int index) {
        int flip = 1;
        // If max
        if (this.isMaxHeap) {
            flip = -1;
        }
        if (index > 0 && index < this.list.size()) {
            E parent = this.list.get((index - 1) / 2);
            E current = this.list.get(index);
            if (flip * parent.compareTo(current) > 0) {
                this.list.set((index - 1) / 2, current);
                this.list.set(index, parent);

                if (parent instanceof LocationAware) {
                    ((LocationAware) parent).setLocation(index);
                    ((LocationAware) current).setLocation((index - 1) / 2);
                }

                this.upHeap((index - 1) / 2);
            }
        }
    }

    private void downHeap(int index) {
        int flip = 1;
        if (this.isMaxHeap) {
            flip = -1;
        }

        int maxChildIndex = 0;
        // Both children exist
        if (2 * index + 2 < this.list.size()) {
            if (flip * this.list.get(2 * index + 2).compareTo(this.list.get(2 * index + 1)) <= 0) {
                maxChildIndex = 2 * index + 2;
            } else {
                maxChildIndex = 2 * index + 1;
            }
        }
        // Only left child exists
        else if (2 * index + 1 < this.list.size()) {
            maxChildIndex = 2 * index + 1;
        }
        // If i = 0 now, node has no children
        if (maxChildIndex > 0 && flip * this.list.get(index).compareTo(this.list.get(maxChildIndex)) > 0) {
            E temp = this.list.get(index);
            this.list.set(index, this.list.get(maxChildIndex));
            this.list.set(maxChildIndex, temp);

            if (temp instanceof LocationAware) {
                ((LocationAware) temp).setLocation(maxChildIndex);
                ((LocationAware) this.list.get(maxChildIndex)).setLocation(index);
            }

            this.downHeap(maxChildIndex);
        } else {
            if (this.list.get(index) instanceof LocationAware) {
                ((LocationAware) this.list.get(index)).setLocation(index);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.list.size() == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(LocationAware data) {
        E parent = this.list.get((data.getLocation() - 1) / 2);
        if (parent.compareTo((E) data) > 0) {
            this.upHeap(data.getLocation());
        } else {
            this.downHeap(data.getLocation());
        }
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
