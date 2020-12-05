package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.database.CallbackUser;
import com.example.database.FirestoreService;
import com.example.database.Fish;
import com.example.database.Location;
import com.example.database.Offer;
import com.example.database.User;
import com.example.digitalnaribarnica.FirestoreCallback;
import com.example.digitalnaribarnica.FishCallback;
import com.example.digitalnaribarnica.LocationCallback;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.databinding.FragmentAddOfferBinding;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class AddOfferFragment extends Fragment {
    FragmentAddOfferBinding binding;

    private Button btnSaveNewOffer;
    private Button btnCancel;

    private Button btnMinusSmall;
    private Button btnPlusSmall;
    private Button btnMinusMedium;
    private Button btnPlusMedium;
    private Button btnMinusLarge;
    private Button btnPlusLarge;

    private AutoCompleteTextView fishSpecies;
    private AutoCompleteTextView location;
    private EditText price;
    private EditText smallQuantity;
    private EditText mediumQuantity;
    private EditText largeQuantity;

    private String userId = "";

    public AddOfferFragment (String userId){
        this.userId = userId;
    }

    @SuppressLint({"RestrictedApi", "SetTextI18n"})
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

        btnSaveNewOffer = binding.btnAdd;
        btnCancel = binding.btnCancel;

        price = binding.priceOffer;

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

        smallQuantity.setFilters(new InputFilter[] { filterDecimals });
        mediumQuantity.setFilters(new InputFilter[] { filterDecimals });
        largeQuantity.setFilters(new InputFilter[] { filterDecimals });

        price.setFilters(new InputFilter[]{filterDecimals});

        btnPlusSmall.setOnClickListener(view1 -> {
            String currentValue = smallQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.1)*100.0)/100.0));
        });

        btnMinusSmall.setOnClickListener(view12 -> {
            String currentValue = smallQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            if (Double.parseDouble(currentValue) >= 0.1){
                smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.1)*100.0)/100.0));
            } else if(Double.parseDouble(currentValue) < 0.1 && Double.parseDouble(currentValue) > 0) {
                smallQuantity.setText("0");
            }
        });

        smallQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String currentValue = smallQuantity.getText().toString();
                if(currentValue.equals(".")){
                    currentValue = "";
                    smallQuantity.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        smallQuantity.setOnFocusChangeListener((v, fokusiran) -> {
            if (!fokusiran) {
                if (smallQuantity.getText().length() > 1) {
                    if (smallQuantity.getText().toString().charAt(0) == '0' && smallQuantity.getText().toString().charAt(1) != '.') {
                        smallQuantity.setText(smallQuantity.getText().toString().substring(1));
                    }
                    if (smallQuantity.getText().toString().charAt(0) == '.') {
                        smallQuantity.setText(getString(R.string._0) + smallQuantity.getText().toString());
                    }
                    if(smallQuantity.getText().toString().charAt(smallQuantity.getText().toString().length() - 1) == '.'){
                        smallQuantity.setText(smallQuantity.getText().toString() + getString(R.string._0));
                    }
                }
            }
        });

        btnPlusMedium.setOnClickListener(view13 -> {
            String currentValue = mediumQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+0.2)*100.0)/100.0));
        });

        btnMinusMedium.setOnClickListener(view14 -> {
            String currentValue = mediumQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            if (Double.parseDouble(currentValue) >= 0.2){
                mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.2)*100.0)/100.0));
            } else if(Double.parseDouble(currentValue) < 0.2 && Double.parseDouble(currentValue) > 0){
                mediumQuantity.setText("0");
            }
        });

        mediumQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String currentValue = mediumQuantity.getText().toString();
                if(currentValue.equals(".")){
                    currentValue = "";
                    mediumQuantity.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mediumQuantity.setOnFocusChangeListener((v, fokusiran) -> {
            if (!fokusiran) {
                if (mediumQuantity.getText().length() > 1) {
                    if (mediumQuantity.getText().toString().charAt(0) == '0' && mediumQuantity.getText().toString().charAt(1) != '.') {
                        mediumQuantity.setText(mediumQuantity.getText().toString().substring(1));
                    }
                    if (mediumQuantity.getText().toString().charAt(0) == '.') {
                        mediumQuantity.setText(getString(R.string._0) + mediumQuantity.getText().toString());
                    }
                    if(mediumQuantity.getText().toString().charAt(mediumQuantity.getText().toString().length() - 1) == '.'){
                        mediumQuantity.setText(mediumQuantity.getText().toString() + getString(R.string._0));
                    }
                }
            }
        });

        btnMinusLarge.setOnClickListener(v -> {
            String currentValue = largeQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            if(Double.parseDouble(currentValue) >= 0.5) {
                largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue) - 0.5) * 100.0) / 100.0));
            } else if(Double.parseDouble(currentValue) < 0.5 && Double.parseDouble(currentValue) > 0){
                largeQuantity.setText("0");
            }
        });

        btnPlusLarge.setOnClickListener(v -> {
            String currentValue = largeQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue) + 0.5)*100.0)/100.0));
        });

        largeQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String currentValue = largeQuantity.getText().toString();
                if(currentValue.equals(".")){
                    currentValue = "";
                    largeQuantity.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        largeQuantity.setOnFocusChangeListener((v, fokusiran) -> {
            if (!fokusiran) {
                if (largeQuantity.getText().length() > 1) {
                    if (largeQuantity.getText().toString().charAt(0) == '0' && largeQuantity.getText().toString().charAt(1) != '.') {
                        largeQuantity.setText(largeQuantity.getText().toString().substring(1));
                    }
                    if (largeQuantity.getText().toString().charAt(0) == '.') {
                        largeQuantity.setText(getString(R.string._0) + largeQuantity.getText().toString());
                    }
                    if(largeQuantity.getText().toString().charAt(largeQuantity.getText().toString().length() - 1) == '.'){
                        largeQuantity.setText(largeQuantity.getText().toString() + getString(R.string._0));
                    }
                }
            }
        });

        fishSpecies = binding.autoFishSpecies;
        location = binding.autoLocation;

        repository.DohvatiRibe(fishes -> {
            ArrayList<String> fishArrayList=new ArrayList<>();
            for(Fish fish: fishes){
                fishArrayList.add(fish.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.autocomplete_custom, R.id.autocomplete_text, fishArrayList);
            adapter.notifyDataSetChanged();
            fishSpecies.setAdapter(adapter);
        });

        repository.DohvatiLokacije(locations -> {
            ArrayList<String> locationArrayList=new ArrayList<>();
            for(Location location: locations){
                locationArrayList.add(location.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.autocomplete_custom, R.id.autocomplete_text, locationArrayList);
            adapter.notifyDataSetChanged();
            location.setAdapter(adapter);
        });

        price.setOnFocusChangeListener((v, hasFocus) -> {
            if (price.getText().length() > 1) {
                if (price.getText().toString().charAt(0) == '0' && price.getText().toString().charAt(1) != '.') {
                    price.setText(price.getText().toString().substring(1));
                }
                if (price.getText().toString().charAt(0) == '.') {
                    price.setText(getString(R.string._0) + price.getText().toString());
                }
                if(price.getText().toString().charAt(price.getText().toString().length() - 1) == '.'){
                    price.setText(price.getText().toString() + getString(R.string._0));
                }
            }
            if(price.getText().toString().equals(".")){
                price.setText("0");
            }
        });

        btnSaveNewOffer.setOnClickListener(v -> {
            smallQuantity.clearFocus();
            mediumQuantity.clearFocus();
            largeQuantity.clearFocus();
            price.clearFocus();

            repository.DohvatiRibe(fishes -> {
                for (int i = 0; i < fishes.size(); i++) {
                    if (fishes.get(i).getName().contains(fishSpecies.getText().toString())) {
                        repository.DodajPonuduSAutoID(fishSpecies.getText().toString(), location.getText().toString(),fishes.get(i).getUrl(), price.getText().toString(), userId, smallQuantity.getText().toString(),
                                mediumQuantity.getText().toString(), largeQuantity.getText().toString());
                        break;
                    }
                }
            });

            Fragment newFragment;
            ((RegisterActivity)getActivity()).changeOnOffersNavigationBar();
            newFragment = new ReservationFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_containter, newFragment).commit();
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(View view) {
                ((RegisterActivity)getActivity()).changeOnSeachNavigationBar();
                selectedFragment = new SearchFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();    }
        });

        return view;
    }

    InputFilter filterDecimals = (source, start, end, dest, dstart, dend) -> {
        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source
                .subSequence(start, end).toString());
        if (!builder.toString().matches(
                "(([0-9])([0-9]{0,"+(3 - 1)+"})?)?(\\.[0-9]{0,"+ 3 +"})?"
        )) {
            if(source.length()==0)
                return dest.subSequence(dstart, dend);
            return "";
        }
        return null;
    };

}
