package com.example.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerStore1 extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        //setContentView(R.layout.activity_scanner_view);


        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        // slicing logic here
        data = rawResult.getText();
        String[] res = data.split("[,]", 0);
        if(res.length >=1) {
            if (search_material.d == 1){
                search_material.et1.setText(res[0]);
                search_material.d = 0;
            }
            else if (loading.z == 1){
                loading.et1.setText(res[0]);
                loading.z = 0;
            }
            else if (loading.z == 2){
                loading.et2.setText(res[0]);
                loading.z =0;
            }
            else if ( store_material.z == 1) {
                store_material.et1.setText(res[0]);
                store_material.z = 0;
            }
            else {
                store_material.et2.setText(res[0]);
                store_material.z =0;
            }
        }
        else {
            onBackPressed();
            Toast.makeText(getApplicationContext(),"Not a valid barcode format",Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

}