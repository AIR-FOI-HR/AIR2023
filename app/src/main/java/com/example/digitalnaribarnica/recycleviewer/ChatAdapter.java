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

import com.example.digitalnaribarnica.Fragments.ChatFragment;
import com.example.digitalnaribarnica.Fragments.SearchFragment;
import com.example.digitalnaribarnica.R;
import com.example.repository.Data.ChatData;
import com.example.repository.Repository;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private ArrayList<ChatData> chat=new ArrayList<>();
    private Context context;
    private CardView cardView;
    private String userID;
    public ImageView chatImage;
    public TextView contactName;
    public TextView chatZadnjaPoruka;
    public TextView chatLastMessageDate;
    private ChatFragment chatFragment;

    public ChatAdapter(Context context, ChatFragment chatFragment) {
        this.context = context;
        this.chatFragment = chatFragment;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView){
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        ChatAdapter.ViewHolder holder = new ChatAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView contactName;
        private ImageView chatImage;
        private TextView chatZadnjaPoruka;
        private TextView chatLastMessageDate;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contactName);
            chatImage= itemView.findViewById(R.id.chatImage);
            chatZadnjaPoruka = itemView.findViewById(R.id.chatZadnjaPoruka);
            chatLastMessageDate = itemView.findViewById(R.id.chatLastMessageDate);
            cardView=itemView.findViewById(R.id.chatParent);

        }
    }


}
