package com.example.digitalnaribarnica.recycleviewer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.database.FirestoreService;
import com.example.database.User;
import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.FirestoreCallback;
import com.example.digitalnaribarnica.FirestoreOffer;
import com.example.digitalnaribarnica.Fragments.ReservationFragment;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.Repository;
import com.example.digitalnaribarnica.RezervationCallback;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private ReservationFragment  reservationFragment;
    private ArrayList<ReservationsData> reservations=new ArrayList<>();
    private Context context;
    private CardView cardView;
    String userID ="";
    String OfferID ="";
    String smallQuantity ="";
    String mediumQuantity ="";
    String largeQuantity="";
    String ReservationID ="";

    public RequestsAdapter(Context context, ReservationFragment reservationFragment, String userId) {
        this.context = context;
        this.reservationFragment = reservationFragment;
        this.userID = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAccept(reservationFragment.getActivity(), "Upozorenje", "Želite li potvrditi rezervaciju?");
                ReservationID = reservations.get(holder.getAdapterPosition()).getReservationID();
                OfferID = reservations.get(holder.getAdapterPosition()).getOfferID();
                smallQuantity = reservations.get(holder.getAdapterPosition()).getSmallFish();
                mediumQuantity = reservations.get(holder.getAdapterPosition()).getMediumfish();
                largeQuantity = reservations.get(holder.getAdapterPosition()).getLargeFish();
            }
        });

        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDecline(reservationFragment.getActivity(), "Upozorenje", "Želite li sigurno obrisati rezervaciju?");
                ReservationID = reservations.get(holder.getAdapterPosition()).getReservationID();
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
                holder.location.setText(offersData.get(0).getLocation());
                holder.fish.setText(offersData.get(0).getName());

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

        repository.DohvatiKorisnikaPoID(reservations.get(position).getCustomerID(), new FirestoreCallback() {
            @Override
            public void onCallback(User user) {
                String textBuyer = "<b>Kupac: </b>" + user.getFullName();
                holder.buyer.setText(Html.fromHtml(textBuyer));
            }
        });

    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setRequests(ArrayList<ReservationsData> reservations) {
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
        private TextView buyer;
        private ImageButton accept;
        private ImageButton decline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fish = itemView.findViewById(R.id.requestTitle);
            location= itemView.findViewById(R.id.textReservationLocation);
            fishImage = itemView.findViewById(R.id.requestImage);
            price = itemView.findViewById(R.id.textReservationPrice);
            fishClassText = itemView.findViewById(R.id.textReservationGrade);
            date = itemView.findViewById(R.id.textDate);
            buyer = itemView.findViewById(R.id.textBuyer);
            accept = itemView.findViewById(R.id.request_accept);
            decline = itemView.findViewById(R.id.request_decline);
            cardView = itemView.findViewById(R.id.parentReservation);
        }
    }

    public void showDialogAccept(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null) builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Repository repository = new Repository();
                FirestoreService firestoreService = new FirestoreService();
                if(!ReservationID.equals("")) {
                    firestoreService.updateReservationStatus(ReservationID, "Potvrđeno", "Rezervation");
                    repository.DohvatiPonuduPrekoIdPonude(OfferID, new FirestoreOffer() {
                        @Override
                        public void onCallback(ArrayList<OffersData> offersData) {

                            String currentSmall = offersData.get(0).getSmallFish();
                            Double updatedSmall = Math.round((Double.parseDouble(currentSmall) - Double.parseDouble(smallQuantity))*100.0)/100.0;

                            String currentMedium = offersData.get(0).getMediumFish();
                            Double updatedMedium = Math.round((Double.parseDouble(currentMedium) - Double.parseDouble(mediumQuantity))*100.0)/100.0;

                            String currentLarge = offersData.get(0).getLargeFish();
                            Double updatedLarge = Math.round((Double.parseDouble(currentLarge) - Double.parseDouble(largeQuantity))*100.0)/100.0;

                            if(updatedSmall < 0 || updatedMedium < 0 || updatedLarge < 0){
                                StyleableToast.makeText(reservationFragment.getActivity(), "Unesena količina ribe više nije dostupna", 3, R.style.Toast).show();

                            }
                            else {
                                firestoreService.updateOfferQuantity(OfferID, updatedSmall.toString(), updatedMedium.toString(), updatedLarge.toString(), "Offers");

                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void showDialogDecline(Activity activity, String title, CharSequence message) {
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

                }
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

}