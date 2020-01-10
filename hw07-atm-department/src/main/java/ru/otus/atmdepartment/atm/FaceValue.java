package ru.otus.atmdepartment.atm;

public enum FaceValue {
    Hundred(100),
    FiveHundred(500),
    Thousand(1000),
    FiveThousand(5000);

    private final int faceValue;

    FaceValue(int faceValue) {
        this.faceValue = faceValue;
    }

    public int faceValue() {
        return faceValue;
    }
}
