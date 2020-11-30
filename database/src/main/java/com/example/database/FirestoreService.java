package com.example.database;

import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirestoreService {
    private FirestoreService getInstance() {
        if(instance==null)
            instance=new FirestoreService();
        return instance;
    }

    private void setInstance() {
        if(instance==null)
            this.instance = new FirestoreService();
    }

    private FirestoreService instance= null;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private static ArrayList<User> mArrayList = new ArrayList<>();;

    public ArrayList<User> getListItems(String collection){

        mFirebaseFirestore.collection(collection).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            //Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<User> types = documentSnapshots.toObjects(User.class);

                            // Add all to your list
                            mArrayList.addAll(types);
                            //Log.d(TAG, "onSuccess: " + mArrayList);
                        }
                    };


                });
        return mArrayList;
    }


    public User getUserByID(String collection, String document){
        User user=new User();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection(collection).document(document);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();
                    if(doc.exists()){
                        Log.d("Document",doc.getData().toString());

                    }else
                    {
                        Log.d("Document","No data");
                    }
                }
            }
        });

        return user;
    }


    public Task<QuerySnapshot> getCollectionWithField(String collection, String field, String value){
        //Log.d("Pokusaj",FirebaseFirestore.getInstance().collection(collection).whereEqualTo("Email", "bozo.kvesic1@gmail.com").get().toString());
        return FirebaseFirestore.getInstance().collection(collection).whereEqualTo(field, value).get();
    }


    public Task<QuerySnapshot> getCollection(String collection){
        //Log.d("Pokusaj",FirebaseFirestore.getInstance().collection(collection).whereEqualTo("Email", "bozo.kvesic1@gmail.com").get().toString());
        return FirebaseFirestore.getInstance().collection(collection).get();
    }

    public void writeNewUser(String userId, String name, String email, String phone,String password,String photo,String adress,String collection) {
        User user = new User(userId,name,email,phone,adress,photo,password,false);
        //FirebaseDatabase.getInstance().getReference().child(collection).child(user.userID).setValue(user).isSuccessful();
        //FirebaseFirestore.getInstance().collection(collection).add(user);
        FirebaseFirestore.getInstance().collection(collection).document(user.userID).set(user);
    }

    public void writeOffer(Offer offer,String collection) {
        //FirebaseDatabase.getInstance().getReference().child(collection).child(user.userID).setValue(user).isSuccessful();
        //FirebaseFirestore.getInstance().collection(collection).add(user);
        FirebaseFirestore.getInstance().collection(collection).document().set(offer);
    }

    public void updateUser(User trenutniKorisnik,String collection) {
        //User user = trenutniKorisnik;
        //FirebaseDatabase.getInstance().getReference().child(collection).child(user.userID).setValue(user).isSuccessful();
        //FirebaseFirestore.getInstance().collection(collection).add(user);
        //FirebaseFirestore.getInstance().collection(collection).document(user.userID).update("blokiran",true,"fullName",user.fullName);
        FirebaseFirestore.getInstance().collection(collection).document(trenutniKorisnik.userID).update(
                "adress",trenutniKorisnik.adress,
                "blokiran",trenutniKorisnik.blokiran,
                "email",trenutniKorisnik.email,
                "fullName",trenutniKorisnik.fullName,
                "password",trenutniKorisnik.password,
                "phone",trenutniKorisnik.phone,
                "photo",trenutniKorisnik.photo,
                "userID",trenutniKorisnik.userID);
    }

    public static void addPhoto(Uri data){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://digitalna-ribarnica-fb.appspot.com");
        String path="firememes/" + UUID.randomUUID() + ".png";
        StorageReference firememeRef =storage.getReference(path);
        UploadTask uploadTask=firememeRef.putFile(data);

    }

    public static void addPhotoWithID(Uri data,String id){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://digitalna-ribarnica-fb.appspot.com");
        String path="profilne/" + id + ".png";
        StorageReference profilne =storage.getReference(path);
        UploadTask uploadTask=profilne.putFile(data);
    }

    public static void getProfilePhotoWithID(String id, CallbackUser callbackUser){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://digitalna-ribarnica-fb.appspot.com");
        StorageReference storageRef = storage.getReference();
        storageRef.child("profilne/" + id + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                callbackUser.onCallback(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callbackUser.onCallback(null);
            }
        });
    }

    public static void getData(){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://digitalna-ribarnica-fb.appspot.com");
        StorageReference storageRef = storage.getReference();

        storageRef.child("firememes/5d068fd4-1f37-4ce6-89c9-c85bf5fc5c3b.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

}