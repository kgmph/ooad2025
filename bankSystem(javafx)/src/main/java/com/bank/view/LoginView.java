package com.bank.view;

import com.bank.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LoginView extends VBox {

    private final LoginController controller;
    private final Runnable onShowRegister;
    private final Runnable onLoginSuccess;

    // UI fields
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Label flash = new Label();

    public LoginView(LoginController controller,
                     Runnable onShowRegister,
                     Runnable onLoginSuccess) {
        this.controller = controller;
        this.onShowRegister = onShowRegister;
        this.onLoginSuccess = onLoginSuccess;

        buildUI();
    }

    private void buildUI() {
        setSpacing(18);
        setPadding(new Insets(28));
        setAlignment(Pos.CENTER);

        Label title = new Label("Banking System — Login");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        usernameField.setPromptText("Username (e.g., alice)");
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");
        HBox buttons = new HBox(10, loginBtn, registerBtn);

        flash.setStyle("-fx-text-fill: #a00;");

        // Form layout
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Username:"), usernameField);
        form.addRow(1, new Label("Password:"), passwordField);
        form.add(buttons, 1, 2);

        // Handlers
        loginBtn.setOnAction(e -> handleLogin());
        registerBtn.setOnAction(e -> {
            if (onShowRegister != null) onShowRegister.run();
        });

        getChildren().addAll(title, form, flash);
    }

    private void handleLogin() {
        try {
            boolean ok = controller.login(usernameField.getText(), passwordField.getText());
            if (ok) {
                flash.setText("");
                if (onLoginSuccess != null) onLoginSuccess.run();
            } else {
                showError("Invalid credentials.");
            }
        } catch (IllegalArgumentException ex) {
            // From controller validation (e.g., empty/too-short user/pass)
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Unexpected error: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        flash.setText(message); // inline message
        
    }
}
