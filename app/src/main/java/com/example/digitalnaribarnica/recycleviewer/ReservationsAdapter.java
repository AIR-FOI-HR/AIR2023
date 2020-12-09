package com.example.digitalnaribarnica.recycleviewer;

import android.content.Context;

import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.text.Html;
import android.util.Log;
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
import com.example.database.FirestoreService;
import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.FirestoreOffer;
import com.example.digitalnaribarnica.Fragments.OfferDetailFragment;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.Repository;

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

        Repository repository = new Repository();
        FirestoreService firestoreService=new FirestoreService();

       repository.DohvatiPonuduPrekoIdPonude(reservations.get(position).getOfferID(), new FirestoreOffer() {
            @Override
            public void onCallback(ArrayList<OffersData> offersData) {
                holder.fish.setText(offersData.get(0).getName());
                holder.location.setText(offersData.get(0).getLocation());
                Double quantity = 0.0;
                String text ="";
                if (!offersData.get(0).getSmallFish().equals("0")){
                    text = text + "<b>" + context.getString(R.string.smallFish) + "</b>" +  " "  + offersData.get(0).getSmallFish() + " kg" + "<br>";
                    quantity = quantity + Double.parseDouble(offersData.get(0).getSmallFish());
                }
                if (!offersData.get(0).getMediumFish().equals("0")){
                    text = text + "<b>" + context.getString(R.string.mediumFish) + "</b>" +  " " + offersData.get(0).getMediumFish() + " kg" + "<br>";
                    quantity = quantity + Double.parseDouble(offersData.get(0).getMediumFish());
                }
                if (!offersData.get(0).getLargeFish().equals("0")){
                    text = text +  "<b>" + context.getString(R.string.largeFish) + "</b>" +  " " + offersData.get(0).getLargeFish() + " kg";
                    quantity = quantity + Double.parseDouble(offersData.get(0).getLargeFish());
                }
                holder.fishClassText.setText(Html.fromHtml(text));
                Double priceQuantity = quantity * Double.parseDouble(offersData.get(0).getPrice());
                String textPrice = priceQuantity.toString() + " kn";
                holder.price.setText(textPrice);

                Glide.with(context)
                        .asBitmap()
                        .load(offersData.get(0).getImageurl())
                        .into(holder.fishImage);
            }
        });
       /* SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss tt");
        sfd.format(new Date(String.valueOf(reservations.get(position).getDate().toDate())));*/
        Calendar calendar = DateParse.dateToCalendar(reservations.get(position).getDate().toDate());
        String textDate = "<b>" +  context.getString(R.string.dateReservation) + "</b>" + "\n" + calendar.getTime().toString();
        holder.date.setText(Html.fromHtml(textDate));





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

        private TextView fish;
        private TextView location;
        private TextView price;
        private ImageView fishImage;
        private TextView fishClassText;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fish = itemView.findViewById(R.id.reservationTitle);
            location= itemView.findViewById(R.id.textReservationLocation);
            fishImage = itemView.findViewById(R.id.reservationImage);
            price = itemView.findViewById(R.id.textReservationPrice);
            fishClassText = itemView.findViewById(R.id.textReservationGrade);
            date = itemView.findViewById(R.id.textDate);
            cardView=itemView.findViewById(R.id.parentReservation);
        }
    }
}
