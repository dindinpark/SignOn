package com.signononlinesignatureapp.signon;

import android.net.UrlQuerySanitizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.lang.String;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignAndVerifyActivity extends AppCompatActivity {

 public TextView ECDSATextview ;
    public Button signbutton;
    Point userpubkey ;

    String thedigest;
    String signature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_and_verify);
        Firebase.setAndroidContext(this);
        Bundle extras = getIntent().getExtras();
        thedigest = extras.getString("SHAhash");
        signbutton= (Button)findViewById(R.id.ECDSAbutton);
        ECDSATextview= (TextView)findViewById(R.id.signatureText);




    }

    public void signclick(View V){

        try {
            // sign ducoment


            ECDSA app = new ECDSA();  // eliptic curve opject
             // public key will be stored in firebase

            BigInteger password = new BigInteger("43453");

            app.setdA(password);
            signature = app.signingMessage(thedigest);

            ECDSATextview.setText(signature);
            userpubkey=app.getQA();
            String userID = "-KB3Hr-hppGC_lVeqjjE"; /// here connection

            storePubkeyAndSignature(userID,userpubkey,signature,thedigest);



        } catch (java.lang.Exception e1) {  //
            ECDSATextview.setText("in java.lang exciption");
            Toast toast = Toast.makeText(getApplicationContext(), "Error please try again later", Toast.LENGTH_LONG);
        }



    }



    public void verfiySignature(Point pubkey,String sign,String msg){



        try {
            ECDSA app = new ECDSA();
             app.setQA(pubkey);
            boolean check = app.checkSignature(msg, sign);
            if (check == true) {
                ECDSATextview.setText(" varifcation is true");

            }
            else
                ECDSATextview.setText("varifcation is false"); /// to change
        }
        catch (java.lang.Exception e1){

            ECDSATextview.setText("in java.lang.exception");

        }


    }


    public void storePubkeyAndSignature(String userID,Point pubkey,String signature,String msg){


    Firebase mFirebase = new Firebase ("https://torrid-heat-4458.firebaseio.com/users");
    /*    Map<String, Object> users = new HashMap<String, Object>();
        users.put("x", pubkey.getX().toString());
        users.put("y", pubkey.getY().toString());
        users.put("a", pubkey.getA().toString());
        users.put("p", pubkey.getP().toString());
        if(pubkey.isInfinity())
        users.put("infinity","true");
        else
        users.put("infinity","false"); */
        mFirebase.child(userID).child("x").setValue(pubkey.getX().toString());
        mFirebase.child(userID).child("y").setValue(pubkey.getY().toString());
        mFirebase.child(userID).child("a").setValue(pubkey.getA().toString());
        mFirebase.child(userID).child("p").setValue(pubkey.getP().toString());
        if(pubkey.isInfinity())
        mFirebase.child(userID).child("infinity").setValue("TRUE");
        else
            mFirebase.child(userID).child("infinity").setValue("FALSE");

         mFirebase = new Firebase ("https://torrid-heat-4458.firebaseio.com/documents");
        mFirebase.child("0").child("messagedigest").setValue(msg);
        mFirebase.child("0").child("signature").setValue(signature);


    }


    public void retrievepubKey(final String userID, final String docuID){

Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users/"+userID+"/");

       Query queryref = ref.orderByValue();
 // 0 key of person who signed the document
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BigInteger x,y,a,p;
                String inf;
                boolean infinity;

                x=new BigInteger(dataSnapshot.child("x").getValue(String.class));
                y=new BigInteger(dataSnapshot.child("y").getValue(String.class));
                a=new BigInteger(dataSnapshot.child("a").getValue(String.class));
                p=new BigInteger(dataSnapshot.child("p").getValue(String.class));
                inf=dataSnapshot.child("infinity").getValue(String.class);
                if(inf=="TRUE")
                    infinity=true;
                else
                    infinity=false;

                Point pubkey = new Point(x,y,a,p);
                pubkey.setInfinity(infinity);

                  retrieveMSG(pubkey,docuID);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        queryref.addValueEventListener(listener);




    }

    public void retrieveMSG(final Point pubkey,final String docuID){

        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/documents/"+docuID+"/");
     Query queryref = ref.orderByValue();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String MSG=  dataSnapshot.child("messagedigest").getValue().toString();
                ECDSATextview.setText(MSG);
                  retrieveSign(pubkey, MSG,docuID);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        queryref.addValueEventListener(listener);


    }


    public void retrieveSign(final Point pubkey , final String msg, String docuID){

        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/documents/"+docuID+"");
        Query queryref = ref.orderByValue();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String signature=  dataSnapshot.child("signature").getValue(String.class);
                ECDSATextview.setText(signature);

                verfiySignature(pubkey, signature, msg);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        queryref.addValueEventListener(listener);





    }


    public void verfiyclick(View view){

retrievepubKey("","");


    }
}
