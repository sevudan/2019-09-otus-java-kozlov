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

    private Map<Integer, MoneyCell> Atm01loadBanknotes = new HashMap<>();
    private Map<Integer, MoneyCell> Atm02loadBanknotes = new HashMap<>();
    private AtmImpl atm1;
    private AtmImpl atm2;
    private AtmImpl atm3;
    private AtmDepartment atmDepartment;

    @BeforeEach
    public void init() {
        //Total 21000
        Atm01loadBanknotes.put(100, new MoneyCellImpl(5, 100));
        Atm01loadBanknotes.put(500, new MoneyCellImpl(5, 500));
        Atm01loadBanknotes.put(1000, new MoneyCellImpl(3, 1000));
        Atm01loadBanknotes.put(5000, new MoneyCellImpl(3, 5000));

        //Total 6700
        Atm02loadBanknotes.put(100, new MoneyCellImpl(2, 100));
        Atm02loadBanknotes.put(500, new MoneyCellImpl(1, 500));
        Atm02loadBanknotes.put(1000, new MoneyCellImpl(1, 1000));
        Atm02loadBanknotes.put(5000, new MoneyCellImpl(1, 5000));
    }


    @Test
    public void testDepartmentRemoveAtm(){
        atmDepartment = new AtmDepartmentImpl();
        AtmImpl atm1 = new AtmImpl(Atm01loadBanknotes);
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
        AtmImpl atm1 = new AtmImpl(Atm01loadBanknotes);
        AtmImpl atm2 = new AtmImpl(Atm02loadBanknotes);
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
        AtmImpl atm1 = new AtmImpl(Atm01loadBanknotes);
        AtmImpl atm2 = new AtmImpl(Atm02loadBanknotes);
        AtmImpl atm3 = new AtmImpl();

        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
        atmDepartment.addAtm(atm3);
        atm3.addCash(3, 500);
        atm3.addCash(2, 1000);
        atm3.addCash(2, 5000);
        Map<String, Integer> balance = atmDepartment.getBalance(atmDepartment.getAtmAll());

        assertEquals(41200, balance.get("TotalMoney"));
        assertEquals(28, balance.get("TotalBanknotes"));
    }

    @Test
    public void testDepartmentResetAllAtm() {
        atmDepartment = new AtmDepartmentImpl();
        AtmImpl atm1 = new AtmImpl(Atm01loadBanknotes);
        AtmImpl atm2 = new AtmImpl(Atm02loadBanknotes);
        AtmImpl atm3 = new AtmImpl();

        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
        atmDepartment.addAtm(atm3);
        atm1.addCash(2, 500);
        atm2.addCash(30, 1000);
        atm3.addCash(10, 5000);

        atmDepartment.restore();

        atm1.getTotalCash();
        atm2.getTotalCash();
        atm3.getTotalCash();

        assertEquals(21000, atm1.getTotalCash().get("TotalMoney"));
        assertEquals(16, atm1.getTotalCash().get("TotalBanknotes"));
        assertEquals(6700, atm2.getTotalCash().get("TotalMoney"));
        assertEquals(5, atm2.getTotalCash().get("TotalBanknotes"));
        assertEquals(0, atm3.getTotalCash().get("TotalMoney"));
        assertEquals(0, atm3.getTotalCash().get("TotalBanknotes"));
    }


    @AfterEach
    public void clearAll(){
        Atm01loadBanknotes.clear();
        Atm02loadBanknotes.clear();

        atmDepartment.removeAtm(atm1);
        atmDepartment.removeAtm(atm2);
        atmDepartment.removeAtm(atm3);
    }
}

