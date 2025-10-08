package com.bank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import com.bank.domain.exceptions.BankingException;
import com.bank.domain.exceptions.InvalidOperationException;
import com.bank.domain.exceptions.InsufficientFundsException;

public abstract class Account {
    private static final AtomicLong SEQ = new AtomicLong(1000000000L);

    private final String accountNumber;
    private final String branch;
    protected BigDecimal balance;
    private final LocalDateTime openedAt;
    private Customer owner;

    protected Account(String branch, BigDecimal openingBalance) {
        if (branch == null || branch.isBlank()) {
            throw new InvalidOperationException("Branch is required.");
        }
        this.accountNumber = String.valueOf(SEQ.getAndIncrement());
        this.branch = branch;
        this.balance = openingBalance == null ? BigDecimal.ZERO : openingBalance.setScale(2, RoundingMode.HALF_UP);
        this.openedAt = LocalDateTime.now();
    }

    public void deposit(BigDecimal amount) {
        validatePositive(amount, "Deposit");
        balance = balance.add(amount);
    }
    public void deposit(double amount) { deposit(BigDecimal.valueOf(amount)); }

    public void withdraw(BigDecimal amount) throws BankingException {
        validatePositive(amount, "Withdrawal");
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds.");
        }
        balance = balance.subtract(amount);
    }
    public void withdraw(double amount) throws BankingException { withdraw(BigDecimal.valueOf(amount)); }

    protected void validatePositive(BigDecimal amount, String label) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException(label + " amount must be > 0.");
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public String getBranch() { return branch; }
    public BigDecimal getBalance() { return balance; }
    public LocalDateTime getOpenedAt() { return openedAt; }
    public Customer getOwner() { return owner; }
    void setOwner(Customer owner) { this.owner = owner; }

    public abstract String getAccountType();

    @Override public String toString() {
        return getAccountType() + "{" +
               "accountNumber='" + accountNumber + '\'' +
               ", branch='" + branch + '\'' +
               ", balance=" + balance +
               ", owner=" + (owner != null ? owner.getFullName() : "N/A") +
               '}';
    }
}
