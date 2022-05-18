package com.example.finalprep;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class genericRecycleViewAdapter extends RecyclerView.Adapter<genericRecycleViewAdapter.ListViewholder>{

    //change Arraylist to type
    ArrayList<String> list;

    //change arraylist to type
    public genericRecycleViewAdapter(ArrayList<String> list){
        this.list = list;
    }

    @NonNull
    @Override
    public genericRecycleViewAdapter.ListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //change resource file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout_json, parent, false);

        //change arraylist list type
        ArrayList<String> list = this.list;

        genericRecycleViewAdapter.ListViewholder listViewholder = new genericRecycleViewAdapter.ListViewholder(view);
        return listViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull genericRecycleViewAdapter.ListViewholder holder, int position) {
        //set texts

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListViewholder extends RecyclerView.ViewHolder {
        public ListViewholder(@NonNull View itemView) {
            super(itemView);
            //find view by id

        }
    }
}

