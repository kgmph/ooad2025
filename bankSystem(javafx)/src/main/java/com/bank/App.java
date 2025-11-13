package com.bank;

import com.bank.controller.AccountController;
import com.bank.controller.LoginController;
import com.bank.port.AccountService;
import com.bank.port.AuthService;
import com.bank.repo.AccountRepository;
import com.bank.repo.CustomerRepository;
import com.bank.repo.TransactionRepository;
import com.bank.repo.jdbc.JdbcAccountRepository;
import com.bank.repo.jdbc.JdbcCustomerRepository;
import com.bank.repo.jdbc.JdbcTransactionRepository;
import com.bank.service.AccountServiceImpl;
import com.bank.service.AuthServiceImpl;
import com.bank.util.DbInit;
import com.bank.view.AccountView;
import com.bank.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


import com.bank.domain.Customer;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // 1) Initialize the in-memory H2 DB and execute src/main/resources/db/schema.sql
        DbInit.runSchema();

        // 2) Wire repositories (JDBC)
        CustomerRepository customers = new JdbcCustomerRepository();
        AccountRepository accounts   = new JdbcAccountRepository();
        TransactionRepository txs    = new JdbcTransactionRepository();

        // 3) Wire services (implement your ports)
        AuthService authSvc       = new AuthServiceImpl(customers);
        AccountService acctSvc    = new AccountServiceImpl(accounts, txs);

        // 4) Controllers
        var loginCtl = new LoginController(authSvc);
        var acctCtl  = new AccountController(acctSvc);

        // 5) Views
        // LoginView(LoginController, onShowRegister, onLoginSuccess)
        var loginView = new LoginView(
            loginCtl,
           
                // After successful login, navigate to Accounts screen.
                // For now we show Alice (seeded in schema.sql). If you add a
                // real "current user" session later, pass that here instead.
                Customer placeholder = new Customer("DB-USER", "Alice", "alice@example.com", "+267-71-000-000");
                var accountView = new AccountView(placeholder, acctCtl);
                stage.setScene(new Scene(accountView, 880, 640));
                stage.centerOnScreen();
            }
        );

        stage.setTitle("Banking System");
        stage.setScene(new Scene(loginView, 560, 320));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
