package com.example.finalprep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListViewholder> {
    ArrayList<Tweet> list;
    ITweets iTweets;

    public RecyclerViewAdapter(ArrayList<Tweet> list, ITweets iTweets) {
        this.list = list;
        this.iTweets = iTweets;
    }


    @NonNull
    @Override
    public ListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout_tweets1, parent, false);
        ArrayList<Tweet> list = this.list;
        ListViewholder listViewholder = new ListViewholder(view, list, iTweets);
        return listViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewholder holder, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        holder.body.setText(list.get(position).getBody());
        holder.name.setText(list.get(position).getUserName());

        String myDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mmaa").format(list.get(position).getDate());
        holder.timestamp.setText(myDateFormat);

        //Shows Trashcan if current user
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(!currentUserId.equals(list.get(position).getUserId())){
            holder.imgViewTrash.setVisibility(View.GONE);
        } else{
            holder.imgViewTrash.setVisibility(View.VISIBLE);
        }


        holder.imgViewHeart.setTag(0);
        db.collection("FinalPrep").document(list.get(position).getDocId())
                .collection("likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot doc: value){
                    if (doc.getString("userId") == currentUserId){
                        holder.imgViewHeart.setTag(1);
                        holder.imgViewHeart.setImageResource(R.drawable.like_favorite);
                    } else{
                        holder.imgViewHeart.setTag(0);
                        holder.imgViewHeart.setImageResource(R.drawable.like_not_favorite);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListViewholder extends RecyclerView.ViewHolder{
        //textview fields
        TextView body, name, timestamp;
        ImageView imgViewTrash, imgViewHeart;
        ArrayList<Tweet> list = new ArrayList<>();
        private FirebaseAuth mAuth;
        private FirebaseUser user;
        ITweets iTweets;

        //pass in the list down the line
        public ListViewholder(@NonNull View itemView, ArrayList<Tweet> mylist, ITweets iTweets){
            super(itemView);
            this.list = mylist;
            this.iTweets = iTweets;

            body = itemView.findViewById(R.id.tweetRecycle);
            name = itemView.findViewById(R.id.userRecycle);
            timestamp = itemView.findViewById(R.id.dateRecycle);
            imgViewTrash = itemView.findViewById(R.id.garbage);
            imgViewHeart = (ImageView) itemView.findViewById(R.id.heart);


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();


            imgViewHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((Integer) imgViewHeart.getTag() == 0){
                        imgViewHeart.setImageResource(R.drawable.like_favorite);
                        imgViewHeart.setTag(1);

                        Map<String, Object> map = new HashMap<>();
                        map.put("userId", mAuth.getUid());

                        db.collection("FinalPrep").document(list.get(getAdapterPosition()).getDocId())
                                .collection("likes").add(map);

                    } else {
                        imgViewHeart.setImageResource(R.drawable.like_not_favorite);
                        imgViewHeart.setTag(0);
                        db.collection("FinalPrep").document(list.get(getAdapterPosition()).getDocId())
                                .collection("likes").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot doc: task.getResult()){
                                    if (doc.getString("userId").equals(user.getUid())){
                                        db.collection("FinalPrep")
                                                .document(list.get(getAdapterPosition()).getDocId())
                                                .collection("likes")
                                                .document(doc.getId()).delete();
                                    }
                                }
                            }
                        });
                    }
                }
            });


            imgViewTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(view.getContext());
                    ad.setTitle("Delete Post?");
                    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.collection("FinalPrep").document(list.get(getAdapterPosition()).getDocId())
                                    .delete();
                        }
                    });
                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    ad.create().show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iTweets.viewTweet(list.get(getAdapterPosition()).getDocId());
                }
            });
        }
    }
}
