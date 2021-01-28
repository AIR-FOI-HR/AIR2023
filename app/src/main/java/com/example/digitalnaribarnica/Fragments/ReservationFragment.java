package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.badges.BadgeCallback;
import com.example.badges.BadgesData;
import com.example.badges.BadgesRepository;
import com.example.badges.CustomDialogBadge;
import com.example.database.FirestoreService;
import com.example.database.User;
import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.repository.Listener.FirestoreCallback;
import com.example.repository.Listener.FirestoreOffer;
import com.example.digitalnaribarnica.R;
import com.example.repository.Repository;
import com.example.repository.Listener.RezervationCallback;
import com.example.digitalnaribarnica.databinding.FragmentReservationBinding;
import com.example.digitalnaribarnica.recycleviewer.ConfirmedRequestsAdapter;
import com.example.repository.Data.OffersData;
import com.example.digitalnaribarnica.recycleviewer.ReservationsAdapter;
import com.example.repository.Data.ReservationsData;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.example.digitalnaribarnica.recycleviewer.RequestsAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class ReservationFragment extends Fragment {

    private String userID;
    public String searchText;

    FragmentReservationBinding binding;

    public RecyclerView recyclerView;

    Button buttonReservation;
    Button buttonReservationHistory;
    Button buttonRequest;
    Button buttonAccepted;

    public Boolean onMyReservations = true;
    public Boolean onRequests = false;
    public Boolean onAcceptedRequests = false;
    public Boolean onReservationsHistory = false;

    Boolean startDontSearch = true;
    Boolean isSearching = false;
    Boolean fromReview = false;

    MaterialButtonToggleGroup toggleButtonGroup;

    SearchView searchViewThisSearch;
    MenuItem itemThisSearch;


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

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        toggleButtonGroup = binding.toggleButton;

        buttonReservation=binding.myReservationsButton;
        buttonRequest=binding.requestsButton;
        buttonAccepted=binding.acceptedRequestsButton;
        buttonReservationHistory=binding.reservationHistory;
        recyclerView = binding.recyclerReservations;

        if(fromReview && ((RegisterActivity) getActivity()).buyer){
            fromReview = false;
            buttonReservation.setVisibility(View.VISIBLE);
            buttonReservationHistory.setVisibility(View.VISIBLE);
            buttonRequest.setVisibility(View.GONE);
            buttonAccepted.setVisibility(View.GONE);

            toggleButtonGroup.check(R.id.reservation_history);
            buttonReservationHistory.setClickable(false);

            showReservationHistory();
        }

        else if(fromReview && !((RegisterActivity) getActivity()).buyer){
            fromReview = false;
            buttonReservation.setVisibility(View.GONE);
            buttonReservationHistory.setVisibility(View.GONE);
            buttonRequest.setVisibility(View.VISIBLE);
            buttonAccepted.setVisibility(View.VISIBLE);

            toggleButtonGroup.check(R.id.acceptedRequests_button);
            buttonAccepted.setClickable(false);

            confirmedRequests();;
        }

        else if (((RegisterActivity) getActivity()).buyer){
            buttonReservation.setVisibility(View.VISIBLE);
            buttonReservationHistory.setVisibility(View.VISIBLE);
            buttonRequest.setVisibility(View.GONE);
            buttonAccepted.setVisibility(View.GONE);

            toggleButtonGroup.check(R.id.myReservations_button);
            buttonReservation.setClickable(false);

            showMyReservations();
        }

        else{
            buttonReservation.setVisibility(View.GONE);
            buttonReservationHistory.setVisibility(View.GONE);
            buttonRequest.setVisibility(View.VISIBLE);
            buttonAccepted.setVisibility(View.VISIBLE);

            toggleButtonGroup.check(R.id.requests_button);
            buttonRequest.setClickable(false);

            showRequests();
        }

        Repository repository = new Repository();

        repository.DohvatiKorisnikaPoID(userID, new FirestoreCallback() {
            @Override
            public void onCallback(User user) {
                Integer purchases = user.getNumberOfPurchases();
                int compareTen = purchases.compareTo(10);
                int compareTwenty = purchases.compareTo(20);
                int compareThirty = purchases.compareTo(30);

                BadgesRepository badgesRepository=new BadgesRepository();
                if(((compareTen > 0 || compareTen == 0) && compareTwenty < 0) && !user.getBadgeBuyerURL().contains("broncana"))
                {
                    badgesRepository.DohvatiZnackuPoNazivu("Brončana značka kupca", new BadgeCallback() {
                        @Override
                        public void onCallback(ArrayList<BadgesData> badges) {
                            /*
                            badgesRepository.DodijeliZnackuKupcu(user,badges.get(0));
                            CustomDialogBadge customDialogBadge= new CustomDialogBadge(getContext(), badges.get(0).getBadgeUrl());
                            customDialogBadge.PokaziNagradu();
                             */
                            CustomDialogBadge customDialogBadge=new CustomDialogBadge();
                            customDialogBadge.setContexPrikazivanja(getContext());
                            customDialogBadge.setData(user,badges.get(0));
                            customDialogBadge.izvrsiUpdateKupca();
                            customDialogBadge.prikaziNagraduKorisniku();
                        }
                    });
                }
                else if(((compareTwenty > 0 || compareTwenty == 0) && compareThirty < 0) && !user.getBadgeBuyerURL().contains("srebrna"))
                {
                    badgesRepository.DohvatiZnackuPoNazivu("Srebrna značka kupca", new BadgeCallback() {
                        @Override
                        public void onCallback(ArrayList<BadgesData> badges) {
                            /*
                            badgesRepository.DodijeliZnackuKupcu(user,badges.get(0));
                            CustomDialogBadge customDialogBadge=new CustomDialogBadge(getContext(), badges.get(0).getBadgeUrl());
                            customDialogBadge.PokaziNagradu();
                             */
                            CustomDialogBadge customDialogBadge=new CustomDialogBadge();
                            customDialogBadge.setContexPrikazivanja(getContext());
                            customDialogBadge.setData(user,badges.get(0));
                            customDialogBadge.izvrsiUpdateKupca();
                            customDialogBadge.prikaziNagraduKorisniku();
                        }
                    });
                }
                else if((compareThirty > 0 || compareThirty == 0) && !user.getBadgeBuyerURL().contains("zlatna"))
                {
                    badgesRepository.DohvatiZnackuPoNazivu("Zlatna značka kupca", new BadgeCallback() {
                        @Override
                        public void onCallback(ArrayList<BadgesData> badges) {
                            /*
                            badgesRepository.DodijeliZnackuKupcu(user,badges.get(0));
                            CustomDialogBadge customDialogBadge=new CustomDialogBadge(getContext(), badges.get(0).getBadgeUrl());
                            customDialogBadge.PokaziNagradu();
                             */
                            CustomDialogBadge customDialogBadge=new CustomDialogBadge();
                            customDialogBadge.setContexPrikazivanja(getContext());
                            customDialogBadge.setData(user,badges.get(0));
                            customDialogBadge.izvrsiUpdateKupca();
                            customDialogBadge.prikaziNagraduKorisniku();
                        }
                    });
                }
            }
        });

        toggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.myReservations_button:
                            buttonReservation.setClickable(false);
                            buttonReservationHistory.setClickable(true);
                            buttonRequest.setClickable(true);
                            buttonAccepted.setClickable(true);

                            toggleButtonGroup.uncheck(R.id.reservation_history);
                            toggleButtonGroup.uncheck(R.id.requests_button);
                            toggleButtonGroup.uncheck(R.id.acceptedRequests_button);
                            break;

                        case R.id.reservation_history:
                            buttonReservation.setClickable(true);
                            buttonReservationHistory.setClickable(false);
                            buttonRequest.setClickable(true);
                            buttonAccepted.setClickable(true);

                            toggleButtonGroup.uncheck(R.id.myReservations_button);
                            toggleButtonGroup.uncheck(R.id.requests_button);
                            toggleButtonGroup.uncheck(R.id.acceptedRequests_button);
                            break;

                        case R.id.requests_button:
                            buttonReservation.setClickable(true);
                            buttonReservationHistory.setClickable(true);
                            buttonRequest.setClickable(false);
                            buttonAccepted.setClickable(true);

                            toggleButtonGroup.uncheck(R.id.myReservations_button);
                            toggleButtonGroup.uncheck(R.id.reservation_history);
                            toggleButtonGroup.uncheck(R.id.acceptedRequests_button);
                            break;

                        case R.id.acceptedRequests_button:
                            buttonReservation.setClickable(true);
                            buttonReservationHistory.setClickable(true);
                            buttonRequest.setClickable(true);
                            buttonAccepted.setClickable(false);

                            toggleButtonGroup.uncheck(R.id.myReservations_button);
                            toggleButtonGroup.uncheck(R.id.reservation_history);
                            toggleButtonGroup.uncheck(R.id.requests_button);
                            break;
                    }
                }
                /*else{
                    switch (checkedId) {
                        case R.id.myReservations_button:
                            toggleButtonGroup.check(R.id.myReservations_button);

                            break;
                    }
                }*/
            }
        });

        buttonReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyReservations();
            }
        });

        buttonReservationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservationHistory();
            }
        });

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showRequests();
            }
        });

        buttonAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearching = false;
                confirmedRequests();
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
                            if(reservations.get(i).getCustomerID().equals(userID) && reservations.get(i).getStatus().equals("Nepotvrđeno")) {
                                if(deletedOldItems(reservations.get(i)) != 0) {
                                    deletedReservations += deletedOldItems(reservations.get(i));
                                }
                                else {
                                    reservationList.add(reservations.get(i));
                                }
                            }
                            else if(reservations.get(i).getCustomerID().equals(userID) && reservations.get(i).getStatus().equals("Potvrđeno")){
                                reservationList.add(reservations.get(i));
                            }
                        }

                        ArrayList<ReservationsData> reservationListSearched = new ArrayList<ReservationsData>();

                        for (ReservationsData d : reservationList) {
                            repository.DohvatiPonuduPrekoIdPonude(d.getOfferID(), new FirestoreOffer() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    if (offersData.get(0).getName().toLowerCase().contains(search.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(search.toLowerCase())) {
                                        reservationListSearched.add(d);
                                        reservationListSearched.sort(Comparator.comparing(ReservationsData::getDate));
                                        adapter.setReservations(reservationListSearched);
                                    }
                                }
                            });
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

        else if(onReservationsHistory){
            ReservationsAdapter adapter = new ReservationsAdapter(getActivity(), this, userID);
            Repository repository = new Repository();
            repository.DohvatiRezervacije1(offersData -> {
                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onCallback(ArrayList<ReservationsData> reservations) {
                        for (int i = 0; i < reservations.size(); i++) {
                            if(reservations.get(i).getCustomerID().equals(userID) && (reservations.get(i).getStatus().equals("Uspješno")) || reservations.get(i).getStatus().equals("Neuspješno")) {
                                    reservationList.add(reservations.get(i));
                            }
                        }

                        ArrayList<ReservationsData> reservationListSearched = new ArrayList<ReservationsData>();

                        for (ReservationsData d : reservationList) {
                            repository.DohvatiPonuduPrekoIdPonude(d.getOfferID(), new FirestoreOffer() {
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    if (offersData.get(0).getName().toLowerCase().contains(search.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(search.toLowerCase())) {
                                         reservationListSearched.add(d);
                                         reservationListSearched.sort(Comparator.comparing(ReservationsData::getDate));
                                         adapter.setReservations(reservationListSearched);
                                    }
                                }
                            });
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem((R.id.action_search));
        menu.findItem((R.id.language)).setVisible(false);
        menu.findItem((R.id.filter_menu)).setVisible(false);
        menu.findItem((R.id.all_offers_menu)).setVisible(false);
        menu.findItem((R.id.my_offers_menu)).setVisible(false);
        menu.findItem((R.id.sort_offers_menu)).setVisible(false);
        SearchView searchView = (SearchView) item.getActionView();

        itemThisSearch = item;
        searchViewThisSearch = searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                    searchText = query;
                    searchReservation(query);
                    if (query.equals("")) {
                        isSearching = false;
                    }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(!startDontSearch) {
                    searchText = newText;
                    searchReservation(newText);
                    if (newText.equals("")) {
                        isSearching = false;
                    }
                }
                startDontSearch = false;
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
            if(reservation.getStatus().equals("Nepotvrđeno")) {
                firestoreService.deleteReservation(reservation.getReservationID(), "Rezervation");
                ++numberOfDeletedItems;
            }
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
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCallback(ArrayList<ReservationsData> reservations) {
                    int deletedReservations = 0;
                    for (int i = 0; i < reservations.size(); i++) {
                        if(reservations.get(i).getCustomerID().equals(userID) && reservations.get(i).getStatus().equals("Nepotvrđeno")) {
                            if(deletedOldItems(reservations.get(i)) != 0) {
                                deletedReservations += deletedOldItems(reservations.get(i));
                            }
                            else {
                                reservationList.add(reservations.get(i));
                            }
                        }
                        else if(reservations.get(i).getCustomerID().equals(userID) && reservations.get(i).getStatus().equals("Potvrđeno")){
                            reservationList.add(reservations.get(i));
                        }
                    }

                    if (deletedReservations > 0) {
                        showDialog(getActivity(), "Obrisane rezervacije",
                                "Zbog isteka vremena, obrisan je sljedeći broj Vaših rezervacija: "
                                        + String.valueOf(deletedReservations), "deletedFirebase");
                    }

                    reservationList.sort(Comparator.comparing(ReservationsData::getDate));
                    adapter.setReservations(reservationList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onCallback(ArrayList<OffersData> offersData) {
                                if(offersData.get(0).getIdKorisnika().equals(userID)){
                                    if(isSearching){
                                        if (offersData.get(0).getName().toLowerCase().contains(searchText.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                                            reservationList.add(rezervations.get(finalI));
                                            reservationList.sort(Comparator.comparing(ReservationsData::getDate));
                                            adapterRequest.setRequests(reservationList);
                                        }}
                                    else {
                                        reservationList.add(rezervations.get(finalI));
                                        reservationList.sort(Comparator.comparing(ReservationsData::getDate));
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

    public void showMyReservations(){
        onMyReservations = true;
        onReservationsHistory = false;
        onRequests = false;
        onAcceptedRequests = false;
        isSearching = false;

        ReservationsAdapter adapterFromRequests = new ReservationsAdapter(getActivity(),ReservationFragment.this, userID);
        ArrayList<ReservationsData> reservationList = new ArrayList<>();
        Repository repository=new Repository();
        repository.DohvatiRezervacije1(new RezervationCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCallback(ArrayList<ReservationsData> reservations) {
                int deletedReservations = 0;
                for (int i = 0; i < reservations.size(); i++) {
                    if(reservations.get(i).getCustomerID().equals(userID) && reservations.get(i).getStatus().equals("Nepotvrđeno")) {
                        if(deletedOldItems(reservations.get(i)) != 0) {
                            deletedReservations += deletedOldItems(reservations.get(i));
                        }
                        else {
                            reservationList.add(reservations.get(i));
                        }
                        }
                    else if(reservations.get(i).getCustomerID().equals(userID) && reservations.get(i).getStatus().equals("Potvrđeno")){
                        reservationList.add(reservations.get(i));
                    }
                }

                if (deletedReservations > 0) {
                    showDialog(getActivity(), "Obrisane rezervacije",
                            "Zbog isteka vremena, obrisan je sljedeći broj Vaših rezervacija: "
                                    + String.valueOf(deletedReservations), "deletedFirebase");}

                reservationList.sort(Comparator.comparing(ReservationsData::getDate));
                adapterFromRequests.setReservations(reservationList);
            }
        });

        recyclerView.setAdapter(adapterFromRequests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void showReservationHistoryDelete(){
        if(isSearching) {

            ReservationsAdapter adapter = new ReservationsAdapter(getActivity(), this, userID);
            Repository repository = new Repository();
            repository.DohvatiRezervacije1(offersData -> {
                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onCallback(ArrayList<ReservationsData> reservations) {
                        for (int i = 0; i < reservations.size(); i++) {
                            if (reservations.get(i).getCustomerID().equals(userID) && (reservations.get(i).getStatus().equals("Uspješno")) || reservations.get(i).getStatus().equals("Neuspješno")) {
                                reservationList.add(reservations.get(i));
                            }
                        }

                        ArrayList<ReservationsData> reservationListSearched = new ArrayList<ReservationsData>();

                        for (ReservationsData d : reservationList) {
                            repository.DohvatiPonuduPrekoIdPonude(d.getOfferID(), new FirestoreOffer() {
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    if (offersData.get(0).getName().toLowerCase().contains(searchText.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                                        reservationListSearched.add(d);
                                        adapter.setReservations(reservationListSearched);
                                    }
                                }
                            });
                        }
                        reservationListSearched.sort(Comparator.comparing(ReservationsData::getDate));
                        adapter.setReservations(reservationListSearched);

                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                });
            });
        }
        else{
            showReservationHistory();
        }
    }

    public void showReservationHistory(){
        onMyReservations = false;
        onReservationsHistory = true;
        onRequests = false;
        onAcceptedRequests = false;
        isSearching = false;

        ReservationsAdapter adapterFromRequests = new ReservationsAdapter(getActivity(),ReservationFragment.this, userID);
        ArrayList<ReservationsData> reservationList = new ArrayList<>();
        Repository repository=new Repository();
        repository.DohvatiRezervacije1(new RezervationCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCallback(ArrayList<ReservationsData> reservations) {
                int deletedReservations = 0;
                for (int i = 0; i < reservations.size(); i++) {
                    if(reservations.get(i).getCustomerID().equals(userID) && (reservations.get(i).getStatus().equals("Uspješno") || reservations.get(i).getStatus().equals("Neuspješno"))){
                            reservationList.add(reservations.get(i));
                    }
                }

                reservationList.sort(Comparator.comparing(ReservationsData::getDate));
                adapterFromRequests.setReservations(reservationList);
            }
        });

        recyclerView.setAdapter(adapterFromRequests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showRequests(){
        onMyReservations = false;
        onReservationsHistory = false;
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
                                    reservationList.sort(Comparator.comparing(ReservationsData::getDate));
                                    Collections.reverse(reservationList);
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

    private void confirmedRequests() {
        onMyReservations = false;
        onReservationsHistory = false;
        onRequests = false;
        onAcceptedRequests = true;

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
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onCallback(ArrayList<OffersData> offersData) {
                                if(offersData.get(0).getIdKorisnika().equals(userID)){
                                    if(isSearching){
                                        if (offersData.get(0).getName().toLowerCase().contains(searchText.toLowerCase()) || offersData.get(0).getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                                            reservationList.add(reservations.get(finalI));
                                            reservationList.sort(Comparator.comparing(ReservationsData::getDate));
                                            Collections.reverse(reservationList);
                                            adapterConfirmedRequests.setConfirmedRequests(reservationList);
                                        }
                                    }
                                    else {
                                        reservationList.add(reservations.get(finalI));
                                        reservationList.sort(Comparator.comparing(ReservationsData::getDate));
                                        Collections.reverse(reservationList);
                                        adapterConfirmedRequests.setConfirmedRequests(reservationList);
                                    }
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

    public void destroySearch() {
        MenuItemCompat.collapseActionView(itemThisSearch);
    }
}
