package ru.otus.atm.controller;

import java.util.Map;

public interface IAtmAction<T> {

    Map<T, ? extends Object> getCash(int cash)  throws Exception;

    void addCash(int count, int faceValue)  throws Exception;
}
