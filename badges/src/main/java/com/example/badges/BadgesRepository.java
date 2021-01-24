package com.example.badges;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.FirestoreService;
import com.example.database.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BadgesRepository {
    FirestoreService firestoreService;
    public BadgesRepository() {
        firestoreService=new FirestoreService();
    }
    public void DohvatiSveZnačke(BadgeCallback firestoreCallback){
        firestoreService.getCollection("Badges").addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ime=queryDocumentSnapshots.getDocuments();
                ArrayList<BadgesData> badgesDataArrayList=new ArrayList<>();
                for(DocumentSnapshot d: ime){
                    d.getData();
                    String json= new Gson().toJson(d.getData());
                    BadgesData badgesData=new Gson().fromJson(json,BadgesData.class);
                    badgesDataArrayList.add(badgesData);
                }
                firestoreCallback.onCallback(badgesDataArrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "Ne valja", Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallback(null);
            }
        });
    }

    public void DohvatiZnackuPoNazivu(String naziv,BadgeCallback firestoreCallback){
        firestoreService.getCollectionWithField("Badges","title",naziv).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ime=queryDocumentSnapshots.getDocuments();
                ArrayList<BadgesData> badgesDataArrayList=new ArrayList<>();
                for(DocumentSnapshot d: ime){
                    d.getData();
                    String json= new Gson().toJson(d.getData());
                    BadgesData badgesData=new Gson().fromJson(json,BadgesData.class);
                    badgesDataArrayList.add(badgesData);
                }
                firestoreCallback.onCallback(badgesDataArrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "Ne valja", Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallback(null);
            }
        });
    }

    public void DodijeliZnackuProdavatelju(User user, BadgesData badgesData){
        firestoreService.updateBadgeSeller(user.getUserID(), Uri.parse(badgesData.getBadgeUrl()),"Users");
    }

    public void DodijeliZnackuKupcu(User user, BadgesData badgesData){
        firestoreService.updateBadgeBuyer(user.getUserID(), Uri.parse(badgesData.getBadgeUrl()),"Users");
    }


}