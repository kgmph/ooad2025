package com.bank.domain;

import java.math.BigDecimal;
import java.util.*;

import com.bank.domain.exceptions.BankingException;

public class Bank {
    private final String name;
    private final Map<String, Customer> customers = new HashMap<>();

    public Bank(String name) { this.name = name; }
    public String getName() { return name; }

    public void registerCustomer(Customer c) { customers.put(c.getCustomerId(), c); }
    public Optional<Customer> findCustomer(String id) { return Optional.ofNullable(customers.get(id)); }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account acc = getAccountByNumber(accountNumber);
        acc.deposit(amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) throws BankingException {
        Account acc = getAccountByNumber(accountNumber);
        acc.withdraw(amount);
    }

    public BigDecimal applyMonthlyInterest(String accountNumber) {
        Account acc = getAccountByNumber(accountNumber);
        if (acc instanceof InterestBearing ib) {
            return ib.applyMonthlyInterest();
        }
        return BigDecimal.ZERO;
    }

    private Account getAccountByNumber(String accountNumber) {
        return customers.values().stream()
                .flatMap(c -> c.getAccounts().stream())
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));
    }
}
