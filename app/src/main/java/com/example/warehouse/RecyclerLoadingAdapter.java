package com.example.warehouse;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerLoadingAdapter extends RecyclerView.Adapter<RecyclerLoadingAdapter.myHolder>{

    List<String> no1,sloc1,tloc1;


    public RecyclerLoadingAdapter(List<String> no1, List<String> sloc1, List<String> tloc1) {
        this.no1 = no1;
        this.sloc1 = sloc1;
        this.tloc1 = tloc1;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item,parent,false);
        RecyclerLoadingAdapter.myHolder viewHolder = new RecyclerLoadingAdapter.myHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.no.setText(no1.get(position));
        holder.sloc.setText(sloc1.get(position));
        holder.tloc.setText(tloc1.get(position));
        holder.slocname.setText("Sloc :");
        holder.tlocname.setText("Tloc :");
    }

    @Override
    public int getItemCount() {
        return no1.size();
    }

    class myHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView no,sloc,tloc,slocname,tlocname;
        //Context context = no.getContext();

        public void RemoveItem(String key){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference =  database.getReference().child("loading").child(key);
            reference.removeValue();
        }

        public myHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.cardno);
            sloc = itemView.findViewById(R.id.cardqty);
            tloc = itemView.findViewById(R.id.cardsloc);
            slocname = itemView.findViewById(R.id.valueof2);
            tlocname = itemView.findViewById(R.id.valueof3);

            itemView.setOnClickListener(this);
            final String[] key = new String[1];


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    new AlertDialog.Builder(view.getContext()).setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("Delete Record")
                            .setMessage("Do you want to delete this record?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    key[0] = no1.get(getAbsoluteAdapterPosition());
                                    no1.remove(getAbsoluteAdapterPosition());
                                    notifyItemRemoved(getAbsoluteAdapterPosition());
                                    RemoveItem(key[0]);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();

                    return true;
                }
            });

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),no1.get(getAbsoluteAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
