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

import com.example.database.FirestoreService;
import com.example.database.Rezervation;
import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.FirestoreOffer;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.RezervationCallback;
import com.example.digitalnaribarnica.databinding.FragmentReservationBinding;
import com.example.digitalnaribarnica.recycleviewer.ConfirmedRequestsAdapter;
import com.example.digitalnaribarnica.recycleviewer.OfferAdapter;
import com.example.digitalnaribarnica.recycleviewer.OffersData;
import com.example.digitalnaribarnica.recycleviewer.ReservationsAdapter;
import com.example.digitalnaribarnica.recycleviewer.ReservationsData;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.example.digitalnaribarnica.recycleviewer.RequestsAdapter;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;


public class ReservationFragment extends Fragment {

    private String userID;
    private String searchText;

    FragmentReservationBinding binding;

    public RecyclerView recyclerView;

    Button buttonReservation;
    Button buttonRequest;
    Button buttonAccepted;

    Boolean isSearching = false;
    Boolean onMyReservations = true;
    Boolean onRequests = false;
    Boolean onAcceptedRequests = false;
    Boolean fromReview = false;

    MaterialButtonToggleGroup toggleButtonGroup;

    public ReservationFragment(String userId){
        this.userID = userId;
    }

    public ReservationFragment(String userId, Boolean fromReview){
        this.userID = userId;
        this.fromReview = fromReview;
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);

        binding = FragmentReservationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        toggleButtonGroup =binding.toggleButton;

        buttonReservation=binding.myReservationsButton;
        buttonRequest=binding.requestsButton;
        buttonAccepted=binding.acceptedRequestsButton;
        recyclerView = binding.recyclerReservations;

       /* if(!fromReview) {
            onMyReservations = true;
            onRequests = false;
            onAcceptedRequests = false;
            toggleButtonGroup.check(R.id.myReservations_button);
            toggleButtonGroup.uncheck(R.id.requests_button);
            toggleButtonGroup.uncheck(R.id.acceptedRequests_button);*/

            ArrayList<ReservationsData> reservations = new ArrayList<>();

            ReservationsAdapter adapter = new ReservationsAdapter(getActivity(), this, userID);

            ArrayList<ReservationsData> reservationList = new ArrayList<>();
            Repository repository = new Repository();
            repository.DohvatiRezervacije1(new RezervationCallback() {
                @Override
                public void onCallback(ArrayList<ReservationsData> reservations) {
                    int deletedReservations = 0;
                    for (int i = 0; i < reservations.size(); i++) {
                        if (reservations.get(i).getCustomerID().equals(userID))
                            reservationList.add(reservations.get(i));
                    }

                    if (deletedReservations > 0) {
                        showDialog(getActivity(), "Obrisane rezervacije",
                                "Zbog isteka vremena, obrisan je sljedeći broj Vaših rezervacija: "
                                        + String.valueOf(deletedReservations), "deletedFirebase");
                    }

                    adapter.setReservations(reservationList);
                }
            });

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

