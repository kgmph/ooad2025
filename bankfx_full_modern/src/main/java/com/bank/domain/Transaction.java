package com.bank.domain;

public class Transaction {

    private String id;
    private String accId;
    private double amount;
    private String type;
    private String ts;

    public Transaction(String id, String accId, double amount, String type, String ts) {
        this.id = id;
        this.accId = accId;
        this.amount = amount;
        this.type = type;
        this.ts = ts;
    }

    public String getId() { return id; }
    public String getAccId() { return accId; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public String getTs() { return ts; }
}
