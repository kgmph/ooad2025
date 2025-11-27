package com.bank.controller;

import com.bank.util.SceneNavigator;
import javafx.fxml.FXML;

public class AdminDashboardController {

    @FXML
    public void logout() {
        SceneNavigator.goToLogin();
    }
}
