package com.example.digitalnaribarnica.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.database.Messages;

import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.databinding.FragmentConversationBinding;
import com.example.digitalnaribarnica.recycleviewer.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;


public class ConversationFragment extends Fragment {

    private String userId;
    private String otherUserId;
    MessageAdapter messageAdapter;
    FragmentConversationBinding binding;
    List<Messages> messagesList;
    RecyclerView recyclerView;
    private ImageButton conversationBack;
    private String cameFrom="Chat";
    ImageButton btn_send;
    EditText text_send;

    public ConversationFragment() {
    }

    public ConversationFragment(String userId, String otherUserId){
        this.userId = userId;
        this.otherUserId = otherUserId;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding = FragmentConversationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        conversationBack=binding.conversationBack;

        btn_send=binding.btnSend;
        text_send=binding.textSend;

        conversationBack.setOnClickListener(new View.OnClickListener() {
            Fragment selectedFragment =null;


            @Override
            public void onClick(View v) {
                if(cameFrom.equals("Chat")){
                    selectedFragment = new ChatFragment(userId);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }/*else if(cameFrom.equals("Offers")){
                    selectedFragment = new SearchFragment(userID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }else if(cameFrom.equals("Confirmed")){
                    selectedFragment = new ReservationFragment(userID);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();
                }*/
            }

        });



        recyclerView = binding.recyclerViewConversation;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);

        ArrayList<Messages> messagesTest = new ArrayList<>();

        messagesTest.add(new Messages(userId, "123", "Bok"));
        messagesTest.add(new Messages("123", userId, "Disi" + "\n" + "Kako si?"));
        messagesTest.add(new Messages("123", userId, "Disi" + "\n" + "Kako si?"));
        messagesTest.add(new Messages("123", userId, "Disi" + "\n" + "Kako si?"));

        messageAdapter = new MessageAdapter(getActivity(), messagesTest, userId);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!text_send.getText().toString().equals("")) {
                    messagesTest.add(new Messages(userId, "123", text_send.getText().toString()));
                    messageAdapter.notifyDataSetChanged();
                    text_send.setText("");
                    recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                }
            }
        });


        messageAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void sendMessage(String sender,String reciever, String message){
        ArrayList<Messages> messagesTest = new ArrayList<>();
        messagesTest.add(new Messages(sender, reciever,message));
    }

}