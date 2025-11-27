package com.bank.controller;

import com.bank.domain.Customer;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomerDashboardController {

    @FXML private Label welcome;

    @FXML
    public void initialize() {
        Customer c = SceneNavigator.getLoggedCustomer();
        welcome.setText("Welcome, " + c.getFullname());
    }

    @FXML
    public void createAccount() {
        SceneNavigator.goToCreateAccount();
    }

    @FXML
    public void viewAccounts() {
        SceneNavigator.goToCustomerAccounts();
    }

    @FXML
    public void logout() {
        SceneNavigator.goToLogin();
    }
}

