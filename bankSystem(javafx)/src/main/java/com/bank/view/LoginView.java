package com.bank.view;
import com.bank.controller.LoginController; import com.bank.domain.Customer;
import javafx.geometry.*; import javafx.scene.control.*; import javafx.scene.layout.*; import java.util.Optional; import java.util.function.Consumer;
public class LoginView extends VBox {
    public LoginView(LoginController controller, Runnable onShowRegister, Consumer<Customer> onSuccess){
        setSpacing(18); setPadding(new Insets(28)); setAlignment(Pos.CENTER);
        Label title=new Label("Banking System — Login"); title.setStyle("-fx-font-size:22px;-fx-font-weight:bold;");
        TextField username=new TextField(); username.setPromptText("Username (e.g., alice)");
        PasswordField password=new PasswordField(); password.setPromptText("Password");
        Button login=new Button("Login"); Button register=new Button("Register"); HBox btns=new HBox(10, login, register);
        Label flash=new Label(); flash.setStyle("-fx-text-fill:red;");
        GridPane form=new GridPane(); form.setHgap(10); form.setVgap(10);
        form.addRow(0, new Label("Username:"), username); form.addRow(1, new Label("Password:"), password); form.add(btns,1,2);
        login.setOnAction(e->{ Optional<Customer> m=controller.login(username.getText(), password.getText());
            if(m.isPresent()){ flash.setText(""); onSuccess.accept(m.get()); } else { flash.setText("Invalid credentials. Try 'alice'/'pass123' or Register."); } });
        register.setOnAction(e-> onShowRegister.run());
        getChildren().addAll(title, form, flash);
    }
}
