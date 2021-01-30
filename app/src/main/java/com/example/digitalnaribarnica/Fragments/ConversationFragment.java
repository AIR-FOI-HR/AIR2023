package com.example.digitalnaribarnica.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.database.Messages;
import com.example.digitalnaribarnica.R;
import com.example.digitalnaribarnica.databinding.FragmentChatBinding;
import com.example.digitalnaribarnica.databinding.FragmentConversationBinding;
import com.example.digitalnaribarnica.recycleviewer.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment {

    private String userId;
    private String otherUserId;
    MessageAdapter messageAdapter;
    FragmentConversationBinding binding;
    List<Messages> messagesList;
    RecyclerView recyclerView;

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
        binding = FragmentConversationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = binding.recyclerViewConversation;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);

        ArrayList<Messages> messagesTest = new ArrayList<>();

        messagesTest.add(new Messages(userId, "kSfbtBmWjtMv65jL4mFcZUlDzho1", "Bok"));
        messagesTest.add(new Messages("kSfbtBmWjtMv65jL4mFcZUlDzho1", userId, "Disi" + "\n" + "Kako si?"));

        messageAdapter = new MessageAdapter(getActivity(), messagesTest, userId);
        messageAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }
}