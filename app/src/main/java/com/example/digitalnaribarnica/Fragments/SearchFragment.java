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
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalnaribarnica.FirestoreOffer;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentSearchBinding;
import com.example.digitalnaribarnica.recycleviewer.OffersData;
import com.example.digitalnaribarnica.recycleviewer.OfferAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    RecyclerView recyclerView;
    private String ime="";
    private String id="";
    private String photo="";
    private String email="";
    private String adress="";
    private String phone="";
    private Button edit;
    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    public SearchFragment() {
    }
    public SearchFragment(String ime, String id, String photo, String email, String adress, String phone, GoogleSignInAccount acct, FirebaseUser mUser, FirebaseAuth mAuth, GoogleSignInClient mGoogleSignInClient) {
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ponude");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));

        setHasOptionsMenu(true);
        recyclerView = binding.recycleViewOffer;

        ArrayList<OffersData> offers=new ArrayList<>();
        /*
        offers.add(new OffersData("Prva ponuda","Varaždin", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "25,00 kn", "Razred: 2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Druga ponuda","Zagreb", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "30,00 kn", "Razred: 3","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Treća ponuda","Varaždin", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "30,00 kn", "Razred: 2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Četvrta ponuda","Varaždin", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "25,00 kn", "Razred: 1","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Peta ponuda","Čakovec", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "30,00 kn", "Razred: 2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        */
        Repository repository=new Repository();
        repository.DohvatiSvePonude(new FirestoreOffer() {
            @Override
            public void onCallback(ArrayList<OffersData> offersData) {
                OfferAdapter adapter=new OfferAdapter(getActivity());
                adapter.setOffers(offersData);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });


        return view;


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("TagPolje", "ulazi");
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem((R.id.action_search));
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem filter = menu.findItem((R.id.dialog_filter_offers));
        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            Fragment selectedFragment =null;
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectedFragment = new FilterOffersFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}