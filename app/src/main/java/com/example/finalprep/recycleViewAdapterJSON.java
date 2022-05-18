package com.example.finalprep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class recycleViewAdapterJSON extends  RecyclerView.Adapter<recycleViewAdapterJSON.ListViewholder>{
    //CHANGE ARRAYLIST TYPE
    ArrayList<Post> list;

    //CHANGE ARRAYLIST TYPE
    public recycleViewAdapterJSON(ArrayList<Post> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CHANGE LAYOUTFILE
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout_json, parent, false);

        //CHANGE ARRAYLIST TYPE
        ArrayList<Post> list = this.list;
        recycleViewAdapterJSON.ListViewholder listViewholder = new recycleViewAdapterJSON.ListViewholder(view);
        return listViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull recycleViewAdapterJSON.ListViewholder holder, int position) {
        //setTexts
        holder.body.setText(list.get(position).getPost_text());
        holder.name.setText(list.get(position).getCreated_by_name());
        holder.time.setText(list.get(position).getCreated_at());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListViewholder extends RecyclerView.ViewHolder{
        TextView body, name, time;
        public ListViewholder(@NonNull View itemView){
            super(itemView);
            //find view by id
            body = itemView.findViewById(R.id.textView4);
            name = itemView.findViewById(R.id.textView5);
            time = itemView.findViewById(R.id.textView6);

        }
    }
}