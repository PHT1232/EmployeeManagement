package com.example.crm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/home.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Employee CRM - Dashboard");
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(850);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}