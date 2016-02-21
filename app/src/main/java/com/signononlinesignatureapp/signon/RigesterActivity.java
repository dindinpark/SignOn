package com.signononlinesignatureapp.signon;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class RigesterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);
        Firebase.setAndroidContext(this);

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
        final String email = etEmail.getText().toString().trim().toLowerCase();
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
            Firebase.setAndroidContext(this);
            final Firebase mFirebase = new Firebase ("https://torrid-heat-4458.firebaseio.com/users");
            final UserAdapter mAdapter = new UserAdapter(this);
            final User CurrentUser = new User(null, email, birthdate, password, name);
            EmailExist(email, CurrentUser, mAdapter);


        }
    }

    private void EmailExist(String email, final User currentUser, final UserAdapter mAdapter) {
        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users");
        Query queryRef = ref.orderByChild("Email").equalTo(email);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        Toast toast = Toast.makeText(RigesterActivity.this, "email is already registered", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                }
                else {
                    createAccount(currentUser, mAdapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        };
        queryRef.addListenerForSingleValueEvent(listener);

    }

    public void createAccount (User CurrentUser, UserAdapter mAdapter)
    {
        //long PrK = CurrentUser.GeneratePK();
        long PrK = 1245;
        CurrentUser.CreateECDSAobject(PrK);
        mAdapter.addItem(CurrentUser);
        Toast MSG = Toast.makeText(RigesterActivity.this, "register is successful", Toast.LENGTH_SHORT);
        MSG.show();

    }


}
