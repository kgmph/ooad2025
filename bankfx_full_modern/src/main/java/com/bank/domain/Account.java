package com.bank.domain;

public abstract class Account {

    protected String id;
    protected String customer;
    protected double balance;

    public Account(String id, String customer, double balance) {
        this.id = id;
        this.customer = customer;
        this.balance = balance;
    }

    public String getId() { return id; }
    public String getCustomer() { return customer; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public abstract String getType();
}
