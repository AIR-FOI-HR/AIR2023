package com.example.digitalnaribarnica;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.FirestoreService;
import com.example.database.Fish;
import com.example.database.Location;
import com.example.database.Offer;
import com.example.database.User;
import com.example.database.Utils.SHA256;
import com.example.digitalnaribarnica.recycleviewer.OffersData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void DodajKorisnikaUBazu(String name, String email, String phone,String password,String photo,String adress){
        try {
            firestoreService.writeNewUserWithoutID(name,email,phone, SHA256.toHexString(SHA256.getSHA(password)),photo,adress,"Users");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //dodavanje novog korisnika sa ID
    public void DodajKorisnikaUBazuSaID(String id, String name, String email, String phone,String password,String photo,String adress){
        try {
            firestoreService.writeNewUser(id,name,email,phone, SHA256.toHexString(SHA256.getSHA(password)),photo,adress,"Users");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
    public void DodajPonudu(String name,String description, String imageurl, String price, String fishClass,String imageurlTrophey,String idKorisnika){
        //Offer offer=new Offer("Štuka","Požega","https://firebasestorage.googleapis.com/v0/b/digitalna-ribarnica-fb.appspot.com/o/ribe%2Fstuka.png?alt=media&token=f29a9276-9b02-4a00-94e0-d6166c15bcfd","40,00kn","Razred 2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png");
        Offer offer=new Offer(name,description,imageurl,price,fishClass,imageurlTrophey);
        if(idKorisnika!="")
            offer.setIdKorisnika(idKorisnika);
        firestoreService.writeOffer(offer,"Offers");
    }

    public boolean ProvjeriPassword(String sha256,String unesenaLozinka){
        return sha256.equals(unesenaLozinka);
    }

    public String random(int length) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char tempChar;
        for (int i = 0; i < length; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
