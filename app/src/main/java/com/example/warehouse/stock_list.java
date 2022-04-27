package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
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



public class stock_list extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    //List<String> moviesList;
    List<String> no,qty,sloc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);


        //moviesList = new ArrayList<>();

        no = new ArrayList<>();
        qty = new ArrayList<>();
        sloc = new ArrayList<>();

        Context context = getApplicationContext();


        // not working
        FirebaseDatabase rootNode;
        DatabaseReference reference;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("stock");

        Query query = reference.orderByChild("materialNO");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    no.add(String.valueOf(dataSnapshot1.child("materialNO").getValue()));
                    qty.add(String.valueOf(dataSnapshot1.child("qty").getValue()));
                    sloc.add(String.valueOf(dataSnapshot1.child("sloc").getValue()));
                }

                SwipeRefreshLayout swipeRefreshLayout;

                recyclerView = findViewById(R.id.recyclerView);
                //recyclerAdapter = new RecyclerAdapter(moviesList);
                recyclerAdapter = new RecyclerAdapter(no,qty,sloc);


                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(recyclerAdapter);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);


                swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        finish();
                        Intent i = new Intent(context,stock_list.class);
                        swipeRefreshLayout.setRefreshing(false);
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),
                        "Problem can be here",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });


        //refresh the list



    }
}