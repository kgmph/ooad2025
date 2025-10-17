package com.bank.controller;
import com.bank.port.AuthService;
public class AuthController {
    private final AuthService auth; public AuthController(AuthService auth){ this.auth = auth; }
    public boolean register(String name,String username,String password,String email,String phone){ return auth.registerNewUser(name,username,password,email,phone); }
}
