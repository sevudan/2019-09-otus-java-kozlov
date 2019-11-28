package ru.otus.atmdepartment.director;

import ru.otus.atmdepartment.atm.Atm;
import ru.otus.atmdepartment.atm.AtmImpl;
import ru.otus.atmdepartment.director.command.AtmCommandBalance;
import ru.otus.atmdepartment.director.command.DepartmentBalance;
import ru.otus.atmdepartment.director.command.DepartmentCommand;

import java.util.List;
import java.util.Map;

public class AtmDepartmentImpl implements AtmDepartment{

    private final AtmDepartmentProducer listeners = new AtmDepartmentProducer();
    private DepartmentBalance departmentBalance;
    private DepartmentCommand departmentCommand;

    public AtmDepartmentImpl() {
        this.departmentBalance = new DepartmentBalance();
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

    public Map<String,Integer>  getBalance(List<Atm> units) {
        listeners.onGetTotalBalance();
        departmentCommand.addCommand(new AtmCommandBalance(units, departmentBalance));
        departmentCommand.executeCommands();
        Map<String,Integer> bb = departmentBalance.getTotalBanknotes();
        System.out.printf("Total cash in all ATMs: %s, total banknotes: %s",
                bb.get("TotalMoney"),
                bb.get("TotalBanknotes"));
        return departmentBalance.getTotalBanknotes();
    }

    public void restore() {
        listeners.onResetAtm();
    }
}
