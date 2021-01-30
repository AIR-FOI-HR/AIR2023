package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.Contacts;
import com.example.database.User;
import com.example.database.Utils.DateParse;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.RegisterActivity;
import com.example.digitalnaribarnica.databinding.FragmentChatBinding;
import com.example.digitalnaribarnica.databinding.FragmentSearchBinding;
import com.example.digitalnaribarnica.recycleviewer.ChatAdapter;
import com.example.digitalnaribarnica.recycleviewer.OfferAdapter;
import com.example.repository.Data.ChatData;
import com.example.repository.Data.OffersData;
import com.example.repository.Listener.ContactsCallback;
import com.example.repository.Listener.FirestoreCallback;
import com.example.repository.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String userId = "";
    public String chatImage;
    public String contactName;
    public String chatZadnjaPoruka;
    public String chatLastMessageDate;

    private ArrayList<ChatData> chatMessagesGeneral =new ArrayList<>();

    FragmentChatBinding binding;
    RecyclerView recyclerView;

    public ChatFragment(String userId) {
        this.userId = userId;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);

        recyclerView = binding.recyclerViewChat;

        ((RegisterActivity) getActivity()).fragmentId = this.getId();

        ArrayList<ChatData> chatDataTest = new ArrayList<>();
        Repository repository=new Repository();
        repository.DohvatiImenikPoID(userId, new ContactsCallback() {
            @Override
            public void onCallback(ArrayList<Contacts> contacts) {
                for(Contacts d:contacts){
                    Log.d("CONTACTS",d.getId());
                    repository.DohvatiKorisnikaPoID(d.getId(), new FirestoreCallback() {
                        @Override
                        public void onCallback(User user) {
                            Log.d("CONTACTS",user.getFullName());
                            chatDataTest.add( new ChatData(user.getUserID(),user.getFullName(),"Zadnja poruka",user.getPhoto(),"10.4.2029"));
                            ChatAdapter adapterChat = new ChatAdapter(getActivity(), userId, ChatFragment.this);
                            adapterChat.setChatMessages(chatDataTest);
                            adapterChat.notifyDataSetChanged();
                            recyclerView.setAdapter(adapterChat);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }

                    });
                }

            }
        });

        /*chatDataTest.add(new ChatData("adjhak", "Neko ime", "Seen", "https://firebasestorage.googleapis.com/v0/b/digitalna-ribarnica-fb.appspot.com/o/profilne%2F113865007966208087640.png?alt=media&token=083f1696-2b91-4247-a6c2-b1f974215874","2021-01-30 12:13:14"));
        chatDataTest.add(new ChatData("adjhak", "Neko ime 2", "Bla bla", "https://firebasestorage.googleapis.com/v0/b/digitalna-ribarnica-fb.appspot.com/o/profilne%2FXgsooqJxFjhuu2czKHmNccML6lA2.png?alt=media&token=d119f01d-e230-4b44-9b0c-aa78040dc369","2021-01-30 10:02:14"));*/









        return view;
    }
}
