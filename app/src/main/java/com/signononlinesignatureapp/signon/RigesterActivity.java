package com.signononlinesignatureapp.signon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.firebase.client.Firebase;

public class RigesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);
        Firebase.setAndroidContext(this);
        Firebase mydatabase = new Firebase("https://torrid-heat-4458.firebaseio.com/");
        
    }

    public void registerbuttonClick (View v){




    }



}
