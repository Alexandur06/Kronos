package com.example.kronosapp.group_chat;

public class Group {
    private String name;
    private String email;
    private String uid;

    public Group(){}

    public Group(String name, String email, String uid){
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getUid(){
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
