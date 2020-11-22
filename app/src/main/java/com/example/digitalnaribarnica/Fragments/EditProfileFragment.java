package com.example.digitalnaribarnica.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.databinding.FragmentEditProfileBinding;
import com.example.digitalnaribarnica.databinding.FragmentOfferDetailBinding;

public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    private Button btnDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        btnDetails = binding.btnDetaljiPonude;

        btnDetails.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment = null;

            @Override
            public void onClick(View v) {
                selectedFragment = new OfferDetailFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

        return  view;
    }
}
