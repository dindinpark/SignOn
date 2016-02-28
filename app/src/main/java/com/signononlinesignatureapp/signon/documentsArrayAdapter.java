package com.signononlinesignatureapp.signon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Naseebah on 26/02/16.
 */
public class documentsArrayAdapter extends BaseAdapter implements ChildEventListener {


    private final LayoutInflater mInflater;
    private List<documents> mdocuments;
    private List<documents> waitdocuments;
    private Firebase mFireBase;

    public documentsArrayAdapter(Context context) {
        Firebase.setAndroidContext(context);
        mInflater = LayoutInflater.from(context);
        mdocuments = new ArrayList<documents>();
        waitdocuments = new ArrayList<documents>();
        mFireBase=new Firebase("https://torrid-heat-4458.firebaseio.com/documents");
        mFireBase.addChildEventListener(this);

    }

    @Override
    public int getCount() {
        return mdocuments.size();
    }

    public void removeItem(documents doc) {
        //TODO: Remove data from Firebase
        mFireBase.child(doc.getKey()).removeValue();
    }

    public void addItem(documents doc) {
        //TODO: Push new data to Firebase
        Map<String, String> newDocument = new HashMap<String, String>();
        newDocument.put("documentName", doc.getDocumentName());
        newDocument.put("documentOwnerID", doc.getDocumentOwnerID());
        newDocument.put("documentURL", doc.getDocumentURL());
        newDocument.put("ekey", doc.getEkey());
        newDocument.put("messagedigest", doc.getMessagedigest());
        mFireBase.push().setValue(newDocument);

    }

    public void updateItem(documents doc) {
        //TODO: Push changes to Firebase
        Map<String, String> newDocument = new HashMap<String, String>();
        newDocument.put("documentName", doc.getDocumentName());
        newDocument.put("documentOwnerID", doc.getDocumentOwnerID());
        newDocument.put("documentURL", doc.getDocumentURL());
        newDocument.put("ekey", doc.getEkey());
        newDocument.put("messagedigest", doc.getMessagedigest());
        mFireBase.child(doc.getKey()).child("documentName").setValue(doc.getDocumentName());
        mFireBase.child(doc.getKey()).child("documentOwnerID").setValue(doc.getDocumentOwnerID());
        mFireBase.child(doc.getKey()).child("documentURL").setValue(doc.getDocumentURL());
        mFireBase.child(doc.getKey()).child("ekey").setValue(doc.getEkey());
        mFireBase.child(doc.getKey()).child("messagedigest").setValue(doc.getMessagedigest());
    }

    @Override
    public documents getItem(int position) {
        return mdocuments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.docexlist_child, parent, false);
        } else {
            view = convertView;
        }

        documents child_doc = getItem(position);
        TextView childtxt= (TextView)view.findViewById(R.id.docchildtxt);
        childtxt.setText(child_doc.getDocumentName());
        return view;
    }
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previeousChildName) {
        String Skey = dataSnapshot.getKey();
        String documentName = dataSnapshot.child("documentName").getValue(String.class);
        String documentOwnerID = dataSnapshot.child("documentOwnerID").getValue(String.class);
        String documentURL = dataSnapshot.child("documentURL").getValue(String.class);
        String ekey = dataSnapshot.child("ekey").getValue(String.class);
        String messagedigest = dataSnapshot.child("messagedigest").getValue(String.class);
      //  String email = dataSnapshot.child("SignerEmail").getValue(String.class);
        //String status = dataSnapshot.child("status").getValue(String.class);



        //if((email.equals(session.userEmail))&&(status=="waiting"))
       // {
         //   waitdocuments.add(0, new documents(Skey, messagedigest, ekey, documentURL, documentOwnerID, documentName));
       // }/*else if((email.equals(session.userEmail))&&(status=="done"))*/
        /*else*/
     //   if(documentOwnerID.equals(session.userkey)){
            mdocuments.add(0, new documents(Skey, messagedigest, ekey, documentURL, documentOwnerID, documentName));// add to the top
       // }
         /*documentOwnerID.equals(session.userkey)refuser.child("Email").equalTo(refreq.("SignerEmail")) && refreq.orderByChild("rDocumentID").equalTo(Skey));
*/
        notifyDataSetChanged();// update adapter

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String Skey = dataSnapshot.getKey();
        String documentName = dataSnapshot.child("documentName").getValue(String.class);
        String documentOwnerID = dataSnapshot.child("documentOwnerID").getValue(String.class);
        String documentURL = dataSnapshot.child("documentURL").getValue(String.class);
        String ekey = dataSnapshot.child("ekey").getValue(String.class);
        String messagedigest = dataSnapshot.child("messagedigest").getValue(String.class);
        for (documents newDocument : mdocuments) {
            if (Skey.equals(newDocument.getKey())) {
                newDocument.setDocumentName(documentName);
                newDocument.setDocumentOwnerID(documentOwnerID);
                newDocument.setDocumentURL(documentURL);
                newDocument.setEkey(ekey);
                newDocument.setMessagedigest(messagedigest);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        for ( documents newdocuments : mdocuments)
        {
            if (key.equals(newdocuments.getKey()))
            {
                mdocuments.remove(newdocuments);
                break;
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