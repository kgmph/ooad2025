package com.bank.domain;

import java.math.BigDecimal;

public class Demo {
    public static void main(String[] args) {
        Bank bank = new Bank("Botswana Bank");
        Customer kg = new Customer("C001", "Kago", "Gaone", "Gaborone");
        bank.registerCustomer(kg);

        var sav = kg.openSavings("GABS01", new BigDecimal("1000.00"));
        var inv = kg.openInvestment("GABS01", new BigDecimal("500.00"));
        var chq = kg.openCheque("GABS01", new BigDecimal("200.00"), "Orange Botswana", "CBD");

        bank.deposit(sav.getAccountNumber(), new BigDecimal("500.00"));
        bank.deposit(inv.getAccountNumber(), new BigDecimal("1000.00"));
        bank.withdraw(chq.getAccountNumber(), new BigDecimal("50.00"));

        bank.applyMonthlyInterest(sav.getAccountNumber());
        bank.applyMonthlyInterest(inv.getAccountNumber());

        System.out.println(sav);
        System.out.println(inv);
        System.out.println(chq);
    }
}
