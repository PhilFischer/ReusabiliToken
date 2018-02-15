package com.example.philipp.cashierapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.philipp.cashierapp.Tests.DummyReputationToken;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


public class StoreSignIn extends AppCompatActivity {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Boolean stored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_sign_in);
        SecureRandom random;
        KeyPairGenerator keyGen;
        try  {
            keyGen = KeyPairGenerator.getInstance("DSA");
            random = SecureRandom.getInstance("sharp1ing");
            keyGen.initialize(256, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
            stored = true;
        }
        catch(NoSuchAlgorithmException noSuchAlgorithmException){}

    }

    public void signIn(View view) {
        EditText editName = (EditText) findViewById(R.id.nameView);
        EditText editId = (EditText) findViewById(R.id.idView);
        String name = editName.getText().toString();
        long id = Long.parseLong(editId.getText().toString(), 10);

        StoreDatabase storeDatabase = StoreDatabase.getInstance();
        DummyReputationToken dt = new DummyReputationToken();
        storeDatabase.addStore(id, publicKey, dt);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
