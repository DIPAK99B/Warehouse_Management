package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class loading extends AppCompatActivity implements View.OnClickListener {


    public static Integer z = 0;
    public  static EditText et1 , et2;

    FirebaseDatabase rootNode ,rootNode1;
    DatabaseReference reference ,reference1 ;

    public void clearFunction(){
        et1.setText("");
        et2.setText("");
    }

    public boolean validation(String no1,String no2){

        if(no1.length()==0) {
            et1.requestFocus();
            et1.setError("Please Enter Material No");
            return false;
        }
        else if(no2.length()==0) {
            et2.requestFocus();
            et2.setError("Please Enter Store Location");
            return false;
        }
        else {
            return true;
        }
    }

    public void getkey(String key , String tloc){

        //Need to rewrite firebase query to moving stock node into loading with tloc
        rootNode1 = FirebaseDatabase.getInstance();
        reference1 = rootNode.getReference().child("stock");

        Query query = reference1.orderByChild("materialNO").equalTo(et1.getText().toString());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String material = String.valueOf(dataSnapshot1.child("materialNO").getValue());
                    String des = String.valueOf(dataSnapshot1.child("description").getValue());
                    String qty = String.valueOf(dataSnapshot1.child("qty").getValue());
                    String supplier = String.valueOf(dataSnapshot1.child("supplier").getValue());
                    String sloc = String.valueOf(dataSnapshot1.child("sloc").getValue());

                    //to store
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("loading").child(key);
                    MainModel mm = new MainModel(material,des,qty,supplier,sloc,tloc);
                    try {
                        reference.setValue(mm);
                        reference1.child(key).removeValue();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Error Updating Data",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                    clearFunction();
                    Toast.makeText(getApplicationContext(),
                            "Record Inserted Successfully",
                            Toast.LENGTH_LONG)
                            .show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        clearFunction();
    }


    public void getno(String no1 , String tloc){

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("stock");

        Query query = reference.orderByChild("materialNO").equalTo(no1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    getkey(key,tloc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Button btn1 , btn2 , btnc, btno;

        btn1 = (Button) findViewById(R.id.QRscan);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.QRscan2);
        btn2.setOnClickListener(this);
        btnc = (Button) findViewById(R.id.btnclear);
        btnc.setOnClickListener(this);
        btno = (Button) findViewById(R.id.btnok);
        btno.setOnClickListener(this);

        et1 = (EditText) findViewById(R.id.et11);
        et2 = (EditText) findViewById(R.id.et22);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case (R.id.QRscan) :
                z = 1;
                i = new Intent(this, ScannerStore1.class);
                startActivity(i);
                break;

            case (R.id.QRscan2) :
                z = 2;
                i = new Intent(this, ScannerStore1.class);
                startActivity(i);
                break;

            case (R.id.btnclear) :
                clearFunction();
                break;

            case (R.id.btnok) :
                String no1 = et1.getText().toString();
                String no2 = et2.getText().toString();
                if(validation(no1,no2)){
                    getno(no1,no2);
                }
                break;
        }
    }
}