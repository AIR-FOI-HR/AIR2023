package com.example.digitalnaribarnica.recycleviewer;

import androidx.annotation.NonNull;

public class RequestsData {

    private String requestTitle;
    private String location;
    private String requestImage;
    private String price;
    private String grade;
    private String requestTrophyImage;

    public RequestsData(String requestTitle,String location,String requestImage,String price,String grade,String requestTrophyImage) {
        this.requestTitle=requestTitle;
        this.location=location;
        this.requestImage=requestImage;
        this.price=price;
        this.grade=grade;
        this.requestTrophyImage=requestTrophyImage;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequestImage() {
        return requestImage;
    }

    public void setReservationImage(String requestImage) {
        this.requestImage = requestImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRequestTrophyImage() {
        return requestTrophyImage;
    }

    public void setRequestTrophyImage(String requestTrophyImage) {
        this.requestTrophyImage = requestTrophyImage;
    }

    public RequestsData(String requestTitle, String location) {
        this.requestTitle = requestTitle;
        this.location = location;
    }

    public RequestsData(String requestTitle, String location, String requestImage) {
        this.requestTitle = requestTitle;
        this.location = location;
        this.requestImage = requestImage;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }


}
