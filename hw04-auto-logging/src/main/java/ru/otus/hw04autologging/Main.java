package ru.otus.hw04autologging;

public class Main {
    public static void main(String... args) throws Exception {

        new AutoLogging().loggingMethod(TestClass.class).diff(4);
        new AutoLogging().loggingMethod(TestClass.class).summ(25, 30);
        new AutoLogging().loggingMethod(TestClass.class).mult(7);

    }
}
