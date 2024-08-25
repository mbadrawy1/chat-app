package com.example.chatting_app;

public class DataSingletTon {
    private static final DataSingletTon instance = new DataSingletTon();
    private String username;

    protected DataSingletTon() {}

    public static DataSingletTon getInstance() {
        return instance;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
