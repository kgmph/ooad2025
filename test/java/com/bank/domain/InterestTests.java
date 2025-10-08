package com.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InterestTests {

    @Test
    @DisplayName("SavingsAccount: 0.05% monthly interest is applied and rounded to cents")
    void savings_interest() {
        SavingsAccount s = new SavingsAccount("GABS01", new BigDecimal("1000.00"));
        BigDecimal credited = s.applyMonthlyInterest();
        assertEquals(new BigDecimal("0.50"), credited);
        assertEquals(new BigDecimal("1000.50"), s.getBalance());
    }

    @Test
    @DisplayName("InvestmentAccount: 5% monthly interest is applied correctly")
    void investment_interest() {
        InvestmentAccount inv = new InvestmentAccount("GABS01", new BigDecimal("500.00"));
        BigDecimal credited = inv.applyMonthlyInterest();
        assertEquals(new BigDecimal("25.00"), credited);
        assertEquals(new BigDecimal("525.00"), inv.getBalance());
    }
}
