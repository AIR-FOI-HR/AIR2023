package com.example.digitalnaribarnica.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalnaribarnica.R;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        binding = FragmentReservationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        toggleButtonGroup =binding.toggleButton;




        buttonReservation=binding.myReservationsButton;
        buttonRequest=binding.requestsButton;
        buttonAccepted=binding.acceptedRequestsButton;

        recyclerView = binding.recyclerReservations;

        ArrayList<ReservationsData> reservations=new ArrayList<>();

        reservations.add(new ReservationsData("Rezervacija 1","Jelas", "https://i.pinimg.com/originals/dd/54/b0/dd54b0fb0c8f4af950bfb3c15baeea8b.jpg", "25,00 kn", "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 2","Jaruge", "https://i.pinimg.com/originals/21/f1/d2/21f1d20bb776dd8e774d6e36c57dc123.jpg", "30,00 kn", "3","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 3","Garchin", "https://i.pinimg.com/originals/36/3e/27/363e2738af6e7ff65c7ed7d87eaace88.jpg", "30,00 kn", "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 4","Oprisavci", "https://i.pinimg.com/236x/aa/c8/97/aac897078e2f67e83c64f52d688d771a--saltwater-tank-saltwater-aquarium.jpg", "25,00 kn", "1","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
        reservations.add(new ReservationsData("Rezervacija 5","Sredanci", "https://www.tportal.hr/media/thumbnail/w1000/119470.jpeg", "30,00 kn", "2","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));

        ReservationsAdapter adapter=new ReservationsAdapter(getActivity());
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
}
