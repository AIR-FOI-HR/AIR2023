package com.example.digitalnaribarnica;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.database.User;
import com.example.digitalnaribarnica.Fragments.ChatFragment;
import com.example.digitalnaribarnica.Fragments.AddOfferFragment;
import com.example.digitalnaribarnica.Fragments.PersonFragment;
import com.example.digitalnaribarnica.Fragments.ReservationFragment;
import com.example.digitalnaribarnica.Fragments.SearchFragment;
import com.example.digitalnaribarnica.databinding.ActivityRegisterBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private RelativeLayout rlayout;
    private Animation animation;
    ActivityRegisterBinding binding;
    ImageView imageView;
    TextView name,email,id;
    Button signOut;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;
    GoogleSignInAccount acct;
    FirebaseUser mUser;
    String personName = "";
    String personEmail ="";
    String personId="";
    String personPhoto="";
    String phone="";
    String adress="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        //Incijalizacija Firebase
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding=ActivityRegisterBinding.inflate((getLayoutInflater()));
        View view=binding.getRoot();
        setContentView(view);
        bottomNavigationView=binding.bottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        /*
        imageView = binding.Slika;
        name = binding.Name;
        email = binding.Email;
        id = binding.ID;
        signOut=binding.odjava;
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.odjava:
                        if(mUser!=null){
                            mAuth.signOut();
                            LoginManager.getInstance().logOut();;
                            finish();
                        }
                        signOut();
                        break;
                }
            }
        });

         */
        acct = GoogleSignIn.getLastSignedInAccount(this);



        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            personPhoto = acct.getPhotoUrl().toString();
            /*
            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            Glide.with(this).load(personPhoto).into(imageView);
            */
        }
        else if(mUser!=null){
            personName=mUser.getDisplayName();
            personEmail=mUser.getEmail();
            personId=mUser.getUid();
            personPhoto=mUser.getPhotoUrl().toString();
            /*
            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            Glide.with(this).load(personPhoto).into(imageView);
            */
        }
        else {
            User user=(User)getIntent().getSerializableExtra("CurrentUser");
            personName=user.getFullName();
            personEmail=user.getEmail();
            personId=user.getUserID();
            personPhoto=user.getPhoto();
            adress=user.getAdress();
            phone=user.getPhone();
            //personPhoto=mUser.getPhotoUrl().toString();
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment =null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new AddOfferFragment();
                            break;
                        case R.id.nav_chat:
                            selectedFragment = new ChatFragment();
                            break;
                        case R.id.nav_person:
                            //Toast.makeText(RegisterActivity.this,personName , Toast.LENGTH_LONG).show();
                            /*
                            Bundle args = new Bundle();
                            args.putString("ime",personName);
                            args.putString("email",personEmail);
                            args.putString("id",personId);
                            args.putString("photo",personPhoto);
                            selectedFragment.setArguments(args);
                            */
                            selectedFragment = new PersonFragment(personName,personId,personPhoto,personEmail,adress,phone,acct,mUser,mAuth,mGoogleSignInClient);
                            break;
                        case R.id.nav_ponude:
                            selectedFragment = new ReservationFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment(personName,personId,personPhoto,personEmail,adress,phone,acct,mUser,mAuth,mGoogleSignInClient);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                    return true;
                }
            };

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegisterActivity.this,"Odjavljen!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        /*
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Želite izaći iz aplikacije?");
        alertDialogBuilder
                .setMessage("Stisnite DA ako želite")
                .setCancelable(false)
                .setPositiveButton("Da",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        */
        if (doubleBackToExitPressedOnce) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Želite izaći iz aplikacije?");
            alertDialogBuilder
                    .setMessage("Stisnite DA ako želite")
                    .setCancelable(false)
                    .setPositiveButton("Da",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Stisnite BACK još jedno za izlaz", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
