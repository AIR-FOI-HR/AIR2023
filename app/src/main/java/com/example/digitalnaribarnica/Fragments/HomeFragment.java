package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digitalnaribarnica.MainActivity;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.databinding.FragmentHomeBinding;
import com.example.digitalnaribarnica.recycleviewer.OfferAdapter;
import com.example.repository.Data.OffersData;
import com.example.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String userId = "";
    private RelativeLayout roleBuyer;
    private RelativeLayout roleSeller;
    private ImageView statusBuyer;
    private ImageView statusSeller;
    private TextView textBuyer;
    private TextView textSeller;


    public HomeFragment(String userId) {
        this.userId = userId;
    }

    @SuppressLint({"RestrictedApi", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);

        roleBuyer = binding.roleBuyer;
        roleSeller = binding.roleSeller;

        statusBuyer = binding.statusBuyer;
        statusSeller = binding.statusSeller;

        textBuyer = binding.textbuyer;
        textSeller = binding.textseller;

        if(((RegisterActivity) getActivity()).buyer){
            statusBuyer.setVisibility(view.VISIBLE);
            statusSeller.setVisibility(view.INVISIBLE);
            textBuyer.setVisibility(view.INVISIBLE);
            textSeller.setVisibility(view.VISIBLE);
        }else{
            statusBuyer.setVisibility(view.INVISIBLE);
            statusSeller.setVisibility(view.VISIBLE);
            textBuyer.setVisibility(view.VISIBLE);
            textSeller.setVisibility(view.INVISIBLE);
        }

        roleBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity) getActivity()).changeRole(true);
                statusBuyer.setVisibility(view.VISIBLE);
                statusSeller.setVisibility(view.INVISIBLE);
                textBuyer.setVisibility(view.INVISIBLE);
                textSeller.setVisibility(view.VISIBLE);
            }
        });

        roleSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity) getActivity()).changeRole(false);
                statusBuyer.setVisibility(view.INVISIBLE);
                statusSeller.setVisibility(view.VISIBLE);
                textBuyer.setVisibility(view.VISIBLE);
                textSeller.setVisibility(view.INVISIBLE);
            }
        });



        return view;
    };


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        menu.findItem((R.id.all_offers_menu)).setVisible(false);
        menu.findItem((R.id.my_offers_menu)).setVisible(false);
        menu.findItem((R.id.action_search)).setVisible(false);
        menu.findItem((R.id.filter_menu)).setVisible(false);
        menu.findItem((R.id.sort_offers_menu)).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.language:
                String[] items = {"HRV", "ENG"};
                Log.d("TagPolje", "ulazi za language");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Odaberite jezik: ");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                updateResources(getContext(), "");
                                ((RegisterActivity) getActivity()).refreshFragmentHome();
                                break;

                            case 1:
                                updateResources(getContext(), "en");
                                ((RegisterActivity) getActivity()).refreshFragmentHome();
                                break;
                        }
                    }
                });
                builder.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
