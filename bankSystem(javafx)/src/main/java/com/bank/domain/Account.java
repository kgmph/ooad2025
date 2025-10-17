package com.bank.domain;
import com.bank.util.BankExceptions;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.*;
public abstract class Account {
    protected final String accountNo; protected final Customer owner; protected BigDecimal balance; protected final LocalDate openedAt = LocalDate.now();
    protected final java.util.List<Transaction> transactions = new java.util.ArrayList<>();
    protected Account(String accountNo, Customer owner, BigDecimal openingBalance) {
        this.accountNo = Objects.requireNonNull(accountNo); this.owner = Objects.requireNonNull(owner);
        this.balance = openingBalance == null ? BigDecimal.ZERO : openingBalance;
    }
    public String getAccountNo(){return accountNo;} public Customer getOwner(){return owner;} public BigDecimal getBalance(){return balance;}
    public LocalDate getOpenedAt(){return openedAt;} public java.util.List<Transaction> getTransactions(){return java.util.Collections.unmodifiableList(transactions);}
    public void deposit(BigDecimal amount, String note){ requirePositive(amount); balance = balance.add(amount);
        addTransaction(new Transaction(TransactionType.DEPOSIT, amount, balance, note)); }
    public void withdraw(BigDecimal amount, String note){
        requirePositive(amount); if (balance.compareTo(amount) < 0) throw new BankExceptions.InsufficientFunds(accountNo);
        balance = balance.subtract(amount); addTransaction(new Transaction(TransactionType.WITHDRAWAL, amount.negate(), balance, note)); }
    protected void addTransaction(Transaction tx){ transactions.add(tx); }
    protected static void requirePositive(BigDecimal amount){ if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Amount must be > 0"); }
}
