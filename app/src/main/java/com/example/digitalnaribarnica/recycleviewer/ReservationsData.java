package com.example.digitalnaribarnica.recycleviewer;

import androidx.annotation.NonNull;

public class ReservationsData {
    private String reservationTitle;
    private String location;
    private String reservationImage;
    private String price;
    private String grade;
    private String reservationTrophyImage;

    public ReservationsData(String reservationTitle, String location, String reservationImage, String price, String grade, String locationImage) {
        this.reservationTitle = reservationTitle;
        this.location = location;
        this.reservationImage = reservationImage;
        this.price = price;
        this.grade = grade;
        this.reservationTrophyImage = locationImage;
    }

    public String getReservationTitle() {
        return reservationTitle;
    }

    public void setReservationTitle(String reservationTitle) {
        this.reservationTitle = reservationTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReservationImage() {
        return reservationImage;
    }

    public void setReservationImage(String reservationImage) {
        this.reservationImage = reservationImage;
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

    public String getReservationTrophyImage() {
        return reservationTrophyImage;
    }

    public void setReservationTrophyImage(String reservationTrophyImage) {
        this.reservationTrophyImage = reservationTrophyImage;
    }

    public ReservationsData(String reservationTitle, String location) {
        this.reservationTitle = reservationTitle;
        this.location = location;
    }

    public ReservationsData(String reservationTitle, String location, String reservationImage) {
        this.reservationTitle = reservationTitle;
        this.location = location;
        this.reservationImage = reservationImage;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
