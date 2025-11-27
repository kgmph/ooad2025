package com.bank.repo;

import com.bank.db.Db;
import com.bank.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankRepo {

    // CREATE ACCOUNT
    public static void createAccount(String id, String customer, String type, double balance, String employer) {
        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "INSERT INTO accounts(id, customer, type, balance, employer) VALUES(?,?,?,?,?)"
             )) {

            s.setString(1, id);
            s.setString(2, customer);
            s.setString(3, type);
            s.setDouble(4, balance);
            s.setString(5, employer);
            s.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // GET ACCOUNT BY ID → returns correct subclass
    public static Account findAccount(String id) {
        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "SELECT * FROM accounts WHERE id=?"
             )) {

            s.setString(1, id);
            ResultSet rs = s.executeQuery();

            if (!rs.next()) return null;

            String accId = rs.getString("id");
            String customer = rs.getString("customer");
            String type = rs.getString("type");
            double balance = rs.getDouble("balance");
            String employer = rs.getString("employer");

            return switch (type.toLowerCase()) {
                case "savings"     -> new SavingsAccount(accId, customer, balance);
                case "investment"  -> new InvestmentAccount(accId, customer, balance);
                case "cheque"      -> new ChequeAccount(accId, customer, balance, employer);
                default -> throw new RuntimeException("Unknown account type: " + type);
            };

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // LIST ALL ACCOUNTS FOR A CUSTOMER
    public static List<Account> listCustomerAccounts(String username) {
        List<Account> list = new ArrayList<>();

        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "SELECT * FROM accounts WHERE customer=?"
             )) {

            s.setString(1, username);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {

                String id = rs.getString("id");
                String customer = rs.getString("customer");
                String type = rs.getString("type");
                double balance = rs.getDouble("balance");
                String employer = rs.getString("employer");

                Account acc = switch (type.toLowerCase()) {
                    case "savings"     -> new SavingsAccount(id, customer, balance);
                    case "investment"  -> new InvestmentAccount(id, customer, balance);
                    case "cheque"      -> new ChequeAccount(id, customer, balance, employer);
                    default -> null;
                };

                if (acc != null)
                    list.add(acc);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // UPDATE BALANCE
    public static void updateBalance(String id, double newBalance) {
        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "UPDATE accounts SET balance=? WHERE id=?"
             )) {

            s.setDouble(1, newBalance);
            s.setString(2, id);
            s.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ADD TRANSACTION
    public static void addTx(String id, String accId, double amount, String type, String ts) {
        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "INSERT INTO tx(id, accId, amount, type, ts) VALUES(?,?,?,?,?)"
             )) {

            s.setString(1, id);
            s.setString(2, accId);
            s.setDouble(3, amount);
            s.setString(4, type);
            s.setString(5, ts);
            s.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // LIST ALL TRANSACTIONS FOR ONE ACCOUNT
    public static List<Transaction> listAccountTx(String accId) {
        List<Transaction> list = new ArrayList<>();

        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "SELECT * FROM tx WHERE accId=? ORDER BY ts DESC"
             )) {

            s.setString(1, accId);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                list.add(new Transaction(
                        rs.getString("id"),
                        rs.getString("accId"),
                        rs.getDouble("amount"),
                        rs.getString("type"),
                        rs.getString("ts")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}

