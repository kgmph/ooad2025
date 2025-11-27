package com.bank.controller;

import com.bank.repo.CustomerRepo;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField fullnameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Label msg;

    @FXML
    public void register() {

        String full = fullnameField.getText().trim();
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();
        String confirm = confirmField.getText().trim();

        if (full.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            msg.setText("All fields are required");
            return;
        }

        if (!pass.equals(confirm)) {
            msg.setText("Passwords do not match");
            return;
        }

        if (CustomerRepo.exists(user)) {
            msg.setText("Username already exists");
            return;
        }

        CustomerRepo.createCustomer(user, pass, full);
        msg.setText("Registration successful! Return to login.");

        SceneNavigator.goToLogin();
    }

    @FXML
    public void backToLogin() {
        SceneNavigator.goToLogin();
    }
}
