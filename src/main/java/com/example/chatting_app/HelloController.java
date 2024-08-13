package com.example.chatting_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private PasswordField passwd;

    @FXML
    private TextField passwordText;

    @FXML
    private PasswordField passwd1;

    @FXML
    private TextField RePasswdText;

    @FXML
    private TextField username;

    @FXML
    private RadioButton visibililty_btn;
    @FXML
    private RadioButton visibililty_btn1;

    @FXML
    public void check_visible(ActionEvent actionEvent) {
        if (visibililty_btn.isSelected()) {
            passwd.setVisible(false);
            passwordText.setText(passwd.getText());
            passwordText.setVisible(true);
        }else {
            passwordText.setVisible(false);
            passwd.setVisible(true);
        }
    }

    @FXML
    public void check_visible1(ActionEvent actionEvent) {
        if (visibililty_btn1.isSelected()) {
            passwd1.setVisible(false);
            RePasswdText.setText(passwd1.getText());
            RePasswdText.setVisible(true);
        }else {
            RePasswdText.setVisible(false);
            passwd1.setVisible(true);
        }
    }


    @FXML
    void onclick_Signup(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Signup-View.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void onclick_Login (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
