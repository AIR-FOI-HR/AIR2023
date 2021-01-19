package com.example.digitalnaribarnica;

import com.example.database.Review;

import java.util.ArrayList;

public interface ReviewCallback {
    void onCallback(ArrayList<Review> reviews);
}