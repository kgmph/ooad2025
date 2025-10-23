package com.bank.controller;

import com.bank.port.AuthService;

import java.util.Objects;

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = Objects.requireNonNull(authService, "authService");
    }

    public void register(String username, String password) {
        String u = requireUsername(username);
        String p = requirePassword(password);
        authService.register(u, p); // Service handles user creation + starter accounts
    }

    // Reuse the same simple rules as LoginController
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
