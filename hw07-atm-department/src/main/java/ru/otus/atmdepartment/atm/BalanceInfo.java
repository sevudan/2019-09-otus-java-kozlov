package ru.otus.atmdepartment.atm;

public interface BalanceInfo {

    int getTotalMoney();

    int getTotalBanknotes();

    void setTotalMoney(int totalMoney);

    void setTotalBanknotes(int totalMoney);
}
