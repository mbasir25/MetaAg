package com.example.myapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fieldnameAdapter extends RecyclerView.Adapter<fieldnameAdapter.myviewholder>{

    ArrayList<FieldListDataModel> fieldnameholder;
    private  SelectListener listener;

    public fieldnameAdapter(ArrayList<FieldListDataModel> fieldnameholder, SelectListener listener) {
        this.fieldnameholder = fieldnameholder;
        this.listener = listener;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_fieldlsit,parent,false);
        return  new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.fieldname.setText(fieldnameholder.get(position).getFieldname());
        holder.entry.setText(fieldnameholder.get(position).getEntry());
        holder.exit.setText(fieldnameholder.get(position).getExit());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(fieldnameholder.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return fieldnameholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView fieldname, entry,exit;
        public CardView cardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            fieldname = itemView.findViewById(R.id.fieldname);
            entry = itemView.findViewById(R.id.entry);
            exit = itemView.findViewById(R.id.exit);
            cardView = itemView.findViewById(R.id.single_fieldview);



        }
    }

}
