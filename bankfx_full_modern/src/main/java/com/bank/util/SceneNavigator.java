package com.bank.util;

import com.bank.domain.Account;
import com.bank.domain.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator {

    private static Stage stage;

    private static Customer loggedCustomer = null;
    private static Account selectedAccount = null;

    public static void setStage(Stage s) {
        stage = s;
    }

    // Store logged in customer
    public static void setLoggedCustomer(Customer c) {
        loggedCustomer = c;
    }

    public static Customer getLoggedCustomer() {
        return loggedCustomer;
    }

    // Store selected account
    public static void setSelectedAccount(Account a) {
        selectedAccount = a;
    }

    public static Account getSelectedAccount() {
        return selectedAccount;
    }

    // Generic loader
    private static void load(String path) {
        try {
            Parent root = FXMLLoader.load(SceneNavigator.class.getResource(path));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load: " + path, e);
        }
    }

    // ======================
    //      NAVIGATION
    // ======================

    public static void goToLogin() {
        loggedCustomer = null;
        selectedAccount = null;
        load("/fxml/Login.fxml");
    }

    public static void goToRegister() {
        load("/fxml/Register.fxml");
    }

    public static void goToAdminDashboard() {
        load("/fxml/AdminDashboard.fxml");
    }

    public static void goToCustomerDashboard() {
        load("/fxml/CustomerDashboard.fxml");
    }

    public static void goToCustomerAccounts() {
        load("/fxml/CustomerAccounts.fxml");
    }

    public static void goToAccountDashboard() {
        load("/fxml/CustomerAccountDashboard.fxml");
    }

    public static void goToCreateAccount() {
        load("/fxml/CreateAccount.fxml");
    }

    public static void goToDeposit() {
        load("/fxml/Deposit.fxml");
    }

    public static void goToWithdraw() {
        load("/fxml/Withdraw.fxml");
    }

    public static void goToTransactions() {
        load("/fxml/Transaction.fxml");
    }
}
