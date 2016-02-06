package com.signononlinesignatureapp.signon;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigInteger;
import android.widget.Toast;
import java.io.File;

public class hash extends AppCompatActivity {
    public TextView checksum;
    public TextView equalhash;
    public Button checkHashButton;
    public Button hashButton;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon";
    Point pubkey;
    String digest;
    String signature;
String hashValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash);

        checksum= (TextView) findViewById(R.id.checksum);
        equalhash=(TextView) findViewById(R.id.equalhash);
        hashButton=(Button) findViewById(R.id.hashButton);
        checkHashButton=(Button) findViewById(R.id.checkHashButton);
        File dir = new File(path);
        dir.mkdirs();
        File file = new File (path + "/word.pdf");
        hashValue = SHA512.calculateSHA512(file);
        checksum.setText(hashValue);
        /*


        ///
        try {
            // sign ducoment

             digest = SHA512.calculateSHA512(dir);
            ECDSA app = new ECDSA();  // eliptic curve opject

            BigInteger password = new BigInteger("764");

            app.setdA(password);
            signature = app.signingMessage(digest);

            checksum.setText(signature);
            pubkey = app.getQA();




        } catch (java.lang.Exception e1) {  //
            checksum.setText("in java.lang exciption");
            Toast toast = Toast.makeText(getApplicationContext(), "Error please try again later", Toast.LENGTH_LONG);
        }*/
    }
    public void hashButtonClick(View v){
        File file = new File (path + "/word.pdf");
        hashValue=SHA512.calculateSHA512(file);
        checksum.setText(hashValue);
        /*
        // varify document

        try {
            ECDSA app = new ECDSA();
            app.setQA(pubkey);
            boolean check = app.checkSignature(digest, signature);
            if (check == true) {
                checksum.setText(" varifcation is true");

            }
            else
            checksum.setText("varifcation is false");
        }
        catch (java.lang.Exception e1){

            checksum.setText("in java.lang.exception");

        } */
    }

    public void gotoSign(View v){

        Intent intent = new Intent(this,SignAndVerifyActivity.class);
        intent.putExtra("SHAhash", hashValue);
        startActivity(intent);


        }


    public void performButtonClick(View v){
        File file = new File (path + "/word2.pdf");
        if(SHA512.checkSHA512(hashValue,file))
        equalhash.setText("Same File");
        else
            equalhash.setText("Different File");


    }
}
