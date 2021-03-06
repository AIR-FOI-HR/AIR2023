package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.badges.BadgeCallback;
import com.example.badges.BadgeID;
import com.example.badges.BadgeIDCallback;
import com.example.badges.BadgesAdapter;
import com.example.badges.BadgesRepository;
import com.example.badges.Logic;
import com.example.database.FirestoreService;
import com.example.digitalnaribarnica.ViewModel.SharedViewModel;
import com.example.digitalnaribarnica.R;
import com.example.repository.Repository;
import com.example.digitalnaribarnica.databinding.FragmentSearchBinding;
import com.example.badges.BadgesData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class BadgesFragment extends Fragment {

    private String userId = "";
    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private String cameFrom = "";
    private String currentUser = "";
    private String offerID = "";
    private SharedViewModel sharedViewModel;
    private FloatingActionButton floatingActionButton;

    public BadgesFragment() {}
    public BadgesFragment(String userId) {
        this.userId = userId;
    }

    //Google
    public BadgesFragment(String userId,  String currentUser, GoogleSignInClient mGoogleSignInClient, FirebaseUser mUser, FirebaseAuth mAuth, String cameFrom) {
        this.userId = userId;
        this.currentUser = currentUser;
        this.mGoogleSignInClient = mGoogleSignInClient;
        this.mUser = mUser;
        this.mAuth = mAuth;
        this.cameFrom = cameFrom;
    }

    public BadgesFragment(String userId,  String currentUser, GoogleSignInClient mGoogleSignInClient, FirebaseUser mUser, FirebaseAuth mAuth, String cameFrom, String offerID) {
        this.userId = userId;
        this.currentUser = currentUser;
        this.mGoogleSignInClient = mGoogleSignInClient;
        this.mUser = mUser;
        this.mAuth = mAuth;
        this.cameFrom = cameFrom;
        this.offerID = offerID;

    }


    Menu actionMenu;
    FragmentSearchBinding binding;
    RecyclerView recyclerView;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getString(R.string.badgesDescription));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);// set drawable icon
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        Repository repository = new Repository();
        BadgesAdapter adapter = new BadgesAdapter(getActivity(), userId, this);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        recyclerView = binding.recycleViewOffer;

        floatingActionButton = binding.floatingbtnAddOffer;
        floatingActionButton.setVisibility(view.INVISIBLE);

        sharedViewModel.VratiSveZnacke();
        sharedViewModel.badgesDataArrayList.observe(this, new Observer<ArrayList<BadgesData>>() {
            @Override
            public void onChanged(ArrayList<BadgesData> badgesData) {
                adapter.setBadges(badgesData);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        menu.findItem((R.id.filter_menu)).setVisible(false);
        menu.findItem((R.id.all_offers_menu)).setVisible(false);
        menu.findItem((R.id.my_offers_menu)).setVisible(false);
        menu.findItem((R.id.sort_offers_menu)).setVisible(false);
        menu.findItem((R.id.action_search)).setVisible(false);
        menu.findItem((R.id.language)).setVisible(false);
        menu.findItem((R.id.current_language)).setVisible(false);
        menu.findItem((R.id.onboardingHelp)).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                setHasOptionsMenu(false);
                Fragment selectedFragment = null;
                // ((RegisterActivity) getActivity()).changeOnSearchNavigationBar();
                //selectedFragment = new PersonFragment(userId);
                if(cameFrom.equals("Person")){
                    selectedFragment = new ProfileFragment(userId, mGoogleSignInClient, mUser, mAuth);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                } else if(cameFrom.equals("Details")){
                    selectedFragment = new ProfileFragment(userId, currentUser, cameFrom, offerID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else{
                    selectedFragment = new ProfileFragment(userId, currentUser, cameFrom);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
        }

        return true;
    }
}
