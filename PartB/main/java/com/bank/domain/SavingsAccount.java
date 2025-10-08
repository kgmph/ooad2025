package com.bank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bank.domain.exceptions.InvalidOperationException;

public class SavingsAccount extends Account implements InterestBearing {
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("0.0005"); // 0.05%

    public SavingsAccount(String branch, BigDecimal openingBalance) {
        super(branch, openingBalance);
    }

    @Override public String getAccountType() { return "SavingsAccount"; }

    @Override
    public void withdraw(BigDecimal amount) {
        throw new InvalidOperationException("Withdrawals are not allowed from SavingsAccount.");
    }

    @Override
    public BigDecimal applyMonthlyInterest() {
        BigDecimal interest = getBalance().multiply(MONTHLY_RATE).setScale(2, RoundingMode.HALF_UP);
        deposit(interest);
        return interest;
    }
}
