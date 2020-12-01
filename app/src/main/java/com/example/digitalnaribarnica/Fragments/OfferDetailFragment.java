package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.database.User;
import com.example.digitalnaribarnica.FirestoreCallback;
import com.example.digitalnaribarnica.FirestoreOffer;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentOfferDetailBinding;
import com.example.digitalnaribarnica.recycleviewer.OffersData;

import java.util.ArrayList;

public class OfferDetailFragment extends Fragment {
    FragmentOfferDetailBinding binding;
    private OffersData offersData;
    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detalji ponude");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));

        binding = FragmentOfferDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.razredRibeOd.setText(offersData.getFishClass());
        binding.cijenaPonudeOd.setText(offersData.getPrice());
        binding.lokacijaPonudeOd.setText(offersData.getDescription());
        binding.nazivPonudeOd.setText(offersData.getName());
        Glide.with(this).load(offersData.getImageurl()).into(binding.slikaRibe);
        Repository repository =new Repository();
        repository.DohvatiKorisnikaPoIDu(offersData.getIdKorisnika(), new FirestoreCallback() {
            @Override
            public void onCallback(User user) {
                binding.imePrezimePonuditelja.setText(user.getFullName());
                Glide.with(getContext()).load(user.getPhoto()).into(binding.slikaProfila);
            }
        });
        //return inflater.inflate(R.layout.fragment_offer_detail,container,false);
        return view;
    }

    public OfferDetailFragment(OffersData offersData) {
        this.offersData = offersData;
    }

    public OfferDetailFragment() {
    }
}
