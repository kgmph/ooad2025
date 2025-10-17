package com.bank.domain;
import com.bank.util.BankExceptions; import java.math.BigDecimal;
public class ChequeAccount extends Account {
    private final BigDecimal overdraftLimit; private Employer employer;
    public ChequeAccount(String accountNo, Customer owner, BigDecimal openingBalance, BigDecimal overdraftLimit, Employer employer){
        super(accountNo, owner, openingBalance); this.overdraftLimit = overdraftLimit == null ? BigDecimal.ZERO : overdraftLimit.abs(); this.employer = employer; }
    @Override public void withdraw(BigDecimal amount, String note){
        requirePositive(amount); BigDecimal projected = balance.subtract(amount);
        if (projected.compareTo(overdraftLimit.negate()) < 0) throw new BankExceptions.InsufficientFunds(accountNo, "Overdraft limit exceeded");
        balance = projected; addTransaction(new Transaction(TransactionType.WITHDRAWAL, amount.negate(), balance, note)); }
    public Employer getEmployer(){return employer;} public BigDecimal getOverdraftLimit(){return overdraftLimit;}
}
