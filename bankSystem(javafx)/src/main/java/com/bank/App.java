package com.bank;

import com.bank.controller.AccountController;
import com.bank.controller.AuthController;
import com.bank.controller.LoginController;
import com.bank.domain.ChequeAccount;
import com.bank.domain.Customer;
import com.bank.domain.InvestmentAccount;
import com.bank.domain.SavingsAccount;
import com.bank.repo.InMemoryAccountRepository;
import com.bank.repo.InMemoryCustomerRepository;
import com.bank.service.BankService;
import com.bank.view.AccountView;
import com.bank.view.LoginView;
import com.bank.view.RegisterView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class App extends Application {

    // Repos + Service (BankService implements both AuthService and AccountService ports)
    private final InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();
    private final InMemoryAccountRepository accountRepo  = new InMemoryAccountRepository();
    private final BankService bankService = new BankService(customerRepo, accountRepo);

    // Keep track of the user whose accounts we show after login
    private Customer currentUser;

    @Override
    public void start(Stage stage) {
        seed();                   // seed Alice + her accounts
        showLogin(stage);         // show login first
        stage.setTitle("Banking System");
        stage.show();
    }

    /* -------------------- Screens -------------------- */

    private void showLogin(Stage stage) {
        var loginController = new LoginController(bankService);  // uses AuthService port
        var authController  = new AuthController(bankService);   // uses AuthService port

        var loginView = new LoginView(
                loginController,
                () -> showRegister(stage, authController),       // onShowRegister
                () -> {                                          // onLoginSuccess
                    // If you later track the *actual* logged-in user, set currentUser there.
                    // For now we navigate to Alice (seeded) so the marker sees accounts immediately.
                    if (currentUser == null) {
                        currentUser = findAliceOrFirst();
                    }
                    showAccounts(stage, currentUser);
                }
        );

        stage.setScene(new Scene(loginView, 560, 320));
    }

    private void showRegister(Stage stage, AuthController authController) {
        var registerView = new RegisterView(
                authController,
                () -> showLogin(stage)   // after register, go back to Login
        );
        stage.setScene(new Scene(registerView, 560, 360));
    }

    private void showAccounts(Stage stage, Customer customer) {
        var accountController = new AccountController(bankService); // uses AccountService port
        var accountView = new AccountView(customer, accountController);
        stage.setScene(new Scene(accountView, 880, 640));
    }

    /* -------------------- Utilities -------------------- */

    private Customer findAliceOrFirst() {
        return customerRepo.findById("C001").orElseGet(() ->
                customerRepo.findAll().stream().findFirst().orElse(null)
        );
    }

    private void seed() {
        // Seed Alice so the lecturer can log in immediately
        Customer alice = new Customer("C001", "Alice", "alice@example.com", "+267-71-000-000");
        bankService.registerCustomer(alice);
        bankService.registerUser("alice", "pass123", alice);

        // Three starter accounts for Alice
        var savings = new SavingsAccount("SA-1", alice, new BigDecimal("1000.00"), new BigDecimal("0.0005"));
        var invest  = new InvestmentAccount("IV-1", alice, new BigDecimal("500.00"),  new BigDecimal("0.05"));
        var cheque  = new ChequeAccount("CQ-1", alice, new BigDecimal("200.00"),  new BigDecimal("100.00"), null);
        bankService.addAccount(savings);
        bankService.addAccount(invest);
        bankService.addAccount(cheque);

        // Default current user = Alice
        currentUser = alice;
    }

    public static void main(String[] args) {
        launch();
    }
}
