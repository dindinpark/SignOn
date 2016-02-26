package com.signononlinesignatureapp.signon;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Request_Signture extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        ListView listView = (ListView) findViewById(R.id.listView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_request_signature);
    }




    public void AddSignerButtonOnClick (View view)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Add Signer");
        dialog.setContentView(R.layout.activity_request__signture);
        dialog.show();

        final EditText etEmail, etOrder;
        etEmail = (EditText) dialog.findViewById(R.id.EmailEditText);
        etOrder = (EditText) dialog.findViewById(R.id.OrderEditText);

        Button btnRequest = (Button) dialog.findViewById(R.id.req_btn);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, order;
                email = etEmail.getText().toString();
                order = etOrder.getText().toString();
                Request request = new Request(null, email, "", session.userkey, order, "waiting");
                CheckEmail(request);
                dialog.cancel();
            }
        });

    }


    private void CheckEmail(final Request request) {
        Firebase.setAndroidContext(getApplicationContext());
        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users");
        Query queryRef = ref.orderByChild("Email").equalTo(request.getSignerEmail());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child: dataSnapshot.getChildren()) {

                        AddRequest(request);
                        break;

                    }
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "email not found", Toast.LENGTH_LONG);
                    toast.show();

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        };
        queryRef.addListenerForSingleValueEvent(listener);
    }

    private void AddRequest(Request request) {
        Firebase reqRef = new Firebase("https://torrid-heat-4458.firebaseio.com/requests");
        Map<String, String> newRequest = new HashMap<String, String>();
        newRequest.put("SignerEmail", request.getSignerEmail());
        newRequest.put("rDocumentId", "");
        newRequest.put("requesterID",request.getRequesterID());
        newRequest.put("signingSeq",request.getOrder());
        newRequest.put("status", "waiting");
        reqRef.push().setValue(newRequest);
        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

        
    }
}