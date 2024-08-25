package com.example.chatting_app;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class ChatClient implements Runnable {

    private Socket socket;
    private String userName;
    private BufferedReader in;
    private PrintWriter out;
    private ListView<HBox> chatArea;

    public ChatClient(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                String finalServerMessage = serverMessage;
                Platform.runLater(() -> {
                    if (chatArea != null) {
                        addMessageToChat(finalServerMessage);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void addMessageToChat(String message) {
        HBox messageBox = new HBox();
        Text text = new Text(message);
        messageBox.getChildren().add(text);
        chatArea.getItems().add(messageBox);
    }

    public void disconnect() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
