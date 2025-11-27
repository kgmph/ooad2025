package com.bank.repo;

import com.bank.db.Db;
import com.bank.domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerRepo {

    // CREATE CUSTOMER (register)
    public static void createCustomer(String username, String password, String fullname) {
        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "INSERT INTO customers(username, password, fullname) VALUES(?,?,?)"
             )) {

            s.setString(1, username);
            s.setString(2, password);
            s.setString(3, fullname);
            s.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // CHECK IF USER EXISTS
    public static boolean exists(String username) {
        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "SELECT username FROM customers WHERE username=?"
             )) {

            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // LOGIN VALIDATION
    public static Customer login(String username, String password) {
        try (Connection c = Db.connect();
             PreparedStatement s = c.prepareStatement(
                     "SELECT * FROM customers WHERE username=? AND password=?"
             )) {

            s.setString(1, username);
            s.setString(2, password);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname")
                );
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
