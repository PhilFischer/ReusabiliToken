package com.example.philipp.cashierapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.example.philipp.cashierapp.Features.operations.actions.EActionType;
import com.example.philipp.cashierapp.barcode.BarcodeCaptureActivity;
import com.example.philipp.cashierapp.Features.operations.actions.AHumanConfirmableAction;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class SignedStore extends AppCompatActivity {
    private static final int BARCODE_READER_REQUEST_CODE =1;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String ID_FILENAME = "ReusabiliStore";
    public final static int QRcodeWidth = 500;
    private static long id;
    private static String privateKey_string;
    private static String publicKey_string;
    private static String action_string;
    private static String address_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_store);
        Intent intent = getIntent();

        //load keys and id from intent
        id = intent.getLongExtra("id", 0);
        String id_string = Long.toString(id);
        privateKey_string = intent.getStringExtra("privateKey");
        publicKey_string = intent.getStringExtra("publicKey");
        TextView textView = (TextView) findViewById(R.id.idTextView);
        textView.setText(id_string);

        //load private key from shared preferences
        //skipped in demo version
        /*
        final SharedPreferences sharedPref = getSharedPreferences(ID_FILENAME, 0);
        String privateKey_string = sharedPref.getString("KEY1", null);
        long id = sharedPref.getLong("KEY2", 0);
        */
    }

    public void scanClaim(View view) {
            Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setVisibility(View.INVISIBLE);
            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
        String barcode_return = "0";
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    barcode_return = barcode.displayValue;
                }
            } else
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format), CommonStatusCodes.getStatusCodeString(resultCode)));
        } else
            super.onActivityResult(requestCode, resultCode, data);

        recieveClaim(barcode_return);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Something went wrong or false QR-Code scanned!", Toast.LENGTH_LONG).show();
        }
    }

    protected void recieveClaim(String barcode_return) {
        action_string = barcode_return.split(" ")[0];
        address_string = barcode_return.split(" ")[1];

        Button approveButton = (Button) findViewById(R.id.approveButton);
        TextView claimView = (TextView) findViewById(R.id.claimName);
        approveButton.setVisibility(View.VISIBLE);
        claimView.setText(action_string);
    }

    public void approveClaim(View view) {
        Button approveClaim = (Button) findViewById(R.id.approveButton);
        TextView claimView = (TextView) findViewById(R.id.claimName);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        approveClaim.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        claimView.setText("");


        AHumanConfirmableAction action = (AHumanConfirmableAction) EActionType.actionFromString(action_string);
        Address address = new Address(address_string);
        action.setCustomerAddress(address);
        action.setStoreID(id);
        int hashcode = action.hashCode();

        byte[] qrSign = {0};
        try {
            KeyFactory kFactory = KeyFactory.getInstance("DSA");
            byte yourKey[] = Base64.decode(privateKey_string, 0);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(yourKey);
            PrivateKey privateKey = (PrivateKey) kFactory.generatePrivate(spec);

            Signature dsa = Signature.getInstance("DSA");

            dsa.initSign(privateKey);
            for (int i = 0; i < 4; ++i) {
                dsa.update((byte)(hashcode >> 8*i));
            }
            qrSign = dsa.sign();
        }
        catch (Exception e){}

        String qrString = Long.toString(id) + " " + qrSign.toString() + " " + publicKey_string;
        Bitmap bitmap;
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        try {
            bitmap = TextToImageEncode(qrString);
            iv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }





    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorBlack) : getResources().getColor(R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
