package com.example.digitalnaribarnica.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.digitalnaribarnica.MainActivity;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.databinding.FragmentPersonBinding;
import com.example.digitalnaribarnica.databinding.ActivityRegisterBinding;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URI;

public class PersonFragment extends Fragment {
    FragmentPersonBinding binding;
    private String ime="";
    private String id="";
    private String photo="";
    private String email="";
    private Button edit;
    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    public PersonFragment() {
    }


    public PersonFragment(String ime, String id, String photo, String email) {
        this.ime = ime;
        this.id = id;
        this.photo = photo;
        this.email = email;
    }

    public PersonFragment(String ime, String id, String photo, String email, GoogleSignInAccount acct, FirebaseUser mUser, FirebaseAuth mAuth, GoogleSignInClient mGoogleSignInClient) {
        this.ime = ime;
        this.id = id;
        this.photo = photo;
        this.email = email;
        this.acct = acct;
        this.mUser = mUser;
        this.mAuth = mAuth;
        this.mGoogleSignInClient = mGoogleSignInClient;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentPersonBinding.inflate(inflater,container,false);
        View view =binding.getRoot();

        edit = binding.btnUrediProfil;


        edit.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View v) {
                Log.d("NOVITAG", "Trebalo bi pokrenuti");
                selectedFragment = new EditProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });



        binding.emailP.setText(email);
        binding.adresaP.setText(id);
        binding.brojMobitelaP.setText(ime);

       /* binding.odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.odjava:
                        if (mUser != null) {
                            mAuth.signOut();
                            LoginManager.getInstance().logOut();
                            getActivity().finish();
                        }
                        signOut();
                        break;
                }
            }
        });*/
        //Glide.with(this).load(photo).into(binding.Slika);
        return  view;


        /*View personFragmentView = inflater.inflate(R.layout.fragment_person, container, false);
        TextView Email = (TextView)personFragmentView.findViewById(R.id.Email);
        TextView ID = (TextView)personFragmentView.findViewById(R.id.ID);
        TextView Ime = (TextView)personFragmentView.findViewById(R.id.Name);
        Button odjava=(Button)personFragmentView.findViewById(R.id.odjava);

        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.odjava:
                        if(mUser!=null){
                            mAuth.signOut();
                            LoginManager.getInstance().logOut();
                            getActivity().finish();
                        }
                        signOut();
                        break;
                }
            }
        });*/


        /*Email.setText(email);
        ID.setText(id);
        Ime.setText(this.ime);
        Glide.with(this).load(photo).into((ImageView) personFragmentView.findViewById(R.id.Slika));*/



        //return personFragmentView;

    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(),"Odjavljen!",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });
    }
}