package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalnaribarnica.BadgeCallback;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentSearchBinding;
import com.example.digitalnaribarnica.recycleviewer.BadgesAdapter;
import com.example.digitalnaribarnica.recycleviewer.BadgesData;
import com.example.digitalnaribarnica.recycleviewer.OfferAdapter;
import com.example.digitalnaribarnica.recycleviewer.OffersData;

import java.util.ArrayList;

public class BadgesFragment extends Fragment {

    private String userId = "";

    public BadgesFragment(String userId) {
        this.userId = userId;
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Značke");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);// set drawable icon
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        Repository repository = new Repository();
        BadgesAdapter adapter = new BadgesAdapter(getActivity(), userId, this);
        repository.DohvatiSveZnačke(new BadgeCallback() {
            @Override
            public void onCallback(ArrayList<BadgesData> badges) {
                recyclerView = binding.recycleViewOffer;
                adapter.setBadges(badges);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });

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
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                setHasOptionsMenu(false);
                Fragment selectedFragment = null;
                // ((RegisterActivity) getActivity()).changeOnSearchNavigationBar();
                selectedFragment = new PersonFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
        }

        return true;
    }
}
