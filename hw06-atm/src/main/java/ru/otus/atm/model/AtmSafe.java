package ru.otus.atm.model;

import ru.otus.atm.view.InfoMessage;

import java.util.Map;
import java.util.HashMap;

public class AtmSafe {

    private final Map<Integer, MoneyCell> cellStorage = new HashMap<>();

    /**Функция проверяет доступность купюр в АТМ .**/
    private boolean isAvailableMoneyInAtm(int idx, int cash) {

        int local = 0;
        for (int i = idx; i >= 0; i--) {
            int key = FaceValue.values()[i].index();
            local = local + cellStorage.get(key).getTotalAmount();
        }

         return (cash > local);
    }

    /**Функция выполняет добавления купюр в ячейку.**/
    public void addCashSafe(int count, int faceValue) {
        if (cellStorage.get(faceValue) == null) {
            cellStorage.put(faceValue, new MoneyCell(count, faceValue));
        } else {
            int newCount = count + cellStorage.get(faceValue).getBanknotesCount();
            cellStorage.get(faceValue).setBanknotesCount(newCount);
        }
    }

    /**Функция возвращает ячейку(и) с купюрами.**/
    public Map<Integer, MoneyCell> getMoneyCell(int cash) throws Error {
        Map<Integer, MoneyCell> resultCell = new HashMap<>();

        for (int idx = FaceValue.values().length-1; idx >= 0 ; idx--) {
            int key = FaceValue.values()[idx].index();

            //Получаем объкт MoneyCell.
            MoneyCell moneyCell = cellStorage.get(key);
            if (moneyCell == null) continue;

            int totalAmount = moneyCell.getTotalAmount();
            int faceValue = moneyCell.getFaceValue();

            if (totalAmount == 0 ) continue;

            if (isAvailableMoneyInAtm(idx, cash)){
                InfoMessage.errorGetMoneMessage();
                resultCell.clear();
                return resultCell;
            }

            int moneyCellBanknotes = moneyCell.getBanknotesCount();

            //Выполняем арифметические операци.
            int remainder = (cash % faceValue);
            int remainderCash = (cash - remainder);
            int customerBanknotes = (remainderCash / faceValue);
            int remainderBanknotes =  moneyCellBanknotes - customerBanknotes;

            //Если остаток от деления входящего знчения равен 0 - изменяем значение объекта.
            if (remainder == 0) {
                    resultCell.put(faceValue, new MoneyCell(customerBanknotes, faceValue));
                    moneyCell.setBanknotesCount(remainderBanknotes);
                    break;
            } else {
                //Выполняем, если кол-во необходимых купюр меньше чем в ячейке.
                if (remainderBanknotes < 0) {
                    resultCell.put(faceValue, new MoneyCell(moneyCellBanknotes, faceValue));
                    cash = remainder + (remainderBanknotes * faceValue *(-1));
                    moneyCell.setBanknotesCount(0);
                } else {
                    moneyCell.setBanknotesCount(remainderBanknotes);
                    resultCell.put(faceValue, new MoneyCell(customerBanknotes, faceValue));
                    cash = remainder;
                }
            }
        }
        return resultCell;
    }

    /**Получить общую сумму денег во всех ячейках.**/
    public int getTotalMoney() {
        return cellStorage.values().stream().mapToInt(k -> k.getTotalAmount()).sum();
    }

    /**Получить общее кол-во банкнот во всех ячейках*.*/
    public int getTotalBanknotes() {
        return cellStorage.values().stream().mapToInt(k -> k.getBanknotesCount()).sum();
    }
}
