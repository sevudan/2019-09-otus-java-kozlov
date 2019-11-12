package ru.otus.testframework;

import java.util.ArrayList;
import java.util.stream.IntStream;

import ru.otus.testframework.framework.BeforeAll;
import ru.otus.testframework.framework.AfterEach;
import ru.otus.testframework.framework.AfterAll;
import ru.otus.testframework.framework.BeforeEach;
import ru.otus.testframework.framework.Test;
import static ru.otus.testframework.framework.Assert.*;


public class TestClass {

    private final ArrayList<Integer> list = new ArrayList<>(4);;
    private final ArrayList<Integer> list2 = new ArrayList<>(4);
    private final ArrayList<Integer> array1 = new ArrayList<>();
    private final ArrayList<Integer> array2 = new ArrayList<>();


    @BeforeAll
    public void initArray(){
        IntStream.range(15, 20).forEach(i -> array1.add(i));
        IntStream.range(0, 10).forEach(i -> array2.add(i) );
    }

    @BeforeEach
    public void addElementIntoList() {
        list.addAll(array2);
    }

    @BeforeEach
    public void addElementIntoList2() {
        list.addAll(array1);
    }
    @Test
    public void listEmpty() {
        list2.add(10);
        list2.add(20);
        list2.remove(0);
        list2.remove(0);

        assertTrue(list2.isEmpty());
    }

    @Test
    public void getElementsByIndx() {
        assertEquals(15, list.get(0));
    }

    @Test
    public void removeElementsByIndx() {
        assertEquals(8, list.remove(8));
    }

    @AfterEach
    public void  clearList() {
        list.clear();
        array1.clear();
        array2.clear();
    }

    @AfterAll
    public void  clearOther() {
        list2.clear();
    }
}
