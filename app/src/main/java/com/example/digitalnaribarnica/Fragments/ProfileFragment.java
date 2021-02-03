package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.badges.BadgeID;
import com.example.badges.BadgesData;
import com.example.database.FirestoreService;
import com.example.database.Review;
import com.example.database.User;
import com.example.digitalnaribarnica.MainActivity;
import com.example.digitalnaribarnica.ViewModel.SharedViewModel;
import com.example.repository.Listener.FirestoreCallback;
import com.example.digitalnaribarnica.R;
import com.example.repository.Repository;
import com.example.repository.Listener.ReviewCallback;
import com.example.digitalnaribarnica.databinding.FragmentPersonBinding;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    FragmentPersonBinding binding;
    ArrayList<BadgesData> badgesList;
    private String adress="";
    private String phone="";
    private ImageView edit;
    private String userID="";
    private ImageView badges;
    private ImageView btnGoBack;
    private RatingBar ratingBar;
    private TextView showRatings;
    private Boolean otherProfile= false;
    private String currentUser = "";
    private String cameFrom = "Person";
    private String offerID = "";

    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    private SharedViewModel sharedViewModel;

    public ProfileFragment() {}

    public ProfileFragment(String selectedUser, String userID, String cameFrom) {
        this.userID = selectedUser;
        this.currentUser = userID;
        this.cameFrom = cameFrom;
        otherProfile = true;
    }

    public ProfileFragment(String selectedUser, String userID, String cameFrom, String offerID) {
        this.userID = selectedUser;
        this.currentUser=userID;
        this.cameFrom = cameFrom;
        this.offerID = offerID;
        otherProfile = true;
    }

    public ProfileFragment(String userId, GoogleSignInClient mGoogleSignInClient, FirebaseUser mUser, FirebaseAuth mAuth) {
        this.userID = userId;
        this.mGoogleSignInClient = mGoogleSignInClient;
        this.mUser = mUser;
        this.mAuth = mAuth;
        otherProfile = false;
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
        ratingBar = binding.ratingBar;
        showRatings = binding.showRatings;
        btnGoBack = binding.btnBack;

        if(otherProfile==true){
                btnGoBack.setVisibility(view.VISIBLE);
            if(!currentUser.equals(userID)){
                edit.setVisibility(view.INVISIBLE);
                badges.setVisibility(view.INVISIBLE);
                binding.odjava.setVisibility(view.INVISIBLE);
            }
         }

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);


        sharedViewModel.VratiSveZnacke();
        sharedViewModel.badgesDataArrayList.observe(this, new Observer<ArrayList<BadgesData>>() {
            @Override
            public void onChanged(ArrayList<BadgesData> badgesData) {
                badgesList = badgesData;
            }
        });

        sharedViewModel.VratiSveIDZnackeKorisnika(userID);
        sharedViewModel.badgesIDDataArrayList.observe(this, new Observer<ArrayList<BadgeID>>() {
            @Override
            public void onChanged(ArrayList<BadgeID> badgeIDS) {
                try{
                for (int i = 0; i < badgesList.size(); i++) {
                    for (int j = 0; j < badgeIDS.size(); j++) {
                        if (badgesList.get(i).getBadgeID().equals(badgeIDS.get(j).getId())) {
                            if (badgesList.get(i).getCategory().equals("buyer")) {
                                Glide.with(getActivity())
                                        .asBitmap()
                                        .load(badgesList.get(i).getBadgeURL())
                                        .into(binding.badgeBuyer);
                            } else if (badgesList.get(i).getCategory().equals("seller")) {
                                Glide.with(getActivity())
                                        .asBitmap()
                                        .load(badgesList.get(i).getBadgeURL())
                                        .into(binding.badgeSeller);
                            } else {
                                Glide.with(getActivity())
                                        .asBitmap()
                                        .load(badgesList.get(i).getBadgeURL())
                                        .into(binding.badgeQuiz);
                            }
                        }
                    }
                }
            }catch (Exception ex){}
            }
        });



        sharedViewModel.DohvatiKorisnikaPoID(userID);
        sharedViewModel.userMutableLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
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

              /*  if(!user.getBadgeBuyerURL().equals("")){
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

                if(!user.getBadgeQuizURL().equals("")){
                    Glide.with(getActivity())
                            .asBitmap()
                            .load(user.getBadgeQuizURL())
                            .into(binding.badgeQuiz);
                }*/
            }
        });



        sharedViewModel.DohvatiOcjenePoID(userID);
        sharedViewModel.reviewDataArrayList.observe(this, new Observer<ArrayList<Review>>() {
            @Override
            public void onChanged(ArrayList<Review> reviews) {
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
                if(cameFrom.equals("Details")){
                    selectedFragment = new RatingsFragment(userID, currentUser, mGoogleSignInClient, mUser, mAuth, cameFrom, offerID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else{
                    selectedFragment = new RatingsFragment(userID, currentUser, mGoogleSignInClient, mUser, mAuth, cameFrom);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View view) {
                if(!userID.equals(currentUser)){
                    userID = currentUser;
                }
                if(cameFrom.equals("Requests")){
                    selectedFragment = new ReservationFragment(userID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else if(cameFrom.equals("Offers")){
                    selectedFragment = new SearchFragment(userID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else if(cameFrom.equals("Confirmed")){
                    selectedFragment = new ReservationFragment(userID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else if(cameFrom.equals("Details")){
                   selectedFragment = new OfferDetailFragment(offerID, userID, false);
                   getFragmentManager().beginTransaction().replace(R.id.fragment_containter, selectedFragment).commit();
                }

            }
        });

        binding.btnBadges.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View view) {
                if(cameFrom.equals("Details")){
                    selectedFragment = new BadgesFragment(userID, currentUser, mGoogleSignInClient, mUser, mAuth, cameFrom, offerID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else{
                    selectedFragment = new BadgesFragment(userID, currentUser, mGoogleSignInClient, mUser, mAuth, cameFrom);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }
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

                if(cameFrom.equals("Details")){
                    selectedFragment = new EditProfileFragment(userID, currentUser, mGoogleSignInClient, mUser, mAuth, cameFrom, offerID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else{
                    selectedFragment = new EditProfileFragment(userID, currentUser, mGoogleSignInClient, mUser, mAuth, cameFrom);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }
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
                            Toast.makeText(getActivity(),getActivity().getString(R.string.userSignOut),Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),getActivity().getString(R.string.error),Toast.LENGTH_LONG).show();
        }
    }
}
