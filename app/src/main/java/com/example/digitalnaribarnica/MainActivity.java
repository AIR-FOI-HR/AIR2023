package com.example.digitalnaribarnica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.FirestoreService;
import com.example.database.User;
import com.example.database.Utils.SHA256;
import com.example.digitalnaribarnica.databinding.ActivityMainBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView registracija;

    ActivityMainBinding binding;
    SignInButton signin;
    LoginButton loginButton;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    int SIGN_IN_FB=1;

    private View view;
    private Boolean vrijemeTece = false;

    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding=ActivityMainBinding.inflate((getLayoutInflater()));
        View view=binding.getRoot();
        setContentView(view);

        registracija = binding.registracijaButton;

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        //Inicijalizacija Facebook SDK
        FacebookSdk.sdkInitialize(MainActivity.this);
        //Incijalizacija Firebase
        mAuth = FirebaseAuth.getInstance();
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        LoginButton loginButton =binding.loginButton;
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        //binding.helloWorld.setText("Digitalna ribarnica");
        signin=binding.signInButton;
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        binding.btnPrijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(intent);
                 */
                Repository repository=new Repository();
                //Toast.makeText(MainActivity.this, binding.emailEDIT.getText(), Toast.LENGTH_LONG).show();

                repository.DohvatiKorisnikaPoEmailu(binding.emailEDIT.getText().toString(), new FirestoreCallback() {

                        @Override
                        public void onCallback (User user){

                        if (user != null) {
                            Log.d("TagPolje", "Ulazi");
                            //Toast.makeText(MainActivity.this, user.getFullName(), Toast.LENGTH_LONG).show();
                            //SHA256.getSHA(binding.)
                            //Toast.makeText(MainActivity.this,user.getPassword() , Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity.this, user.getBlokiran().toString(), Toast.LENGTH_SHORT).show();
                            if (!user.getBlokiran()) {
                                String s1 = binding.passwordEDIT.getText().toString();

                                try {
                                    //Toast.makeText(MainActivity.this, String.valueOf(repository.ProvjeriPassword(user.getPassword(),SHA256.toHexString(SHA256.getSHA(s1)))), Toast.LENGTH_SHORT).show();
                                    //Log.d("SHA256",SHA256.toHexString(SHA256.getSHA(s1)));
                                    if (repository.ProvjeriPassword(user.getPassword(), SHA256.toHexString(SHA256.getSHA(s1)))) {
                                        //Toast.makeText(MainActivity.this, "Pass je isti", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                        intent.putExtra("CurrentUser", user);
                                        MainActivity.this.startActivity(intent);


                                    } else
                                        showToast(view, "Unijeli ste krivu lozinku!!!");
                                        //Toast.makeText(MainActivity.this, "Unijeli ste krivu lozinku!!!",Toast.LENGTH_SHORT).show();

                                } catch (NoSuchAlgorithmException e) {
                                    showToast(view, "Nije moguće izračunati SHA256");
                                    //Toast.makeText(MainActivity.this, "Nije moguće izračunati SHA256", Toast.LENGTH_SHORT).show();
                                }
                            } else
                                showToast(view, "Korisnik je blokiran");
                                //Toast.makeText(MainActivity.this, "Korisnik je blokiran", Toast.LENGTH_SHORT).show();
                        } else
                            showToast(view, "Korisnik nije pronađen u bazi");
                            //Toast.makeText(MainActivity.this, "Korisnik nije pronađen u bazi", Toast.LENGTH_SHORT).show();

                    }

                });

            }
        });


    }

    public void showToast(View v, String poruka){
        if(!vrijemeTece){
            vrijemeTece = true;
            StyleableToast.makeText(this, poruka, 3, R.style.Toast).show();
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    vrijemeTece = false;
                }
            }.start();
        }
    }


    public void saveInformation(String username,String password) {
        SharedPreferences shared = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ERROR", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            updateUI(currentUser);
        }
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user !=null){
            Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Please sign in to continue ", Toast.LENGTH_SHORT).show();
        }
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

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

        showToast(view, "Ime premašuje broj znakova");

        Toast.makeText(this, "Stisnite BACK još jedno za izlaz", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}