package com.example.chatting_app;

public class TestEncryption {

    public static void main(String[] args) {
        try {
            String password = "12345";

            // Encrypt
            String encrypted = EncryptionUtil.encrypt(password);
            System.out.println("Encrypted: " + encrypted);

            // Decrypt
            String decrypted = EncryptionUtil.decrypt(encrypted);
            System.out.println("Decrypted: " + decrypted);

            // Check if decrypted password matches
            System.out.println("Passwords match: " + password.equals(decrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
