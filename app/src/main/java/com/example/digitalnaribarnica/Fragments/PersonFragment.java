package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.database.FirestoreService;
import com.example.database.Review;
import com.example.database.User;
import com.example.digitalnaribarnica.FirestoreCallback;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.ReviewCallback;
import com.example.digitalnaribarnica.databinding.FragmentPersonBinding;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PersonFragment extends Fragment {
    FragmentPersonBinding binding;
    private Button showRatingFragment;
    private String ime="";
    private String id="";
    private String photo="";
    private String email="";
    private String adress="";
    private String phone="";
    private ImageView edit;
    private String userID="";
    private ImageView badges;
    private ImageView buyerBadge;
    private ImageView sellerBadge;
    private RatingBar ratingBar;
    private TextView showRatings;

    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    public PersonFragment() {
    }

    public PersonFragment(String userId, GoogleSignInClient mGoogleSignInClient, FirebaseUser mUser, FirebaseAuth mAuth) {
        this.userID = userId;
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
        binding=FragmentPersonBinding.inflate(inflater,container,false);
        View view =binding.getRoot();

        edit = binding.btnUrediProfil;
        badges = binding.btnBadges;
        buyerBadge = binding.badgeBuyer;
        sellerBadge = binding.badgeSeller;
        ratingBar = binding.ratingBar;
        showRatings = binding.showRatings;

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

        repository.DohvatiOcjenePoID(userID, new ReviewCallback() {
            @Override
            public void onCallback(ArrayList<Review> reviews) {
                if(reviews.size()!=0){

                    float sum = 0;
                    for (int i = 0; i < reviews.size(); i++) {
                        sum = sum + Float.parseFloat(reviews.get(i).getRating());
                    }
                    float ratingTotal = sum / reviews.size();
                    ratingBar.setRating(ratingTotal);
                }
            }
        });

        showRatings.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View view) {
                selectedFragment = new RatingsFragment(userID, mGoogleSignInClient, mUser, mAuth);
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

        binding.btnBadges.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View view) {
                selectedFragment = new BadgesFragment(userID, mGoogleSignInClient, mUser, mAuth);
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
                selectedFragment = new EditProfileFragment(userID, mGoogleSignInClient, mUser, mAuth);
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

        return  view;
    }

    private void signOut() {
        try {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(),"Odjavljen!",Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Dogodila se pogreška!",Toast.LENGTH_LONG).show();
        }
    }
}
