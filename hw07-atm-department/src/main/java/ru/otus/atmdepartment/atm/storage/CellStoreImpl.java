package ru.otus.atmdepartment.atm.storage;

import ru.otus.atmdepartment.atm.FaceValue;
import ru.otus.atmdepartment.atm.MoneyCell;
import ru.otus.atmdepartment.atm.MoneyCellImpl;

import java.util.*;

public class CellStoreImpl implements CellStore {

    private final Map<Integer, MoneyCell> cellStore = new LinkedHashMap<>();

    public CellStoreImpl(AtmOriginator originator) {
        init();
        saveState(originator);
    }

    public CellStoreImpl(AtmOriginator originator, Map<Integer, MoneyCell> initCellStore) {
        cellStore.putAll(initCellStore);
        saveState(originator);
    }

    /**Сохранить первоначальное состояние.**/
    private void saveState(AtmOriginator originator) {
        originator.saveState(new AtmState(cellStore));
    }

    /**Начальная инициализация. Создаем все ячейки.*/
    private void init() {
        if (cellStore.isEmpty()) {
            for (FaceValue value: FaceValue.values()) {
                int faceValue = value.faceValue();
                cellStore.put(faceValue, new MoneyCellImpl(0, faceValue));
            }
        }
    }

    /**Функция проверяет доступность купюр в АТМ.**/
    private boolean isAvailableMoneyInAtm(List<FaceValue> listFaceValues,
                                          int faceValue,
                                          int cash) {

        int totalAmount = getTotalCash(listFaceValues.indexOf(faceValue));
        return (cash > totalAmount);
    }

    private int clearCell(MoneyCell moneyCell) {
        int faceValue = moneyCell.getFaceValue();
        cellStore.put(faceValue, new MoneyCellImpl(0, faceValue));
        return 0;
    }

    @Override
    public void setBanknotes(int faceValue, MoneyCell moneyCell) {
        cellStore.put(faceValue, moneyCell);
    }

    /**Функция выполняет добавления купюр в ячейку.**/
    @Override
    public void addBanknotes(MoneyCell moneyCell){
        int faceValue = moneyCell.getFaceValue();
        int banknotes = moneyCell.getBanknotesCount();
        int newBanknotesCount = banknotes + getCell(faceValue).getBanknotesCount();
        setBanknotes(faceValue, new MoneyCellImpl(newBanknotesCount, faceValue));
    }

    /**Функция возвращает ячейку(и) с купюрами.**/
    @Override
    public Map<Integer, MoneyCell> getBanknotes(int cash) throws Error {
        Map<Integer, MoneyCell> resultCell = new HashMap<>();
        List<FaceValue> listFaceValues = Arrays.asList(FaceValue.values());
        Collections.reverse(listFaceValues);

        for (FaceValue value: listFaceValues){
            int faceValue = value.faceValue();
            MoneyCell moneyCell = getCell(faceValue);

            if (moneyCell == null || moneyCell.getTotalAmount() == 0) continue;
            if (isAvailableMoneyInAtm(listFaceValues, faceValue, cash)) {
                throw new IllegalAccessError("Unable to issue the indicated amount!");
            }
            //Выполняем арифметические операци.
            int atmBanknotes = moneyCell.getBanknotesCount();
            int remainder = (cash % faceValue);
            int remainderCash = (cash - remainder);
            int customerBanknotes = (remainderCash / faceValue);
            int remainderBanknotes =  atmBanknotes - customerBanknotes;
            //Если остаток от деления входящего знчения равен 0 - изменяем значение объекта.
            if (remainder == 0) {
                resultCell.put(faceValue, new MoneyCellImpl(customerBanknotes, faceValue));
                setBanknotes(faceValue, new MoneyCellImpl(remainderBanknotes, faceValue));
                System.out.println(remainderBanknotes);
                break;
            } else {
                //Выполняем, если кол-во необходимых купюр меньше чем в ячейке.
                if (remainderBanknotes < 0) {
                    resultCell.put(faceValue, new MoneyCellImpl(atmBanknotes, faceValue));
                    clearCell(moneyCell); //addMoneyCell(new MoneyCellImpl(0, faceValue));
                    cash = remainder + (remainderBanknotes * faceValue *(-1));
                } else {
                    setBanknotes(faceValue, new MoneyCellImpl(remainderBanknotes, faceValue));
                    resultCell.put(faceValue, new MoneyCellImpl(customerBanknotes, faceValue));
                    cash = remainder;
                }
            }
        }
        return resultCell;
    }

    /**Получить ячейку**/
    public MoneyCell getCell(int faceValue) {
        return cellStore.get(faceValue);
    }

    /**Получить общую сумму денег во всех ячейках.**/
    public int getTotalCash(int var) {
        if (var > 0) {
            return cellStore.values().stream()
                    .limit(var)
                    .mapToInt(k -> k.getTotalAmount())
                    .sum();
        }
        int result = cellStore.values().stream()
                .mapToInt(k -> k.getTotalAmount())
                .sum();
        return result;
    }

    /**Получить общее кол-во банкнот во всех ячейках*.*/
    public int getTotalBanknotes() {
        return cellStore.values().stream().mapToInt(k -> k.getBanknotesCount()).sum();
    }
}
