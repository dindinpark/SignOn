package com.signononlinesignatureapp.signon;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omaimah on 2/27/2016.
 */
public class main_request__edit extends AppCompatActivity{

    private boolean bool;
    private RequestArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__edit);
        Firebase.setAndroidContext(getApplicationContext());
        Button Update = (Button) findViewById(R.id.add_dialog_ok);
        final EditText etEmail, etOrder;
        etEmail = (EditText) findViewById(R.id.add_dialog_signer_email);
        etOrder = (EditText) findViewById(R.id.add_dialog_signer_order);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, order;
                email = etEmail.getText().toString();
                order = etOrder.getText().toString();
                Firebase mFirebase = new Firebase("https://torrid-heat-4458.firebaseio.com/requests");
                if (EmailExist(email))
                {
                    Bundle extras = getIntent().getExtras();
                    String Key = extras.getString("currentRequestKey");
                    Map<String, String> rq = new HashMap<String, String>();
                    rq.put("signingSeq",order);
                    rq.put("SignerEmail", email);
                    mFirebase.child(Key).child("signingSeq").setValue(order);
                    mFirebase.child(Key).child("SignerEmail").setValue(email);
                    Toast.makeText(main_request__edit.this, "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(main_request__edit.this, Request_Signture.class));
                }
                else
                {
                    Toast.makeText(main_request__edit.this, "email not found", Toast.LENGTH_SHORT).show();
                }
            }
            });
        }

    private boolean EmailExist(String signerEmail) {
        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users");
        final Query queryRef = ref.orderByChild("Email").equalTo(signerEmail);

        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        bool = true;
                        break;
                    }
                } else {
                    bool = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        };
        queryRef.addListenerForSingleValueEvent(listener);
        return bool;

    }

}


/*final EditText etEmail, etOrder;
                etEmail = (EditText) dialog.findViewById(R.id.add_dialog_signer_email);
                etOrder = (EditText) dialog.findViewById(R.id.add_dialog_signer_order);

                Button btnRequest = (Button) dialog.findViewById(R.id.add_dialog_ok);

                btnRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email, order;
                        email = etEmail.getText().toString();
                        order = etOrder.getText().toString();
                        if (EmailExist(email))
                        {
                            mAdapter.updateItem(request,email,order);
                            Toast.makeText(Request_Signture.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Request_Signture.this, "email not found", Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                });
                //mAdapter.updateItem(request, );
            }
            mAdapter.notifyDataSetChanged();
        }*/