package com.bank.service;

import com.bank.port.AccountService;
import com.bank.repo.AccountRepository;
import com.bank.repo.TransactionRepository;
import com.bank.util.DbInit;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class AccountServiceImpl implements AccountService {

  private final AccountRepository accounts;
  private final TransactionRepository txs;

  public AccountServiceImpl(AccountRepository accounts, TransactionRepository txs) {
    this.accounts = Objects.requireNonNull(accounts);
    this.txs = Objects.requireNonNull(txs);
  }

  @Override
  public void deposit(String toAccNo, BigDecimal amount, String note) {
    long id = accounts.findIdByAccNo(toAccNo).orElseThrow(() -> new IllegalArgumentException("No such account: " + toAccNo));
    BigDecimal newBal = accounts.getBalance(id).add(amount);
    accounts.updateBalance(id, newBal);
    txs.add(id, "DEPOSIT", amount, newBal, note);
  }

  @Override
  public void withdraw(String fromAccNo, BigDecimal amount, String note) {
    long id = accounts.findIdByAccNo(fromAccNo).orElseThrow(() -> new IllegalArgumentException("No such account: " + fromAccNo));
    BigDecimal cur = accounts.getBalance(id);
    if (cur.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds.");
    BigDecimal newBal = cur.subtract(amount);
    accounts.updateBalance(id, newBal);
    txs.add(id, "WITHDRAW", amount.negate(), newBal, note);
  }

  @Override
  public void transfer(String fromAccNo, String toAccNo, BigDecimal amount, String note) {
    if (fromAccNo.equals(toAccNo)) throw new IllegalArgumentException("From and To must differ.");

    Long fromId = accounts.findIdByAccNo(fromAccNo).orElseThrow(() -> new IllegalArgumentException("No such account: " + fromAccNo));
    Long toId   = accounts.findIdByAccNo(toAccNo).orElseThrow(() -> new IllegalArgumentException("No such account: " + toAccNo));

    // Transaction boundary for atomic transfer
    try (Connection c = DbInit.getConnection()) {
      c.setAutoCommit(false);

      BigDecimal fromBal = accounts.getBalance(fromId);
      if (fromBal.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds.");

      BigDecimal toBal = accounts.getBalance(toId);

      BigDecimal newFrom = fromBal.subtract(amount);
      BigDecimal newTo   = toBal.add(amount);

      // Update using direct SQL inside same connection (to guarantee same TX)
      try (PreparedStatement ps1 = c.prepareStatement("UPDATE accounts SET balance=? WHERE id=?");
           PreparedStatement ps2 = c.prepareStatement("UPDATE accounts SET balance=? WHERE id=?")) {

        ps1.setBigDecimal(1, newFrom); ps1.setLong(2, fromId); ps1.executeUpdate();
        ps2.setBigDecimal(1, newTo);   ps2.setLong(2, toId);   ps2.executeUpdate();
      }

      // Log transactions (two rows)
      try (PreparedStatement ps = c.prepareStatement(
              "INSERT INTO transactions(acc_id, kind, amount, balance_after, note) VALUES (?,?,?,?,?)")) {
        ps.setLong(1, fromId); ps.setString(2, "TRANSFER"); ps.setBigDecimal(3, amount.negate()); ps.setBigDecimal(4, newFrom); ps.setString(5, note); ps.executeUpdate();
        ps.setLong(1, toId);   ps.setString(2, "TRANSFER"); ps.setBigDecimal(3, amount);         ps.setBigDecimal(4, newTo);   ps.setString(5, note); ps.executeUpdate();
      }

      c.commit();
    } catch (Exception e) {
      throw new RuntimeException("Transfer failed", e);
    }
  }

  @Override
  public void applyMonthlyInterest() {
    // Example: +2.5% to all SAVINGS/INVESTMENT
    try (Connection c = DbInit.getConnection()) {
      c.setAutoCommit(false);

      // Increase balances (simple demo rule)
      try (PreparedStatement ps = c.prepareStatement(
              "UPDATE accounts SET balance = balance * 1.025 WHERE type IN ('SAVINGS','INVESTMENT')")) {
        ps.executeUpdate();
      }

      // Optionally log a summary per eligible account
      // (Left out for brevity)

      c.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

  }
}
