package ru.otus.atmdepartment.atm.storage;

import ru.otus.atmdepartment.atm.MoneyCell;

import java.util.Map;

public interface CellStore {

    void setBanknotes(int faceValue, MoneyCell moneyCell);

    void addBanknotes(MoneyCell userMoneyCell);

    Map<Integer, MoneyCell> getBanknotes(int cash) throws Error;

    MoneyCell getCell(int faceValue);

    int getTotalCash(int var);

    int getTotalBanknotes();

}
