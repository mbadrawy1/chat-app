package com.example.demo12;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Signup Page");

        // Create a GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Name Label and TextField
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        // Email Label and TextField
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 1);

        // Username Label and TextField
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 2);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 2);

        // Password Label and PasswordField
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 3);

        // Signup Button
        Button signupButton = new Button("Sign Up");
        GridPane.setConstraints(signupButton, 1, 4);

        // Add all elements to the grid
        grid.getChildren().addAll(nameLabel, nameInput, emailLabel, emailInput, usernameLabel, usernameInput, passwordLabel, passwordInput, signupButton);

        // Set the scene with the grid
        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
