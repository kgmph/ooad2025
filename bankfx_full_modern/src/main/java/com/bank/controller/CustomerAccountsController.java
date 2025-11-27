package com.bank.controller;

import com.bank.domain.Account;
import com.bank.domain.Customer;
import com.bank.repo.BankRepo;
import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.List;

public class CustomerAccountsController {

    @FXML private VBox accountList;

    @FXML
    public void initialize() {

        Customer c = SceneNavigator.getLoggedCustomer();
        List<Account> accounts = BankRepo.listCustomerAccounts(c.getUsername());

        accountList.getChildren().clear();

        for (Account a : accounts) {

            Button b = new Button(a.getType() + " (" + a.getId() + ")");
            b.setPrefWidth(300);

            b.setOnAction(e -> {
                SceneNavigator.setSelectedAccount(a);
                SceneNavigator.goToAccountDashboard();
            });

            accountList.getChildren().add(b);
        }
    }

    @FXML
    public void back() {
        SceneNavigator.goToCustomerDashboard();
    }
}

