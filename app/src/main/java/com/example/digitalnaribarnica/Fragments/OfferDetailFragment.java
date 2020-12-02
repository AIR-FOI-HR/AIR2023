package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
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

        smallQuantity = binding.smallFishQuantity;
        mediumQuantity = binding.mediumFishQuantity;
        largeQuantity = binding.largeFishQuantity;

        smallQuantity.setText("0");
        mediumQuantity.setText("0");
        largeQuantity.setText("0");

        availableSmall = binding.availableSmall;
        availableMedium = binding.availableMedium;
        availableLarge = binding.availableLarge;

        availableSmall.setText("1.5");
        availableMedium.setText("4.2");
        availableLarge.setText("5");

        btnPlusSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = smallQuantity.getText().toString();
                if(currentValue.equals("")){
                    currentValue="0";
                }
                String availableQuantity = availableSmall.getText().toString();
                Log.d("TagPolje", currentValue);
                Log.d("TagPolje", availableQuantity);
                if (Double.parseDouble(availableQuantity) > Double.parseDouble(currentValue)){
                    smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.1)*100.0)/100.0));
                }
            }
        });

        smallQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

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


        btnMinusSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = smallQuantity.getText().toString();
                if(currentValue.equals("")){
                    currentValue="0";
                }
                if (Double.parseDouble(currentValue) >= 0.1){
                    smallQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.1)*100.0)/100.0));
                } else if(Double.parseDouble(currentValue) < 0.1 && Double.parseDouble(currentValue) > 0){
                    smallQuantity.setText("0");
                }
            }
        });

        btnPlusMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mediumQuantity.getText().toString();
                if(currentValue.equals("")){
                    currentValue="0";
                }
                String availableQuantity = availableMedium.getText().toString();
                Log.d("TagPolje", currentValue);
                Log.d("TagPolje", availableQuantity);
                if (Double.parseDouble(availableQuantity) > Double.parseDouble(currentValue)){
                    mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.2)*100.0)/100.0));
                }
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

        btnMinusMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mediumQuantity.getText().toString();
                if(currentValue.equals("")){
                    currentValue="0";
                }
                if (Double.parseDouble(currentValue) >= 0.2){
                    mediumQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)-0.2)*100.0)/100.0));
                }  else if(Double.parseDouble(currentValue) < 0.2 && Double.parseDouble(currentValue) > 0){
                    mediumQuantity.setText("0");
                }
            }
        });

        btnMinusLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = largeQuantity.getText().toString();
                if(currentValue.equals("")){
                    currentValue="0";
                }
                if(Double.parseDouble(currentValue) >= 0.5) {
                    largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue) - 0.5) * 100.0) / 100.0));
                }  else if(Double.parseDouble(currentValue) < 0.5 && Double.parseDouble(currentValue) > 0){
                    largeQuantity.setText("0");
                }
            }
        });

        btnPlusLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = largeQuantity.getText().toString();
                if(currentValue.equals("")){
                    currentValue="0";
                }
                String availableQuantity = availableLarge.getText().toString();
                Log.d("TagPolje", currentValue);
                Log.d("TagPolje", availableQuantity);
                if (Double.parseDouble(availableQuantity) > Double.parseDouble(currentValue)){
                    largeQuantity.setText(String.valueOf(Math.round((Double.parseDouble(currentValue)+ 0.5)*100.0)/100.0));
                }
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

        return view;
       //return inflater.inflate(R.layout.fragment_offer_detail,container,false);
    }
}
