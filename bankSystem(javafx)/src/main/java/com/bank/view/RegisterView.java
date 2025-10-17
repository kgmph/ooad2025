package com.bank.view;
import com.bank.controller.AuthController; import javafx.geometry.*; import javafx.scene.control.*; import javafx.scene.layout.*;
public class RegisterView extends VBox {
    public RegisterView(AuthController controller, Runnable onDone){
        setSpacing(18); setPadding(new Insets(28)); setAlignment(Pos.CENTER);
        Label title=new Label("Create Account"); title.setStyle("-fx-font-size:20px;-fx-font-weight:bold;");
        TextField name=new TextField(); name.setPromptText("Full name");
        TextField username=new TextField(); username.setPromptText("Username");
        PasswordField password=new PasswordField(); password.setPromptText("Password (min 4 chars)");
        TextField email=new TextField(); email.setPromptText("Email (optional)");
        TextField phone=new TextField(); phone.setPromptText("Phone (optional)");
        Button create=new Button("Register"); Button cancel=new Button("Cancel"); Label flash=new Label();
        GridPane form=new GridPane(); form.setHgap(10); form.setVgap(10);
        form.addRow(0,new Label("Name:"),name); form.addRow(1,new Label("Username:"),username); form.addRow(2,new Label("Password:"),password);
        form.addRow(3,new Label("Email:"),email); form.addRow(4,new Label("Phone:"),phone); form.addRow(5,create,cancel);
        create.setOnAction(e->{ boolean ok=controller.register(name.getText(), username.getText(), password.getText(), email.getText(), phone.getText());
            if(ok){ flash.setStyle("-fx-text-fill:#0a0;"); flash.setText("Registered! Go back and log in."); onDone.run(); }
            else { flash.setStyle("-fx-text-fill:#a00;"); flash.setText("Could not register. Username may be taken or password too short."); } });
        cancel.setOnAction(e-> onDone.run()); getChildren().addAll(title, form, flash);
    }
}
