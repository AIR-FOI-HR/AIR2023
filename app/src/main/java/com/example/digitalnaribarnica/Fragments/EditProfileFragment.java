package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.database.CallbackUser;
import com.example.database.FirestoreService;
import com.example.database.User;
import com.example.database.Utils.SHA256;
import com.example.repository.Listener.FirestoreCallback;
import com.example.digitalnaribarnica.R;
import com.example.repository.Repository;
import com.example.digitalnaribarnica.databinding.FragmentEditProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.NoSuchAlgorithmException;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    private Button btnDetails;
    private String photo="";
    private String ime="";
    private String id="";
    private String email="";
    private String adress="";
    private String phone="";
    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    public EditProfileFragment() {
    }

    //Google
    public EditProfileFragment(String userId, GoogleSignInClient mGoogleSignInClient, FirebaseUser mUser, FirebaseAuth mAuth) {
        this.id = userId;
        this.mGoogleSignInClient = mGoogleSignInClient;
        this.mUser = mUser;
        this.mAuth = mAuth;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Repository repository = new Repository();
        repository.DohvatiKorisnikaPoID(id, new FirestoreCallback() {
            @Override
            public void onCallback(User user) {
                binding.emailEditPe.setText(user.getEmail());
                if(user.getFullName()!=""){
                    binding.imeEditEp.setText(user.getFullName().split(" ")[0]);
                }
                if(user.getFullName()!=""){
                    binding.prezimeEditEp.setText(user.getFullName().split(" ")[1]);
                }
                if(user.getAdress()!=""){
                    binding.adresaEditPe.setText(user.getAdress());
                }
                if(user.getPhone()!=""){
                    binding.brojMobitelaEditPe.setText(user.getPhone());
                }
                if(user.getEmail() != "") {
                    binding.emailEditPe.setText(user.getEmail());
                    email = user.getEmail();
                }
                Glide.with(getActivity())
                        .asBitmap()
                        .load(user.getPhoto())
                        .into(binding.slikaProfila);
            }
        });

        binding.btnOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new ProfileFragment(id, mGoogleSignInClient, mUser, mAuth)).commit();
            }
        });
        binding.btnUcitajSLiku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        binding.btnSpremiPromjene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreService firestoreService=new FirestoreService();
                Repository repository=new Repository();
                repository.DohvatiKorisnikaPoEmailu(email, new FirestoreCallback() {
                    @Override
                    public void onCallback(User user) {
                        FirestoreService.getProfilePhotoWithID(id, new CallbackUser() {
                            @Override
                            public void onCallback(Uri slika) {
                                if(slika != null) {
                                    photo=slika.toString();
                                }
                                else {
                                    photo = "https://firebasestorage.googleapis.com/v0/b/digitalna-ribarnica-fb.appspot.com/o/default_profilna%2Fuser_image.jpg?alt=media&token=e30a1426-9be2-40d8-8e5a-b5e4c43337e7";
                                }

                                User updateKorisnik;
                                if(binding.lozinkaEditPe.getText().toString() ==" ")
                                    updateKorisnik = new User(id,binding.imeEditEp.getText().toString()+" "+binding.prezimeEditEp.getText().toString(),binding.emailEditPe.getText().toString(),binding.brojMobitelaEditPe.getText().toString(),binding.adresaEditPe.getText().toString(),photo, user.getPassword(),false);
                                else {
                                    try {
                                        updateKorisnik = new User(id,binding.imeEditEp.getText().toString()+" "+binding.prezimeEditEp.getText().toString(),binding.emailEditPe.getText().toString(),binding.brojMobitelaEditPe.getText().toString(),binding.adresaEditPe.getText().toString(),photo, SHA256.toHexString(SHA256.getSHA(binding.lozinkaEditPe.getText().toString())),false);
                                    } catch (NoSuchAlgorithmException e) {
                                        e.printStackTrace();
                                        updateKorisnik = new User(id,binding.imeEditEp.getText().toString()+" "+binding.prezimeEditEp.getText().toString(),binding.emailEditPe.getText().toString(),binding.brojMobitelaEditPe.getText().toString(),binding.adresaEditPe.getText().toString(),photo, user.getPassword(),false);

                                    }
                                }

                                firestoreService.updateUser(updateKorisnik,"Users");
                                try {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new ProfileFragment(id, mGoogleSignInClient, mUser, mAuth)).commit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                });

            }
        });
        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(binding.slikaProfila);
            photo=imageUri.toString();
            FirestoreService.addPhotoWithID(imageUri,id);
        }


    }
}
