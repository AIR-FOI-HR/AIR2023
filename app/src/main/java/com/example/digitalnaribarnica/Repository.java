package com.example.digitalnaribarnica;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.FirestoreService;
import com.example.database.Fish;
import com.example.database.Location;
import com.example.database.User;
import com.example.digitalnaribarnica.recycleviewer.OffersData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    FirestoreService firestoreService;
    public Repository() {
        firestoreService=new FirestoreService();
    }

    public void DohvatiKorisnikaPoEmailu(String email, FirestoreCallback firestoreCallback){
        firestoreService.getCollectionWithField("Users","email",email).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ime=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d: ime){
                    //String fullName = d.getString("fullName");
                    //Toast.makeText(MainActivity.this, fullName, Toast.LENGTH_LONG).show();
                    d.getData();
                    String json= new Gson().toJson(d.getData());
                    User user=new Gson().fromJson(json,User.class);
                    firestoreCallback.onCallback(user);
                    //Toast.makeText(MainActivity.this, user.getFullName(), Toast.LENGTH_LONG).show();
                    Log.d("TEST",user.getFullName());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "Ne valja", Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallback(null);
            }
        });
    }
    public void DohvatiPonudePoID(String id, FirestoreOffer firestoreCallback){
        firestoreService.getCollectionWithField("Offers","idKorisnika",id).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ime=queryDocumentSnapshots.getDocuments();
                ArrayList<OffersData> offersDataArrayList=new ArrayList<>();
                for(DocumentSnapshot d: ime){
                    //String fullName = d.getString("fullName");
                    //Toast.makeText(MainActivity.this, fullName, Toast.LENGTH_LONG).show();
                    d.getData();
                    String json= new Gson().toJson(d.getData());
                    OffersData offersData=new Gson().fromJson(json,OffersData.class);
                    offersDataArrayList.add(offersData);
                    //Toast.makeText(MainActivity.this, user.getFullName(), Toast.LENGTH_LONG).show();
                    //Log.d("TEST",offersData.getFullName());
                }
                firestoreCallback.onCallback(offersDataArrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "Ne valja", Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallback(null);
            }
        });
    }

    public void DohvatiSvePonude(FirestoreOffer firestoreCallback){
        firestoreService.getCollection("Offers").addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ime=queryDocumentSnapshots.getDocuments();
                ArrayList<OffersData> offersDataArrayList=new ArrayList<>();
                for(DocumentSnapshot d: ime){
                    //String fullName = d.getString("fullName");
                    //Toast.makeText(MainActivity.this, fullName, Toast.LENGTH_LONG).show();
                    d.getData();
                    String json= new Gson().toJson(d.getData());
                    OffersData offersData=new Gson().fromJson(json,OffersData.class);
                    offersDataArrayList.add(offersData);
                    //Toast.makeText(MainActivity.this, user.getFullName(), Toast.LENGTH_LONG).show();
                    //Log.d("TEST",offersData.getFullName());
                }
                firestoreCallback.onCallback(offersDataArrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "Ne valja", Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallback(null);
            }
        });
    }
    public void DohvatiRibe(FishCallback firestoreCallback){
        firestoreService.getCollection("Fish").addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ime=queryDocumentSnapshots.getDocuments();
                ArrayList<Fish> fishArrayList=new ArrayList<>();
                for(DocumentSnapshot d: ime){
                    //String fullName = d.getString("fullName");
                    //Toast.makeText(MainActivity.this, fullName, Toast.LENGTH_LONG).show();
                    d.getData();
                    String json= new Gson().toJson(d.getData());
                    Fish fish=new Gson().fromJson(json,Fish.class);
                    fishArrayList.add(fish);
                    //Toast.makeText(MainActivity.this, user.getFullName(), Toast.LENGTH_LONG).show();
                    //Log.d("TEST",offersData.getFullName());
                }
                firestoreCallback.onCallback(fishArrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "Ne valja", Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallback(null);
            }
        });
    }
    public void DohvatiLokacije(LocationCallback firestoreCallback){
        firestoreService.getCollection("Location").addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ime=queryDocumentSnapshots.getDocuments();
                ArrayList<Location> locationArrayList=new ArrayList<>();
                for(DocumentSnapshot d: ime){
                    //String fullName = d.getString("fullName");
                    //Toast.makeText(MainActivity.this, fullName, Toast.LENGTH_LONG).show();
                    d.getData();
                    String json= new Gson().toJson(d.getData());
                    Location location=new Gson().fromJson(json,Location.class);
                    locationArrayList.add(location);
                    //Toast.makeText(MainActivity.this, user.getFullName(), Toast.LENGTH_LONG).show();
                    //Log.d("TEST",offersData.getFullName());
                }
                firestoreCallback.onCallback(locationArrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "Ne valja", Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallback(null);
            }
        });
    }
    public boolean ProvjeriPassword(String sha256,String unesenaLozinka){
        return sha256.equals(unesenaLozinka);
    }
}
