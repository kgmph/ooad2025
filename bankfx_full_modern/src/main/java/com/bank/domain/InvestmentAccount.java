package com.bank.domain;

public class InvestmentAccount extends Account {

    public InvestmentAccount(String id, String customer, double balance) {
        super(id, customer, balance);
    }

    @Override
    public String getType() {
        return "Investment";
    }
}

