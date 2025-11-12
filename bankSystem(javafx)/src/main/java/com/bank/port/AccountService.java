package com.bank.service;

import com.bank.port.AuthService;
import com.bank.repo.CustomerRepository;

import java.util.Objects;

public class AuthServiceImpl implements AuthService {

  private final CustomerRepository customers;

  public AuthServiceImpl(CustomerRepository customers) {
    this.customers = Objects.requireNonNull(customers);
  }

  @Override
  public boolean login(String username, String password) {
    return customers.findIdByUsername(username)
        .map(id -> customers.verifyPassword(id, password))
        .orElse(false);
  }

  @Override
  public void register(String username, String password, String fullName) {
    // TODO: hash password (later)
    customers.create(username, password, fullName);
  }
}
