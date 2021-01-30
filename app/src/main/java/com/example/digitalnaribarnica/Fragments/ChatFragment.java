package com.example.digitalnaribarnica.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.example.digitalnaribarnica.recycleviewer.ReservationsAdapter;
import com.example.repository.Data.ChatData;
import com.example.repository.Data.OffersData;
import com.example.repository.Data.ReservationsData;
import com.example.repository.Listener.ContactsCallback;
import com.example.repository.Listener.FirestoreCallback;
import com.example.repository.Listener.FirestoreOffer;
import com.example.repository.Listener.RezervationCallback;
import com.example.repository.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class ChatFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String userId = "";
    public String chatImage;
    public String contactName;
    public String chatZadnjaPoruka;
    public String chatLastMessageDate;
    Boolean isSearching = false;
    Boolean startDontSearch = true;
    private ArrayList<ChatData> chatMessagesGeneral =new ArrayList<>();
    public String searchText;
    FragmentChatBinding binding;
    RecyclerView recyclerView;

    SearchView searchViewThisSearch;
    MenuItem itemThisSearch;

    public ChatFragment(String userId) {
        this.userId = userId;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getString(R.string.chatFragment));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBlue)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = binding.recyclerViewChat;

        ((RegisterActivity) getActivity()).fragmentId = this.getId();



        ArrayList<ChatData> chatDataTest = new ArrayList<>();
        Repository repository=new Repository();
        //repository.DodajImenikKorisnik("Contacts","234234232ed2d2");
        //OVAKO CEMO DODAVATI KORISNIKE U IMENIK NAKON KONTAKTIRANJA PODNUDITELJA/KUPCA PREMA KUPCU/PONUDITELJU
        //repository.DodatiKorisnikaUImenik("Contacts","Nikola","Pero");
        //repository.DodatiKorisnikaUImenik("Contacts","Pero","Nikola");


        //repository.DodatiKorisnikaUImenik("Contacts","2112313442442","3202340430430");
        //repository.DodatiKorisnikaUImenik("Contacts",userId,"iUXn4446xJWhzG5u02sutYGqbZF2");
        repository.DohvatiImenikPoID(userId, new ContactsCallback() {
            @Override
            public void onCallback(ArrayList<Contacts> contacts) {
                for(Contacts d:contacts){
                    Log.d("CONTACTS",d.getId());
                    repository.DohvatiKorisnikaPoID(d.getId(), new FirestoreCallback() {
                        @Override
                        public void onCallback(User user) {
                            Log.d("CONTACTS",user.getFullName());
                            chatMessagesGeneral.add( new ChatData(user.getUserID(),user.getFullName(),"Zadnja poruka",user.getPhoto(),"10.4.2029"));
                            ChatAdapter adapterChat = new ChatAdapter(getActivity(), userId, ChatFragment.this);
                            adapterChat.setChatMessages(chatMessagesGeneral);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem((R.id.action_search));
        menu.findItem((R.id.filter_menu)).setVisible(false);
        menu.findItem((R.id.all_offers_menu)).setVisible(false);
        menu.findItem((R.id.my_offers_menu)).setVisible(false);
        menu.findItem((R.id.sort_offers_menu)).setVisible(false);
        menu.findItem((R.id.onboardingHelp)).setVisible(false);
        menu.findItem((R.id.language)).setVisible(false);
        menu.findItem((R.id.current_language)).setVisible(false);
        SearchView searchView = (SearchView) item.getActionView();

        itemThisSearch = item;
        searchViewThisSearch = searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchText = query;
                searchChat(query);
                if (query.equals("")) {
                    isSearching = false;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(!startDontSearch) {
                    searchText = newText;
                    searchChat(newText);
                    if (newText.equals("")) {
                        isSearching = false;
                    }
                }
                startDontSearch = false;
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void searchChat(String search) {
        isSearching = true;
        ArrayList<ChatData> novi=new ArrayList<>();
        for(ChatData chat:chatMessagesGeneral){
            if(chat.getName().contains(search)){
                novi.add(chat);
            }
        }
        ChatAdapter adapterChat = new ChatAdapter(getActivity(), userId, ChatFragment.this);
        adapterChat.setChatMessages(novi);
        adapterChat.notifyDataSetChanged();
        recyclerView.setAdapter(adapterChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(search.equals("")){
            adapterChat = new ChatAdapter(getActivity(), userId, ChatFragment.this);
            adapterChat.setChatMessages(chatMessagesGeneral);
            adapterChat.notifyDataSetChanged();
            recyclerView.setAdapter(adapterChat);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }



    public void destroySearch() {
        MenuItemCompat.collapseActionView(itemThisSearch);
    }
}
