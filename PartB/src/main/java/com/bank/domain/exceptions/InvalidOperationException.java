package com.bank.domain.exceptions;

public class InvalidOperationException extends BankingException {
    public InvalidOperationException(String message) { super(message); }
}
