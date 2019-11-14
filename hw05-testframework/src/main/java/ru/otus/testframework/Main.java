package ru.otus.testframework;

import ru.otus.testframework.framework.Launch;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String... args)throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {

        Launch.run(TestClass.class);
    }
}
