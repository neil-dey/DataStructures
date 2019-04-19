package datastructs;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashTableTest {

    @Test
    public void test() {
        Dictionary<Integer, Integer> d = new HashTable<Integer, Integer>();
        d.insert(2, 2);
        d.insert(1, 1);
        assertEquals("{ (1, 1) (2, 2) }", d.toString());
        for (int i = 1; i <= 2; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        d.insert(3, 3);
        for (int i = 1; i <= 3; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        d.insert(4, 4);
        for (int i = 1; i <= 4; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        d.insert(5, 5);
        for (int i = 1; i <= 5; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        d.insert(-1, -1);
        for (int i = 1; i <= 5; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        assertEquals(-1, (int) d.lookUp(-1));

        d.insert(0, 0);
        for (int i = -1; i <= 5; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }

        d.insert(8, 8);
        for (int i = -1; i <= 5; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        assertEquals(8, (int) d.lookUp(8));

        d.insert(10, 10);
        for (int i = -1; i <= 5; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        assertEquals(8, (int) d.lookUp(8));
        assertEquals(10, (int) d.lookUp(10));

        d.insert(6, 6);
        d.insert(7, 7);
        for (int i = -1; i <= 10; i++) {
            if (i == 9) {
                continue;
            }
            assertEquals(i, (int) d.lookUp(i));
        }
        d.insert(9, 9);
        d.insert(-2, -2);
        d.insert(-3, -3);
        for (int i = -3; i <= 10; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }

        d.insert(11, 11);
        d.insert(12, 12);
        d.insert(13, 13);
        for (int i = -3; i <= 13; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }

        assertEquals(-2, (int) d.update(-2, 4));
        assertEquals(4, (int) d.lookUp(-2));
        d.update(0, -17);
        assertEquals(-17, (int) d.lookUp(0));
        d.update(-2, -2);
        d.update(0, 0);
        assertNull(d.update(-27, 0));
        for (int i = -3; i <= 13; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        assertNull(d.lookUp(-27));

        assertEquals(12, (int) d.remove(12));

        // System.out.print(d.toString());

        d = new HashTable<Integer, Integer>(2);
        d.insert(2, 2);
        d.insert(4, 1); // fine-tuned to force a hash collision
        assertEquals(2, (int) d.remove(2));
    }

    @Test
    public void testResizing() {
        Dictionary<Integer, Integer> d = new HashTable<Integer, Integer>(1);
        for (int i = 0; i < 1000; i++) {
            d.insert(i, i);
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        for (int i = 500; i < 750; i += 2) {
            d.remove(i);
        }
        for (int i = 0; i < 500; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }
        for (int i = 501; i < 750; i += 2) {
            assertEquals(i, (int) d.lookUp(i));
        }
        for (int i = 500; i < 750; i += 2) {
            assertNull(d.lookUp(i));
        }
        for (int i = 750; i < 1000; i++) {
            assertEquals(i, (int) d.lookUp(i));
        }

        d.update(992, -5);
        assertEquals(-5, (int) d.lookUp(992));
    }
}
