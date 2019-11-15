package ru.otus.testframework;

import java.util.ArrayList;
import java.util.stream.IntStream;

import ru.otus.testframework.framework.BeforeAll;
import ru.otus.testframework.framework.AfterEach;
import ru.otus.testframework.framework.AfterAll;
import ru.otus.testframework.framework.BeforeEach;
import ru.otus.testframework.framework.Test;
import static ru.otus.testframework.framework.MyAsserts.*;


public class TestClass {

    private final ArrayList<Integer> list = new ArrayList<>(4);;
    private final ArrayList<Integer> list2 = new ArrayList<>(4);
    private final ArrayList<Integer> array1 = new ArrayList<>();
    private final ArrayList<Integer> array2 = new ArrayList<>();

    @BeforeAll
    public static void init(){
        System.out.println("Hello! TEST started. BeforeALL method called!\n");
    }

    @BeforeEach
    public void addElementIntoList() {

        IntStream.range(15, 20).forEach(i -> array1.add(i));
        IntStream.range(0, 10).forEach(i -> array2.add(i));
        list.addAll(array1);
        list.addAll(array2);
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
    public void getIndexOfElement() {
        assertEquals(14, list.get(0));
    }

    @AfterEach
    public void  afterEach() {

        list.clear();
        System.out.println("AfterEach method called!\n");
    }

    @AfterAll
    public static void  clearOther() {
        System.out.println("TEST finished. AfterAll method called!\n");
    }
}
