package com.bank.domain;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public class Transaction {
    private final String id = UUID.randomUUID().toString();
    private final Instant timestamp = Instant.now();
    private final TransactionType type;
    private final BigDecimal amount;
    private final BigDecimal balanceAfter;
    private final String note;
    public Transaction(TransactionType type, BigDecimal amount, BigDecimal balanceAfter, String note) {
        this.type = type; this.amount = amount; this.balanceAfter = balanceAfter; this.note = note;
    }
    public String getId() { return id; }
    public Instant getTimestamp() { return timestamp; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public String getNote() { return note; }
}
