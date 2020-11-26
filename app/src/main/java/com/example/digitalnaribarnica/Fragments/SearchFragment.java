package com.example.digitalnaribarnica.Fragments;

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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.databinding.FragmentSearchBinding;
import com.example.digitalnaribarnica.recycleviewer.OffersData;
import com.example.digitalnaribarnica.recycleviewer.OfferAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ponude");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));

        setHasOptionsMenu(true);
        recyclerView = binding.recycleViewOffer;

        ArrayList<OffersData> offers=new ArrayList<>();
        offers.add(new OffersData("Prva ponuda","Varaždin", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "25,00 kn", "Razred: 2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Druga ponuda","Zagreb", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "30,00 kn", "Razred: 3","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Treća ponuda","Varaždin", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "30,00 kn", "Razred: 2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Četvrta ponuda","Varaždin", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "25,00 kn", "Razred: 1","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        offers.add(new OffersData("Peta ponuda","Čakovec", "https://www.pngitem.com/pimgs/m/263-2638542_fishing-icon-png-fish-circle-png-transparent-png.png", "30,00 kn", "Razred: 2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));

        OfferAdapter adapter=new OfferAdapter(getActivity());
        adapter.setOffers(offers);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        super.onCreateOptionsMenu(menu, inflater);
    }
}