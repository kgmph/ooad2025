package com.bank.controller;

import com.bank.domain.Account;
import com.bank.repo.BankRepo;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;

public class DepositController {

    @FXML private TextField amountField;
    @FXML private Label msg;

    @FXML
    public void deposit() {

        Account a = SceneNavigator.getSelectedAccount();

        double amt;
        try {
            amt = Double.parseDouble(amountField.getText());
        } catch (Exception e) {
            msg.setText("Invalid amount");
            return;
        }

        a.deposit(amt);
        BankRepo.updateBalance(a.getId(), a.getBalance());

        BankRepo.addTx(
                "TX" + System.nanoTime(),
                a.getId(),
                amt,
                "DEPOSIT",
                LocalDateTime.now().toString()
        );

        msg.setText("Deposit successful!");
    }

    @FXML
    public void back() {
        SceneNavigator.goToAccountDashboard();
    }
}


