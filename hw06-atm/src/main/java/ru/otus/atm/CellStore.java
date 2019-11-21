package ru.otus.atm;

import java.util.Map;

public interface CellStore {

    void setBanknotes(int faceValue, MoneyCellImpl moneyCell);

    void addBanknotes(MoneyCellImpl userMoneyCell);

    Map<Integer, MoneyCellImpl> getBanknotes(int cash) throws Error;

    MoneyCell getCell(int faceValue);

    int getTotalCash(int var);

    int getTotalBanknotes();

}
