package ru.otus.atmdepartment.atm.storage;

import ru.otus.atmdepartment.atm.MoneyCell;

import java.util.HashMap;
import java.util.Map;

public class AtmState {

    private final Map<Integer, MoneyCell> store;

    public AtmState(Map<Integer, MoneyCell> cellStore) {
        this.store = cellStore;
    }

    public AtmState(AtmState state) {
        this.store = new HashMap<>(state.getState());
    }

    public Map<Integer, MoneyCell> getState() {
        return store;
    }
}
