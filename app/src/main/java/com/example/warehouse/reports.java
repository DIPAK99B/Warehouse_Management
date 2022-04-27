package com.example.warehouse;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class reports extends AppCompatActivity {
    Button stocks,loaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reports Section");

        Context context = getApplicationContext();


        stocks = (Button) findViewById(R.id.stocks);
        stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(context,stock_list.class);
                startActivity(i);
            }
        });


        loaded = (Button) findViewById(R.id.loaded);
        loaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(context,loading_list.class);
                startActivity(i);
            }
        });
    }
}