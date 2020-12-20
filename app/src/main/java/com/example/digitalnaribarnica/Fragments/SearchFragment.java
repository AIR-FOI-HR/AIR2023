package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
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
import java.util.Collections;

public class SearchFragment extends Fragment {

    Menu actionMenu;

    FragmentSearchBinding binding;
    RecyclerView recyclerView;

    private String fishSpecies;
    private String location;
    private String minPrice;
    private String maxPrice;
    private Boolean smallFish = false;
    private Boolean mediumFish = false;
    private Boolean largeFish = false;
    private Boolean leastExpensive = false;
    private Boolean mostExpensive = false;

    private Boolean filtered = false;
    private Boolean myOffers = false;

    GoogleSignInAccount acct;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private String userId = "";

    public SearchFragment(String userId) {
        this.userId = userId;
        this.filtered = false;
        this.filtered = false;
    }

    public SearchFragment(String userId, Boolean myOffers) {
        this.userId = userId;
        this.myOffers = myOffers;
        this.filtered = false;
    }

    public SearchFragment(String userID, String fishSpecies, String location, String minPrice, String maxPrice, Boolean smallFish, Boolean mediumFish, Boolean largeFish, Boolean leastExpensive, Boolean mostExpensive){
        this.userId = userID;
        this.fishSpecies = fishSpecies;
        this.location = location;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.smallFish = smallFish;
        this.mediumFish = mediumFish;
        this.largeFish = largeFish;
        this.leastExpensive = leastExpensive;
        this.mostExpensive = mostExpensive;
        this.filtered = true;
        this.myOffers = false;
    };

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

        return view;
    }

    public void searchOffers(String search) {
        Repository repository = new Repository();
        repository.DohvatiSvePonude(offersData -> {
            ArrayList<OffersData> offersList = new ArrayList<>();
            for (int i = 0; i < offersData.size(); i++) {
                if (offersData.get(i).getName().toLowerCase().contains(search.toLowerCase()) || offersData.get(i).getLocation().toLowerCase().contains(search.toLowerCase())) {
                    offersList.add(offersData.get(i));
                }
            }
            OfferAdapter adapter = new OfferAdapter(getActivity(), userId);
            adapter.setOffers(offersList);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            if(search.equals("")){
                actionMenu.findItem((R.id.all_offers_menu)).setVisible(false);
                actionMenu.findItem((R.id.my_offers_menu)).setVisible(true);
            }

            else {
                actionMenu.findItem((R.id.all_offers_menu)).setVisible(true);
                actionMenu.findItem((R.id.my_offers_menu)).setVisible(true);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        actionMenu = menu;
        menu.findItem((R.id.all_offers_menu)).setVisible(false);
        MenuItem item = menu.findItem((R.id.action_search));
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchOffers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchOffers(newText);
                return true;
            }
        });

      /*  MenuItem filter = menu.findItem((R.id.dialog_filter_offers));
        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            Fragment selectedFragment =null;
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectedFragment = new FilterOffersFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
                return false;
            }
        });*/

        ArrayList<OffersData> offers=new ArrayList<>();

        if(!this.myOffers && !this.filtered) {
            Log.d("TagPolje", "poziva se za početak");
            Repository repository = new Repository();
            repository.DohvatiSvePonude(new FirestoreOffer() {
                @Override
                public void onCallback(ArrayList<OffersData> offersData) {
                    OfferAdapter adapter = new OfferAdapter(getActivity(), userId);
                    adapter.setOffers(offersData);

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            });
        }
        if(this.myOffers && !this.filtered){
            getMyOffers();
        }

        Log.d("TagPolje", "dal se poziva");

        if(this.filtered){
            Log.d("TagPolje", "dal se poziva za filter");
            Repository repository = new Repository();
            repository.DohvatiSvePonude(offersData -> {
                ArrayList<OffersData> offersList = offersData;
                for (int i = 0; i < offersList.size(); i++) {
                    if (this.fishSpecies != null && !this.fishSpecies.equals("")) {
                        if (!offersList.get(i).getName().contains(this.fishSpecies)) {
                            offersList.remove(offersData.get(i));
                            i = i - 1;
                            continue;
                        }
                    }

                    if (this.location != null && !this.location.equals("")) {
                        if (!offersList.get(i).getLocation().contains(this.location)) {
                            offersList.remove(offersData.get(i));
                            i = i - 1;
                            continue;
                        }
                    }

                    if (Double.parseDouble(this.minPrice) > Double.parseDouble(offersList.get(i).getPrice()) || Double.parseDouble(this.maxPrice) < Double.parseDouble(offersList.get(i).getPrice())) {
                        offersList.remove(offersData.get(i));
                        i = i - 1;
                        continue;
                    }
                    if (!(!smallFish && !mediumFish && !largeFish)) {
                        if (!((smallFish && !offersList.get(i).getSmallFish().equals("0")) || (mediumFish && !offersList.get(i).getMediumFish().equals("0")) || (largeFish && !offersList.get(i).getLargeFish().equals("0")))) {
                            offersList.remove(offersData.get(i));
                            i = i - 1;
                        }
                    }
                }

                if (leastExpensive){
                    Collections.sort(offersList, (exp1, exp2) -> Double.compare(Double.parseDouble(exp1.getPrice()), Double.parseDouble(exp2.getPrice())));
                }

                if(mostExpensive){
                    Collections.sort(offersList, (exp1, exp2) -> Double.compare(Double.parseDouble(exp2.getPrice()), Double.parseDouble(exp1.getPrice())));
                }

                RecyclerView recyclerView2 = binding.recycleViewOffer;
                OfferAdapter adapter2 = new OfferAdapter(getActivity(), userId);
                Log.d("TagPolje", String.valueOf(offersList.size()));
                adapter2.notifyDataSetChanged();
                recyclerView2.setAdapter(adapter2);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

                actionMenu.findItem((R.id.all_offers_menu)).setVisible(true);
                actionMenu.findItem((R.id.my_offers_menu)).setVisible(true);

            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Repository repository=new Repository();
        OfferAdapter adapter = new OfferAdapter(getActivity(), userId);
        switch (id){
            case R.id.all_offers_menu:
                repository.DohvatiSvePonude(offersData -> {
                    adapter.setOffers(offersData);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ponude");
                        actionMenu.findItem((R.id.all_offers_menu)).setVisible(false);
                        actionMenu.findItem((R.id.my_offers_menu)).setVisible(true);
                });
                break;

            case R.id.my_offers_menu:
                getMyOffers();
                break;

            case R.id.filter_menu:
                FilterOffersFragment selectedFragment = new FilterOffersFragment(userId);
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
                break;
        }
        return true;
    }

    public void getMyOffers(){
        Log.d("TagPolje", "poziva se za metodu");
        Repository repository=new Repository();
        OfferAdapter adapter = new OfferAdapter(getActivity(), userId);
        repository.DohvatiSvePonude(offersData -> {
            ArrayList<OffersData> offersList = offersData;
            for (int i = 0; i < offersList.size(); i++) {

                if (!offersList.get(i).getIdKorisnika().equals(userId)) {
                    Log.d("TagPolje", "makni");
                    offersList.remove(offersData.get(i));
                    i = i - 1;
                }
            }

            adapter.setOffers(offersList);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Moje ponude");
            actionMenu.findItem((R.id.all_offers_menu)).setVisible(true);
            actionMenu.findItem((R.id.my_offers_menu)).setVisible(false);
        });
    }
}