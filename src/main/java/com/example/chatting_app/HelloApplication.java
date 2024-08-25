package com.example.chatting_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainScreen-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Welcome to the Chatting App");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load the FXML file.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
