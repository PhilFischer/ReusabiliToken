package com.example.philipp.cashierapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.cashierapp.Tests.DummyReputationToken;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


public class StoreSignIn extends AppCompatActivity {
    private static final String ID_FILENAME = "ReusabiliStore";
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
            keyGen.initialize(512);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = (PrivateKey) keyPair.getPrivate();
            TextView tv = (TextView) findViewById(R.id.privateKeyView);
            tv.setText(privateKey.getEncoded().toString());

            publicKey = (PublicKey) keyPair.getPublic();
            TextView tv2 = (TextView) findViewById(R.id.publicKeyView);
            tv2.setText(publicKey.getEncoded().toString());
        }
        catch(NoSuchAlgorithmException noSuchAlgorithmException){        }

    }

    public void signIn(View view) {
        try {
            EditText editName = (EditText) findViewById(R.id.nameView);
            EditText editId = (EditText) findViewById(R.id.idView);
            String name = editName.getText().toString();
            long id = Long.parseLong(editId.getText().toString(), 10);

            StoreDatabase storeDatabase = StoreDatabase.getInstance();
            storeDatabase.addStore(id, publicKey, new DummyReputationToken());


            //save private key and id as shared preference
            //skipped in demo version
            /*
            final SharedPreferences sharedPref = getSharedPreferences(ID_FILENAME, 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("KEY1", id);
            editor.putString("KEY2", privateKey.getEncoded().toString());
            editor.commit();
            */


            Intent intent = new Intent(this, SignedStore.class);

            //This intent extra is just for demo version
            //Public key should be sent via decentralized data base
            intent.putExtra("publicKey", publicKey.getEncoded().toString());
            intent.putExtra("privateKey", privateKey.getEncoded().toString());
            intent.putExtra("id", id);
            startActivity(intent);

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"No or false Input made, retry", Toast.LENGTH_LONG).show();
        }
    }
}
