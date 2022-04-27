package com.example.warehouse;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    List<String> no1,qty1,sloc1;


    public RecyclerAdapter(List<String> no1, List<String> qty1, List<String> sloc1) {
        this.no1 = no1;
        this.qty1 = qty1;
        this.sloc1 = sloc1;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.sloc.setText(String.valueOf(position));
        holder.no.setText(no1.get(position));
        holder.qty.setText(qty1.get(position));
        holder.sloc.setText(sloc1.get(position));
    }

    @Override
    public int getItemCount() {
        return no1.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView no,qty,sloc;

        public void RemoveItem(String key){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference =  database.getReference().child("stock").child(key);
            reference.removeValue();
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.cardno);
            qty = itemView.findViewById(R.id.cardqty);
            sloc = itemView.findViewById(R.id.cardsloc);

            itemView.setOnClickListener(this);
            final String[] key = new String[1];


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    key[0] = no1.get(getAbsoluteAdapterPosition());
                    no1.remove(getAbsoluteAdapterPosition());
                    notifyItemRemoved(getAbsoluteAdapterPosition());
                    RemoveItem(key[0]);
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
