package com.example.chatting_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ChatServer {
    private static final int PORT = 12345;
    private static final Logger logger = Logger.getLogger(ChatServer.class.getName());
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static Set<String> usernames = new HashSet<>();

    public static void main(String[] args) {
        logger.info("Chat Server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            logger.severe("Error starting server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println(username);
                    username = in.readLine();
                    if (username == null) {
                        return;
                    }

                    synchronized (usernames) {
                        if (!usernames.contains(username)) {
                            usernames.add(username);
                            break;
                        }
                    }
                }

                synchronized (clientWriters) {
                    clientWriters.add(out);
                    broadcastUserList();
                }

                String message;
                while ((message = in.readLine()) != null) {
                    logger.info("Received: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                logger.severe("Error in client handler: " + e.getMessage());
            } finally {

                if (username != null) {
                    synchronized (usernames) {
                        usernames.remove(username);
                    }
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                        broadcastUserList();
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.severe("Error closing socket: " + e.getMessage());
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }

        private void broadcastUserList() {
            synchronized (clientWriters) {
                String userListMessage = "USERLIST:" + String.join(",", usernames);
                for (PrintWriter writer : clientWriters) {
                    writer.println(userListMessage);
                }
            }
        }
    }
}
