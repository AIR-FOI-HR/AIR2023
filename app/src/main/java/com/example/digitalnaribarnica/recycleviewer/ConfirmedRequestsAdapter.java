package com.example.digitalnaribarnica.recycleviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ConfirmedRequestsAdapter extends RecyclerView.Adapter<ConfirmedRequestsAdapter.ViewHolder>{

    private CardView cardView;
    private ArrayList<ReservationsData> confirmedRequests=new ArrayList<>();
    private Context context;
    String userID ="";
    String OfferID ="";
    String smallQuantity ="";
    String mediumQuantity ="";
    String largeQuantity="";
    String ReservationID ="";
    String reservationDate ="";
    String buyerID = "";

    @NonNull
    @Override
    public ConfirmedRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmed_request_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmedRequestsAdapter.ViewHolder holder, int position) {

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
}
