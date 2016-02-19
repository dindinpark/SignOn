package com.signononlinesignatureapp.signon;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RigesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);


    }

    boolean isEmailValid (CharSequence email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void registerRegisterButtonClick (View v)  {

        EditText etName, etEmail, etPassword, etRePassword, etBirthdate;
        Button register;

        etName = (EditText) findViewById(R.id.registerNameEditText);
        etEmail = (EditText) findViewById(R.id.registerEmailEditText);
        etPassword = (EditText) findViewById(R.id.registerPasswordEditText);
        etRePassword = (EditText) findViewById(R.id.registerRePasswordEditText);
        etBirthdate = (EditText) findViewById(R.id.registerBirthdateEditText);
        register = (Button) findViewById(R.id.registerRegisterButton);

        String name = etName.getText().toString();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String password = etPassword.getText().toString().trim();
        String repassword = etRePassword.getText().toString().trim();
        String birthdate = etBirthdate.getText().toString();

        String msg;
        Toast MSG;
        if (name.isEmpty()) {
            msg = "username field cannot be empty";
            MSG = Toast.makeText(RigesterActivity.this, msg, Toast.LENGTH_SHORT);
            MSG.show();
        } else if (email.isEmpty()) {
            msg = "email field cannot be empty";
            MSG = Toast.makeText(RigesterActivity.this, msg, Toast.LENGTH_SHORT);
            MSG.show();
        } else if (password.isEmpty()) {
            msg = "password field cannot be empty";
            MSG = Toast.makeText(RigesterActivity.this, msg, Toast.LENGTH_SHORT);
            MSG.show();
        } else if (!password.equals(repassword)) {
            msg = "passwords do not match";
            MSG = Toast.makeText(RigesterActivity.this, msg, Toast.LENGTH_SHORT);
            MSG.show();
        } else if (!isEmailValid(email))
        {
            msg = "Invalid Email";
            MSG = Toast.makeText(RigesterActivity.this, msg, Toast.LENGTH_SHORT);
            MSG.show();
        }
        else
        {
            UserAdapter mAdapter = new UserAdapter(this);
            User CurrentUser = new User (null, email, birthdate, password, name);
            mAdapter.addItem(CurrentUser);

        }
    }



}
