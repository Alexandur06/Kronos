package com.example.kronosapp.private_chat;

public class Message {
    private String message = null;
    private String senderId = null;

    public Message(){}

    public Message(String message, String senderId){
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }
}
