package ru.otus.hw03gc;

import java.util.ArrayList;

public class Test implements TestMBean {

    private int counter = 15_000;
    private double delete =  (int)(0.20 * counter);

    private ArrayList<byte[]> testInstance = new ArrayList<>();

    public void start() throws Exception {

        int t = 0;
        while (t < 4000) {
            for (int i = 1; i < counter; i++) {
                testInstance.add(new byte[1]);
            }
            for (int n = 1; n < delete; n++) {
                testInstance.remove(n);
            }
            Thread.sleep(10);
            t++;
        }
    }
}
