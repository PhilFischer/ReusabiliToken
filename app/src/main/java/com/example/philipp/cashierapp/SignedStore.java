package com.example.philipp.cashierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SignedStore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_store);
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.STORE_EXTRA_ID);
        TextView textView = (TextView) findViewById(R.id.idTextView);
        textView.setText(id);
    }
}
