package com.signononlinesignatureapp.signon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.client.Firebase;

public class RigesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);
       // Firebase.setAndroidContext(this);
       // Firebase mydatabase = new Firebase("https://torrid-heat-4458.firebaseio.com/");


    }

    public void registerRegisterButtonClick (View v){

        EditText etName, etEmail, etPassword, etRePassword, etBirthdate;
        Button register;

        etName = (EditText) findViewById(R.id.registerNameEditText);
        etEmail = (EditText) findViewById(R.id.registerEmailEditText);
        etPassword = (EditText) findViewById(R.id.registerPasswordEditText);
        etRePassword = (EditText) findViewById(R.id.registerRePasswordEditText);
        etBirthdate = (EditText) findViewById(R.id.registerBirthdateEditText);
        register = (Button) findViewById(R.id.registerRegisterButton);

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String repassword = etRePassword.getText().toString();
        String birthdate = etBirthdate.getText().toString();

        if (!password.equals(repassword)) {
            Toast passwordMSG = Toast.makeText(RigesterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT);
            passwordMSG.show();
        }

    }



}
