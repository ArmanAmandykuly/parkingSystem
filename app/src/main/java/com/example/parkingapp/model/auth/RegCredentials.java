package com.example.parkingapp.model.auth;

public class RegCredentials {
    private String name;

    private String password;

    private String contacts;


    public RegCredentials(String name, String password, String contacts) {
        this.name = name;
        this.password = password;
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}