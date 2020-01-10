package ru.otus.hw04autologging;

public class Main {
    public static void main(String... args) throws Exception {

        TestClass testAutoLogging = AutoLogging.loggingMethod(TestClassImpl.class);
        testAutoLogging.diff(4);
        testAutoLogging.summ(33, 55);
        testAutoLogging.mult(7);

    }
}
