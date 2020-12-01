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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.Rezervation;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.RezervationCallback;
import com.example.digitalnaribarnica.databinding.FragmentReservationBinding;
import com.example.digitalnaribarnica.recycleviewer.ReservationsAdapter;
import com.example.digitalnaribarnica.recycleviewer.ReservationsData;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class ReservationFragment extends Fragment {

    FragmentReservationBinding binding;

    RecyclerView recyclerView;


    Button buttonReservation;
    Button buttonRequest;
    Button buttonAccepted;

    MaterialButtonToggleGroup toggleButtonGroup;

    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Rezervacije");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));

        setHasOptionsMenu(true);


        binding = FragmentReservationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        toggleButtonGroup =binding.toggleButton;

        buttonReservation=binding.myReservationsButton;
        buttonRequest=binding.requestsButton;
        buttonAccepted=binding.acceptedRequestsButton;

        recyclerView = binding.recyclerReservations;

        ArrayList<ReservationsData> reservations=new ArrayList<>();
         /*


        reservations.add(new ReservationsData("Rezervacija 1","Jelas", "https://i.pinimg.com/originals/dd/54/b0/dd54b0fb0c8f4af950bfb3c15baeea8b.jpg", "25,00 kn", "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 2","Jaruge", "https://i.pinimg.com/originals/21/f1/d2/21f1d20bb776dd8e774d6e36c57dc123.jpg", "30,00 kn", "3","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 3","Garchin", "https://i.pinimg.com/originals/36/3e/27/363e2738af6e7ff65c7ed7d87eaace88.jpg", "30,00 kn", "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 4","Oprisavci", "https://i.pinimg.com/236x/aa/c8/97/aac897078e2f67e83c64f52d688d771a--saltwater-tank-saltwater-aquarium.jpg", "25,00 kn", "1","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 5","Sredanci", "https://www.tportal.hr/media/thumbnail/w1000/119470.jpeg", "30,00 kn", "2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        */
        ArrayList<Rezervation> reservation=new ArrayList<>();
        Repository repository=new Repository();
        repository.DohvatiRezervacije(new RezervationCallback() {
            @Override
            public void onCallback(ArrayList<Rezervation> rezervations) {
                for(Rezervation r: rezervations){
                    Toast.makeText(getActivity(), r.getDate().toDate().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ReservationsAdapter adapter = new ReservationsAdapter(getActivity());
        /*
        adapter.setReservations(reservations);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*

        toggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                List<Integer> listaIDa= toggleButtonGroup.getCheckedButtonIds();
                for(Integer s: listaIDa) {
                    if(s!=checkedId)
                        toggleButtonGroup.uncheck(s);
                }
            }
        });

        */

        toggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.myReservations_button:
                            toggleButtonGroup.uncheck(R.id.requests_button);
                            toggleButtonGroup.uncheck(R.id.acceptedRequests_button);
                            break;
                        case R.id.requests_button:
                            toggleButtonGroup.uncheck(R.id.myReservations_button);
                            toggleButtonGroup.uncheck(R.id.acceptedRequests_button);
                            break;
                        case R.id.acceptedRequests_button:
                            toggleButtonGroup.uncheck(R.id.myReservations_button);
                            toggleButtonGroup.uncheck(R.id.requests_button);
                            break;
                    }
                }
            }
        });


        buttonReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reservations.clear();
                reservations.add(new ReservationsData("Rezervacija 1","Jelas", "https://i.pinimg.com/originals/dd/54/b0/dd54b0fb0c8f4af950bfb3c15baeea8b.jpg", "25,00 kn", "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                reservations.add(new ReservationsData("Rezervacija 2","Jaruge", "https://i.pinimg.com/originals/21/f1/d2/21f1d20bb776dd8e774d6e36c57dc123.jpg", "30,00 kn", "3","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                reservations.add(new ReservationsData("Rezervacija 3","Garchin", "https://i.pinimg.com/originals/36/3e/27/363e2738af6e7ff65c7ed7d87eaace88.jpg", "30,00 kn", "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                reservations.add(new ReservationsData("Rezervacija 4","Oprisavci", "https://i.pinimg.com/236x/aa/c8/97/aac897078e2f67e83c64f52d688d771a--saltwater-tank-saltwater-aquarium.jpg", "25,00 kn", "1","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                reservations.add(new ReservationsData("Rezervacija 5","Sredanci", "https://www.tportal.hr/media/thumbnail/w1000/119470.jpeg", "30,00 kn", "2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                adapter.setReservations(reservations);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });


        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservations.clear();
                reservations.add(new ReservationsData("Rezervacija 5","Brod","https://i.pinimg.com/originals/dd/54/b0/dd54b0fb0c8f4af950bfb3c15baeea8b.jpg", "25,00 kn", "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                reservations.add(new ReservationsData("Rezervacija 6","Pozega","https://i.pinimg.com/originals/dd/54/b0/dd54b0fb0c8f4af950bfb3c15baeea8b.jpg", "30,00 kn", "4","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                adapter.setReservations(reservations);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });

        buttonAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservations.clear();

                adapter.setReservations(reservations);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
