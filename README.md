Java Chatting Application
Project Overview
The Java Chatting Application is a secure, real-time messaging platform built using JavaFX for the client-side interface and a client-server architecture for backend processing. The application allows users to register, log in, and chat with other users in real-time while ensuring that all sensitive data, such as passwords and messages, are securely encrypted.

Features
User Registration & Authentication: Users can sign up with a unique username and password. Passwords are securely encrypted using AES before storage in the database.
Real-Time Messaging: Users can send and receive messages instantly. The chat interface updates in real-time, displaying messages and user statuses.
User Status Management: The application shows whether users are online, offline, or busy, updating these statuses dynamically as users interact with the application.
Secure Communication: All sensitive data, including passwords and chat messages, are encrypted using AES (Advanced Encryption Standard) to ensure security and privacy.
Responsive UI: The application features a user-friendly interface designed with JavaFX, making it intuitive and easy to navigate.
Installation & Setup
Prerequisites
Java Development Kit (JDK) 8 or higher
JavaFX SDK
MySQL Database
Step 1: Clone the Repository
bash
Copy code
git clone https://github.com/Youssef-Elbanna/chat-app.git
cd chat-app
Step 2: Set Up the Database
Install MySQL and create a database for the chat application.
Import the SQL schema provided in the database.sql file located in the project repository.
sql
Copy code
CREATE DATABASE chat_app;
USE chat_app;

-- Create the necessary tables as per the schema
Update the database connection details in the DatabaseService.java file:
java
Copy code
private static final String URL = "jdbc:mysql://localhost:3306/chat_app";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
Step 3: Build & Run the Application
Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
Build the project to ensure all dependencies are resolved.
Run the ChatServer.java to start the server.
Run the HelloApplication.java to launch the client-side application.
Step 4: Using the Application
Sign Up: Enter a username and password to create a new account.
Login: Use your credentials to log in to the chat application.
Chat: Start chatting with other online users.
Project Structure
css
Copy code
.
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.example.chatting_app
│   │   │   │   ├── ChatClient.java
│   │   │   │   ├── ChatController.java
│   │   │   │   ├── ChatServer.java
│   │   │   │   ├── DatabaseService.java
│   │   │   │   ├── DataSingleton.java
│   │   │   │   ├── EncryptionUtil.java
│   │   │   │   ├── HelloApplication.java
│   │   │   │   ├── HelloController.java
│   │   │   ├── resources
│   │   │   │   ├── Chatting_Screen.fxml
│   │   │   │   ├── MainScreen-view.fxml
│   │   │   │   ├── Signup-View.fxml
├── database.sql
└── README.md
Contributing
Contributions are welcome! Please follow these steps to contribute:

Fork the repository.
Create a new feature branch (git checkout -b feature-name).
Commit your changes (git commit -am 'Add new feature').
Push to the branch (git push origin feature-name).
Open a pull request.
Project Management
Trello Dashboard: Chatting Room Project
GitHub Repo: Java Chatting Application
Authors
Youssef Elbanna: https://github.com/Youssef-Elbanna
Mohammed Badrawy: https://github.com/mbadrawy1
Youssef Elgazzar: https://github.com/Youssefelgazar
Noureen Elsayed
License
This project is licensed under the MIT License - see the LICENSE file for details.
