package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class Material_IN extends AppCompatActivity implements View.OnClickListener {

    Button btn1 , btn2 , btn3;
    public static EditText et1, et2, et3, et4;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public void clearFunction(){
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
    }

    public void createData(String no, String desc, String qty, String sup){
        //write down database code to insert
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("stock").child(no);
        MainModel mm = new MainModel(no,desc,qty,sup);
        reference.setValue(mm);
        clearFunction();
        Toast.makeText(getApplicationContext(),
                "Record Inserted Successfully",
                Toast.LENGTH_LONG)
                .show();
    }
    public boolean isnum(){
        try {
            Integer.parseInt(et3.getText().toString());
            return false;
        }
        catch (Exception e) {
            return true;
        }
    }

    /* Crashed code
    public boolean exist(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("stock");
        String no = et1.getText().toString();

        reference.addValueEventListener(new ValueEventListener() {
            List ls = null;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String value = String.valueOf(snapshot.child("materialNO").getValue());
                    ls.add(value);
                }
                checkIt();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            public boolean checkIt(){
                Integer i = 0;
                while ( i <= ls.size()) {
                    if(no == ls.get(i)){
                        return true;
                    }
                    i++;
                }
                return false;
            }
        });
        return false;
    }
     */


    public boolean validation(String no,String desc,String qty,String sup){
        if (no.length()==0) {
            et1.requestFocus();
            et1.setError("Please Enter Material NO");
            return false;
        }
        else if(desc.length()==0) {
            et2.requestFocus();
            et2.setError("Please Enter Description");
            return false;
        }
        else if(qty.length()==0) {
            et3.requestFocus();
            et3.setError("Please Enter Quantity");
            return false;
        }
        else if(isnum()) {
            et3.requestFocus();
            et3.setError("Enter Number only");
            et3.setText("");
            return false;
        }
        else if(sup.length()==0) {
            et4.requestFocus();
            et4.setError("Please Enter Supplier Details");
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_in);

        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
        et4 = (EditText)findViewById(R.id.et4);

        btn1 = (Button) findViewById(R.id.button5);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.button4);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.QRscan);
        btn3.setOnClickListener(this);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Material IN");

    }

    @Override
    public void onClick(View vi) {
        switch (vi.getId()) {

            case R.id.button5:
                clearFunction();
                break;

            case R.id.button4:
                //get all values from fields
                String no = et1.getText().toString();
                String desc = et2.getText().toString();
                String qty = et3.getText().toString();
                String sup = et4.getText().toString();

                if (validation(no,desc,qty,sup)){
                        createData(no,desc,qty,sup);
                 }
                break;

            case R.id.QRscan:
                Intent i;
                i = new Intent(this,scannerView.class);
                startActivity(i);
        }
    }
}