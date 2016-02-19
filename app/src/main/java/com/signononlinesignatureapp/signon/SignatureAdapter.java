package com.signononlinesignatureapp.signon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by daniah on 2/19/2016.
 */

public class SignatureAdapter extends BaseAdapter implements ChildEventListener {


    private List<Signature> mSignatures;
    private Firebase mFirebase;

    public SignatureAdapter(Context context) {
        mSignatures = new ArrayList<Signature>();
        Firebase.setAndroidContext(context);
        mFirebase = new Firebase ("https://torrid-heat-4458.firebaseio.com/signature");
        mFirebase.addChildEventListener(this);
    }

    @Override
    public int getCount() {
        return mSignatures.size();
    }

    public void removeItem(Signature signature) {
        //TODO: Remove data from Firebase
        mFirebase.child(signature.getKey()).removeValue();
    }

    public void addItem(Signature signature) {
        //TODO: Push new data to Firebase
        Map<String, String> newSignature = new HashMap<String, String>();
        newSignature.put("signatureName",signature.getSignatureName());
        newSignature.put("signatureBase64", signature.getSignatureBase64());
        newSignature.put("signerID", signature.getSignerID());
        mFirebase.push().setValue(newSignature);
    }

    public void updateItem(Signature signature) {
        //TODO: Push changes to Firebase
        Map<String, String> newSignature = new HashMap<String, String>();
        newSignature.put("signatureName",signature.getSignatureName());
        newSignature.put("signatureBase64", signature.getSignatureBase64());
        newSignature.put("signerID", signature.getSignerID());
        mFirebase.child(signature.getKey()).setValue(newSignature);
    }

    @Override
    public Signature getItem(int position) {
        return mSignatures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previeousChildName) {
        String key = dataSnapshot.getKey();
        String SignatureName = dataSnapshot.child("signatureName").getValue(String.class);
        String SignatureBase64 = dataSnapshot.child("SignatureBase64").getValue(String.class);
        String SignerID = dataSnapshot.child("SignerID").getValue(String.class);
        mSignatures.add(0, new Signature(key,SignatureName,SignatureBase64,SignerID));// add to the top
        notifyDataSetChanged();// update adapter
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        String SignatureName = dataSnapshot.child("signatureName").getValue(String.class);
        String SignatureBase64 = dataSnapshot.child("SignatureBase64").getValue(String.class);
        String SignerID = dataSnapshot.child("SignerID").getValue(String.class);
        for ( Signature newSignature : mSignatures)
        {
            if (key.equals(newSignature.getKey()))
            {
                newSignature.setSignatureBase64(SignatureBase64);
                newSignature.setSignatureName(SignatureName);
                newSignature.setSignerID(SignerID);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        for ( Signature newSignature : mSignatures)
        {
            if (key.equals(newSignature.getKey()))
            {
                mSignatures.remove(newSignature);
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
