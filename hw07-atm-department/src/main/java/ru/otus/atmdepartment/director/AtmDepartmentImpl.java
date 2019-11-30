package ru.otus.atmdepartment.director;

import ru.otus.atmdepartment.atm.Atm;
import ru.otus.atmdepartment.atm.AtmImpl;
import ru.otus.atmdepartment.atm.BalanceInfo;
import ru.otus.atmdepartment.atm.BalanceInfoImpl;
import ru.otus.atmdepartment.director.command.AtmCommandBalance;
import ru.otus.atmdepartment.director.command.DepartmentCommand;

import java.util.List;

public class AtmDepartmentImpl implements AtmDepartment{

    private final AtmDepartmentProducer listeners = new AtmDepartmentProducer();
    private final BalanceInfo balanceInfo;
    private final DepartmentCommand departmentCommand;

    public AtmDepartmentImpl() {
        this.balanceInfo = new BalanceInfoImpl();
        this.departmentCommand = new DepartmentCommand();
    }

    public void addAtm(AtmImpl atm){
        listeners.addListener(atm);
    }

    public List<Atm> getAtmAll() {
        return listeners.getListeners();
    }

    public void removeAtm(AtmImpl atm) {
        listeners.removeListener(atm);
    }

    public BalanceInfo getBalance() {
        listeners.onGetTotalBalance();
        departmentCommand.addCommand(new AtmCommandBalance(listeners.getListeners(), balanceInfo));
        departmentCommand.executeCommands();
        return balanceInfo;
    }

    public void restore() {
        listeners.onResetAtm();
    }
}
