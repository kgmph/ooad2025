package com.bank.domain;
import java.util.*;
public class Customer {
    private String id; private String name; private String email; private String phone;
    private final List<Account> accounts = new ArrayList<>();
    public Customer(String id, String name, String email, String phone){this.id=id;this.name=name;this.email=email;this.phone=phone;}
    public String getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPhone(){return phone;}
    public List<Account> getAccounts(){return Collections.unmodifiableList(accounts);} public void addAccount(Account a){accounts.add(a);}
}
