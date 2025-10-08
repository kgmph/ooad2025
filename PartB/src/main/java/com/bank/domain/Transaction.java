package com.bank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final String accountNumber;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;

    public Transaction(String accountNumber, TransactionType type, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getAccountNumber() { return accountNumber; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
