package com.example.database;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class Messages {
    private String sender;
    private String receiver;
    private String message;
    private Timestamp dateTimeMessage;

    public Messages(String sender, String receiver, String message, Timestamp dateTimeMessage) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.dateTimeMessage = dateTimeMessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateTimeMessage() {
        return dateTimeMessage;
    }

    public void setDateTimeMessage(Timestamp dateTimeMessage) {
        this.dateTimeMessage = dateTimeMessage;
    }
}
