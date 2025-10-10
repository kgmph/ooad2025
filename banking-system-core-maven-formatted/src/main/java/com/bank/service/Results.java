package com.bank.service;
import java.math.BigDecimal;
public final class Results { private Results() {} public record DepositResult(String accountNo, BigDecimal amount, BigDecimal newBalance) {} public record WithdrawResult(String accountNo, BigDecimal amount, BigDecimal newBalance) {} public record TransferResult(String fromAcc, String toAcc, BigDecimal amount, BigDecimal fromNewBalance, BigDecimal toNewBalance) {} }
