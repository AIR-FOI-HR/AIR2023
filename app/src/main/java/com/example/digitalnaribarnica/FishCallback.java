package com.example.digitalnaribarnica;

import com.example.database.Fish;
import com.example.database.User;

import java.util.ArrayList;

public interface FishCallback {
    void onCallback(ArrayList<Fish> fishes);
}
