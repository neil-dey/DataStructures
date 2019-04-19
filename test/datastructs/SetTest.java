package datastructs;

import static org.junit.Assert.*;

import org.junit.Test;

public class SetTest {

    @Test
    public void test() {
        Set<Character> s = new Set<Character>();
        
        for (int i = 0; i < 12; i++) {
            s.makeSet((char) ('a' + i));
        }

        for (int i = 0; i < 12; i++) {
            assertEquals((char) ('a' + i), (char) s.find((char) ('a' + i)));
        }
        s.union('a', 'c');
        assertEquals('a', (char)s.find('a'));
        assertEquals('a', (char)s.find('c'));
        
        s.union('b', 'd');
        s.union('f', 'i');
        s.union('l', 'k');
        s.union('j', 'l');
        assertEquals('l', (char)s.find('k'));
        assertEquals('l', (char)s.find('l'));
        assertEquals('l', (char)s.find('j'));
        
        s.union('h', 'j');
        assertEquals('l', (char)s.find('j'));
        assertEquals('l', (char)s.find('h'));
        assertEquals('l', (char)s.find('k'));
        
        s.union('h', 'c');
        assertEquals('l', (char)s.find('c'));
        assertEquals('l', (char)s.find('a'));
        assertEquals('l', (char)s.find('j'));
        assertEquals('l', (char)s.find('h'));
        assertEquals('l', (char)s.find('k'));
        
        assertEquals(6, s.getNumInSet('j'));
    }

}
