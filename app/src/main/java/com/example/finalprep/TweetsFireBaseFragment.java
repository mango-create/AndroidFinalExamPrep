package com.example.finalprep;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TweetsFireBaseFragment extends Fragment {
    Button logoutButton, newTweetButton, JSONButton;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewAdapter adapter;
    ArrayList<Tweet> list = new ArrayList<>();

    public TweetsFireBaseFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Tweets");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets, container, false);
        logoutButton = view.findViewById(R.id.logoutButton);
        newTweetButton = view.findViewById(R.id.newTweetButton);
        JSONButton = view.findViewById(R.id.jsonButton);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(list, iTweets);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("FinalPrep").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                for (QueryDocumentSnapshot doc: value){
                    list.add(new Tweet(
                            doc.getString("body"),
                            doc.getString("userName"),
                            doc.getString("userId"),
                            doc.getDate("date"),
                            doc.getString("docId")));
                }

                Collections.sort(list, new Comparator<Tweet>() {
                    @Override
                    public int compare(Tweet t1, Tweet t2) {
                        return t2.getDate().compareTo(t1.getDate());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                iTweets.logout();
            }
        });

        JSONButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTweets.JSON();
            }
        });

        newTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTweets.newTweet();
            }
        });
        return view;
    }


    ITweets iTweets;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ITweets) {
            iTweets = (ITweets) context;
        } else {
            throw new RuntimeException("You forgot something important");
        }
    }
}
interface ITweets {
    void logout();
    void newTweet();
    void viewTweet(String docId);
    void JSON();
}