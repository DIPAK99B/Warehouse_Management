package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class store_material extends AppCompatActivity implements View.OnClickListener {

    Button btn1 , btn2 , btnclear , btnok;
    public  static EditText et1 , et2;
    public static Integer z = 0;

    FirebaseDatabase rootNode;
    DatabaseReference reference , reference1;

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

    public void getkey(String key){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("stock").child(key);

        HashMap updatedata = new HashMap();
        updatedata.put("sloc",et2.getText().toString());

        reference.updateChildren(updatedata).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                clearFunction();
                Toast.makeText(store_material.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getno(String no1){

        FirebaseDatabase rootNode;
        DatabaseReference reference;
        String value = "Notworking";


        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("stock");

        Query query = reference.orderByChild("materialNO").equalTo(no1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    getkey(key);
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
        setContentView(R.layout.activity_store_material);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Store Material");

        btn1 = (Button) findViewById(R.id.QRscan);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.QRscan2);
        btn2.setOnClickListener(this);
        btnclear = (Button) findViewById(R.id.btnclear);
        btnclear.setOnClickListener(this);
        btnok = (Button) findViewById(R.id.btnok);
        btnok.setOnClickListener(this);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);


    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case (R.id.btnclear) :
                clearFunction();
                break;

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

            case (R.id.btnok) :
                String no1 = et1.getText().toString();
                String no2 = et2.getText().toString();
                if(validation(no1,no2)){
                    getno(no1);
                }
                break;
        }

    }
}