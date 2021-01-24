package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String userId = "";
    private RelativeLayout roleBuyer;
    private RelativeLayout roleSeller;
    private ImageView statusBuyer;
    private ImageView statusSeller;
    private TextView textBuyer;
    private TextView textSeller;


    public HomeFragment(String userId) {
        this.userId = userId;
    }

    @SuppressLint({"RestrictedApi", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        roleBuyer = binding.roleBuyer;
        roleSeller = binding.roleSeller;

        statusBuyer = binding.statusBuyer;
        statusSeller = binding.statusSeller;

        textBuyer = binding.textbuyer;
        textSeller = binding.textseller;

        if(((RegisterActivity) getActivity()).buyer){
            statusBuyer.setVisibility(view.VISIBLE);
            statusSeller.setVisibility(view.INVISIBLE);
            textBuyer.setVisibility(view.INVISIBLE);
            textSeller.setVisibility(view.VISIBLE);
        }else{
            statusBuyer.setVisibility(view.INVISIBLE);
            statusSeller.setVisibility(view.VISIBLE);
            textBuyer.setVisibility(view.VISIBLE);
            textSeller.setVisibility(view.INVISIBLE);
        }

        roleBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity) getActivity()).changeRole(true);
                statusBuyer.setVisibility(view.VISIBLE);
                statusSeller.setVisibility(view.INVISIBLE);
                textBuyer.setVisibility(view.INVISIBLE);
                textSeller.setVisibility(view.VISIBLE);
            }
        });

        roleSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity) getActivity()).changeRole(false);
                statusBuyer.setVisibility(view.INVISIBLE);
                statusSeller.setVisibility(view.VISIBLE);
                textBuyer.setVisibility(view.VISIBLE);
                textSeller.setVisibility(view.INVISIBLE);
            }
        });

        return view;
    };
}
