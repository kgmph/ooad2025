package com.bank.util;
import java.math.BigDecimal;
public final class BankExceptions {
    private BankExceptions() {}
    public static class InsufficientFunds extends RuntimeException {
        public InsufficientFunds(String acc) { super("Insufficient funds in account " + acc); }
        public InsufficientFunds(String acc, String msg) { super(msg + " [account " + acc + "]"); }
    }
    public static class InvalidOperation extends RuntimeException { public InvalidOperation(String msg){super(msg);} }
    public static class MinimumOpeningDeposit extends RuntimeException {
        public MinimumOpeningDeposit(BigDecimal min){super("Minimum opening deposit is " + min);}
    }
    public static class AccountNotFound extends RuntimeException { public AccountNotFound(String id){super("Account not found: " + id);} }
}
