package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class manpower extends AppCompatActivity {

    public void getkey(String v){
        String Value = v;
        Log.d("I got it",v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manpower);


        FirebaseDatabase rootNode;
        DatabaseReference reference;


        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("stock");

        Query query = reference.orderByChild("materialNO").equalTo("Q");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    Log.d("test",key);
                    getkey(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}