      /*  } else{
            fromReview=false;
            toggleButtonGroup.uncheck(R.id.myReservations_button);
            toggleButtonGroup.uncheck(R.id.requests_button);
            toggleButtonGroup.check(R.id.acceptedRequests_button);
            onMyReservations = false;
            onRequests = false;
            onAcceptedRequests = true;
            isSearching = false;
            ConfirmedRequestsAdapter adapterConfirmedRequests = new ConfirmedRequestsAdapter(getActivity(), ReservationFragment.this, userID);
            ArrayList<ReservationsData> reservationList = new ArrayList<>();
            Repository repository=new Repository();
            repository.DohvatiRezervacije1(new RezervationCallback() {
                @Override
                public void onCallback(ArrayList<ReservationsData> reservations) {

                    for (int i = 0; i < reservations.size(); i++) {
                        if(reservations.get(i).getStatus().equals("Potvrđeno")) {
                            Log.d("TagPolje", "onCallback:ghbgndngn ");
                            int finalI = i;
                            repository.DohvatiPonuduPrekoIdPonude(reservations.get(i).getOfferID(), new FirestoreOffer() {
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    if(offersData.get(0).getIdKorisnika().equals(userID)){
                                        reservationList.add(reservations.get(finalI));
                                        adapterConfirmedRequests.setConfirmedRequests(reservationList);
                                    }
                                }
                            });

                        }
                    }
                }
            });

            recyclerView.setAdapter(adapterConfirmedRequests);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }*/

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
                onMyReservations = true;
                onRequests = false;
                onAcceptedRequests = false;
                isSearching = false;
                ReservationsAdapter adapterFromRequests = new ReservationsAdapter(getActivity(),ReservationFragment.this, userID);
                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                Repository repository=new Repository();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @Override
                    public void onCallback(ArrayList<ReservationsData> reservations) {
                        for (int i = 0; i < reservations.size(); i++) {
                            if(reservations.get(i).getCustomerID().equals(userID))
                                reservationList.add(reservations.get(i));
                        }

                        adapterFromRequests.setReservations(reservationList);
                    }
                });

                recyclerView.setAdapter(adapterFromRequests);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });


        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyReservations = false;
                onRequests = true;
                onAcceptedRequests = false;
                isSearching = false;

                RequestsAdapter adapterRequest = new RequestsAdapter(getActivity(), ReservationFragment.this, userID);
                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                Repository repository=new Repository();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @Override
                    public void onCallback(ArrayList<ReservationsData> reservations) {

                        for (int i = 0; i < reservations.size(); i++) {
                            if(reservations.get(i).getStatus().equals("Nepotvrđeno")) {
                                int finalI = i;
                                repository.DohvatiPonuduPrekoIdPonude(reservations.get(i).getOfferID(), new FirestoreOffer() {
                                    @Override
                                    public void onCallback(ArrayList<OffersData> offersData) {
                                        if(offersData.get(0).getIdKorisnika().equals(userID)){
                                            reservationList.add(reservations.get(finalI));
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
                onMyReservations = false;
                onRequests = false;
                onAcceptedRequests = true;
                isSearching = false;
                ConfirmedRequestsAdapter adapterConfirmedRequests = new ConfirmedRequestsAdapter(getActivity(), ReservationFragment.this, userID);
                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                Repository repository=new Repository();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @Override
                    public void onCallback(ArrayList<ReservationsData> reservations) {

                        for (int i = 0; i < reservations.size(); i++) {
                            if(reservations.get(i).getStatus().equals("Potvrđeno")) {
                                int finalI = i;
                                repository.DohvatiPonuduPrekoIdPonude(reservations.get(i).getOfferID(), new FirestoreOffer() {
                                    @Override
                                    public void onCallback(ArrayList<OffersData> offersData) {
                                        if(offersData.get(0).getIdKorisnika().equals(userID)){
                                            reservationList.add(reservations.get(finalI));
                                            adapterConfirmedRequests.setConfirmedRequests(reservationList);
                                       }
                                    }
                                });

                            }
                        }
                    }
                });

                recyclerView.setAdapter(adapterConfirmedRequests);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });

        return view;
    }

    public void searchReservation(String search) {
        isSearching = true;

        if(onMyReservations) {
        ReservationsAdapter adapter = new ReservationsAdapter(getActivity(), this, userID);
        Repository repository = new Repository();
        repository.DohvatiRezervacije1(offersData -> {
            ArrayList<ReservationsData> reservationList = new ArrayList<>();
            repository.DohvatiRezervacije1(new RezervationCallback() {
                @Override
                public void onCallback(ArrayList<ReservationsData> reservations) {
                    int deletedReservations = 0;
                    for (int i = 0; i < reservations.size(); i++) {
                        if (reservations.get(i).getCustomerID().equals(userID)) {
                            if (deletedOldItems(reservations.get(i)) != 0) {
                                deletedReservations += deletedOldItems(reservations.get(i));
                            } else {
                                reservationList.add(reservations.get(i));
                            }
                        }
                    }

                    ArrayList<ReservationsData> reservationListSearched = new ArrayList<ReservationsData>();

                    if (reservationList != null) {
                        for (ReservationsData d : reservationList) {
                            repository.DohvatiPonuduPrekoIdPonude(d.getOfferID(), new FirestoreOffer() {
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    if (offersData.get(0).getName().toLowerCase().contains(search.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(search.toLowerCase())) {
                                        reservationListSearched.add(d);
                                        adapter.setReservations(reservationListSearched);
                                    }
                                }
                            });
                        }
                    }

                    if (deletedReservations > 0) {
                        showDialog(getActivity(), "Obrisane rezervacije",
                                "Zbog isteka vremena, obrisan je sljedeći broj Vaših rezervacija: "
                                        + String.valueOf(deletedReservations), "deletedFirebase");
                    }

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            });
        });
    }
        else if(onRequests){
            refreshRequestsList();
        }
        else{
            confirmedRequests();
        }
    }

    private void confirmedRequests() {
            onMyReservations = false;
            onRequests = false;
            onAcceptedRequests = true;
            isSearching = false;
            ConfirmedRequestsAdapter adapterConfirmedRequests = new ConfirmedRequestsAdapter(getActivity(), ReservationFragment.this, userID);
            ArrayList<ReservationsData> reservationList = new ArrayList<>();
            Repository repository=new Repository();
            repository.DohvatiRezervacije1(new RezervationCallback() {
                @Override
                public void onCallback(ArrayList<ReservationsData> reservations) {

                    for (int i = 0; i < reservations.size(); i++) {
                        if(reservations.get(i).getStatus().equals("Potvrđeno")) {
                            int finalI = i;
                            repository.DohvatiPonuduPrekoIdPonude(reservations.get(i).getOfferID(), new FirestoreOffer() {
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    if(offersData.get(0).getIdKorisnika().equals(userID)){
                                        reservationList.add(reservations.get(finalI));
                                        adapterConfirmedRequests.setConfirmedRequests(reservationList);
                                    }
                                }
                            });

                        }
                    }
                }
            });

            recyclerView.setAdapter(adapterConfirmedRequests);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            toggleButtonGroup.uncheck(R.id.myReservations_button);
            toggleButtonGroup.uncheck(R.id.requests_button);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem((R.id.action_search));

        menu.findItem((R.id.filter_menu)).setVisible(false);
        menu.findItem((R.id.all_offers_menu)).setVisible(false);
        menu.findItem((R.id.my_offers_menu)).setVisible(false);
        menu.findItem((R.id.sort_offers_menu)).setVisible(false);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                    searchText = query;
                    searchReservation(query);
                    if(query.equals("")){
                        isSearching = false;
                    }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                    searchText = newText;
                    searchReservation(newText);
                    if(newText.equals("")){
                        isSearching = false;
                    }
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void showDialog(Activity activity, String title, CharSequence message, String condition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        if(condition.equals("deletedFirebase")){
            builder.setPositiveButton("U redu", null);
        }
        else if(condition.equals("userDeleted")) {
            builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("TagPolje", "Tu ide kao poz");
                }
            });
            builder.setNegativeButton("Ne", null);
        }
        builder.show();
    }

    private int deletedOldItems(ReservationsData reservation){
        int numberOfDeletedItems = 0;
        Calendar now = Calendar.getInstance();
        Calendar calendar = DateParse.dateToCalendar(reservation.getDate().toDate());
        calendar.add(Calendar.SECOND, 1800);

        if (calendar.getTimeInMillis() < now.getTimeInMillis()) {
            FirestoreService firestoreService = new FirestoreService();
            firestoreService.deleteReservation(reservation.getReservationID(), "Rezervation");
            ++numberOfDeletedItems;
        }
        return numberOfDeletedItems;
    }

    public void refreshReservationList(){
        if(!isSearching) {

            ArrayList<ReservationsData> reservations = new ArrayList<>();

            ReservationsAdapter adapter = new ReservationsAdapter(getActivity(), this, userID);

            ArrayList<ReservationsData> reservationList = new ArrayList<>();
            Repository repository = new Repository();
            repository.DohvatiRezervacije1(new RezervationCallback() {
                @Override
                public void onCallback(ArrayList<ReservationsData> reservations) {
                    int deletedReservations = 0;
                    for (int i = 0; i < reservations.size(); i++) {
                        if (reservations.get(i).getCustomerID().equals(userID)) {
                            if (deletedOldItems(reservations.get(i)) != 0) {
                                deletedReservations += deletedOldItems(reservations.get(i));
                            } else {
                                reservationList.add(reservations.get(i));
                            }
                        }
                    }
                    if (deletedReservations > 0) {
                        showDialog(getActivity(), "Obrisane rezervacije",
                                "Zbog isteka vremena, obrisan je sljedeći broj Vaših rezervacija: "
                                        + String.valueOf(deletedReservations), "deletedFirebase");
                    }

                    adapter.setReservations(reservationList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Log.d("TagPolje", "Trebalo bi uc za refreshanje");
                }
            });
        }
        else{
            searchReservation(searchText);
        }
    }


    public void refreshRequestsList(){
        RequestsAdapter adapterRequest = new RequestsAdapter(getActivity(), this, userID);
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
                                    if(isSearching){
                                        if (offersData.get(0).getName().toLowerCase().contains(searchText.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                                            reservationList.add(rezervations.get(finalI));
                                            adapterRequest.setRequests(reservationList);
                                        }}
                                    else {
                                        reservationList.add(rezervations.get(finalI));
                                        adapterRequest.setRequests(reservationList);
                                    }
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

    public void refreshConfirmedRequestsList(){
        RequestsAdapter adapterRequest = new RequestsAdapter(getActivity(), this, userID);
        ArrayList<ReservationsData> reservationList = new ArrayList<>();
        Repository repository=new Repository();
        repository.DohvatiRezervacije1(new RezervationCallback() {
            @Override
            public void onCallback(ArrayList<ReservationsData> rezervations) {

                for (int i = 0; i < rezervations.size(); i++) {
                    if(rezervations.get(i).getStatus().equals("Potvrđeno")) {
                        int finalI = i;
                        repository.DohvatiPonuduPrekoIdPonude(rezervations.get(i).getOfferID(), new FirestoreOffer() {
                            @Override
                            public void onCallback(ArrayList<OffersData> offersData) {
                                if(offersData.get(0).getIdKorisnika().equals(userID)){
                                    if(isSearching){
                                        if (offersData.get(0).getName().toLowerCase().contains(searchText.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                                            reservationList.add(rezervations.get(finalI));
                                            adapterRequest.setRequests(reservationList);
                                        }}
                                    else {
                                        reservationList.add(rezervations.get(finalI));
                                        adapterRequest.setRequests(reservationList);
                                    }
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
}
