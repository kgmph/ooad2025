package com.bank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bank.domain.exceptions.InvalidOperationException;

public class InvestmentAccount extends Account implements InterestBearing {
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("0.05"); // 5%
    private static final BigDecimal MIN_OPEN = new BigDecimal("500.00");

    public InvestmentAccount(String branch, BigDecimal openingBalance) {
        super(branch, openingBalance);
        if (getBalance().compareTo(MIN_OPEN) < 0) {
            throw new InvalidOperationException("InvestmentAccount requires a minimum opening deposit of BWP500.00.");
        }
    }

    @Override public String getAccountType() { return "InvestmentAccount"; }

    @Override
    public BigDecimal applyMonthlyInterest() {
        BigDecimal interest = getBalance().multiply(MONTHLY_RATE).setScale(2, RoundingMode.HALF_UP);
        deposit(interest);
        return interest;
    }
}
