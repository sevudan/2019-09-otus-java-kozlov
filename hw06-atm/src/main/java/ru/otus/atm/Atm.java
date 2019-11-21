package ru.otus.atm;

import java.util.Map;

public interface Atm {

    Map<Integer, MoneyCellImpl> getCash(int cash);

    void addCash(int count, int faceValue);

    Map<String, Integer> getTotalCash();
}
