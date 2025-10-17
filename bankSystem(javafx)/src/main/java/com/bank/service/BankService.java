package com.bank.service;
import com.bank.domain.*; import com.bank.port.*; import com.bank.repo.*; import java.math.BigDecimal; import java.util.*;
public class BankService implements AccountService, AuthService {
    private final CustomerRepository customers; private final AccountRepository accounts;
    private final Map<String,String> userPasswords = new HashMap<>(); private final Map<String,Customer> userOwners = new HashMap<>();
    public BankService(CustomerRepository customers, AccountRepository accounts){ this.customers = customers; this.accounts = accounts; }
    // AuthService
    @Override public java.util.Optional<Customer> authenticate(String u, String p){
        if(u==null||p==null) return java.util.Optional.empty(); String key=u.toLowerCase();
        if(!userPasswords.containsKey(key)) return java.util.Optional.empty();
        return java.util.Objects.equals(userPasswords.get(key), p) ? java.util.Optional.ofNullable(userOwners.get(key)) : java.util.Optional.empty();
    }
    @Override public boolean registerNewUser(String name,String u,String p,String email,String phone){
        String key = u==null? "": u.trim().toLowerCase(); if(key.isBlank() || p==null || p.length()<4) return false; if(userPasswords.containsKey(key)) return false;
        Customer c = new Customer("C"+(customers.findAll().size()+1), name, email, phone); customers.save(c); userPasswords.put(key,p); userOwners.put(key,c);
        addAccount(new SavingsAccount("SA-"+(accounts.findAll().size()+1), c, new BigDecimal("0.00"), new BigDecimal("0.0005")));
        addAccount(new InvestmentAccount("IV-"+(accounts.findAll().size()+1), c, new BigDecimal("500.00"), new BigDecimal("0.05")));
        addAccount(new ChequeAccount("CQ-"+(accounts.findAll().size()+1), c, new BigDecimal("200.00"), new BigDecimal("100.00"), null));
        return true;
    }
    // helpers for seeding
    public void registerCustomer(Customer c){ customers.save(c); }
    public void registerUser(String u,String p,Customer owner){ userPasswords.put(u.toLowerCase(), p); userOwners.put(u.toLowerCase(), owner); }
    // AccountService
    @Override public void addAccount(Account a){ accounts.save(a); a.getOwner().addAccount(a); }
    @Override public Account findAccount(String id){ return accounts.findById(id); }
    @Override public java.util.List<Account> getAccountsFor(Customer c){ return accounts.findByOwner(c); }
    @Override public Results.DepositResult deposit(String id, BigDecimal amt, String note){ Account a=findAccount(id); a.deposit(amt,note); return new Results.DepositResult(id,amt,a.getBalance()); }
    @Override public Results.WithdrawResult withdraw(String id, BigDecimal amt, String note){ Account a=findAccount(id); a.withdraw(amt,note); return new Results.WithdrawResult(id,amt,a.getBalance()); }
    @Override public Results.TransferResult transfer(String from,String to, BigDecimal amt, String note){
        if(from.equals(to)) throw new IllegalArgumentException("Cannot transfer to same account"); Account f=findAccount(from), t=findAccount(to);
        f.withdraw(amt, "Transfer to "+to+(note==null? "": " - "+note)); t.deposit(amt, "Transfer from "+from+(note==null? "": " - "+note));
        return new Results.TransferResult(from,to,amt,f.getBalance(),t.getBalance()); }
    @Override public void applyMonthlyInterest(){ for(Account a: accounts.findAll()) if(a instanceof InterestBearing ib){ var i=ib.calculateInterest(); if(i.signum()>0) a.deposit(i, "Monthly interest"); } }
    @Override public java.util.Collection<Account> getAllAccounts(){ return accounts.findAll(); }
    public java.util.Collection<Customer> getAllCustomers(){ return customers.findAll(); }
}
