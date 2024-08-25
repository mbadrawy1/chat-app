package com.example.chatting_app;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

    @FXML
    private ComboBox<String> comboBoxStatus;

    @FXML
    private TextField txtFieldMsg;

    @FXML
    private Button sendButton;

    @FXML
    private ImageView iconLogout;

    @FXML
    private ImageView iconExport;

    @FXML
    private ListView<HBox> listViewChat;

    @FXML
    private ListView<String> contactListView;

    @FXML
    private Label homeLabel;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private String status;
    private String name;

    @FXML
    void initialize() {
        DataSingletTon data = DataSingletTon.getInstance();
        name = data.getUsername();
        username = name.contains("@") ? name.substring(0, name.indexOf('@')) : name;
        homeLabel.setText(username);

        comboBoxStatus.getItems().addAll("Online", "Busy", "Offline", "Do Not Disturb");
        comboBoxStatus.setValue("Online");

        connectToServer();
        startMessageListener();
        changeStatusToOnline();

        sendButton.setOnAction(event -> sendMessage());
        txtFieldMsg.setOnAction(event -> sendMessage());
        iconLogout.setOnMouseClicked(event -> iconLogoutAction());
        comboBoxStatus.setOnAction(event -> changeStatus());

        loadOnlineUsers();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try (Connection connection = DatabaseService.getConnection();
                 PreparedStatement stmt = connection.prepareStatement("SELECT status FROM users WHERE username = ?")) {
                stmt.setString(1, name);
                try (ResultSet rs = stmt.executeQuery()) {
                    status = rs.next() ? rs.getString("status") : "Offline";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Retrieved status: " + status);
            out.println((status != null ? status : comboBoxStatus.getValue()) + ":" + username);
            System.out.println("Sending status to server: " + (status != null ? status : comboBoxStatus.getValue()) + ":" + username);

        } catch (UnknownHostException e) {
            throw new RuntimeException("Unknown host", e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error", e);
        }
    }

    private void startMessageListener() {
        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Received message: " + line);
                    String finalLine = line;
                    Platform.runLater(() -> {
                        if (finalLine.startsWith("USERLIST:")) {
                            updateContactList(finalLine.substring(9));
                        } else if (finalLine.startsWith("USEROFFLINE:")) {
                            handleUserOffline(finalLine.substring(12));
                        } else if (!finalLine.startsWith(username + ":")) {
                            displayMessage(finalLine, false);
                        }
                    });
                }
            } catch (IOException e) {
                if (!socket.isClosed()) e.printStackTrace();
            }
        }).start();
    }

    private void loadOnlineUsers() {
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT username FROM users WHERE status = 'Online'");
             ResultSet resultSet = stmt.executeQuery()) {

            List<String> usernames = new ArrayList<>();
            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }

            Platform.runLater(() -> {
                contactListView.getItems().clear();
                contactListView.getItems().addAll(usernames);
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateContactList(String userList) {
        Platform.runLater(() -> {
            contactListView.getItems().clear();
            for (String user : userList.split(",")) {
                if (!user.startsWith("STATUS:") && !user.equals("SUBMITNAME")) {
                    contactListView.getItems().add(user);
                }
            }
        });
    }

    private void handleUserOffline(String username) {
        Platform.runLater(() -> contactListView.getItems().remove(username));
    }

    @FXML
    private void sendMessage() {
        String message = txtFieldMsg.getText().trim();
        if (!message.isEmpty() && out != null) {
            String formattedMessage = username + ": " + message;
            out.println(formattedMessage);
            displayMessage(formattedMessage, true);
            txtFieldMsg.clear();
        }
    }

    private void displayMessage(String message, boolean isSent) {
        Platform.runLater(() -> {
            if (message == null || message.trim().isEmpty()) return;

            HBox messageBox = new HBox();
            messageBox.setPadding(new Insets(5, 10, 5, 10));
            messageBox.setAlignment(isSent ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
            messageBox.setSpacing(5);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);

            Text timestampText = new Text(timestamp + " ");
            timestampText.setStyle("-fx-font-size: 10px;");

            Text messageText = new Text(message);
            messageText.setFill(isSent ? Color.BLACK : Color.WHITE);

            TextFlow textFlow = new TextFlow(timestampText, messageText);
            textFlow.setStyle("-fx-background-color: " + (isSent ? "DarkCyan" : "grey") + ";"
                    + "-fx-background-radius: 20px;"
                    + "-fx-padding: 10px;"
                    + "-fx-background-insets: 0;");

            double textWidth = textFlow.prefWidth(-1);
            textFlow.setPrefWidth(textWidth + 20);

            messageBox.getChildren().add(textFlow);
            listViewChat.getItems().add(messageBox);
            listViewChat.scrollTo(listViewChat.getItems().size() - 1);
        });
    }

    @FXML
    private void iconLogoutAction() {
        if (out != null) out.println(username + " LOGGED OUT");
        updateStatusInDatabase("Offline");

        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) iconLogout.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void iconExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Chat");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (HBox messageBox : listViewChat.getItems()) {
                    TextFlow textFlow = (TextFlow) messageBox.getChildren().get(0);
                    StringBuilder messageContent = new StringBuilder();
                    for (javafx.scene.Node node : textFlow.getChildren()) {
                        if (node instanceof Text) {
                            messageContent.append(((Text) node).getText());
                        }
                    }
                    writer.write(messageContent.toString() + "\n");
                }
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Could not save chat.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void changeStatus() {
        updateStatusInDatabase(comboBoxStatus.getValue());
    }

    private void updateStatusInDatabase(String status) {
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE users SET status = ? WHERE username = ?")) {
            stmt.setString(1, status);
            stmt.setString(2, name);
            if (stmt.executeUpdate() > 0) {
                System.out.println("Status updated to " + status);
            } else {
                System.out.println("No status updated. Check if the name exists.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update status.");
            e.printStackTrace();
        }
    }

    private void changeStatusToOnline() {
        updateStatusInDatabase("Online");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
