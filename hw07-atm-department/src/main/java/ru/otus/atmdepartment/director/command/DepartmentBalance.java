package ru.otus.atmdepartment.director.command;

import java.util.HashMap;
import java.util.Map;

public class DepartmentBalance {

    private final Map<String,Integer> totalBanknotes = new HashMap<>();

    public DepartmentBalance() {}

    public Map<String,Integer>  getTotalBanknotes() {
        return totalBanknotes;
    }

    void putBanknotes(Map<String,Integer> banknotes) {
        totalBanknotes.putAll(banknotes);
    }
}
