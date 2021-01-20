package com.example.digitalnaribarnica.recycleviewer;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.database.FirestoreService;
import com.example.digitalnaribarnica.Fragments.OfferDetailFragment;
import com.example.digitalnaribarnica.Fragments.PersonFragment;
import com.example.digitalnaribarnica.Fragments.SearchFragment;
import com.example.digitalnaribarnica.R;
import com.example.repository.Repository;
import com.example.repository.Data.OffersData;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private ArrayList<OffersData> offers=new ArrayList<>();
    private Context context;
    private CardView cardView;
    private String userID;
    private SearchFragment searchFragment;
    private TextView fishClassText;


    public OfferAdapter(Context context, String userID, SearchFragment fragment) {
        this.userID = userID;
        this.context = context;
        this.searchFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        fishClassText = view.findViewById(R.id.fish_class_text);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fish.setText(offers.get(position).getName());
        holder.location.setText(offers.get(position).getLocation());
        String priceText = offers.get(position).getPrice() + " " + context.getString(R.string.knperkg);
        holder.price.setText(priceText);
        holder.fishClassText.setText("");

        if(offers.get(position).getSmallFish()!= null && !offers.get(position).getSmallFish().equals("0") && !offers.get(position).getSmallFish().equals("0.0")){
            holder.fishClassText.append(context.getString(R.string.small));
        }

        if(offers.get(position).getMediumFish()!= null && !offers.get(position).getMediumFish().equals("0") && !offers.get(position).getMediumFish().equals("0.0")) {
            if(!holder.fishClassText.getText().toString().equals("")){
                holder.fishClassText.append(", ");
                holder.fishClassText.append(context.getString(R.string.medium));
            } else{
                holder.fishClassText.append(context.getString(R.string.medium));
            }
        }

        if(offers.get(position).getLargeFish()!= null && !offers.get(position).getLargeFish().equals("0") && !offers.get(position).getLargeFish().equals("0.0")) {
            if(!holder.fishClassText.getText().toString().equals("")){
                holder.fishClassText.append(", ");
                holder.fishClassText.append(context.getString(R.string.large));
            } else{
                holder.fishClassText.append(context.getString(R.string.large));
            }
        }

        if(holder.fishClassText.getText().toString().equals("")){
            fishClassText.setText("");
        }

        holder.seller.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment = null;
            @Override
            public void onClick(View view) {
                selectedFragment = new PersonFragment(offers.get(holder.getAdapterPosition()).getIdKorisnika(), userID, "Offers");
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment = null;

            @Override
            public void onClick(View v) {
                selectedFragment = new OfferDetailFragment(offers.get(holder.getAdapterPosition()).getOfferID(), userID, searchFragment.getLastVisited());
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(offers.get(position).getImageurl())
                .into(holder.fishImage);

        String userID = offers.get(position).getIdKorisnika();
        Repository repository = new Repository();
        FirestoreService firestoreService=new FirestoreService();

        repository.DohvatiKorisnikaPoID(userID, user -> {
           String badge = user.getBadgeSellerURL();
            String textSeller =  user.getFullName();
            holder.seller.setText(Html.fromHtml(textSeller));
           if(!badge.equals("")){
                Glide.with(context)
                        .asBitmap()
                        .load(badge)
                        .into(holder.trophyImage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void setOffers(ArrayList<OffersData> offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fish;
        private TextView location;
        private ImageView fishImage;
        private TextView price;
        private TextView fishClassText;
        private ImageView trophyImage;
        private TextView seller;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fish = itemView.findViewById(R.id.textOfferName);
            location= itemView.findViewById(R.id.textOfferLocation);
            fishImage = itemView.findViewById(R.id.offerImage);
            price = itemView.findViewById(R.id.textOfferPrice);
            fishClassText = itemView.findViewById(R.id.textOfferFishClass);
            trophyImage = itemView.findViewById(R.id.trophyOfferImage);
            seller = itemView.findViewById(R.id.textSeller);
            cardView=itemView.findViewById(R.id.parent);
        }
    }
}