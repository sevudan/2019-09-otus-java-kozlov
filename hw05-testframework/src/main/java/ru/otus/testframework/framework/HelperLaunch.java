package ru.otus.testframework.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class HelperLaunch {

    /**Поочередный запуск методов**/
    public static void invokeMethods(Collection<Method> methods, Object newInstance) {
        try {
            for (Method method: methods) method.invoke(newInstance);
        } catch(IllegalAccessException | InvocationTargetException e){
            throw new Error(e);
        }
    }

    /** Функция возвращает лист методов**/
    public static List<Method> getAnnotationMethods(Class clazz, Method[] methods) {
        ArrayList<Method> tmpList = new ArrayList<>();
        for (Method method: methods) {
            if (method.isAnnotationPresent(clazz)) tmpList.add(method);
        }
        return tmpList;
    }
}
