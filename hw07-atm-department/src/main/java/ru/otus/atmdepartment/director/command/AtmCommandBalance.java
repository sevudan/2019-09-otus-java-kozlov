package ru.otus.atmdepartment.director.command;

import ru.otus.atmdepartment.atm.Atm;
import ru.otus.atmdepartment.atm.BalanceInfo;

import java.util.List;

public class AtmCommandBalance implements AtmCommand {

    private final List<Atm> listAtms;
    private final  BalanceInfo balanceInfo;

    public AtmCommandBalance(List<Atm> listAtms, BalanceInfo balanceInfo) {
        this.listAtms = listAtms;
        this.balanceInfo = balanceInfo;
    }

    @Override
    public boolean execute() {

        int totalMoney = 0;
        int banknotesCount = 0;
        for (Atm atm : listAtms) {
            totalMoney += atm.getTotalCash().getTotalMoney();
            banknotesCount += atm.getTotalCash().getTotalBanknotes();
        }
        balanceInfo.setTotalMoney(totalMoney);
        balanceInfo.setTotalBanknotes(banknotesCount);
        return true;
    }

}
