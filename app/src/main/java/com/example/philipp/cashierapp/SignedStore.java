package com.example.philipp.cashierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.philipp.cashierapp.barcode.BarcodeCaptureActivity;

public class SignedStore extends AppCompatActivity {
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_store);
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.STORE_EXTRA_ID);
        TextView textView = (TextView) findViewById(R.id.idTextView);
        textView.setText(id);
    }

    public void scanClaim(View view) {
        Intent intent = new Intent(this, BarcodeCaputureActivity.class);
        startActivity(intent);
    }

    public void approveClaim(View view) {

    }
}
