package com.bank.controller;

import com.bank.domain.Customer;
import com.bank.repo.CustomerRepo;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label msg;

    @FXML
    public void login() {

        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        if (user.equals("admin") && pass.equals("admin")) {
            SceneNavigator.goToAdminDashboard();
            return;
        }

        Customer c = CustomerRepo.login(user, pass);

        if (c == null) {
            msg.setText("Invalid username or password");
            return;
        }

        SceneNavigator.setLoggedCustomer(c);
        SceneNavigator.goToCustomerDashboard();
    }

    @FXML
    public void goRegister() {
        SceneNavigator.goToRegister();
    }
}
