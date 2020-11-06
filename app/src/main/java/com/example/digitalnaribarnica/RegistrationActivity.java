package com.example.digitalnaribarnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digitalnaribarnica.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {
    
    ActivityRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate((getLayoutInflater()));
        View view=binding.getRoot();
        setContentView(view);
       
    }
    
    
}