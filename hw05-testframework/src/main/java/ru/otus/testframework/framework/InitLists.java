package ru.otus.testframework.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class InitLists {

    /** Функция возвращает лист методов**/
    protected static ArrayList addAnnotationMethods(Class clazz, Method[] methods) {
        ArrayList<Method> tmpList = new ArrayList<>();
        for (Method method: methods) {
            if (method.isAnnotationPresent(clazz)) tmpList.add(method);
        }
        return tmpList;
    }
}
