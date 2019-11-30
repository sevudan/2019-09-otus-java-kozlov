package ru.otus.atmdepartment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import ru.otus.atmdepartment.director.*;
import ru.otus.atmdepartment.atm.*;


public class TestExecutor {

    private Map<Integer, MoneyCell> atm01loadBanknotes = new HashMap<>();
    private Map<Integer, MoneyCell> atm02loadBanknotes = new HashMap<>();
    private AtmImpl atm1;
    private AtmImpl atm2;
    private AtmImpl atm3;
    private AtmDepartment atmDepartment;

    @BeforeEach
    public void init() {
        //Total 21000
        atm01loadBanknotes.put(100, new MoneyCellImpl(5, 100));
        atm01loadBanknotes.put(500, new MoneyCellImpl(5, 500));
        atm01loadBanknotes.put(1000, new MoneyCellImpl(3, 1000));
        atm01loadBanknotes.put(5000, new MoneyCellImpl(3, 5000));

        //Total 6700
        atm02loadBanknotes.put(100, new MoneyCellImpl(2, 100));
        atm02loadBanknotes.put(500, new MoneyCellImpl(1, 500));
        atm02loadBanknotes.put(1000, new MoneyCellImpl(1, 1000));
        atm02loadBanknotes.put(5000, new MoneyCellImpl(1, 5000));
    }


    @Test
    public void testDepartmentRemoveAtm(){
        atmDepartment = new AtmDepartmentImpl();
        AtmImpl atm1 = new AtmImpl(atm01loadBanknotes);
        AtmImpl atm3 = new AtmImpl();

        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
        atmDepartment.addAtm(atm3);

        atmDepartment.removeAtm(atm1);
        atmDepartment.removeAtm(atm2);
        atmDepartment.removeAtm(atm3);
        assertTrue(atmDepartment.getAtmAll().isEmpty());

    }

    @Test
    public void testDepartmentGetAtm(){
        atmDepartment = new AtmDepartmentImpl();
        AtmImpl atm1 = new AtmImpl(atm01loadBanknotes);
        AtmImpl atm2 = new AtmImpl(atm02loadBanknotes);
        AtmImpl atm3 = new AtmImpl();

        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
        atmDepartment.addAtm(atm3);

        assertFalse(atmDepartment.getAtmAll().isEmpty());
        assertEquals(3, atmDepartment.getAtmAll().size());
    }


    @Test
    public void testDepartmentGetTotalCash() {
        atmDepartment = new AtmDepartmentImpl();
        AtmImpl atm1 = new AtmImpl(atm01loadBanknotes);
        AtmImpl atm2 = new AtmImpl(atm02loadBanknotes);
        AtmImpl atm3 = new AtmImpl();

        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
        atmDepartment.addAtm(atm3);
        atm3.addCash(3, 500);
        atm3.addCash(2, 1000);
        atm3.addCash(2, 5000);
        BalanceInfo balance = atmDepartment.getBalance();

        assertEquals(41200, balance.getTotalMoney());
        assertEquals(28, balance.getTotalBanknotes());
    }

    @Test
    public void testDepartmentResetAllAtm() {
        atmDepartment = new AtmDepartmentImpl();
        AtmImpl atm1 = new AtmImpl(atm01loadBanknotes);
        AtmImpl atm2 = new AtmImpl(atm02loadBanknotes);
        AtmImpl atm3 = new AtmImpl();

        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
        atmDepartment.addAtm(atm3);
        atm1.addCash(2, 500);
        atm2.addCash(30, 1000);
        atm3.addCash(10, 5000);

        atmDepartment.restore();

        assertEquals(21000, atm1.getTotalCash().getTotalMoney());
        assertEquals(16, atm1.getTotalCash().getTotalBanknotes());
        assertEquals(6700, atm2.getTotalCash().getTotalMoney());
        assertEquals(5, atm2.getTotalCash().getTotalBanknotes());
        assertEquals(0, atm3.getTotalCash().getTotalMoney());
        assertEquals(0, atm3.getTotalCash().getTotalBanknotes());
    }


    @AfterEach
    public void clearAll(){
        atm01loadBanknotes.clear();
        atm02loadBanknotes.clear();

        atmDepartment.removeAtm(atm1);
        atmDepartment.removeAtm(atm2);
        atmDepartment.removeAtm(atm3);
    }
}