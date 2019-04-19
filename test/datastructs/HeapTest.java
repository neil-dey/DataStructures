package datastructs;

import static org.junit.Assert.*;

import org.junit.Test;

public class HeapTest {

    @Test
    public void test() {
        ArrayList<Integer> l = new ArrayList<Integer>();
        for (int i = 10; i >= 0; i--) {
            l.add(i);
        }
        PriorityQueue<Integer> pq = new Heap<Integer>(false);
        for (int i = 0; i < l.size(); i++) {
            pq.enqueue(l.get(i));
        }
        for (int i = 0; i < l.size(); i++) {
            l.set(i, pq.dequeue());
        }

        for (int i = 0; i <= 10; i++) {
            assertEquals(i, (int) l.get(i));
        }

        pq = new Heap<Integer>(true);
        for (int i = 0; i < l.size(); i++) {
            pq.enqueue(l.get(i));
        }
        for (int i = 0; i < l.size(); i++) {
            l.set(i, pq.dequeue());
        }

        for (int i = 10; i >= 0; i--) {
            assertEquals(10 - i, (int) l.get(i));
        }
        
        pq = new Heap<Integer>(false);
        for(int i = 8; i > 0; i--) {
            pq.enqueue(i);
        }
        System.out.println(pq.toString());
        //assertEquals("[1, 2, 3, 4, null, null, null, null, null, null]", pq.toString());
    }

}
