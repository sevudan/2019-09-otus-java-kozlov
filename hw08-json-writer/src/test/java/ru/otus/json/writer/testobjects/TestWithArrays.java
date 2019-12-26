package ru.otus.json.writer.testobjects;

import java.util.ArrayList;
import java.util.Collection;

public class TestWithArrays {
    public String value = "TestGson JSON with array";
    public int id = 100;

    public int[] intArray = {8080, 443, 179, 646};
    public Object[] objectArray = {null,
                                new String(new char['F']),
                                new String(new char['o']),
                                new String(new char['o'])};

    public Collection<String> stringCollection = new ArrayList<>();
    public Collection<Integer> integerCollection = new ArrayList<>();
    private String[][] stringArray = {{"One ", "Two ", "Three "}, {"Otus!"}};

    public void addStringCollection(String value) {
        stringCollection.add(value);
    }

    public void addObjettsCollection(Integer value) {
        integerCollection.add(value);
    }
}
