package com.example.digitalnaribarnica.recycleviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalnaribarnica.Fragments.OfferDetailFragment;
import com.example.digitalnaribarnica.R;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private ArrayList<OffersData> offers=new ArrayList<>();
    private Context context;
    private CardView cardView;


    public OfferAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.prvi.setText(offers.get(position).getName());
        holder.drugi.setText(offers.get(position).getDescription());
        holder.cetvrti.setText(offers.get(position).getPrice());
        holder.peti.setText(offers.get(position).getFishClass());

        cardView.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment = null;

            @Override
            public void onClick(View v) {
                selectedFragment = new OfferDetailFragment();
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(offers.get(position).getImageurl())
                .into(holder.treci);

        Glide.with(context)
                .asBitmap()
                .load(offers.get(position).getImageurltrophey())
                .into(holder.sesti);
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
        private TextView prvi;
        private TextView drugi;
        private ImageView treci;
        private TextView cetvrti;
        private TextView peti;
        private ImageView sesti;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prvi = itemView.findViewById(R.id.textOfferName);
            drugi= itemView.findViewById(R.id.textOfferLocation);
            treci = itemView.findViewById(R.id.offerImage);
            cetvrti = itemView.findViewById(R.id.textOfferPrice);
            peti = itemView.findViewById(R.id.textOfferFishClass);
            sesti = itemView.findViewById(R.id.trophyOfferImage);
            cardView=itemView.findViewById(R.id.parent);
        }
    }
}