package com.example.chatting_app;

import java.sql.*;

public class DatabaseService {
    private static final String URL = "jdbc:mysql://localhost:3306/chattingdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver successfully loaded!");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC Driver");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean registerUser(String email, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, EncryptionUtil.encrypt(password));
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString("password");
                return EncryptionUtil.decrypt(encryptedPassword).equals(password);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUserStatus(String username, String status) {
        String sql = "UPDATE users SET status = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getChatServerAddress() {
        // Replace with your logic to get the server address from the database or configuration
        return "localhost"; // Example placeholder
    }

    public static int getChatServerPort() {
        // Replace with your logic to get the server port from the database or configuration
        return 12345; // Example placeholder
    }

    public static boolean userExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
