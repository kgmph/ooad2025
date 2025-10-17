package com.bank.repo;
import com.bank.domain.Customer; import java.util.*;
public interface CustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(String id);
    Collection<Customer> findAll();
}
