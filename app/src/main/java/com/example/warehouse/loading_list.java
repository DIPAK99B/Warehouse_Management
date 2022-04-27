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

public class loading_list extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerLoadingAdapter recyclerAdapter1;

    //List<String> moviesList;
    List<String> no3,sloc3,tloc3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_list);

        //moviesList = new ArrayList<>();

        no3 = new ArrayList<>();
        sloc3 = new ArrayList<>();
        tloc3 = new ArrayList<>();

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
                    no3.add(String.valueOf(dataSnapshot1.child("materialNO").getValue()));
                    sloc3.add(String.valueOf(dataSnapshot1.child("sloc").getValue()));
                    tloc3.add(String.valueOf(dataSnapshot1.child("tloc").getValue()));
                }

                SwipeRefreshLayout swipeRefreshLayout2;

                recyclerView = findViewById(R.id.recyclerView2);
                //recyclerAdapter = new RecyclerAdapter(moviesList);
                recyclerAdapter1 = new RecyclerLoadingAdapter(no3,sloc3,tloc3);


                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(recyclerAdapter1);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);

                swipeRefreshLayout2 = findViewById(R.id.swipeRefreshLayout2);
                swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        finish();
                        Intent i = new Intent(context,loading_list.class);
                        swipeRefreshLayout2.setRefreshing(false);
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

    }
}