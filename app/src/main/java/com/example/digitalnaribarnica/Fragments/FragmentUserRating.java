package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.database.User;
import com.example.digitalnaribarnica.FirestoreCallback;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentUserRatingBinding;
import com.example.digitalnaribarnica.recycleviewer.OffersData;
import com.example.digitalnaribarnica.recycleviewer.ReservationsData;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class FragmentUserRating extends Fragment {

    FragmentUserRatingBinding binding;
    private Button rateUser;
    private RatingBar rating;
    private EditText comment;
    private TextView name;

    private ReservationsData reservation;

    String userID ="";

    public FragmentUserRating( String userId) {
        this.userID = userId;
    }

    public FragmentUserRating( String userId, ReservationsData reservation) {
        this.userID = userId;
        this.reservation = reservation;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ocijeni korisnika");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);

        binding = FragmentUserRatingBinding.inflate(inflater,container,false);
        View view =binding.getRoot();
        rateUser = binding.btnOcijeni;
        rating = binding.ratingBar;
        comment = binding.comment;
        name = binding.imePrezime;

        Repository repository = new Repository();

        if(reservation == null) {
            repository.DohvatiKorisnikaPoID(userID, new FirestoreCallback() {
                @Override
                public void onCallback(User user) {
                        name.setText(user.getFullName());
                }
            });
        }
        else{
            repository.DohvatiSvePonude(offersData -> {
                for (int i = 0; i < offersData.size(); i++) {
                    if(offersData.get(i).getOfferID().equals(reservation.getOfferID())) {
                        repository.DohvatiKorisnikaPoID(offersData.get(i).getIdKorisnika(), new FirestoreCallback() {
                            @Override
                            public void onCallback(User user) {
                                name.setText(user.getFullName());
                            }
                        });
                        break;
                    }
                }
            });
        }

        rateUser.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View v) {
                repository.DodajOcjenu(userID, String.valueOf(rating.getRating()), comment.getText().toString());
                StyleableToast.makeText(getActivity(), "Uspješno ocjenjen korisnik", 3, R.style.ToastGreen).show();
                selectedFragment = new ReservationFragment(userID, true);
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });
        return  view;
    }

}
