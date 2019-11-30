package ru.otus.atmdepartment.director;

import ru.otus.atmdepartment.atm.Atm;
import ru.otus.atmdepartment.atm.AtmImpl;
import ru.otus.atmdepartment.atm.BalanceInfo;

import java.util.List;

public interface AtmDepartment {

    void addAtm(AtmImpl atm);

    List<Atm> getAtmAll();

    BalanceInfo getBalance();

    void removeAtm(AtmImpl atm);

    void restore();
}
