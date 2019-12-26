package ru.otus.atmdepartment.director;

import ru.otus.atmdepartment.atm.Atm;

import java.util.ArrayList;
import java.util.List;

public class AtmDepartmentProducer {

    private final List<AtmDepartmentListener> listeners = new ArrayList<>();

    public void addListener(AtmDepartmentListener listener) {
        listeners.add(listener);
    }

    public void removeListener(AtmDepartmentListener listener) {
        listeners.remove(listener);
    }

    public List<Atm> getListeners() {
        return (List<Atm>)(List<?>) listeners;
    }

    public void onGetTotalBalance(){
        listeners.forEach(listener -> listener.onGetTotalBalance());
    }

    public void onResetAtm(){listeners.forEach(listener -> listener.onResetAtm());
    }

}
