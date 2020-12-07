package com.example.digitalnaribarnica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private Button posalji;
    private EditText uneseniEmail;
    private FirebaseAuth fAuthen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().hide();

        uneseniEmail = (EditText) findViewById(R.id.emailinput);
        fAuthen = FirebaseAuth.getInstance();

        //klikom na gumb "Pošalji" se provjerava ispravnos unesenog email-a, provjerava se postoji li email u bazi podataka te ukoliko su uvjeti zadovoljeni,
        //korisniku se šalje na uneseni email poveznica za promjenu lozinke
        posalji = (Button) findViewById(R.id.btnPrijava);
        posalji.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(uneseniEmail.getText().length() != 0) {
                    fAuthen.sendPasswordResetEmail(uneseniEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Poveznica za promjenu lozinke je poslana na Vaš email.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent (ResetPasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                if(!uneseniEmail.getText().toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                                    Toast.makeText(ResetPasswordActivity.this, "Unijeli ste neispravan format email-a!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ResetPasswordActivity.this, "Uneseni email ne postoji u našoj bazi podataka!", Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(ResetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}