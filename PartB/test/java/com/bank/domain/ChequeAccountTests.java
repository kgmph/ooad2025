package com.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bank.domain.exceptions.InvalidOperationException;

public class ChequeAccountTests {

    @Test
    @DisplayName("ChequeAccount requires employer name")
    void employer_required() {
        assertThrows(InvalidOperationException.class,
            () -> new ChequeAccount("GABS01", new BigDecimal("100.00"), "", "CBD"));
    }

    @Test
    @DisplayName("ChequeAccount deposit & withdraw affect balance")
    void deposit_withdraw_affect_balance() {
        ChequeAccount c = new ChequeAccount("GABS01", new BigDecimal("200.00"), "Mascom", "Gabs");
        c.deposit(new BigDecimal("50.00"));
        c.withdraw(new BigDecimal("20.00"));
        assertEquals(new BigDecimal("230.00"), c.getBalance());
    }
}
