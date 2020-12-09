package com.example.digitalnaribarnica;

import com.example.database.Location;
import com.example.database.Rezervation;
import com.example.digitalnaribarnica.recycleviewer.ReservationsData;

import java.util.ArrayList;

public interface RezervationCallback {
    void onCallback(ArrayList<ReservationsData> rezervations);
}
