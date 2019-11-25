package ru.otus.atm;

import java.util.Map;

public interface CellStore {

    void setBanknotes(int faceValue, MoneyCell moneyCell);

    void addBanknotes(MoneyCell userMoneyCell);

    Map<Integer, MoneyCell> getBanknotes(int cash) throws Error;

    MoneyCell getCell(int faceValue);

    int getTotalCash(int var);

    int getTotalBanknotes();

}
