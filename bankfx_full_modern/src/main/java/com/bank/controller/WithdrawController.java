package com.bank.controller;

import com.bank.domain.Account;
import com.bank.repo.BankRepo;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class WithdrawController {

    @FXML private TextField amountField;
    @FXML private Label msg;

    @FXML
    public void withdraw() {

        Account a = SceneNavigator.getSelectedAccount();

        double amt;
        try {
            amt = Double.parseDouble(amountField.getText());
        } catch (Exception e) {
            msg.setText("Invalid amount");
            return;
        }

        if (amt > a.getBalance()) {
            msg.setText("Insufficient funds");
            return;
        }

        a.withdraw(amt);
        BankRepo.updateBalance(a.getId(), a.getBalance());

        BankRepo.addTx(
                "TX" + System.nanoTime(),
                a.getId(),
                amt,
                "WITHDRAW",
                LocalDateTime.now().toString()
        );

        msg.setText("Withdrawal successful!");
    }

    @FXML
    public void back() {
        SceneNavigator.goToAccountDashboard();
    }
}
