package ru.otus.atm.model;

public enum FaceValue {
    Hundred(100),
    FiveHundred(500),
    Thousand(1000),
    FiveThousand(5000);

    private final int index;

    FaceValue(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}
