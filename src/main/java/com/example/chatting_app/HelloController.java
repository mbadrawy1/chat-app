package com.example.chatting_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {

    private Stage stage;
    private Parent root;
    public String email;

    @FXML
    private TextField usernameMainScreen;

    @FXML
    private PasswordField PasswordMainScreen;

    @FXML
    private TextField passwordTextMainScreen;

    @FXML
    private RadioButton visibility_btnMainScreen;

    @FXML
    private TextField usernameSignUp;

    @FXML
    private PasswordField passwordSignUp;

    @FXML
    private PasswordField rePasswordSignUP;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField RePasswdText;

    @FXML
    private Button SignUpButton_DB;

    @FXML
    private RadioButton visibility_btn;

    @FXML
    private RadioButton visibility_btn1;

    private DataSingletTon data = DataSingletTon.getInstance();

    @FXML
    public void check_visibleMainScreen(ActionEvent actionEvent) {
        if (visibility_btnMainScreen.isSelected()) {
            PasswordMainScreen.setVisible(false);
            passwordTextMainScreen.setText(PasswordMainScreen.getText());
            passwordTextMainScreen.setVisible(true);
        } else {
            passwordTextMainScreen.setVisible(false);
            PasswordMainScreen.setVisible(true);
        }
    }

    @FXML
    public void check_visible(ActionEvent actionEvent) {
        if (visibility_btn.isSelected()) {
            passwordSignUp.setVisible(false);
            passwordText.setText(passwordSignUp.getText());
            passwordText.setVisible(true);
        } else {
            passwordText.setVisible(false);
            passwordSignUp.setVisible(true);
        }
    }

    @FXML
    public void check_visible1(ActionEvent actionEvent) {
        if (visibility_btn1.isSelected()) {
            rePasswordSignUP.setVisible(false);
            RePasswdText.setText(rePasswordSignUP.getText());
            RePasswdText.setVisible(true);
        } else {
            RePasswdText.setVisible(false);
            rePasswordSignUP.setVisible(true);
        }
    }

    @FXML
    void onclick_Signup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Signup-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("SignUp");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onclick_Login(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScreen-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void SignUp_DB(ActionEvent event) throws IOException {
        email = usernameSignUp.getText();
        String password = passwordSignUp.getText();
        String rePassword = rePasswordSignUP.getText();

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (!password.equals(rePassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match!");
            return;
        }

        String encryptedPassword;
        try {
            encryptedPassword = EncryptionUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Encryption Error", "Error encrypting password.");
            return;
        }

        if (DatabaseService.registerUser(email, encryptedPassword)) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Error registering user.");
        }
    }

    @FXML
    void Login_DB(ActionEvent event) throws IOException {
        data.setUsername(usernameMainScreen.getText());
        String email = usernameMainScreen.getText();
        String enteredPassword = PasswordMainScreen.getText();

        if (email.isEmpty() || enteredPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both email and password.");
            return;
        }

        String encryptedPassword;
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT password FROM users WHERE username = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                encryptedPassword = rs.getString("password");
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Error", "No user found with this email.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error accessing the database.");
            return;
        }

        try {
            String decryptedPassword = EncryptionUtil.decrypt(EncryptionUtil.decrypt(encryptedPassword));
            if (decryptedPassword.equals(enteredPassword)) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + email + "!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Chatting_Screen.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Error", "Incorrect password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Decryption Error", "Error decrypting password.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
