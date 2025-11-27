package com.bank.app;

import com.bank.util.SceneNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Give SceneNavigator the stage
        SceneNavigator.setStage(primaryStage);

        // Load Login.fxml
        Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/Login.fxml")
        );

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("BankFX System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
