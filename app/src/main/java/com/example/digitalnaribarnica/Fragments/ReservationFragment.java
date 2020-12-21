package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.Rezervation;
import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.FirestoreOffer;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.RezervationCallback;
import com.example.digitalnaribarnica.databinding.FragmentReservationBinding;
import com.example.digitalnaribarnica.recycleviewer.OfferAdapter;
import com.example.digitalnaribarnica.recycleviewer.OffersData;
import com.example.digitalnaribarnica.recycleviewer.ReservationsAdapter;
import com.example.digitalnaribarnica.recycleviewer.ReservationsData;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.example.digitalnaribarnica.recycleviewer.RequestsAdapter;
import com.example.digitalnaribarnica.recycleviewer.RequestsData;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;


public class ReservationFragment extends Fragment {

    private String userID;

    FragmentReservationBinding binding;

    RecyclerView recyclerView;

    Button buttonReservation;
    Button buttonRequest;
    Button buttonAccepted;

    MaterialButtonToggleGroup toggleButtonGroup;

    public ReservationFragment(String userId){
        this.userID = userId;
    }

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

        ArrayList<ReservationsData> reservations = new ArrayList<>();

        ReservationsAdapter adapter = new ReservationsAdapter(getActivity(),this);

        ArrayList<ReservationsData> reservationList = new ArrayList<>();
        Repository repository=new Repository();
        repository.DohvatiRezervacije1(new RezervationCallback() {
            @Override
            public void onCallback(ArrayList<ReservationsData> rezervations) {
                for (int i = 0; i < rezervations.size(); i++) {
                    if(rezervations.get(i).getCustomerID().equals(userID))
                        reservationList.add(rezervations.get(i));
                }

                adapter.setReservations(reservationList);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
            public void onClick(View view) {
                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                Repository repository=new Repository();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @Override
                    public void onCallback(ArrayList<ReservationsData> rezervations) {
                        for (int i = 0; i < rezervations.size(); i++) {
                            if(rezervations.get(i).getCustomerID().equals(userID))
                                reservationList.add(rezervations.get(i));
                        }

                       adapter.setReservations(reservationList);
                    }
                });

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
/*
        buttonReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Rezervation> reservation=new ArrayList<>();
                Repository repository=new Repository();
                repository.DohvatiRezervacije(new RezervationCallback() {
                    @Override
                    public void onCallback(ArrayList<Rezervation> rezervations1) {
                        reservations.clear();
                        for(Rezervation r: rezervations1){
                            //Toast.makeText(getActivity(), r.getDate().toDate().toString(), Toast.LENGTH_SHORT).show();

                            Calendar calendar = DateParse.dateToCalendar(r.getDate().toDate());
                            reservations.add(new ReservationsData(r.getOfferID(),calendar.getTime().toString(), "https://i.pinimg.com/originals/dd/54/b0/dd54b0fb0c8f4af950bfb3c15baeea8b.jpg", String.valueOf(r.getKolicina()), "5","https://www.iconpacks.net/icons/1/free-badge-icon-1361-thumb.png"));
                        }
                        adapter.setReservations(reservations);

                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                });

*/
        RequestsAdapter adapterRequest = new RequestsAdapter(getActivity(), this);
        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                Repository repository=new Repository();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @Override
                    public void onCallback(ArrayList<ReservationsData> rezervations) {

                        for (int i = 0; i < rezervations.size(); i++) {
                            if(rezervations.get(i).getStatus().equals("Nepotvrđeno")) {
                                int finalI = i;
                                repository.DohvatiPonuduPrekoIdPonude(rezervations.get(i).getOfferID(), new FirestoreOffer() {
                                    @Override
                                    public void onCallback(ArrayList<OffersData> offersData) {
                                        if(offersData.get(0).getIdKorisnika().equals(userID)){
                                            reservationList.add(rezervations.get(finalI));
                                            adapterRequest.setRequests(reservationList);
                                        }
                                    }
                                });

                            }
                        }
                    }
                });

                recyclerView.setAdapter(adapterRequest);
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

    public void searchReservation(String search) {
        ArrayList<ReservationsData> reservationListSearched = new ArrayList<>();
        ReservationsAdapter adapter = new ReservationsAdapter(getActivity(), this);
        Repository repository = new Repository();
        repository.DohvatiRezervacije1(offersData -> {
            ArrayList<ReservationsData> reservationList = new ArrayList<>();
            repository.DohvatiRezervacije1(new RezervationCallback() {
                @Override
                public void onCallback(ArrayList<ReservationsData> rezervations) {
                    for (int i = 0; i < rezervations.size(); i++) {
                        if(rezervations.get(i).getCustomerID().equals(userID)) {
                            reservationList.add(rezervations.get(i));
                        }
                    }
                    ArrayList<ReservationsData> reservationListSearched = new ArrayList<ReservationsData>();

                    if(reservationList != null) {
                        for(ReservationsData d : reservationList){
                            repository.DohvatiPonuduPrekoIdPonude(d.getOfferID(), new FirestoreOffer() {
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    if(offersData.get(0).getName().toLowerCase().contains(search.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(search.toLowerCase())){
                                        reservationListSearched.add(d);
                                        adapter.setReservations(reservationListSearched);
                                    }
                                }
                            });
                        }
                    }
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            });
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem((R.id.action_search));

        menu.findItem((R.id.filter_menu)).setVisible(false);
        menu.findItem((R.id.all_offers_menu)).setVisible(false);
        menu.findItem((R.id.my_offers_menu)).setVisible(false);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchReservation(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                searchReservation(newText);
                //showDialog(getActivity(), "Upozorenje", "Želite li sigurno obrisati rezervaciju?");
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TagPolje", "Tu ide kao poz");
            }
        });
        builder.setNegativeButton("Ne", null);
        builder.show();
    }
}
