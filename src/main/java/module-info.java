module com.example.chatting_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatting_app to javafx.fxml;
    exports com.example.chatting_app;
}