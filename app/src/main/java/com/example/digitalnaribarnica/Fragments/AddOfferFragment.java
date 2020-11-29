package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.database.Fish;
import com.example.database.Location;
import com.example.digitalnaribarnica.FishCallback;
import com.example.digitalnaribarnica.LocationCallback;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentAddOfferBinding;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class AddOfferFragment extends Fragment {
    FragmentAddOfferBinding binding;
    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nova ponuda");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        binding= FragmentAddOfferBinding.inflate(inflater,container,false);
        View view =binding.getRoot();
        String compareValue = "some value";

        Repository repository =new Repository();
        repository.DohvatiRibe(new FishCallback() {
            @Override
            public void onCallback(ArrayList<Fish> fishes) {
                ArrayList<String> ribe=new ArrayList<>();
                for(Fish riba: fishes){
                    ribe.add(riba.getName());
                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, ribe);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                binding.cbVrstaRibe.setAdapter(adapter);
            }
        });
        repository.DohvatiLokacije(new LocationCallback() {
            @Override
            public void onCallback(ArrayList<Location> locations) {
                ArrayList<String> lokacije=new ArrayList<>();
                for(Location lokacija: locations){
                    lokacije.add(lokacija.getName());
                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, lokacije);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                binding.cbLokacija.setAdapter(adapter);
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_add_offer,container,false);

    }


}
