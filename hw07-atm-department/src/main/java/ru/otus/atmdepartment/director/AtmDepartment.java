package ru.otus.atmdepartment.director;

import ru.otus.atmdepartment.atm.Atm;
import ru.otus.atmdepartment.atm.AtmImpl;

import java.util.List;
import java.util.Map;

public interface AtmDepartment {

    void addAtm(AtmImpl atm);

    List<Atm> getAtmAll();

    Map<String,Integer> getBalance(List<Atm> units);

    void removeAtm(AtmImpl atm);

    void restore();
}
