package com.example.digitalnaribarnica;

import com.example.database.Fish;
import com.example.database.Location;

import java.util.ArrayList;

public interface LocationCallback {
    void onCallback(ArrayList<Location> locations);
}
