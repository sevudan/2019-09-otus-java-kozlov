package ru.otus.testframework.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class TestRunner {

    private final static List<Method> methodsBeforeAll = new ArrayList<>();
    private final static List<Method> methodsAfterAll = new ArrayList<>();
    private final static List<Method> methodsTest = new ArrayList<>();
    private final static List<Method> methodsBeforEach = new ArrayList<>();
    private final static List<Method> methodsAfterEach = new ArrayList<>();

    /**Наполняем списки методами.**/
    private static void addListMethodAnnotation(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        methodsBeforeAll.addAll(addAnnotationMethods(BeforeAll.class, methods));
        methodsAfterAll.addAll(addAnnotationMethods(AfterAll.class, methods));
        methodsTest.addAll(addAnnotationMethods(Test.class, methods));
        methodsBeforEach.addAll(addAnnotationMethods(BeforeEach.class, methods));
        methodsAfterEach.addAll(addAnnotationMethods(AfterEach.class, methods));
    }

    /** Функция возвращает лист методов**/
    private static ArrayList addAnnotationMethods(Class clazz, Method[] methods) {
        ArrayList<Method> tmpList = new ArrayList<>();
        for (Method method: methods) {
            if (method.isAnnotationPresent(clazz)) tmpList.add(method);
        }
        return tmpList;
    }

    /**Поочередный запуск методов**/
    private static void invokeMethods(List<Method> methods, Object newInstance) {
        try {
            for (Method method: methods) method.invoke(newInstance);
        } catch(IllegalAccessException | InvocationTargetException e){
            throw new Error(e);
        }
    }

    /**Запуск теста.**/
    public static void run(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        int testsPassedCount = 0;
        int errorsCount = 0;

        System.out.printf("Start tests. Testing class: %s \n\n", clazz.getName());
        addListMethodAnnotation(clazz);

        for (Method methodTest : methodsTest) {
            String errors = "";
            Object newInstance = clazz.getConstructor().newInstance();
            try {
                if (!methodsBeforeAll.isEmpty()) {
                    invokeMethods(methodsBeforeAll, newInstance);
                }
                invokeMethods(methodsBeforEach, newInstance);
                methodTest.invoke(newInstance);
                testsPassedCount++;
            } catch (Exception e) {
                errors = e.getCause().getMessage();
                errorsCount++;
            } finally {
                invokeMethods(methodsAfterEach, newInstance);
                if (!methodsAfterAll.isEmpty()) {
                    invokeMethods(methodsAfterAll, newInstance);
                }
            }
            if (!errors.equals("")) {
                System.out.printf("Test method name : %s. Result: Failed!\nCause: %s\n\n", methodTest.getName(), errors);
            } else {
                System.out.print(String.format("Test method name: %s. Result: Passed!\n", methodTest.getName()));
            }
        }
        System.out.printf("\nTest done. Successfully: %s Errors count: %s\n", testsPassedCount, errorsCount);
    }
}