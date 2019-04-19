package datastructs;

import java.util.Iterator;

public class LinkedList<E> implements List<E> {
    private Node head;
    private Node back;
    private int size;

    public LinkedList() {
        this.head = new Node(null);
        this.back = head;
        this.size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == this.size - 1) {
            return this.back.data;
        }
        Node p = this.head.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.data;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == this.size) {
            this.back.next = new Node(element, null);
            this.back = this.back.next;
            this.size++;
            return;
        }

        Node p = this.head;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        p.next = new Node(element, p.next);
        this.size++;
    }

    @Override
    public void add(E element) {
        this.add(this.size, element);
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == this.size - 1) {
            E temp = back.data;
            back.data = element;
            return temp;
        }

        Node p = this.head.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        E temp = p.data;
        p.data = element;
        return temp;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node p = this.head;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        E temp = p.next.data;
        p.next = p.next.next;
        if (index == this.size - 1) {
            this.back = p;
        }
        this.size--;
        return temp;
    }

    @Override
    public boolean remove(E element) {
        Node p = this.head.next;
        Node q = this.head;
        while (p != null) {
            if (p.data.equals(element)) {
                q.next = q.next.next;
                if (p.next == null) {
                    this.back = q;
                }
                this.size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
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

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            private Node curNode = LinkedList.this.head;

            @Override
            public boolean hasNext() {
                return this.curNode.next != null;
            }

            @Override
            public E next() {
                this.curNode = this.curNode.next;
                return this.curNode.data;
            }
        };
        return it;
    }

    private class Node {
        public E data;
        public Node next;

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Node(E data) {
            this(data, null);
        }
    }

}