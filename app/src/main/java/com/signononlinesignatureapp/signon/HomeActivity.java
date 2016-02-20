package com.signononlinesignatureapp.signon;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

//push 2
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toolbar toolbar;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
       session.userkey = extras.getString("key");
    }
    public void testOn(View v){
        startActivity(new Intent(HomeActivity.this, SignatureSelectActivity.class));


    }
}
