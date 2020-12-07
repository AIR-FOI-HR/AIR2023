package com.example.database;


import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class User implements Serializable {
    //@DocumentId
    String userID ="";
    String fullName ="";
    String email ="";
    String phone ="";
    String adress="";
    String trophyImageUrl="";
    Boolean blokiran=false;

    public Boolean getBlokiran() {
        return blokiran;
    }

    public void setBlokiran(Boolean blokiran) {
        this.blokiran = blokiran;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    String photo="";
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password="";

    public String getTrophyImageUrl() {
        return trophyImageUrl;
    }

    public void setTrophyImageUrl(String trophyImageUrl) {
        this.trophyImageUrl = trophyImageUrl;
    }

    public User() {
    }


    public User(String userID, String fullName, String email, String phone, String adress, String photo, String password, Boolean blokiran) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.photo = photo;
        this.password = password;
        this.blokiran=blokiran;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
