package ru.otus.atm;

import java.util.LinkedHashMap;
import java.util.Map;

public class AtmImpl implements Atm {

    private final CellStore cellStore = new CellStoreImpl();

    private void checkCash(int var) {
        if (var % 2 > 0 || var < 0) throw new IllegalArgumentException("Error. Not valid argument!");
    }

    @Override
    public Map<Integer, MoneyCell> getCash(int cash)  {
        checkCash(cash);

        var result =  cellStore.getBanknotes(cash);
        result.values().stream().filter(e -> e.getBanknotesCount() > 0)
                .forEach(e -> System.out.printf("Banknotes are issued by face value: %s, total: %s\n",
                        e.getFaceValue(), e.getBanknotesCount()));
        return result;
    }

    @Override
    public void addCash(int count, int faceValue) {
        checkCash(count * faceValue);
        cellStore.addBanknotes(new MoneyCellImpl(count, faceValue));
    }

    @Override
    public  Map<String, Integer> getTotalCash() {
        Map<String,Integer> totalCash = new LinkedHashMap<>();

        totalCash.put("TotalMoney", cellStore.getTotalCash(0));
        totalCash.put("TotalBanknotes",cellStore.getTotalBanknotes());

        return totalCash;
    }

}
