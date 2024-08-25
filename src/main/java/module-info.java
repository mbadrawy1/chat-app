module com.example.chatting_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens com.example.chatting_app to javafx.fxml;
    exports com.example.chatting_app;
}