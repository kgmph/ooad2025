package com.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankFacadeTests {

    @Test
    @DisplayName("Bank facade can register customer, open accounts, and apply interest")
    void bank_end_to_end() {
        Bank bank = new Bank("Botswana Bank");
        Customer kg = new Customer("C001", "Kago", "Gaone", "Gaborone");
        bank.registerCustomer(kg);

        var sav = kg.openSavings("GABS01", new BigDecimal("1000.00"));
        var inv = kg.openInvestment("GABS01", new BigDecimal("500.00"));

        bank.deposit(sav.getAccountNumber(), new BigDecimal("500.00"));
        bank.deposit(inv.getAccountNumber(), new BigDecimal("1000.00"));

        var sInt = bank.applyMonthlyInterest(sav.getAccountNumber());
        var iInt = bank.applyMonthlyInterest(inv.getAccountNumber());

        assertEquals(new BigDecimal("0.75"), sInt);
        assertEquals(new BigDecimal("75.00"), iInt);

        assertEquals(new BigDecimal("1500.75"), sav.getBalance());
        assertEquals(new BigDecimal("1575.00"), inv.getBalance());
    }
}
