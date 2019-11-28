package ru.otus.atmdepartment.director.command;

import ru.otus.atmdepartment.atm.Atm;

import java.util.List;
import java.util.Map;

public class AtmCommandBalance implements AtmCommand {

    private final List<Atm> listAtms;
    private DepartmentBalance departmentBalance;

    public AtmCommandBalance(List<Atm> listAtms,
                             DepartmentBalance departmentBalance) {
        this.listAtms = listAtms;
        this.departmentBalance = departmentBalance;
    }

    @Override
    public boolean execute() {
        Map<String,Integer>  balance = departmentBalance.getTotalBanknotes();

        for (Atm atm : listAtms){
            Map<String, Integer> map = atm.getTotalCash();
            map.forEach((k,v) -> balance.merge(k, v, Integer::sum));
        }

        departmentBalance.putBanknotes(balance);
        return true;
    }
}
