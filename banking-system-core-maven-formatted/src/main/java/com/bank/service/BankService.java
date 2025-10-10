package com.bank.service;
import com.bank.domain.*;
import com.bank.util.BankExceptions;
import java.math.BigDecimal;
import java.util.*;

public class BankService {
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Account> accounts = new HashMap<>();
    public void registerCustomer(Customer c) { customers.put(c.getId(), c); }
    public void addAccount(Account account) { accounts.put(account.getAccountNo(), account); account.getOwner().addAccount(account); }
    public Account findAccount(String accountNo) {
        Account a = accounts.get(accountNo);
        if (a == null) throw new BankExceptions.AccountNotFound(accountNo);
        return a;
    }
    public Results.DepositResult deposit(String accountNo, BigDecimal amount, String note) {
        Account a = findAccount(accountNo); a.deposit(amount, note);
        return new Results.DepositResult(accountNo, amount, a.getBalance());
    }
    public Results.WithdrawResult withdraw(String accountNo, BigDecimal amount, String note) {
        Account a = findAccount(accountNo); a.withdraw(amount, note);
        return new Results.WithdrawResult(accountNo, amount, a.getBalance());
    }
    public Results.TransferResult transfer(String fromAcc, String toAcc, BigDecimal amount, String note) {
        if (fromAcc.equals(toAcc)) throw new IllegalArgumentException("Cannot transfer to same account");
        Account from = findAccount(fromAcc); Account to = findAccount(toAcc);
        from.withdraw(amount, "Transfer to " + toAcc + (note == null ? "" : " - " + note));
        to.deposit(amount, "Transfer from " + fromAcc + (note == null ? "" : " - " + note));
        return new Results.TransferResult(fromAcc, toAcc, amount, from.getBalance(), to.getBalance());
    }
    public void applyMonthlyInterest() {
        for (Account a : accounts.values()) {
            if (a instanceof InterestBearing ib) {
                var interest = ib.calculateInterest();
                if (interest.signum() > 0) {
                    a.deposit(interest, "Monthly interest");
                }
            }
        }
    }
    public Collection<Account> getAllAccounts() { return Collections.unmodifiableCollection(accounts.values()); }
    public Collection<Customer> getAllCustomers() { return Collections.unmodifiableCollection(customers.values()); }
}
