package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class loading_list extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    //List<String> moviesList;
    List<String> no,tloc,sloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        //moviesList = new ArrayList<>();

        no = new ArrayList<>();
        tloc = new ArrayList<>();
        sloc = new ArrayList<>();

        Context context = getApplicationContext();


        // not working
        FirebaseDatabase rootNode;
        DatabaseReference reference;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("loading");

        Query query = reference.orderByChild("materialNO");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    no.add(String.valueOf(dataSnapshot1.child("materialNO").getValue()));
                    tloc.add(String.valueOf(dataSnapshot1.child("tloc").getValue()));
                    sloc.add(String.valueOf(dataSnapshot1.child("sloc").getValue()));
                }

                recyclerView = findViewById(R.id.recyclerView2);
                //recyclerAdapter = new RecyclerAdapter(moviesList);
                recyclerAdapter = new RecyclerAdapter(no,tloc,sloc);


                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(recyclerAdapter);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),
                        "Problem can be here",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
}