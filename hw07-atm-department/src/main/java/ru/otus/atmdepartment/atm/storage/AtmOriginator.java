package ru.otus.atmdepartment.atm.storage;

public interface AtmOriginator {

    void saveState(AtmState state);

    AtmState restoreState();
}
