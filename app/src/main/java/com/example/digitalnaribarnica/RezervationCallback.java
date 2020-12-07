package com.example.digitalnaribarnica;

import com.example.database.Location;
import com.example.database.Rezervation;

import java.util.ArrayList;

public interface RezervationCallback {
    void onCallback(ArrayList<Rezervation> rezervations);
}
