package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.databinding.FragmentAddOfferBinding;
import com.example.digitalnaribarnica.databinding.FragmentOfferDetailBinding;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class OfferDetailFragment extends Fragment {

    FragmentOfferDetailBinding binding;
    private Button btnMinusSmall;
    private Button btnPlusSmall;
    private Button btnMinusMedium;
    private Button btnPlusMedium;
    private Button btnMinusLarge;
    private Button btnPlusLarge;

    private  Button btnReserve;

    private EditText smallQuantity;
    private EditText mediumQuantity;
    private EditText largeQuantity;

    private TextView availableSmall;
    private TextView availableMedium;
    private TextView availableLarge;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detalji ponude");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        binding= FragmentOfferDetailBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        btnMinusSmall = binding.btnMinusSmall;
        btnPlusSmall = binding.btnPlusSmall;
        btnMinusMedium = binding.btnMinusMedium;
        btnPlusMedium = binding.btnPlusMedium;
        btnMinusLarge = binding.btnMinusLarge;
        btnPlusLarge = binding.btnPlusLarge;

        btnReserve = binding.btnRezerviraj;

        smallQuantity = binding.smallFishQuantity;
        mediumQuantity = binding.mediumFishQuantity;
        largeQuantity = binding.largeFishQuantity;

        smallQuantity.setText("0");
        mediumQuantity.setText("0");
        largeQuantity.setText("0");

        availableSmall = binding.availableSmall;
        availableMedium = binding.availableMedium;
        availableLarge = binding.availableLarge;

        availableSmall.setText("3.5");
        availableMedium.setText("4.2");
        availableLarge.setText("5");

        smallQuantity.setFilters(new InputFilter[] { filterDecimals });
        mediumQuantity.setFilters(new InputFilter[] { filterDecimals });
        largeQuantity.setFilters(new InputFilter[] { filterDecimals });

        btnPlusSmall.setOnClickListener(view1 -> {
            btnPlusSmall.requestFocus();
            String currentValue = smallQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            String availableQuantity = availableSmall.getText().toString();
            if (Double.parseDouble(availableQuantity) > Double.parseDouble(currentValue)){
                smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.1)*100.0)/100.0));
            }
        });

        smallQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String currentValue = smallQuantity.getText().toString();
                String smallAvailable= availableSmall.getText().toString();
                if(currentValue.equals(".")){
                    currentValue = "";
                    smallQuantity.setText("");
                }
                if(!currentValue.isEmpty()){
                  if(Double.parseDouble(smallAvailable) < Double.parseDouble(currentValue)){
                      StyleableToast.makeText(getActivity(), "Nedostupno", 3, R.style.Toast).show();
                      smallQuantity.setText(smallAvailable);
                  }
               }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        smallQuantity.setOnFocusChangeListener((view15, fokusiran) -> {
            if (!fokusiran) {
                if (smallQuantity.getText().length() > 1) {
                    if (smallQuantity.getText().toString().charAt(0) == '0' && smallQuantity.getText().toString().charAt(1) != '.') {
                        smallQuantity.setText(smallQuantity.getText().toString().substring(1));
                    }
                    if (smallQuantity.getText().toString().charAt(0) == '.') {
                        smallQuantity.setText(getString(R.string._0) + smallQuantity.getText().toString());
                    }
                }
            }
        });

        btnMinusSmall.setOnClickListener(view12 -> {
            String currentValue = smallQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }

            if (Double.parseDouble(currentValue) >= 0.1){
                smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.1)*100.0)/100.0));
            } else if(Double.parseDouble(currentValue) < 0.1 && Double.parseDouble(currentValue) > 0){
                smallQuantity.setText("0");
            }
        });

        btnPlusMedium.setOnClickListener(view13 -> {
            //btnPlusMedium.setFocusableInTouchMode(true);
            //btnPlusMedium.requestFocus();
            String currentValue = mediumQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            String availableQuantity = availableMedium.getText().toString();
            if (Double.parseDouble(availableQuantity) > Double.parseDouble(currentValue)){
                mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.2)*100.0)/100.0));
            }
        });

        mediumQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String currentValue = mediumQuantity.getText().toString();
                String mediumAvailable= availableMedium.getText().toString();
                if(currentValue.equals(".")){
                    currentValue = "";
                    mediumQuantity.setText("");
                }
                if(!currentValue.isEmpty()){
                    if(Double.parseDouble(mediumAvailable) < Double.parseDouble(currentValue)){
                        StyleableToast.makeText(getActivity(), "Nedostupno", 3, R.style.Toast).show();
                        mediumQuantity.setText(mediumAvailable);
                    }
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
                }
            }
        });

        btnMinusMedium.setOnClickListener(view14 -> {
            String currentValue = mediumQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            if (Double.parseDouble(currentValue) >= 0.2){
                mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.2)*100.0)/100.0));
            }  else if(Double.parseDouble(currentValue) < 0.2 && Double.parseDouble(currentValue) > 0){
                mediumQuantity.setText("0");
            }
        });

        btnMinusLarge.setOnClickListener(v -> {
            String currentValue = largeQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            if(Double.parseDouble(currentValue) >= 0.5) {
                largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue) - 0.5) * 100.0) / 100.0));
            }  else if(Double.parseDouble(currentValue) < 0.5 && Double.parseDouble(currentValue) > 0){
                largeQuantity.setText("0");
            }
        });

        btnPlusLarge.setOnClickListener(v -> {
            String currentValue = largeQuantity.getText().toString();
            if(currentValue.equals("")){
                currentValue="0";
            }
            String availableQuantity = availableLarge.getText().toString();
            if (Double.parseDouble(availableQuantity) > Double.parseDouble(currentValue)){
                largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.5)*100.0)/100.0));
            }
        });

        largeQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String currentValue = largeQuantity.getText().toString();
                String largeAvailable = availableLarge.getText().toString();
                if(currentValue.equals(".")){
                    currentValue = "";
                    largeQuantity.setText("");
                }
                if(!currentValue.isEmpty()){
                    if(Double.parseDouble(largeAvailable) < Double.parseDouble(currentValue)){
                        StyleableToast.makeText(getActivity(), "Nedostupno", 3, R.style.Toast).show();
                        largeQuantity.setText(largeAvailable);
                    }
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
            }
        }
    });

        btnReserve.setOnClickListener(v -> {
            smallQuantity.clearFocus();
            mediumQuantity.clearFocus();
            largeQuantity.clearFocus();
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
