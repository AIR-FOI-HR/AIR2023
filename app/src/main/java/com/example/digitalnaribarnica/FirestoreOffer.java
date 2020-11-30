package com.example.digitalnaribarnica;

import com.example.database.User;
import com.example.digitalnaribarnica.recycleviewer.OffersData;

import java.util.ArrayList;
import java.util.List;

public interface FirestoreOffer {
    void onCallback(ArrayList<OffersData> offersData);
}
