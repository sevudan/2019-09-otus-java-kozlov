package ru.otus.atmdepartment.atm.storage;

import ru.otus.atmdepartment.atm.MoneyCell;

import java.util.Map;

public interface CellStore {

    /**Функция вставку банкнот в хранилище ячеек.**/
    void setBanknotes(int faceValue, MoneyCell moneyCell);

    /**Функция выполняет добавления купюр в ячейку.**/
    void addBanknotes(MoneyCell userMoneyCell);

    /**Функция возвращает ячейку(и) с купюрами.**/
    Map<Integer, MoneyCell> getBanknotes(int cash) throws Error;

    /**Получить ячейку**/
    MoneyCell getCell(int faceValue);

    /**Получить общую сумму денег во всех ячейках.**/
    int getTotalCash(int var);

    /**Получить общее кол-во банкнот во всех ячейках**/
    int getTotalBanknotes();

}
