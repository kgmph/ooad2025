package com.bank.controller;

import com.bank.repo.BankRepo;
import com.bank.domain.Customer;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.UUID;

public class CreateAccountController {

    @FXML private TextField balanceField;
    @FXML private ComboBox<String> typeBox;
    @FXML private TextField employerField;
    @FXML private Label msg;

    @FXML
    public void initialize() {
        typeBox.getItems().addAll("Savings", "Investment", "Cheque");
    }

    @FXML
    public void createAccount() {

        Customer c = SceneNavigator.getLoggedCustomer();
        String type = typeBox.getValue();
        String employer = employerField.getText().trim();

        if (type == null) {
            msg.setText("Select account type");
            return;
        }

        double bal;
        try {
            bal = Double.parseDouble(balanceField.getText());
        } catch (Exception e) {
            msg.setText("Invalid balance amount");
            return;
        }

        if (type.equals("Investment") && bal < 500) {
            msg.setText("Investment accounts require minimum BWP 500");
            return;
        }

        if (type.equals("Cheque") && employer.isEmpty()) {
            msg.setText("Employer required for Cheque account");
            return;
        }

        String id = type.substring(0,1).toUpperCase() + UUID.randomUUID().toString().substring(0,4);

        BankRepo.createAccount(id, c.getUsername(), type, bal, employer);

        msg.setText("Account created successfully!");

        SceneNavigator.goToCustomerDashboard();
    }

    @FXML
    public void back() {
        SceneNavigator.goToCustomerDashboard();
    }
}
