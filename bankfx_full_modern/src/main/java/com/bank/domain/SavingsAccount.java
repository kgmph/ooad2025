package com.bank.domain;

public class SavingsAccount extends Account {

    public SavingsAccount(String id, String customer, double balance) {
        super(id, customer, balance);
    }

    @Override
    public String getType() {
        return "Savings";
    }
}
