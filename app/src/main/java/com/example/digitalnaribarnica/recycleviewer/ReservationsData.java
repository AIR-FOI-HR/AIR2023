package com.example.digitalnaribarnica.recycleviewer;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class ReservationsData {
    private String reservationID;
    private String offerID;
    private Timestamp date;
    private String price;
    private String smallFish;
    private String mediumFish;
    private String largeFish;
    private String customerID;
    private String status;
    private String ratedStatus;

    public ReservationsData(String offerID, Timestamp date, String price, String smallFish, String mediumFish, String largeFish, String customerID, String status) {
        this.offerID=offerID;
        this.date=date;
        this.price=price;
        this.smallFish=smallFish;
        this.mediumFish=mediumFish;
        this.largeFish=largeFish;
        this.customerID = customerID;
        this.status=status;

    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSmallFish() {
        return smallFish;
    }

    public void setSmallFish(String smallFish) {
        this.smallFish = smallFish;
    }

    public String getMediumfish() {
        return mediumFish;
    }

    public void setMediumfish(String mediumfish) {
        this.mediumFish = mediumfish;
    }

    public String getLargeFish() {
        return largeFish;
    }

    public void setLargeFish(String largeFish) {
        this.largeFish = largeFish;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRatedStatus() {
        return ratedStatus;
    }

    public void setRatedStatus(String ratedStatus) {
        this.ratedStatus = ratedStatus;
    }
/*
    public ReservationsData(String reservationTitle, String location) {
        this.reservationTitle = reservationTitle;
        this.location = location;
    }

    public ReservationsData(String reservationTitle, String location, String reservationImage) {
        this.reservationTitle = reservationTitle;
        this.location = location;
        this.reservationImage = reservationImage;
    }
*/

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
