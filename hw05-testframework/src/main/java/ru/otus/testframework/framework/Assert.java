package ru.otus.testframework.framework;

public class Assert {

    private static String failMessage(String message) {
        throw new AssertionError(message);
    }

    public static void assertEquals(int expected, int current) {
        if (expected != current) {
            failMessage(String.format("Returned values should be equals with expected. Actual %s" , current));
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            failMessage(String.format("Values should be True. Actual %s", condition));
        }
    }

}