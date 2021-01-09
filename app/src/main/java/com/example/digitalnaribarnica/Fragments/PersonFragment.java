package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.database.FirestoreService;
import com.example.database.User;
import com.example.digitalnaribarnica.FirestoreCallback;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentPersonBinding;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PersonFragment extends Fragment {
    FragmentPersonBinding binding;
    private String ime="";
    private String id="";
    private String photo="";
    private String email="";
    private String adress="";
    private String phone="";
    private ImageView edit;
    private ImageView badges;
    private ImageView buyerBadge;
    private ImageView sellerBadge;
    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private String userID="";
    public PersonFragment() {
    }

    public PersonFragment(String userId) {
        this.userID = userId;
    }

    public PersonFragment(String ime, String id, String photo, String email) {
        this.ime = ime;
        this.id = id;
        this.photo = photo;
        this.email = email;
    }

    public PersonFragment(String ime, String id, String photo, String email,String adress,String phone, GoogleSignInAccount acct, FirebaseUser mUser, FirebaseAuth mAuth, GoogleSignInClient mGoogleSignInClient) {
        this.ime = ime;
        this.id = id;
        this.photo = photo;
        this.email = email;
        this.acct = acct;
        this.mUser = mUser;
        this.mAuth = mAuth;
        this.adress=adress;
        this.phone=phone;
        this.mGoogleSignInClient = mGoogleSignInClient;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        binding=FragmentPersonBinding.inflate(inflater,container,false);
        View view =binding.getRoot();

        edit = binding.btnUrediProfil;
        badges = binding.btnBadges;
        buyerBadge = binding.badgeBuyer;
        sellerBadge = binding.badgeSeller;

        /*binding.emailP.setText(email);
        //binding.adresaP.setText(id);
        binding.imePrezime.setText(ime);
        //binding.brojMobitelaP.setText(ime);
        Glide.with(this).load(photo).into(binding.slikaProfila);*/

        Repository repository = new Repository();
        FirestoreService firestoreService = new FirestoreService();
        repository.DohvatiKorisnikaPoID(userID, new FirestoreCallback() {
            @Override
            public void onCallback(User user) {
                binding.emailP.setText(user.getEmail());
                if(user.getFullName()!=""){
                    binding.imePrezime.setText(user.getFullName());
                }
                if(user.getAdress()!=""){
                    binding.adresaP.setText(user.getAdress());
                }
                if(user.getPhone()!=""){
                    binding.brojMobitelaP.setText(user.getPhone());
                }
                Glide.with(getActivity())
                        .asBitmap()
                        .load(user.getPhoto())
                        .into(binding.slikaProfila);
              if(!user.getBadgeBuyerURL().equals("")){
            Glide.with(getActivity())
                    .asBitmap()
                    .load(user.getBadgeBuyerURL())
                    .into(binding.badgeBuyer);
              }

                if(!user.getBadgeSellerURL().equals("")){
                    Glide.with(getActivity())
                            .asBitmap()
                            .load(user.getBadgeSellerURL())
                            .into(binding.badgeSeller);
                }
            }
        });


        binding.btnBadges.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View view) {
                selectedFragment = new BadgesFragment(userID);
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

        binding.odjava.setOnClickListener(new View.OnClickListener() {
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
        });
        if(phone!="")
            binding.brojMobitelaP.setText(phone);
        if(adress!="")
            binding.adresaP.setText(adress);
        edit.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View v) {
                Log.d("NOVITAG", "Trebalo bi pokrenuti");
                selectedFragment = new EditProfileFragment(userID);
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
                /*
                Repository repository=new Repository();
                repository.DohvatiKorisnikaPoEmailu(email, new FirestoreCallback() {
                    @Override
                    public void onCallback(User user) {
                        binding.emailP.setText(user.getEmail());
                        //binding.adresaP.setText(id);
                        binding.imePrezime.setText(user.getEmail());
                        //binding.brojMobitelaP.setText(ime);
                        Glide.with(getParentFragment()).load(user.getPhoto()).into(binding.slikaProfila);
                        if(phone!="")
                            binding.brojMobitelaP.setText(user.getPhone());
                        if(adress!="")
                            binding.adresaP.setText(user.getAdress());
                    }
                });
                */


            }
        });




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
