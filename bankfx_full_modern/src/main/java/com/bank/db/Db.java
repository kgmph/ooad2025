package com.bank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Db {

    private static final String URL = "jdbc:h2:./bankdb;AUTO_SERVER=TRUE";

    static {
        try (Connection c = DriverManager.getConnection(URL);
             Statement s = c.createStatement()) {

            // Customers table
            s.execute("""
                CREATE TABLE IF NOT EXISTS customers(
                    username VARCHAR(50) PRIMARY KEY,
                    password VARCHAR(100),
                    fullname VARCHAR(100)
                );
            """);

            // Accounts table
            s.execute("""
                CREATE TABLE IF NOT EXISTS accounts(
                    id VARCHAR(50) PRIMARY KEY,
                    customer VARCHAR(50),
                    type VARCHAR(20),
                    balance DOUBLE,
                    employer VARCHAR(100)
                );
            """);

            // Transactions table
            s.execute("""
                CREATE TABLE IF NOT EXISTS tx(
                    id VARCHAR(50) PRIMARY KEY,
                    accId VARCHAR(50),
                    amount DOUBLE,
                    type VARCHAR(20),
                    ts VARCHAR(50)
                );
            """);

        } catch (Exception e) {
            throw new RuntimeException("DB INIT FAILED", e);
        }
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
