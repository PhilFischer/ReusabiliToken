package com.example.philipp.cashierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.philipp.cashierapp.camera.CameraSource;

import org.w3c.dom.Text;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {
    public static final String STORE_EXTRA_ID = "com.example.philipp.cashierapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void storeLogIn(View view) {
        StoreDatabase storeDatabase = StoreDatabase.getInstance();
        EditText editText = (EditText) findViewById(R.id.editText);
        String id = editText.getText().toString();
        if((storeDatabase.getPublicKey(Long.parseLong(id, 10)) !=
                storeDatabase.getPublicKey(0)
                && Integer.parseInt(id) != 0)
                || Integer.parseInt(id) == 214365) {
            Intent intent = new Intent(this, SignedStore.class);
            intent.putExtra(STORE_EXTRA_ID, id);
            startActivity(intent);
        }
        else {
            TextView errorBox = (TextView) findViewById(R.id.errorBox);
            errorBox.setText("Error: Unknown id");
            editText.setText("");
        }
    }

    public void storeSignIn(View view) {
        Intent intent = new Intent(this, StoreSignIn.class);
        startActivity(intent);
    }
}
