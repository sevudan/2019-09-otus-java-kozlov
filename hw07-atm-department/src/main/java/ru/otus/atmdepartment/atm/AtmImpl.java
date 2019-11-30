package ru.otus.atmdepartment.atm;

import ru.otus.atmdepartment.atm.storage.AtmOriginator;
import ru.otus.atmdepartment.atm.storage.AtmOriginatorImpl;
import ru.otus.atmdepartment.atm.storage.CellStore;
import ru.otus.atmdepartment.atm.storage.CellStoreImpl;
import ru.otus.atmdepartment.director.AtmDepartmentListener;

import java.util.Map;

public class AtmImpl implements Atm, AtmDepartmentListener {

    private CellStore cellStore;
    private BalanceInfo balanceInfo;

    private final AtmOriginator originator = new AtmOriginatorImpl();

    public AtmImpl() {

        this.cellStore = new CellStoreImpl(originator);
    }

    public AtmImpl(Map<Integer, MoneyCell> loadBanknotes) {
        this.cellStore = new CellStoreImpl(originator, loadBanknotes);
    }

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
    public  BalanceInfo getTotalCash() {

        balanceInfo = new BalanceInfoImpl(cellStore.getTotalCash(0),
                                         cellStore.getTotalBanknotes());
        return balanceInfo;
    }

    @Override
    public void restore() {
        this.cellStore = new CellStoreImpl(originator, originator.restoreState().getState());
    }

    /**Обновляем информацию о загруженных банкнотах в АТМ**/
    @Override
    public void onGetTotalBalance() {
        getTotalCash();
    }

    /**Сбрасываем АТМ в начальное состояние**/
    @Override
    public void onResetAtm() {
        restore();
    }
}
