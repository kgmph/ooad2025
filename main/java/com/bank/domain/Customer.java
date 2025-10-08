package com.bank.domain;

import java.util.*;
import java.math.BigDecimal;

import com.bank.domain.exceptions.InvalidOperationException;

public class Customer {
    private final String customerId;
    private final String firstName;
    private final String lastName;
    private String address;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String customerId, String firstName, String lastName, String address) {
        if (customerId == null || customerId.isBlank()) throw new InvalidOperationException("customerId required");
        if (firstName == null || firstName.isBlank()) throw new InvalidOperationException("firstName required");
        if (lastName == null || lastName.isBlank()) throw new InvalidOperationException("lastName required");

        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address == null ? "" : address;
    }

    public String getCustomerId() { return customerId; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address == null ? "" : address; }

    public List<Account> getAccounts() { return Collections.unmodifiableList(accounts); }

    public SavingsAccount openSavings(String branch, BigDecimal openingBalance) {
        SavingsAccount acc = new SavingsAccount(branch, openingBalance);
        attach(acc);
        return acc;
    }

    public InvestmentAccount openInvestment(String branch, BigDecimal openingBalance) {
        InvestmentAccount acc = new InvestmentAccount(branch, openingBalance);
        attach(acc);
        return acc;
    }

    public ChequeAccount openCheque(String branch, BigDecimal openingBalance, String employer, String employerAddr) {
        ChequeAccount acc = new ChequeAccount(branch, openingBalance, employer, employerAddr);
        attach(acc);
        return acc;
    }

    private void attach(Account account) {
        account.setOwner(this);
        accounts.add(account);
    }

    public Optional<Account> findAccount(String accountNumber) {
        return accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst();
    }
}
