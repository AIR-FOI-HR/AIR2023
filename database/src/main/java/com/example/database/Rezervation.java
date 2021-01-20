package com.example.database;

import com.google.firebase.Timestamp;

public class Rezervation {
    private String reservationID;
    private String offerID;
    private Timestamp date;
    private String price;
    private String smallFish;
    private String mediumFish;
    private String largeFish;
    private String customerID;
    private String status;
    private String ratedStatus = "Neocijenjeno";

    public Rezervation(String offerID, Timestamp date, String price, String smallFish, String mediumFish, String largeFish, String customerID, String status) {
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

    public String getMediumFish() {
        return mediumFish;
    }

    public void setMediumFish(String mediumFish) {
        this.mediumFish = mediumFish;
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
}
