package com.signononlinesignatureapp.signon;


import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class SignatureSelectActivity extends ListActivity {

    private static final String FIREBASE_URL = "https://torrid-heat-4458.firebaseio.com/";
    private String mSignature;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private SignatureListArrayAdapter mSignatureListAdapter;
private Query queryRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_select);
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/signature");
        mFirebaseRef = new Firebase(FIREBASE_URL).child("signature");
        //queryRef = ref.orderByChild("signerID").equalTo(session.userkey);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mSignatureListAdapter = new SignatureListArrayAdapter(mFirebaseRef, this, R.layout.signature);
        listView.setAdapter(mSignatureListAdapter);
        mSignatureListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mSignatureListAdapter.getCount() - 1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) { String selected = ((TextView) view.findViewById(R.id.selectSignatureName)).getText().toString();

                Toast toast=Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(SignatureSelectActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignatureSelectActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mSignatureListAdapter.cleanup();
    }



    /*private void populateSignatureList() {
        signatureList = new ArrayList<signature>();
        signaturenames = getResources().getStringArray(R.array.country_names);
        imgs = getResources().obtainTypedArray(R.array.country_flags);
        for(int i = 0; i < countrycodes.length; i++){
            signatureList.add(new signature(signaturenames[i], imgs.getDrawable(i)));
        }
    }*/


}

