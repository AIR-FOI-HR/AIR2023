package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.database.Fish;
import com.example.database.Location;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.Repository;
import  com.example.digitalnaribarnica.databinding.FilterOffersBinding;

import com.example.digitalnaribarnica.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

public class FilterOffersFragment extends Fragment {

    FilterOffersBinding binding;

    private AutoCompleteTextView editFishSpecies;
    private AutoCompleteTextView editLocations;

    private RangeSeekBar rangePrice;

    private TextView rangeMinimum;
    private TextView rangeMaximum;

    private CheckBox smallRadio;
    private CheckBox mediumRadio;
    private CheckBox largeRadio;

    private Button btnFilter;
    private Button btnLeastExpensive;
    private Button btnMostExpensive;

    private Boolean leastExpensiveClicked = false;
    private Boolean mostExpensiveClicked = false;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FilterOffersBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);

        editFishSpecies = binding.autoCompleteFish;
        editLocations = binding.autoCompleteLocation;
        rangePrice = binding.rangeSeekBar;
        rangeMinimum = binding.rangeMin;
        rangeMaximum = binding.rangeMax;

        smallRadio = binding.radioSmall;
        mediumRadio = binding.radioMedium;
        largeRadio = binding.radioLarge;

        btnFilter = binding.btnFilter;
        btnLeastExpensive = binding.btnLeastExpensive;
        btnMostExpensive = binding.btnMostExpensive;

        Repository repository =new Repository();

        repository.DohvatiRibe(fishes -> {
            ArrayList<String> fishArrayList=new ArrayList<>();
            for(Fish riba: fishes){
                fishArrayList.add(riba.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.autocomplete_custom, R.id.autocomplete_text, fishArrayList);
            adapter.notifyDataSetChanged();
            editFishSpecies.setAdapter(adapter);
        });

        repository.DohvatiLokacije(locations -> {
            ArrayList<String> locationArrayList=new ArrayList<>();
            for(Location location: locations){
                locationArrayList.add(location.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.autocomplete_custom, R.id.autocomplete_text, locationArrayList);
            adapter.notifyDataSetChanged();
            editLocations.setAdapter(adapter);
        });

        rangePrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                rangeMinimum.setText(String.valueOf(minValue));
                rangeMaximum.setText(String.valueOf(maxValue));

            }
        });

        btnLeastExpensive.setOnClickListener(v -> {
            if(leastExpensiveClicked){
                leastExpensiveClicked = false;
            }
            else {
                leastExpensiveClicked = true;
                mostExpensiveClicked = false;
            }
        });

        btnMostExpensive.setOnClickListener(v -> {
            if(mostExpensiveClicked){
                mostExpensiveClicked = false;
            }
            else {
                mostExpensiveClicked = true;
                leastExpensiveClicked = false;
            }
        });

        rangePrice.setNotifyWhileDragging(true);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).changeOnSearchNavigationBar();
                selectedFragment = new SearchFragment(editFishSpecies.getText().toString(), editLocations.getText().toString(),rangeMinimum.getText().toString(), rangeMaximum.getText().toString(), smallRadio.isChecked(),
                        mediumRadio.isChecked(), largeRadio.isChecked(), leastExpensiveClicked, mostExpensiveClicked);
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();    }
        });

        return view;
    }



}
