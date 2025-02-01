package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class contentAdapter extends FirebaseRecyclerAdapter<bot_row_model, contentAdapter.myviewholder> {
    TinyDB tinyDB;
    private OnItemsClickListener listener;


    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }
    public contentAdapter(@NonNull FirebaseRecyclerOptions<bot_row_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull bot_row_model model) {


        holder.text.setText(model.getContent());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               tinyDB =new TinyDB(v.getContext());
               String s = (String) holder.text.getText();
//               tinyDB.putString("act_name", s);
               Toast.makeText(v.getContext(), s, Toast.LENGTH_SHORT).show();
               if (listener != null){
                   listener.onItemClick(model);
               }

           }
       });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_row,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView  text;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.row_text);


        }
    }
    public interface OnItemsClickListener{
        void onItemClick(bot_row_model botRowModel);
    }



}
