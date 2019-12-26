package ru.otus.atmdepartment.atm.storage;

/**
 * @author sergey
 * created on 11.09.18.
 */
  class Memento {
    private final AtmState state;

    Memento(AtmState state) {
      this.state = new AtmState(state);
    }

    AtmState getState() {
      return state;
    }
}
