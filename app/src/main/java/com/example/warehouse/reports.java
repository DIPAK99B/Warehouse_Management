package com.example.warehouse;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class reports extends AppCompatActivity {
    Button stocks,loaded;


    public void stock() {
        Intent i;
        i = new Intent(this,stock_list.class);
        startActivity(i);
    }

    public void load(){
        Intent i;
        i = new Intent(this,loading_list.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reports Section");


        stocks = (Button) findViewById(R.id.stocks);
        stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stock();
            }
        });


        loaded = (Button) findViewById(R.id.loaded);
        loaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
    }
}