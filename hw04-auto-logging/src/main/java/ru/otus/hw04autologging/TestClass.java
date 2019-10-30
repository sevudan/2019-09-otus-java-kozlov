package ru.otus.hw04autologging;


public class TestClass implements TestClassIf {

    @Log
    @Override
    public int summ(int var1, int var2) {

        return var1 + var2;
    }

    @Log
    @Override
    public int diff(int param) {
        int x = 2;
        return param - x;
    }


    @Override
    public int mult(int param) {
        int x = 2;
        return x * param;
    }

}
