package ru.otus.atmdepartment.atm;

public class MoneyCellImpl implements MoneyCell {

    private int banknotesCount;

    private int faceValue;

    public MoneyCellImpl(int banknotesCount, int faceValue) {
        this.banknotesCount = banknotesCount;
        this.faceValue = faceValue;
    }

    public int getFaceValue() { return faceValue; }

    @Override
    public int getBanknotesCount() {
        return banknotesCount;
    }

    @Override
    public int getTotalAmount() { return banknotesCount * faceValue;}

}