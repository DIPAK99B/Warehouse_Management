package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class search_material extends AppCompatActivity implements View.OnClickListener {

    public static EditText et1;
    public static Integer d =0;
    Button btn1, search;
    TextView tvno, tvdesc, tvqty, tvsupplier, tvsloc;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public void clear() {
        et1.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_material);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Search Material");


        et1 = (EditText) findViewById(R.id.searchbox);
        tvno = (TextView) findViewById(R.id.tvno);
        tvdesc = (TextView) findViewById(R.id.tvdesc);
        tvqty = (TextView) findViewById(R.id.tvqty);
        tvsupplier = (TextView) findViewById(R.id.tvsupplier);
        tvsloc = (TextView) findViewById(R.id.tvsloc);

        btn1 = (Button) findViewById(R.id.btnsearch);
        btn1.setOnClickListener(this);


        search = (Button) findViewById(R.id.QRscan);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnsearch:
                if (et1.length() == 0) {
                    et1.requestFocus();
                    et1.setError("Enter Material No");
                } else {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference().child("stock");

                    Query query = reference.orderByChild("materialNO").equalTo(et1.getText().toString());

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                String material = String.valueOf(dataSnapshot1.child("materialNO").getValue());
                                String des = String.valueOf(dataSnapshot1.child("description").getValue());
                                String qty = String.valueOf(dataSnapshot1.child("qty").getValue());
                                String supplier = String.valueOf(dataSnapshot1.child("supplier").getValue());
                                String sloc = String.valueOf(dataSnapshot1.child("sloc").getValue());

                            /* This is for debugging output
                            Log.i("onDataChange:", material);
                            Log.i("onDataChange:", des);
                            Log.i("onDataChange:", qty);
                            Log.i("onDataChange:", supplier);
                            Log.i("onDataChange:", sloc);
                             */

                                tvno.setText(material);
                                tvdesc.setText(des);
                                tvqty.setText(qty);
                                tvsupplier.setText(supplier);
                                tvsloc.setText(sloc);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    clear();
                }
                break;

            case R.id.QRscan: {
                Intent i;
                d = 1;
                i = new Intent(this,ScannerStore1.class);
                startActivity(i);
            }

        }

    }
}
