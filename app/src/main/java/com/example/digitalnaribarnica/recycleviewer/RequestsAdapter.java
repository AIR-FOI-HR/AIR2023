package com.example.digitalnaribarnica.recycleviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalnaribarnica.R;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {


    private ArrayList<RequestsData> requests=new ArrayList<>();
    private Context context;
    private CardView cardView;

    public RequestsAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.prvi.setText(requests.get(position).getRequestTitle());
        holder.drugi.setText(requests.get(position).getLocation());
        holder.cetvrti.setText(requests.get(position).getPrice());
        holder.peti.setText(requests.get(position).getGrade());


        Glide.with(context)
                .asBitmap()
                .load(requests.get(position).getRequestImage())
                .into(holder.treci);

        Glide.with(context)
                .asBitmap()
                .load(requests.get(position).getRequestTrophyImage())
                .into(holder.sesti);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void setRequests(ArrayList<RequestsData> requests) {
        this.requests = requests;
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
            prvi = itemView.findViewById(R.id.requestTitle);
            drugi= itemView.findViewById(R.id.textRequestLocation);
            treci = itemView.findViewById(R.id.requestImage);
            cetvrti = itemView.findViewById(R.id.textRequestPrice);
            peti = itemView.findViewById(R.id.textRequestGrade);
            sesti = itemView.findViewById(R.id.trophyRequestImage);
            cardView=itemView.findViewById(R.id.parentRequest);
        }
    }



    /*
    @NonNull
    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    */

}
