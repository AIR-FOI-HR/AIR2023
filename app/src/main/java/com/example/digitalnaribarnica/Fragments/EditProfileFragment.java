package com.example.digitalnaribarnica.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.database.CallbackUser;
import com.example.database.FirestoreService;
import com.example.database.User;
import com.example.database.Utils.SHA256;
import com.example.digitalnaribarnica.FirestoreCallback;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentEditProfileBinding;
import com.example.digitalnaribarnica.databinding.FragmentOfferDetailBinding;
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

    public EditProfileFragment(String Photo) {
        this.photo=Photo;
    }
    public EditProfileFragment(String ime, String id, String photo, String email,String adress,String phone, GoogleSignInAccount acct, FirebaseUser mUser, FirebaseAuth mAuth, GoogleSignInClient mGoogleSignInClient) {
        this.ime = ime;
        this.id = id;
        this.photo = photo;
        this.email = email;
        this.adress=adress;
        this.phone=phone;
        this.acct = acct;
        this.mUser = mUser;
        this.mAuth = mAuth;
        this.mGoogleSignInClient = mGoogleSignInClient;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Glide.with(this).load(photo).into(binding.slikaProfila);
        binding.emailEditPe.setText(email);
        binding.imeEditEp.setText(ime.split(" ")[0]);
        binding.prezimeEditEp.setText(ime.split(" ")[1]);
        binding.adresaEditPe.setText(adress);
        binding.brojMobitelaEditPe.setText(phone);
        binding.btnOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new PersonFragment(ime,id,photo,email,adress,phone,acct,mUser,mAuth,mGoogleSignInClient)).commit();
                //getActivity().finish();
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

                        //FirestoreService.addPhotoWithID(Uri.parse(photo),id);
                        FirestoreService.getProfilePhotoWithID(id, new CallbackUser() {
                            @Override
                            public void onCallback(Uri slika) {
                                photo=slika.toString();
                                Log.d("DOSAO",photo);
                                User updateKorisnik;
                                if(binding.lozinkaEditPe.getText().toString()=="")
                                    updateKorisnik=new User(id,binding.imeEditEp.getText().toString()+" "+binding.prezimeEditEp.getText().toString(),binding.emailEditPe.getText().toString(),binding.brojMobitelaEditPe.getText().toString(),binding.adresaEditPe.getText().toString(),photo, user.getPassword(),false);
                                else {
                                    try {
                                            updateKorisnik=new User(id,binding.imeEditEp.getText().toString()+" "+binding.prezimeEditEp.getText().toString(),binding.emailEditPe.getText().toString(),binding.brojMobitelaEditPe.getText().toString(),binding.adresaEditPe.getText().toString(),photo, SHA256.toHexString(SHA256.getSHA(binding.lozinkaEditPe.getText().toString())),false);
                                    } catch (NoSuchAlgorithmException e) {
                                        e.printStackTrace();
                                        updateKorisnik=new User(id,binding.imeEditEp.getText().toString()+" "+binding.prezimeEditEp.getText().toString(),binding.emailEditPe.getText().toString(),binding.brojMobitelaEditPe.getText().toString(),binding.adresaEditPe.getText().toString(),photo, user.getPassword(),false);

                                    }
                                }

                                firestoreService.updateUser(updateKorisnik,"Users");
                                Toast.makeText(getActivity(), "Uspješno ažuriran korisnik!", Toast.LENGTH_LONG).show();
                                ime=binding.imeEditEp.getText().toString()+" "+binding.prezimeEditEp.getText().toString();
                                email=binding.emailEditPe.getText().toString();
                                phone=binding.brojMobitelaEditPe.getText().toString();
                                adress=binding.adresaEditPe.getText().toString();

                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new PersonFragment(ime,id,photo,email,adress,phone,acct,mUser,mAuth,mGoogleSignInClient)).commit();


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
            //imageView.setImageURI(imageUri);
            FirestoreService.addPhotoWithID(imageUri,id);
        }


    }
}
