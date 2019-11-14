package ru.otus.testframework.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Launch {

    /**Запуск теста.**/
    public static void run(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        int testsPassedCount = 0;

        System.out.printf("Start tests. Test class: %s \n\n", clazz.getName());

        Method[] methods = clazz.getDeclaredMethods();
        List<Method> methodsBeforeAll = HelperLaunch.getAnnotationMethods(BeforeAll.class, methods);
        List<Method> methodsAfterAll = HelperLaunch.getAnnotationMethods(AfterAll.class, methods);
        List<Method> methodsTest = HelperLaunch.getAnnotationMethods(Test.class, methods);
        List<Method> methodsBeforEach = HelperLaunch.getAnnotationMethods(BeforeEach.class, methods);
        List<Method> methodsAfterEach = HelperLaunch.getAnnotationMethods(AfterEach.class, methods);

        Object newInstance = clazz.getConstructor().newInstance();
        if (!methodsBeforeAll.isEmpty()) {
            HelperLaunch.invokeMethods(methodsBeforeAll, newInstance);
        }
        for (Method methodTest : methodsTest) {
            String errors = null;
            try {
                HelperLaunch.invokeMethods(methodsBeforEach, newInstance);
                methodTest.invoke(newInstance);
                testsPassedCount++;
            } catch (Exception e) {
                errors = e.getCause().getMessage();
            } finally {
                HelperLaunch.invokeMethods(methodsAfterEach, newInstance);
            }
            if (errors != null) {
                System.out.printf("Test method name : %s. Result: Failed!\nCause: %s\n\n", methodTest.getName(), errors);
            } else {
                System.out.print(String.format("Test method name: %s. Result: Passed!\n\n", methodTest.getName()));
            }
        }
        if (!methodsAfterAll.isEmpty()) {
            HelperLaunch.invokeMethods(methodsAfterAll, newInstance);
        }
        System.out.printf("\nTest done. Successfully: %s Errors count: %s\n", testsPassedCount, methodsTest.size() - testsPassedCount);
    }
}