package ru.otus.testframework.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

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

        System.out.printf("Start tests. Test class: %s \n\n", clazz.getName());

        Method[] methods = clazz.getDeclaredMethods();
        List<Method> methodsBeforeAll = InitLists.addAnnotationMethods(BeforeAll.class, methods);
        List<Method> methodsAfterAll = InitLists.addAnnotationMethods(AfterAll.class, methods);
        List<Method> methodsTest = InitLists.addAnnotationMethods(Test.class, methods);
        List<Method> methodsBeforEach = InitLists.addAnnotationMethods(BeforeEach.class, methods);
        List<Method> methodsAfterEach = InitLists.addAnnotationMethods(AfterEach.class, methods);

        Object newInstance = clazz.getConstructor().newInstance();

        if (!methodsBeforeAll.isEmpty()) {
            invokeMethods(methodsBeforeAll, newInstance);
        }
        for (Method methodTest : methodsTest) {
            String errors = null;
            try {
                invokeMethods(methodsBeforEach, newInstance);
                methodTest.invoke(newInstance);
                testsPassedCount++;
            } catch (Exception e) {
                errors = e.getCause().getMessage();
            } finally {
                invokeMethods(methodsAfterEach, newInstance);
            }
            if (errors != null) {
                System.out.printf("Test method name : %s. Result: Failed!\nCause: %s\n\n", methodTest.getName(), errors);
            } else {
                System.out.print(String.format("Test method name: %s. Result: Passed!\n", methodTest.getName()));
            }
        }
        if (!methodsAfterAll.isEmpty()) {
            invokeMethods(methodsAfterAll, newInstance);
        }

        System.out.printf("\nTest done. Successfully: %s Errors count: %s\n", testsPassedCount, methodsTest.size() - testsPassedCount);
    }
}