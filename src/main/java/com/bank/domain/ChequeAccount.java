package com.bank.domain;

import java.math.BigDecimal;

import com.bank.domain.exceptions.InvalidOperationException;

public class ChequeAccount extends Account {
    private final String employerName;
    private final String employerAddress;

    public ChequeAccount(String branch, BigDecimal openingBalance, String employerName, String employerAddress) {
        super(branch, openingBalance);
        if (employerName == null || employerName.isBlank()) {
            throw new InvalidOperationException("Employer name is required for ChequeAccount.");
        }
        this.employerName = employerName;
        this.employerAddress = employerAddress == null ? "" : employerAddress;
    }

    @Override public String getAccountType() { return "ChequeAccount"; }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
}
