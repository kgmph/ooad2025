package com.bank.domain;

public class ChequeAccount extends Account {

    private String employer;

    public ChequeAccount(String id, String customer, double balance, String employer) {
        super(id, customer, balance);
        this.employer = employer;
    }

    public String getEmployer() { return employer; }

    @Override
    public String getType() {
        return "Cheque";
    }
}
