package com.example.digitalnaribarnica.recycleviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.database.FirestoreService;
import com.example.database.Utils.DateParse;
import com.example.repository.Listener.FirestoreOffer;
import com.example.digitalnaribarnica.Fragments.FragmentUserRating;
import com.example.digitalnaribarnica.Fragments.ReservationFragment;
import com.example.digitalnaribarnica.R;
import com.example.repository.Repository;
import com.example.repository.Data.OffersData;
import com.example.repository.Data.ReservationsData;

import androidx.fragment.app.Fragment;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder>{

    private ReservationFragment  reservationFragment;
    private ArrayList<ReservationsData> reservations=new ArrayList<>();
    private Context context;
    private CardView cardView;
    private ImageView deleteReservation;
    String userID ="";
    String ReservationID ="";


    public ReservationsAdapter(Context context, ReservationFragment reservationFragment, String userId) {
        this.context = context;
        this.reservationFragment = reservationFragment;
        this.userID = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        ViewHolder holder= new ViewHolder(view);


        holder.deleteReservation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(reservationFragment.getActivity(), reservationFragment.getActivity().getString(R.string.warning), reservationFragment.getActivity().getString(R.string.wantToDeleteReservation));
                ReservationID = reservations.get(holder.getAdapterPosition()).getReservationID();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repository repository = new Repository();
           repository.DohvatiPonuduPrekoIdPonude(reservations.get(position).getOfferID(), new FirestoreOffer() {
                @Override
                public void onCallback(ArrayList<OffersData> offersData) {
                    holder.fish.setText(offersData.get(0).getName());
                    holder.location.setText(offersData.get(0).getLocation());
                    Double quantity = 0.0;
                    String text ="";
                    if (!reservations.get(position).getSmallFish().equals("0")){
                        text = text + "<b>" + context.getString(R.string.smallFish) + "</b>" +  " "  + reservations.get(position).getSmallFish() + " kg" + "<br>";
                        quantity = quantity + Double.parseDouble(reservations.get(position).getSmallFish());
                    }
                    if (!reservations.get(position).getMediumfish().equals("0")){
                        text = text + "<b>" + context.getString(R.string.mediumFish) + "</b>" +  " " + reservations.get(position).getMediumfish() + " kg" + "<br>";
                        quantity = quantity + Double.parseDouble(reservations.get(position).getMediumfish());
                    }
                    if (!reservations.get(position).getLargeFish().equals("0")){
                        text = text +  "<b>" + context.getString(R.string.largeFish) + "</b>" +  " " + reservations.get(position).getLargeFish() + " kg";
                        quantity = quantity + Double.parseDouble(reservations.get(position).getLargeFish());
                    }
                    holder.fishClassText.setText(Html.fromHtml(text));

                    switch(reservations.get(position).getStatus()){
                        case "Nepotvrđeno":
                            holder.status.setTextColor(context.getResources().getColor(R.color.colorGray));
                            break;

                        case "Potvrđeno":
                            holder.status.setTextColor(context.getResources().getColor(R.color.colorBlue));
                            break;

                        case "Neuspješno":
                            holder.status.setTextColor(context.getResources().getColor(R.color.colorRed));
                            break;

                        case "Uspješno":
                            holder.status.setTextColor(context.getResources().getColor(R.color.colorGreen));
                            break;
                    }

                    String statusText = "<b>" + context.getString(R.string.status) + ":</b>" +  " "  + reservations.get(position).getStatus();
                    holder.status.setText(Html.fromHtml(statusText));
                    Double priceQuantity = quantity * Double.parseDouble(offersData.get(0).getPrice());
                    @SuppressLint("DefaultLocale") String textPrice = String.format("%.2f", priceQuantity) + " kn";
                    holder.price.setText(textPrice);

                    Glide.with(context)
                            .asBitmap()
                            .load(offersData.get(0).getImageurl())
                            .into(holder.fishImage);


                    if((reservations.get(position).getStatus().equals("Uspješno") || reservations.get(position).getStatus().equals("Neuspješno")) && reservations.get(position).getRatedStatus().equals("Neocijenjeno")){
                        holder.rateSeller.setVisibility(View.VISIBLE);
                    }
                }
            });

        Calendar calendar = DateParse.dateToCalendar(reservations.get(position).getDate().toDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm | dd.MM.yyyy.");
        String textDate = "<b>" +  context.getString(R.string.dateReservation) + "</b>" + "\n" + dateFormat.format(calendar.getTime());
        holder.date.setText(Html.fromHtml(textDate));


        holder.rateSeller.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment = null;
            @Override
            public void onClick(View view) {
                if(reservations.get(position).getStatus().equals("Neuspješno") || reservations.get(position).getStatus().equals("Uspješno")) {
                    FirestoreService firestoreService=new FirestoreService();
                    firestoreService.updateReservationRatedStatus(reservations.get(position).getReservationID(), "Ocijenjeno", "Rezervation");

                    repository.DohvatiPonuduPrekoIdPonude(reservations.get(position).getOfferID(), new FirestoreOffer() {
                                @Override
                                public void onCallback(ArrayList<OffersData> offersData) {
                                    selectedFragment = new FragmentUserRating(userID, offersData.get(0).getIdKorisnika());
                                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                                            selectedFragment).commit();
                                }

                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(ArrayList<ReservationsData> reservations) {
        Collections.sort(reservations, new Comparator<ReservationsData>() {
            public int compare(ReservationsData m1, ReservationsData m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });
        Collections.reverse(reservations);
        this.reservations = reservations;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fish;
        private TextView status;
        private TextView location;
        private TextView price;
        private ImageView fishImage;
        private TextView fishClassText;
        private TextView date;
        private ImageView deleteReservation;
        private TextView rateSeller;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fish = itemView.findViewById(R.id.reservationTitle);
            location= itemView.findViewById(R.id.textReservationLocation);
            fishImage = itemView.findViewById(R.id.reservationImage);
            price = itemView.findViewById(R.id.textReservationPrice);
            fishClassText = itemView.findViewById(R.id.textReservationGrade);
            date = itemView.findViewById(R.id.textDate);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.parentReservation);
            deleteReservation = itemView.findViewById(R.id.delete_reservation);
            rateSeller = itemView.findViewById(R.id.rate_seller);
        }
    }

    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton(reservationFragment.getActivity().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirestoreService firestoreService = new FirestoreService();
                if(!ReservationID.equals("")) {
                    firestoreService.deleteReservation(ReservationID, "Rezervation");
                    if(reservationFragment.onMyReservations) {
                        reservationFragment.refreshReservationList();
                    }
                    else if (reservationFragment.onReservationsHistory){
                        reservationFragment.showReservationHistoryDelete();
                    }
                }
            }
        });
        builder.setNegativeButton(reservationFragment.getActivity().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

}
