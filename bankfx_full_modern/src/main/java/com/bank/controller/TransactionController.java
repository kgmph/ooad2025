package com.bank.controller;

import com.bank.domain.Transaction;
import com.bank.repo.BankRepo;
import com.bank.domain.Account;
import com.bank.util.SceneNavigator;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TransactionController {

    @FXML private TableView<Transaction> table;
    @FXML private TableColumn<Transaction, String> colId;
    @FXML private TableColumn<Transaction, String> colAcc;
    @FXML private TableColumn<Transaction, Double> colAmount;
    @FXML private TableColumn<Transaction, String> colType;
    @FXML private TableColumn<Transaction, String> colTs;

    @FXML
    public void initialize() {
        Account a = SceneNavigator.getSelectedAccount();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAcc.setCellValueFactory(new PropertyValueFactory<>("accId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTs.setCellValueFactory(new PropertyValueFactory<>("ts"));

        List<Transaction> tx = BankRepo.listAccountTx(a.getId());
        table.getItems().setAll(tx);
    }

    @FXML
    public void back() { SceneNavigator.goToAccountDashboard(); }
}
