Here’s the `README.md` file you requested:

---

# **Java Chatting Application**

## **Project Overview**

The Java Chatting Application is a secure, real-time messaging platform developed using JavaFX for the client interface and a robust client-server architecture. It allows users to register, log in, and communicate in real-time with other users while ensuring that all sensitive data, such as passwords and messages, are encrypted for security.

## **Features**

- **User Registration & Authentication:** Secure user registration and login using AES encryption for passwords.
- **Real-Time Messaging:** Instant messaging with real-time updates.
- **User Status Management:** Real-time updates of user statuses (online, offline, busy).
- **Secure Communication:** AES encryption to secure passwords and chat messages.
- **Responsive UI:** User-friendly and responsive interface designed with JavaFX.

## **Installation & Setup**

### **Prerequisites**

- **Java Development Kit (JDK) 8 or higher**
- **JavaFX SDK**
- **MySQL Database**

### **Step 1: Clone the Repository**

```bash
git clone https://github.com/Youssef-Elbanna/chat-app.git
cd chat-app
```

### **Step 2: Set Up the Database**

1. Install MySQL and create a database for the chat application.
2. Import the SQL schema provided in the `database.sql` file located in the project repository.

```sql
CREATE DATABASE chat_app;
USE chat_app;

-- Create the necessary tables as per the schema
```

3. Update the database connection details in the `DatabaseService.java` file:

```java
private static final String URL = "jdbc:mysql://localhost:3306/chat_app";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### **Step 3: Build & Run the Application**

1. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
2. Build the project to ensure all dependencies are resolved.
3. Run the `ChatServer.java` to start the server.
4. Run the `HelloApplication.java` to launch the client-side application.

### **Step 4: Using the Application**

1. **Sign Up:** Enter a username and password to create a new account.
2. **Login:** Use your credentials to log in to the chat application.
3. **Chat:** Start chatting with other online users.

## **Project Structure**

```
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
```

## **Contributing**

Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Open a pull request.

## **Project Management**

- **Trello Dashboard:** [Chatting Room Project](https://trello.com/invite/b/66b4f968427ee0d136809aa2/ATTI3b25b96d999c770775a6398cee43e5662798F7CA/chatting-room-project)
- **GitHub Repo:** [Java Chatting Application](https://github.com/Youssef-Elbanna/chat-app)

## **Authors**

- **Youssef Elbanna:** [GitHub](https://github.com/Youssef-Elbanna/)
- **Mohammed Badrawy:** [GitHub](https://github.com/mbadrawy1)
- **Youssef Elgazar:** [GitHub](https://github.com/Youssefelgazar)
- **Noureen Elsayed:**

## **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

This file will provide all necessary information for setting up and understanding the project made for Field training 1 | Alamein International University
