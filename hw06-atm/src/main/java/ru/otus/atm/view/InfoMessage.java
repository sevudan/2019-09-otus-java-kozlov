package ru.otus.atm.view;
import ru.otus.atm.model.MoneyCell;

import java.util.Map;

public abstract class InfoMessage {

    public static void messageHello() {
        System.out.println("ATM принимает купюры номиналом: 100, 500 ,1000, 5000 рублей.\n");
    }
    public static void errorMessage() {
        System.out.printf("Ошибка! Внесена не корректная сумма.\n");
    }

    public static void errorGetMoneMessage() throws Error {
        System.out.printf("В ATM не достаточно купюр для выдачи.\n");
    }

    public static void  resultInfoMessage(Map<Integer, MoneyCell> result) {
        result.values().stream().filter(e -> e.getBanknotesCount() > 0)
                .forEach(e -> System.out.printf("Выданы банкноты номиналом: %s, кол-во: %s\n",
                        e.getFaceValue(), e.getBanknotesCount()));
    }

    public static void remainderMessage(int totalMoney, int totalBanknotes) {
        System.out.printf("\nINFO: Сумма остатка в денежных ячейках в ATM: %s, общее количество банкнот: %s",
                            totalMoney, totalBanknotes);
    }

}
