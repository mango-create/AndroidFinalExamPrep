package com.example.finalprep;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class NewTweetFragment extends Fragment {
    TextView tweetContents;
    Button tweet, cancel;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public NewTweetFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Create New Tweet");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_tweet, container, false);
        tweetContents = view.findViewById(R.id.tweetNowTextView);
        tweet = view.findViewById(R.id.tweetButton);
        cancel = view.findViewById(R.id.cancelNewTweet);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tweetContents.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("body", tweetContents.getText().toString());
                    map.put("date", Calendar.getInstance().getTime());
                    map.put("userId", mAuth.getCurrentUser().getUid());
                    map.put("userName", mAuth.getCurrentUser().getDisplayName());

                    db.collection("FinalPrep").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("docId", documentReference.getId());

                            //Adds the DocId to the document
                            db.collection("FinalPrep").document(documentReference.getId()).update(map);
                            iNewTweet.sendTweet();
                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iNewTweet.cancelTweet();
            }
        });
        return view;
    }

    INewTweet iNewTweet;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof INewTweet) {
            iNewTweet = (INewTweet) context;
        } else {
            throw new RuntimeException("You forgot something important");
        }
    }
}

interface INewTweet {
    void sendTweet();
    void cancelTweet();
}