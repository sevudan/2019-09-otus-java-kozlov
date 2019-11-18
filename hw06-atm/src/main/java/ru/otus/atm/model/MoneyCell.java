package ru.otus.atm.model;

import ru.otus.atm.controller.AtmActionImpl;

public class MoneyCell extends AtmActionImpl {

    private int banknotesCount;

    private int faceValue;

    public MoneyCell(int count, int faceValue) {
        this.banknotesCount = count;
        this.faceValue = faceValue;
    }

    public int getBanknotesCount() {
        return banknotesCount;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public int getTotalAmount() { return banknotesCount * faceValue;}

    public void setBanknotesCount(int count) {
        this.banknotesCount = count;
    }

    public void setFaceValue(int faceValue) {
        this.faceValue = faceValue;
    }
}