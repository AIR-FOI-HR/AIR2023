package com.example.digitalnaribarnica.recycleviewer;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.DialogInterface;
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
import com.example.digitalnaribarnica.Fragments.ReservationFragment;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.RezervationCallback;

import androidx.fragment.app.Fragment;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder>{

    private ReservationFragment  reservationFragment;
    private ArrayList<ReservationsData> reservations=new ArrayList<>();
    private Context context;
    private CardView cardView;
    private ImageView deleteReservation;
    String userID ="";
    String ReservationID ="";
    String reservationDate = "";
    Boolean deleteIt = false;


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
                showDialog(reservationFragment.getActivity(), "Upozorenje", "Želite li sigurno obrisati rezervaciju?");
                ReservationID = reservations.get(holder.getAdapterPosition()).getReservationID();
                /*
                ArrayList<ReservationsData> reservationList = new ArrayList<>();
                Repository repository=new Repository();
                repository.DohvatiRezervacije1(new RezervationCallback() {
                    @Override
                    public void onCallback(ArrayList<ReservationsData> rezervations) {
                        for (int i = 0; i < rezervations.size(); i++) {
                            if(rezervations.get(i).getCustomerID().equals(userID))
                                reservationList.add(rezervations.get(i));
                        }

                        setReservations(reservationList);
                    }
                });*/

            }
        });

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
                Double priceQuantity = quantity * Double.parseDouble(offersData.get(0).getPrice());
                String textPrice = priceQuantity.toString() + " kn";
                holder.price.setText(textPrice);

                Glide.with(context)
                        .asBitmap()
                        .load(offersData.get(0).getImageurl())
                        .into(holder.fishImage);
            }
        });

        Calendar calendar = DateParse.dateToCalendar(reservations.get(position).getDate().toDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm | dd.MM.yyyy.");
        String textDate = "<b>" +  context.getString(R.string.dateReservation) + "</b>" + "\n" + dateFormat.format(calendar.getTime());
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
        private ImageView deleteReservation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fish = itemView.findViewById(R.id.reservationTitle);
            location= itemView.findViewById(R.id.textReservationLocation);
            fishImage = itemView.findViewById(R.id.reservationImage);
            price = itemView.findViewById(R.id.textReservationPrice);
            fishClassText = itemView.findViewById(R.id.textReservationGrade);
            date = itemView.findViewById(R.id.textDate);
            cardView = itemView.findViewById(R.id.parentReservation);
            deleteReservation = itemView.findViewById(R.id.delete_reservation);
        }
    }

    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Repository repository = new Repository();
                FirestoreService firestoreService = new FirestoreService();
                if(!ReservationID.equals("")) {
                    firestoreService.deleteReservation(ReservationID, "Rezervation");
                    reservationFragment.refreshReservationList();

                }
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TagPolje", "Tu ide kao poz");
            }
        });
        builder.show();
    }

}
