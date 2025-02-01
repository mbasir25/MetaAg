package com.example.myapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class opAdapter extends FirebaseRecyclerAdapter<OpDataBind, opAdapter.opViewholder > {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public opAdapter(@NonNull FirebaseRecyclerOptions<OpDataBind> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull opAdapter.opViewholder holder, int position, @NonNull OpDataBind model) {
        holder.actName.setText(model.getActivity());
        holder.actType.setText(model.getActivity_type());
        holder.cropName.setText(model.getCrop());
        holder.cropRate.setText(model.getCroprate());
        holder.pUnit.setText(model.getAsset_used());
        holder.pUnitDEsc.setText(model.getAsset_description());
        holder.pNote.setText(model.getAsset_note());
        holder.implementName.setText(model.getImplement());
        holder.materialName.setText(model.getMaterial());
        holder. matType.setText(model.getMaterial_type());
        holder. matAmount.setText(model.getMaterial_amount());
        holder.workNote.setText(model.getWork_amount());
        holder.operator.setText(model.getOperator());
        holder.entryTime.setText(model.getEntry());
        holder.opTime.setText(model.getDuration());
        holder.exitTime.setText(model.getExit());

    }

    @NonNull
    @Override
    public opAdapter.opViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.op_row_toshow,parent,false);
        return new opViewholder(view);
    }

    protected static class opViewholder extends RecyclerView.ViewHolder {
        TextView actName,actType,cropName,cropRate,pUnit, pUnitDEsc,pNote,implementName,materialName,matType,matAmount,workNote,operator,entryTime,opTime, exitTime;

        public opViewholder(@NonNull View itemView) {
            super(itemView);
            actName = (TextView)itemView.findViewById(R.id.actName);
            actType = (TextView)itemView.findViewById(R.id.actType);
            cropName = (TextView)itemView.findViewById(R.id.cropName);
            cropRate = (TextView)itemView.findViewById(R.id.cropRate);
            pUnit = (TextView)itemView.findViewById(R.id.pUnit);
            pUnitDEsc = (TextView)itemView.findViewById(R.id.pUnitDEsc);
            pNote = (TextView)itemView.findViewById(R.id.pNote);
            implementName = (TextView)itemView.findViewById(R.id.implementName);
            materialName = (TextView)itemView.findViewById(R.id.materialName);
            matType = (TextView)itemView.findViewById(R.id.matType);
            matAmount = (TextView)itemView.findViewById(R.id.matAmount);
            workNote = (TextView)itemView.findViewById(R.id.workNote);
            operator = (TextView)itemView.findViewById(R.id.operator);
            entryTime = (TextView)itemView.findViewById(R.id.entryTime);
            opTime = (TextView)itemView.findViewById(R.id.opTime);
            exitTime = (TextView)itemView.findViewById(R.id.exitTime);


        }
    }


}
