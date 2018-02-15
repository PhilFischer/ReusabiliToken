package com.example.philipp.cashierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.philipp.cashierapp.camera.CameraSource;

public class MainActivity extends AppCompatActivity {
    public static final String STORE_EXTRA_ID = "com.example.philipp.cashierapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void storeLogIn(View view) {
        Intent intent = new Intent(this, StoreLogIn.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String id = editText.getText().toString();
        intent.putExtra(STORE_EXTRA_ID, id);
        startActivity(intent);
    }

    public void storeSignIn(View view) {
        Intent intent = new Intent(this, StoreSignIn.class);
        startActivity(intent);
    }
}
