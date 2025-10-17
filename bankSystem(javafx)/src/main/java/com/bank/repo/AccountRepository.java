package com.bank.repo;
import com.bank.domain.Account; import com.bank.domain.Customer; import java.util.*;
public interface AccountRepository {
    void save(Account account);
    Account findById(String id);
    List<Account> findByOwner(Customer owner);
    Collection<Account> findAll();
}
