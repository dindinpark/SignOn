package com.signononlinesignatureapp.signon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.lang.String;
import java.math.BigInteger;

public class SignAndVerifyActivity extends AppCompatActivity {

 public TextView ECDSATextview ;
    public Button signbutton;
    Point pubkey;

    String thedigest;
    String signature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_and_verify);
        Bundle extras = getIntent().getExtras();
        thedigest = extras.getString("SHAhash");
        signbutton= (Button)findViewById(R.id.ECDSAbutton);
        ECDSATextview= (TextView)findViewById(R.id.signatureText);




    }

    public void signclick(View V){

        try {
            // sign ducoment


            ECDSA app = new ECDSA();  // eliptic curve opject

            BigInteger password = new BigInteger("764");

            app.setdA(password);
            signature = app.signingMessage(thedigest);

            ECDSATextview.setText(signature);
            pubkey = app.getQA();




        } catch (java.lang.Exception e1) {  //
            ECDSATextview.setText("in java.lang exciption");
            Toast toast = Toast.makeText(getApplicationContext(), "Error please try again later", Toast.LENGTH_LONG);
        }


    }

    public void verfiySignature(View v){
        // lucifer
        BigInteger myint= new BigInteger("45345");
            ECDSA ecdsa = new ECDSA();
        ecdsa.setdA(myint);
        Point fakepubkey = ecdsa.getQA();

        try {
            ECDSA app = new ECDSA();
            app.setQA(pubkey);
            boolean check = app.checkSignature(thedigest, signature);
            if (check == true) {
                ECDSATextview.setText(" varifcation is true");

            }
            else
                ECDSATextview.setText("varifcation is false");
        }
        catch (java.lang.Exception e1){

            ECDSATextview.setText("in java.lang.exception");

        }


    }
}
