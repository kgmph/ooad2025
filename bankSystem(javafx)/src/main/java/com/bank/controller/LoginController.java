package com.bank.controller;
import com.bank.domain.Customer; import com.bank.port.AuthService; import java.util.Optional;
public class LoginController {
    private final AuthService auth; public LoginController(AuthService auth){ this.auth = auth; }
    public Optional<Customer> login(String username, String password){ return auth.authenticate(username, password); }
}
