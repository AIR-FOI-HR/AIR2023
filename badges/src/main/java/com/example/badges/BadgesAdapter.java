package com.example.badges;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.badges.BadgesData;

import java.util.ArrayList;
import java.util.Locale;


public class BadgesAdapter extends RecyclerView.Adapter<BadgesAdapter.ViewHolder> {

    private ArrayList<BadgesData> badges=new ArrayList<>();
    private CardView cardView;
    private Context context;
    private String userID;
    private Fragment badgesFragment;

    public BadgesAdapter(Context context, String userID, androidx.fragment.app.Fragment fragment) {
        this.userID = userID;
        this.context = context;
        this.badgesFragment = fragment;
    }

    @NonNull
    @Override
    public BadgesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BadgesAdapter.ViewHolder holder, int position) {
        
        if(!Locale.getDefault().getDisplayLanguage().equals("English")){
            holder.badgeTitle.setText(badges.get(position).getTitle());
            holder.badgeDescription.setText(badges.get(position).getDescription());
        }else{
            holder.badgeTitle.setText(badges.get(position).getTitleeng());
            holder.badgeDescription.setText(badges.get(position).getDescriptioneng());
        }

        Glide.with(context)
                .asBitmap()
                .load(badges.get(position).getBadgeURL())
                .into(holder.badgeImage);

    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    public void setBadges(ArrayList<BadgesData> allBadges) {
        this.badges = allBadges;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView badgeTitle;
        private TextView badgeDescription;
        private ImageView badgeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            badgeTitle= itemView.findViewById(R.id.badge_text);
            badgeDescription = itemView.findViewById(R.id.text_badge_description);
            badgeImage = itemView.findViewById(R.id.badgeImage);
            cardView = itemView.findViewById(R.id.parent);
        }
    }
}


