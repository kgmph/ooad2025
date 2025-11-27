package com.bank.controller;

import com.bank.domain.Account;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomerAccountDashboardController {

    @FXML private Label accountId;
    @FXML private Label accountType;
    @FXML private Label accountBalance;

    @FXML
    public void initialize() {
        Account a = SceneNavigator.getSelectedAccount();

        accountId.setText(a.getId());
        accountType.setText(a.getType());
        accountBalance.setText("Balance: BWP " + a.getBalance());
    }

    @FXML
    public void deposit() { SceneNavigator.goToDeposit(); }

    @FXML
    public void withdraw() { SceneNavigator.goToWithdraw(); }

    @FXML
    public void transactions() { SceneNavigator.goToTransactions(); }

    @FXML
    public void back() { SceneNavigator.goToCustomerAccounts(); }
}
