package com.example.finalprep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewTweetFragment extends Fragment {
    String docId;
    TextView body, date, documentId, userId, userName;
    FirebaseFirestore db;

    public ViewTweetFragment(String docId) {
        this.docId = docId;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("View Tweet");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_tweet, container, false);

        body = view.findViewById(R.id.bodyTextView);
        date = view.findViewById(R.id.dateTextView);
        documentId = view.findViewById(R.id.docIdTextView);
        userId = view.findViewById(R.id.userIdTextView);
        userName = view.findViewById(R.id.userNameTextView);

        db = FirebaseFirestore.getInstance();
        db.collection("FinalPrep").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    Tweet t = new Tweet(
                            doc.getString("body"),
                            doc.getString("userName"),
                            doc.getString("userId"),
                            doc.getDate("date"),
                            doc.getString("docId"));

                    body.setText(t.getBody());
                    date.setText(t.getDate()+"");
                    documentId.setText(t.getDocId());
                    userId.setText(t.getUserId());
                    userName.setText(t.getUserName());
                }
            }
        });
        return view;
    }
}