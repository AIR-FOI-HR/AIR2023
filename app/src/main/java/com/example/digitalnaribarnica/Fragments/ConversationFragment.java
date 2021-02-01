package com.example.digitalnaribarnica.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.database.Messages;

import com.example.database.User;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.ViewModel.SharedViewModel;
import com.example.digitalnaribarnica.databinding.FragmentConversationBinding;
import com.example.digitalnaribarnica.recycleviewer.MessageAdapter;
import com.example.repository.Listener.MessageCallback;
import com.example.repository.Repository;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConversationFragment extends Fragment {

    private String currentUserId;
    private String otherUserId;
    private String offerId;
    MessageAdapter messageAdapter;
    FragmentConversationBinding binding;
    List<Messages> messagesList;
    RecyclerView recyclerView;
    private ImageButton conversationBack;
    private String cameFrom="Chat";
    ImageButton btn_send;
    EditText text_send;

    ImageView conversationPhoto;
    TextView conversationName;

    private SharedViewModel sharedViewModel;

    public ConversationFragment() {
    }

    public ConversationFragment(String currentUserId, String otherUserId){
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
    }

    public ConversationFragment(String otherUserId, String currentUserId, String cameFrom, String offerId) {
        this.otherUserId = otherUserId;
        this.currentUserId=currentUserId;
        this.cameFrom = cameFrom;
        this.offerId = offerId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding = FragmentConversationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        conversationBack=binding.conversationBack;

        conversationPhoto = binding.conversationSlika;
        conversationName = binding.conversationImePrezime;

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.DohvatiKorisnikaPoID(otherUserId);
        sharedViewModel.userMutableLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                conversationName.setText(user.getFullName());
                Glide.with(getContext())
                        .asBitmap()
                        .load(user.getPhoto())
                        .into(conversationPhoto);

            }
        });

        btn_send=binding.btnSend;
        text_send=binding.textSend;

        Date date = new Date();
        date.getTime();
        Timestamp timestamp = new Timestamp(date);


        conversationBack.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;

            @Override
            public void onClick(View v) {
                if(cameFrom.equals("Chat")){
                    selectedFragment = new ChatFragment(currentUserId);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else if(cameFrom.equals("Details")){
                }else if(cameFrom.equals("Details")){
                    selectedFragment = new OfferDetailFragment(offerId, currentUserId, false);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }
            }

        });



        recyclerView = binding.recyclerViewConversation;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);

        //čitanje poruka
        readMessages(currentUserId);

        ArrayList<Messages> messagesTest = new ArrayList<>();

        Repository repository=new Repository();
        repository.DohvatiBrojPorukaKorisnika("Contacts", currentUserId, otherUserId, Long.valueOf(30), new MessageCallback() {
            @Override
            public void onCallback(ArrayList<Messages> Messages) {
                for(Messages poruka:Messages){
                    //Log.d("PORUKA",poruka.getMessage());
                    //Log.d("PORUKA",poruka.getSender());
                    if(poruka.getSender().equals(currentUserId))
                        messagesTest.add(new Messages(currentUserId, poruka.getMessage(), poruka.getDatetimeMessage()));
                    else
                        messagesTest.add(new Messages(otherUserId, poruka.getMessage(), poruka.getDatetimeMessage()));

                    messageAdapter = new MessageAdapter(getActivity(), messagesTest, currentUserId);
                    messageAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(messageAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            }
        });

        messageAdapter = new MessageAdapter(getActivity(), messagesTest, currentUserId);
        Log.d("datumm", Timestamp.now().toString());



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")) {
                    messagesTest.add(new Messages(currentUserId, msg, Timestamp.now()));
                    messageAdapter.notifyDataSetChanged();
                    text_send.setText("");
                    recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                    //slanje poruke na firestore
                    sendMessage(currentUserId, msg);
                    Timestamp vrijeme_slanja =Timestamp.now();
                    repository.DodatiKorisnikaUImenik("Contacts",currentUserId,otherUserId);
                    repository.DodatiKorisnikaUImenik("Contacts",otherUserId,currentUserId);

                    repository.DodajPorukuKorisnik("Contacts",currentUserId,otherUserId,currentUserId,msg,vrijeme_slanja);
                    repository.DodajPorukuKorisnik("Contacts",otherUserId,currentUserId,currentUserId,msg,vrijeme_slanja);
                }
            }
        });

        messageAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void sendMessage(String sender, String message){
        //ovdje pozvat funkciju za slanje poruke preko repositoryja

        /*ArrayList<Messages> messagesTest = new ArrayList<>();
        messagesTest.add(new Messages(sender, receiver,message));*/
    }

    private void readMessages(String currentUserId) {
        //ovdje pozvat funkciju za dohvaćanje poruka preko repositoryja i spremit u messagesList i stavit u messageAdapter (part 8)
        /*messagesList.add(new Messages(currentUserId, poruka));
          messageAdapter = new MessageAdapter(getActivity(), messagesList, currentUserId); ili getContext()
          messageAdapter.notifyDataSetChanged();
          recyclerView.setAdapter(messageAdapter);*/
    }

}