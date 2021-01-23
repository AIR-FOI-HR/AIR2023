package com.example.digitalnaribarnica.recycleviewer;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.database.FirestoreService;
import com.example.database.User;
import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.Fragments.ProfileFragment;
import com.example.repository.Listener.FirestoreCallback;
import com.example.repository.Listener.FirestoreOffer;
import com.example.digitalnaribarnica.Fragments.FragmentUserRating;
import com.example.digitalnaribarnica.Fragments.ReservationFragment;
import com.example.digitalnaribarnica.R;
import com.example.repository.Repository;
import com.example.repository.Data.OffersData;
import com.example.repository.Data.ReservationsData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ConfirmedRequestsAdapter extends RecyclerView.Adapter<ConfirmedRequestsAdapter.ViewHolder>{

    private ReservationFragment  reservationFragment;
    private CardView cardView;
    private ArrayList<ReservationsData> confirmedRequests=new ArrayList<>();
    private Context context;
    String userID = "";
    String ReservationID = "";
    String buyerID = "";


    public ConfirmedRequestsAdapter(Context context, ReservationFragment reservationFragment, String userId) {
        this.context = context;
        this.reservationFragment = reservationFragment;
        this.userID = userId;
    }

    @NonNull
    @Override
    public ConfirmedRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmed_request_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAccept(reservationFragment.getActivity(), "Upozorenje", "Želite li potvrditi da je preuzimanje i kupnja ribe uspješno provedena?");
                ReservationID = confirmedRequests.get(holder.getAdapterPosition()).getReservationID();
                buyerID = confirmedRequests.get(holder.getAdapterPosition()).getCustomerID();

            }
        });

        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDecline(reservationFragment.getActivity(), "Upozorenje", "Želite li potvrditi da kupnja ribe nije uspješno provedena?");
                ReservationID = confirmedRequests.get(holder.getAdapterPosition()).getReservationID();
                buyerID = confirmedRequests.get(holder.getAdapterPosition()).getCustomerID();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmedRequestsAdapter.ViewHolder holder, int position) {
        Repository repository = new Repository();
        FirestoreService firestoreService=new FirestoreService();

        repository.DohvatiPonuduPrekoIdPonude(confirmedRequests.get(position).getOfferID(), new FirestoreOffer() {
            @Override
            public void onCallback(ArrayList<OffersData> offersData) {
                holder.location.setText(offersData.get(0).getLocation());
                holder.fish.setText(offersData.get(0).getName());

                Double quantity = 0.0;
                String text ="";
                if (!confirmedRequests.get(position).getSmallFish().equals("0")){
                    text = text + "<b>" + context.getString(R.string.smallFish) + "</b>" +  " "  + confirmedRequests.get(position).getSmallFish() + " kg" + "<br>";
                    quantity = quantity + Double.parseDouble(confirmedRequests.get(position).getSmallFish());
                }
                if (!confirmedRequests.get(position).getMediumfish().equals("0")){
                    text = text + "<b>" + context.getString(R.string.mediumFish) + "</b>" +  " " + confirmedRequests.get(position).getMediumfish() + " kg" + "<br>";
                    quantity = quantity + Double.parseDouble(confirmedRequests.get(position).getMediumfish());
                }
                if (!confirmedRequests.get(position).getLargeFish().equals("0")){
                    text = text +  "<b>" + context.getString(R.string.largeFish) + "</b>" +  " " + confirmedRequests.get(position).getLargeFish() + " kg";
                    quantity = quantity + Double.parseDouble(confirmedRequests.get(position).getLargeFish());
                }
                holder.fishClassText.setText(Html.fromHtml(text));
                Double priceQuantity = quantity * Double.parseDouble(offersData.get(0).getPrice());
                @SuppressLint("DefaultLocale") String textPrice = String.format("%.2f", priceQuantity) + " kn";
                holder.price.setText(textPrice);

                Glide.with(context)
                        .asBitmap()
                        .load(offersData.get(0).getImageurl())
                        .into(holder.fishImage);
            }
        });

        Calendar calendar = DateParse.dateToCalendar(confirmedRequests.get(position).getDate().toDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm | dd.MM.yyyy.");
        String textDate = "<b>" +  context.getString(R.string.dateReservation) + "</b>" + "\n" + dateFormat.format(calendar.getTime());
        holder.date.setText(Html.fromHtml(textDate));

        repository.DohvatiKorisnikaPoID(confirmedRequests.get(position).getCustomerID(), new FirestoreCallback() {
            @Override
            public void onCallback(User user) {
                holder.buyer.setText(user.getFullName());
                String badge = user.getBadgeBuyerURL();
                if(!badge.equals("")){
                    Glide.with(context)
                            .asBitmap()
                            .load(badge)
                            .into(holder.badgeImage);
                }
            }
        });


        holder.buyer.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment = null;
            @Override
            public void onClick(View view) {
                selectedFragment = new ProfileFragment(confirmedRequests.get(holder.getAdapterPosition()).getCustomerID(), userID, "Confirmed");
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                        selectedFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.confirmedRequests.size();
    }

    public void setConfirmedRequests(ArrayList<ReservationsData> confirmedRequests) {
        this.confirmedRequests = confirmedRequests;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fish;
        private TextView location;
        private TextView price;
        private ImageView fishImage;
        private TextView fishClassText;
        private ImageView badgeImage;
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
            badgeImage = itemView.findViewById(R.id.badgeImage);
            decline = itemView.findViewById(R.id.request_decline);
            cardView = itemView.findViewById(R.id.parentReservation);
        }
    }

    public void showDialogAccept(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null) builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            Fragment selectedFragment =null;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Repository repository = new Repository();
                FirestoreService firestoreService = new FirestoreService();
                if(!ReservationID.equals("")) {
                    firestoreService.updateReservationStatus(ReservationID, "Uspješno", "Rezervation");

                    repository.DohvatiKorisnikaPoID(userID, new FirestoreCallback() {
                        @Override
                        public void onCallback(User user) {
                            FirestoreService firestoreService = new FirestoreService();
                            Integer addSales = user.getNumberOfSales();
                            addSales++;
                            firestoreService.updateNumberOfSales(userID, addSales.toString(), "Users");
                        }
                    });

                    repository.DohvatiKorisnikaPoID(buyerID, new FirestoreCallback() {
                        @Override
                        public void onCallback(User user) {
                            FirestoreService firestoreService = new FirestoreService();
                            Integer addPurchases = user.getNumberOfPurchases();
                            addPurchases++;
                            firestoreService.updateNumberOfPurchases(buyerID, addPurchases.toString(), "Users");
                        }
                    });

                    Log.d("TagPolje", buyerID);
                    selectedFragment = new FragmentUserRating(userID, buyerID, "Prodavatelj");
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
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
            Fragment selectedFragment =null;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Repository repository = new Repository();
                FirestoreService firestoreService = new FirestoreService();
                if(!ReservationID.equals("")) {
                    firestoreService.updateReservationStatus(ReservationID, "Neuspješno", "Rezervation");
                    selectedFragment = new FragmentUserRating(userID, buyerID);
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
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