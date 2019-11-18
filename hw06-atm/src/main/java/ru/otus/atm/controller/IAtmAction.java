package ru.otus.atm.controller;

public interface IAtmAction {

    Object getCash(int cash)  throws Exception;

    void addCash(int count, int faceValue)  throws Exception;
}
