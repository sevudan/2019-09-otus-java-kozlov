package ru.otus.atm.controller;

import ru.otus.atm.model.MoneyCell;
import ru.otus.atm.model.AtmSafe;
import ru.otus.atm.view.InfoMessage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AtmActionImpl implements IAtmAction {

    private final AtmSafe atmSafe = new AtmSafe();

    public Map<Integer, MoneyCell> getCash(int cash) throws Error {
        if (cash % 2 > 0) {
            InfoMessage.errorMessage();
            return new HashMap<>();
        }
        return atmSafe.getMoneyCell(cash);
    }

    public void addCash(int count, int faceValue) throws Exception {
        atmSafe.addCashSafe(count, faceValue);
    }

    public  Map<String, Integer> getTotalCash() {
        Map<String,Integer> result = new LinkedHashMap<>();
        result.put("TotalMoney", atmSafe.getTotalMoney());
        result.put("TotalBanknotes",atmSafe.getTotalBanknotes());
        return result;
    }
}
