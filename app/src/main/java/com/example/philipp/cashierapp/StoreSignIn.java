package com.example.philipp.cashierapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        }
        catch(NoSuchAlgorithmException noSuchAlgorithmException){}


    }

    public void signIn(View view) {
        EditText editName = (EditText) findViewById(R.id.nameView);
        EditText editId = (EditText) findViewById(R.id.idView);
        String name = editName.getText().toString();
        long id = Long.parseLong(editId.getText().toString(), 10);

        TextView privateView = (TextView) findViewById(R.id.privateKeyView);
        TextView publicView = (TextView) findViewById(R.id.publicKeyView);
        String privateString = new String(privateKey.getEncoded());
        String publicString = new String(publicKey.getEncoded());
        privateView.setText(privateString);
        publicView.setText(publicString);

        StoreDatabase storeDatabase = StoreDatabase.getInstance();
        storeDatabase.addStore(id, publicKey);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
