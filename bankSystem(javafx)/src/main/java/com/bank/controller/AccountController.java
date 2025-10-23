package com.bank.controller;

import com.bank.port.AccountService;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = Objects.requireNonNull(accountService, "accountService");
    }

    public void deposit(String toAccId, String amountStr, String note) {
        String acc = requireId(toAccId, "Account ID (deposit)");
        BigDecimal amount = requirePositive(amountStr, "Amount (deposit)");
        accountService.deposit(acc, amount, trimOrNull(note));
    }

    public void withdraw(String fromAccId, String amountStr, String note) {
        String acc = requireId(fromAccId, "Account ID (withdraw)");
        BigDecimal amount = requirePositive(amountStr, "Amount (withdraw)");
        accountService.withdraw(acc, amount, trimOrNull(note));
    }

    /**
     * Transfer funds FROM a typed account ID TO the user-selected account.
     */
    public void transfer(String fromAccId, String toAccId, String amountStr, String note) {
        String from = requireId(fromAccId, "From Account ID (transfer)");
        String to   = requireId(toAccId,   "To Account ID (transfer)");
        if (from.equals(to)) {
            throw new IllegalArgumentException("From and To accounts must be different.");
        }
        BigDecimal amount = requirePositive(amountStr, "Amount (transfer)");
        accountService.transfer(from, to, amount, trimOrNull(note));
    }

    public void applyMonthlyInterest() {
        accountService.applyMonthlyInterest();
    }

    // ---------- helpers ----------

    private static String requireId(String raw, String field) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return raw.trim();
    }

    private static BigDecimal requirePositive(String raw, String field) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        try {
            BigDecimal v = new BigDecimal(raw.trim());
            if (v.signum() <= 0) {
                throw new IllegalArgumentException(field + " must be a positive number.");
            }
            return v;
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(field + " must be numeric.");
        }
    }

    private static String trimOrNull(String raw) {
        return (raw == null) ? null : raw.trim();
    }
}
