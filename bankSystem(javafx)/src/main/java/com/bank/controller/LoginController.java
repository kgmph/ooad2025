package com.bank.controller;

import com.bank.port.AuthService;

import java.util.Objects;

public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = Objects.requireNonNull(authService, "authService");
    }

    public boolean login(String username, String password) {
        String u = requireUsername(username);
        String p = requirePassword(password);
        return authService.login(u, p);
    }

    private static String requireUsername(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        String u = raw.trim();
        if (u.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters.");
        }
        return u;
    }

    private static String requirePassword(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }
        String p = raw.trim();
        if (p.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        return p;
    }
}
