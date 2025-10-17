package com.bank.repo;
import com.bank.domain.Customer; import java.util.*;
public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<String, Customer> byId = new HashMap<>();
    @Override public void save(Customer c){ byId.put(c.getId(), c); }
    @Override public Optional<Customer> findById(String id){ return Optional.ofNullable(byId.get(id)); }
    @Override public Collection<Customer> findAll(){ return Collections.unmodifiableCollection(byId.values()); }
}
