package com.example.digitalnaribarnica.recycleviewer;

import android.content.Context;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalnaribarnica.Fragments.OfferDetailFragment;
import com.example.digitalnaribarnica.R;
import androidx.fragment.app.Fragment;




public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder>{

    private ArrayList<ReservationsData> reservations=new ArrayList<>();
    private Context context;
    private CardView cardView;

    public ReservationsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.prvi.setText(reservations.get(position).getReservationTitle());
        holder.drugi.setText(reservations.get(position).getLocation());
        holder.cetvrti.setText(reservations.get(position).getPrice());
        holder.peti.setText(reservations.get(position).getGrade());


        Glide.with(context)
                .asBitmap()
                .load(reservations.get(position).getReservationImage())
                .into(holder.treci);

        Glide.with(context)
                .asBitmap()
                .load(reservations.get(position).getReservationTrophyImage())
                .into(holder.sesti);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(ArrayList<ReservationsData> reservations) {
        this.reservations = reservations;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView prvi;
        private TextView drugi;
        private ImageView treci;
        private TextView cetvrti;
        private TextView peti;
        private ImageView sesti;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prvi = itemView.findViewById(R.id.reservationTitle);
            drugi= itemView.findViewById(R.id.textReservationLocation);
            treci = itemView.findViewById(R.id.reservationImage);
            cetvrti = itemView.findViewById(R.id.textReservationPrice);
            peti = itemView.findViewById(R.id.textReservationGrade);
            sesti = itemView.findViewById(R.id.trophyReservationImage);
            cardView=itemView.findViewById(R.id.parentReservation);
        }
    }
}
