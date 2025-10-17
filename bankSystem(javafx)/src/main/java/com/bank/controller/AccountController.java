package com.bank.controller;
import com.bank.domain.Account; import com.bank.domain.Customer; import com.bank.port.AccountService; import com.bank.service.Results;
import java.math.BigDecimal; import java.util.List;
public class AccountController {
    private final AccountService accounts; public AccountController(AccountService accounts){ this.accounts = accounts; }
    public List<Account> listAccounts(Customer owner){ return accounts.getAccountsFor(owner); }
    public Account getAccount(String id){ return accounts.findAccount(id); }
    public Results.DepositResult deposit(String id, BigDecimal amt, String note){ return accounts.deposit(id, amt, note); }
    public Results.WithdrawResult withdraw(String id, BigDecimal amt, String note){ return accounts.withdraw(id, amt, note); }
    public Results.TransferResult transfer(String from, String to, BigDecimal amt, String note){ return accounts.transfer(from, to, amt, note); }
    public void applyMonthlyInterest(){ accounts.applyMonthlyInterest(); }
}
