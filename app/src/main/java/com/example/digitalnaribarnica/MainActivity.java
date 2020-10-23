package com.example.digitalnaribarnica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.digitalnaribarnica.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding=ActivityMainBinding.inflate((getLayoutInflater()));
        View view=binding.getRoot();
        setContentView(view);
        binding.helloWorld.setText("Digitalna ribarnica");
    }
}