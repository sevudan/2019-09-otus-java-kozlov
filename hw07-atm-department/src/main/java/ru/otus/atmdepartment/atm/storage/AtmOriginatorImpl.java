package ru.otus.atmdepartment.atm.storage;

import java.util.ArrayDeque;
import java.util.Deque;

public class AtmOriginatorImpl implements AtmOriginator {

    private final Deque<Memento> stack = new ArrayDeque<>();

    @Override
    public void saveState(AtmState state) {
        stack.push(new Memento(state));
    }

    @Override
    public AtmState restoreState() {
        return stack.getLast().getState();
    }
}
