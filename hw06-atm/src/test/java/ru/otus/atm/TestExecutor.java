package ru.otus.atm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.collection.IsMapContaining;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;


public class TestExecutor {
    private Atm atm = new AtmImpl();

    @BeforeEach
    public void init() {

        atm.addCash(5,100); //500
        atm.addCash(3,500); //1500
        atm.addCash(3,1000); //3000
        atm.addCash(2,5000); //10000 // Total:

    }

    @Test
    public void getCashThousand() {
        Map<Integer, MoneyCellImpl> map = atm.getCash(1000);

        assertThat(map, IsMapContaining.hasKey(1000));
        assertEquals(1, map.get(1000).getBanknotesCount());
    }

    @Test
    public void getCashMoreThanFiveThousand() {

        Map<Integer, MoneyCellImpl> map2 = atm.getCash(11700);

        assertThat(map2, IsMapContaining.hasKey(5000));
        assertThat(map2, IsMapContaining.hasKey(1000));
        assertThat(map2, IsMapContaining.hasKey(500));
        assertThat(map2, IsMapContaining.hasKey(100));
        assertEquals(2, map2.get(5000).getBanknotesCount());
        assertEquals(1, map2.get(1000).getBanknotesCount());
        assertEquals(1, map2.get(500).getBanknotesCount());
        assertEquals(2, map2.get(100).getBanknotesCount());
    }

    @Test
    public void getCashMoreThanMoneyCell() throws Exception {

        assertTrue(atm.getCash(1_000_000).isEmpty());
    }

    @Test
    public void getIncorrectCash() {
        var map = new HashMap<>();
        assertEquals(map, atm.getCash(5555));
    }

    @AfterEach
    public void infoAfter() {
        System.out.println("\nAfterEach Test message.");
        atm.getTotalCash().forEach((k,v) -> System.out.printf("%s %s \n", k, v));
        System.out.println();
    }
}

