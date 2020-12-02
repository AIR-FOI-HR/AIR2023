package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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
    private Button btnMinusSmall;
    private Button btnPlusSmall;
    private Button btnMinusMedium;
    private Button btnPlusMedium;
    private Button btnMinusLarge;
    private Button btnPlusLarge;

    private EditText smallQuantity;
    private EditText mediumQuantity;
    private EditText largeQuantity;

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

        btnMinusSmall = binding.btnMinusSmall;
        btnPlusSmall = binding.btnPlusSmall;
        btnMinusMedium = binding.btnMinusMedium;
        btnPlusMedium = binding.btnPlusMedium;
        btnMinusLarge = binding.btnMinusLarge;
        btnPlusLarge = binding.btnPlusLarge;

        smallQuantity = binding.smallFishQuantity;
        mediumQuantity = binding.mediumFishQuantity;
        largeQuantity = binding.largeFishQuantity;

        smallQuantity.setText("0");
        mediumQuantity.setText("0");
        largeQuantity.setText("0");

        btnPlusSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = smallQuantity.getText().toString();
                smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.1)*100.0)/100.0));
            }
        });

        btnMinusSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = smallQuantity.getText().toString();
                if (Double.parseDouble(currentValue) >= 0.1){
                    smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.1)*100.0)/100.0));
                }
            }
        });

        btnPlusMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mediumQuantity.getText().toString();
                mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+0.2)*100.0)/100.0));
            }
        });

        btnMinusMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mediumQuantity.getText().toString();
                if (Double.parseDouble(currentValue) >= 0.2){
                    mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.2)*100.0)/100.0));
                }
            }
        });

        btnMinusLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = largeQuantity.getText().toString();
                if(Double.parseDouble(currentValue) >= 0.5) {
                    largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue) - 0.5) * 100.0) / 100.0));
                }
            }
        });

        btnPlusLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = largeQuantity.getText().toString();
                largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue) + 0.5)*100.0)/100.0));
            }
        });

        return view;
        //return inflater.inflate(R.layout.fragment_add_offer,container,false);
    }


}
