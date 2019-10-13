import org.junit.Test;
import ru.otus.hw02arraylist.DIYArrayList;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

public class DIYArrayListTest {

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("DIYArrayListTest");
    }

    @Test
    public void testSizeIf0() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(1);
        testInstance.remove(1);
        assertEquals(0, testInstance.size());
    }

    @Test
    public void testSizeIf1() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(22);
        assertEquals(1, testInstance.size());
    }

    @Test
    public void testIsEmptyWhenEmpty() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(77);
        testInstance.remove(0);
        assertTrue(testInstance.isEmpty());
    }

    @Test
    public void testIsEmptyWhenIsNotEmpty() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(10);
        assertFalse(testInstance.isEmpty());
    }
    @Test
    public void testContains() {

        final List<Integer> testInstance = new DIYArrayList<>();
        for (int i = 0; i < 100; i++) {
            testInstance.add(i);
        }
        assertTrue(testInstance.contains(0) & testInstance.contains(99) );
        assertFalse(testInstance.contains(100));
    }

    @Test
    public void testAdd() {
        final List<Integer> testInstance = new DIYArrayList<>();
        for (int i = -15; i < 50; i++) {
            testInstance.add(i);
        }

        assertEquals(65, testInstance.size());
        assertFalse(testInstance.isEmpty());

    }
    @Test
    public void testGet() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(22);
        testInstance.add(33);
        testInstance.add(44);
        assertEquals((Integer) 33, testInstance.get(1));

    }

    @Test
    public void testIndexOf() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(22);
        testInstance.add(33);
        testInstance.add(44);
        testInstance.add(33);
        assertEquals(1, testInstance.indexOf(33));
    }
    @Test
    public void testLastIndexOf() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(22);
        testInstance.add(33);
        testInstance.add(41);
        testInstance.add(33);
        testInstance.add(44);
        assertEquals(3, testInstance.lastIndexOf(33));
    }

    @Test
    public void testToArray() {

        final List<Integer> testInstance = new DIYArrayList<>();
        for (int i = 1; i < 50; i++) {
            testInstance.add(i);
        }
        testInstance.remove(2);
        testInstance.remove(4);
        testInstance.remove(25);
        testInstance.remove(11);
        testInstance.remove(14);
        if (testInstance.toArray().length != testInstance.size()) {
            throw new RuntimeException("Length not equal of the DIYArrayList!");
        }

    }
    @Test
    public void testRemoveFirstElement() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.remove(1);

        assertEquals(2, testInstance.size());
        assertFalse(testInstance.isEmpty());
    }
    @Test
    public void testRemoveCenterElement() {
        final DIYArrayList <Integer> testInstance = new DIYArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);
        testInstance.add(5);
        testInstance.remove(3);

        assertEquals(4, testInstance.size());
        assertFalse(testInstance.isEmpty());
    }

    @Test
    public void testRemoveLastElement() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);

        testInstance.remove(3);
        assertEquals(3, testInstance.size());
        assertFalse(testInstance.isEmpty());
    }
    @Test
    public void testRemoveObject() {
        final List<String> testInstance = new DIYArrayList<>();
        testInstance.add("1");
        testInstance.add("2");
        testInstance.add("3");
        testInstance.add("4");

        testInstance.remove("3");
        assertEquals(3, testInstance.size());
        assertFalse(testInstance.isEmpty());
    }
    @Test
    public void testContainsAll() {
        final List<Integer> testInstance = new DIYArrayList<>();
        final List<Integer> testInstance2 = new DIYArrayList<>();

        testInstance.add(4);
        testInstance.add(9);

        testInstance2.add(9);
        testInstance2.add(4);

        assertTrue(testInstance.containsAll(testInstance2));
    }

    @Test
    public void testAddAll() {
        final List<Integer> testInstance = new DIYArrayList<>();
        final List<Integer> testInstance2 = new DIYArrayList<>();

        testInstance.add(4);
        testInstance.add(9);

        testInstance2.add(3);
        testInstance2.add(5);

        testInstance.addAll(testInstance2);

        assertTrue(testInstance.contains(3));
        assertTrue(testInstance.contains(5));
    }

    @Test
    public void testRemoveAll() {
        final List<Integer> testInstance = new DIYArrayList<>();
        final List<Integer> testInstance2 = new DIYArrayList<>();
        testInstance.add(10);
        testInstance.add(20);
        testInstance.add(333);
        testInstance.add(444);

        testInstance2.add(22);
        testInstance2.add(333);
        testInstance2.add(10);

        testInstance.removeAll(testInstance2);

        assertEquals(2, testInstance.size());
        assertTrue(testInstance.contains(20) & testInstance.contains(444));
        assertFalse(testInstance.contains(333) & testInstance.contains(10));
    }

    @Test
    public void testIterator() {
        final List<Integer> testInstance = new DIYArrayList<>();
        for (int i = 3; i < 50; i++) {
            testInstance.add(i);
        }
        int i = 0;
        System.out.format("\nTest iterator \nSize: %s\n\n", testInstance.size());
        for (final Integer iter : testInstance) {
            System.out.format("Element %s: %s \n", i++, iter);
        }
        if (i != testInstance.size()) {
            throw new RuntimeException("Elements iterator don't working!");
        }
    }
    @Test
    public void testCollectionsAddAll() {
        final List<Integer> testInstance = new DIYArrayList<>();
        final List<Integer> elements = new DIYArrayList<>();
        for (int i = 100; i < 250; i+=10) {
            testInstance.add(i);
        }
        elements.add(443);
        elements.add(8080);
        elements.add(176);
        elements.add(22);
        elements.add(664);
        elements.add(1008);

        Collections.addAll(testInstance, 443, 8080, 176, 22, 664, 1008);

        assertTrue(testInstance.containsAll(elements));
        assertEquals(21, testInstance.size());
    }

    @Test
    public void testCollectionsSort() {
        final List<String> testInstance = new DIYArrayList<>();
        testInstance.add("l");
        testInstance.add("m");
        testInstance.add("f");
        testInstance.add("g");
        testInstance.add("h");
        testInstance.add("i");
        testInstance.add("r");
        testInstance.add("s");
        testInstance.add("t");
        testInstance.add("u");
        testInstance.add("v");
        testInstance.add("j");
        testInstance.add("k");
        testInstance.add("n");
        testInstance.add("o");
        testInstance.add("a");
        testInstance.add("b");
        testInstance.add("p");
        testInstance.add("q");
        testInstance.add("w");
        testInstance.add("x");
        testInstance.add("c");
        testInstance.add("d");
        testInstance.add("e");
        testInstance.add("y");
        testInstance.add("z");

        Collections.sort(testInstance);
        StringBuilder sb = new StringBuilder();
        for (Object c: testInstance) sb.append(c);
        assertEquals("abcdefghijklmnopqrstuvwxyz", sb.toString());
    }
    @Test
    public void testCollectionsCopy() {
        final List<Integer> testInstance1 = new DIYArrayList<>();
        final List<Integer> testInstance2 = new DIYArrayList<>();
        final List<Integer> elements = new DIYArrayList<>();
        
        for (int i = 0; i < 30; i++) {
            testInstance1.add(i);
        }

        for (int i = 0; i <10; i++) {
            testInstance2.add(i + 50);
        }

        elements.add(50);
        elements.add(51);
        elements.add(52);
        elements.add(53);
        elements.add(54);
        elements.add(55);
        elements.add(56);
        elements.add(57);
        elements.add(58);
        elements.add(59);

        Collections.copy(testInstance1, testInstance2);

        assertTrue(testInstance1.containsAll(elements));
        assertEquals(30, testInstance1.size());

    }
    @Test
    public void testClear() {
        final List<Integer> testInstance = new DIYArrayList<>();
        testInstance.add(1);
        testInstance.add(1);

        testInstance.clear();

        assertTrue(testInstance.isEmpty());
        assertEquals(0, testInstance.size());
    }

}
