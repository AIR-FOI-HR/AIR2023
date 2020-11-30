package com.example.digitalnaribarnica.recycleviewer;

import androidx.annotation.NonNull;

public class OffersData {
    private String name;
    private String description;
    private String imageurl;
    private String price;
    private String fishClass;
    private String imageurltrophey;
    private String idKorisnika;

    public String getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(String idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public OffersData(String name, String description, String imageurl, String price, String fishClass, String imageurltrophey) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
        this.price = price;
        this.fishClass = fishClass;
        this.imageurltrophey = imageurltrophey;
    }

    public OffersData(String name, String description, String imageurl, String price, String fishClass, String imageurltrophey, String idKorisnika) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
        this.price = price;
        this.fishClass = fishClass;
        this.imageurltrophey = imageurltrophey;
        this.idKorisnika = idKorisnika;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFishClass() {
        return fishClass;
    }

    public void setFishClass(String fishClass) {
        this.fishClass = fishClass;
    }

    public String getImageurltrophey() {
        return imageurltrophey;
    }

    public void setImageurltrophey(String imageurltrophey) {
        this.imageurltrophey = imageurltrophey;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffersData(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public OffersData(String name, String description, String imageurl) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}