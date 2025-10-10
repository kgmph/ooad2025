package com.bank;

import com.bank.domain.*;
import com.bank.service.BankService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        BankService svc = new BankService();

        Customer alice = new Customer("C001", "Alice", "alice@example.com", "+267-71-000-000");
        svc.registerCustomer(alice);

        var savings = new SavingsAccount("SA-1", alice, new BigDecimal("1000.00"), new BigDecimal("0.0005")); // 0.05%/mo
        var invest  = new InvestmentAccount("IV-1", alice, new BigDecimal("500.00"),  new BigDecimal("0.05"));   // 5%/mo
        var cheque  = new ChequeAccount("CQ-1", alice, new BigDecimal("200.00"),      new BigDecimal("100.00"), null);

        svc.addAccount(savings);
        svc.addAccount(invest);
        svc.addAccount(cheque);

        // Demo operations
        svc.deposit("CQ-1", new BigDecimal("150.00"), "cash");                 // CQ-1: 200 -> 350
        svc.withdraw("IV-1", new BigDecimal("50.00"), "adjustment");           // IV-1: 500 -> 450
        svc.transfer("CQ-1", "SA-1", new BigDecimal("25.00"), "top-up");       // CQ-1: 350 -> 325; SA-1: 1000 -> 1025
        svc.applyMonthlyInterest();                                            // SA-1 +0.0005; IV-1 +0.05; CQ-1 none

        // Botswana Pula formatting
        Locale locale = new Locale("en", "BW");
        NumberFormat money = NumberFormat.getCurrencyInstance(locale);

        System.out.println("Final balances:");
        System.out.println("---------------");
        svc.getAllAccounts().stream()
            .sorted(Comparator.comparing(a -> a.getAccountNo()))
            .forEach(a -> System.out.printf(
                "%s (%s, %s) -> %s%n",
                a.getAccountNo(),
                a.getClass().getSimpleName(),
                a.getOwner().getName(),
                money.format(a.getBalance())
            ));
    }
}
