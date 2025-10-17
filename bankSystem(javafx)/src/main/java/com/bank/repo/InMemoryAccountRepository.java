package com.bank.repo;
import com.bank.domain.Account; import com.bank.domain.Customer; import com.bank.util.BankExceptions;
import java.util.*;
public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> byId = new HashMap<>();
    @Override public void save(Account account){ byId.put(account.getAccountNo(), account); }
    @Override public Account findById(String id){ Account a = byId.get(id); if (a==null) throw new BankExceptions.AccountNotFound(id); return a; }
    @Override public List<Account> findByOwner(Customer owner){ List<Account> out=new ArrayList<>(); for(Account a:byId.values()) if(a.getOwner().equals(owner)) out.add(a); return out; }
    @Override public Collection<Account> findAll(){ return Collections.unmodifiableCollection(byId.values()); }
}
