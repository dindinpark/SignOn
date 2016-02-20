package com.signononlinesignatureapp.signon;

/**
 * Created by daniah on 2/19/2016.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;

import java.io.ByteArrayOutputStream;

public class SignatureListArrayAdapter extends FirebaseListAdapter<signature> {


    public  SignatureListArrayAdapter(Query ref, Activity activity, int layout) {
        super(ref, signature.class, layout, activity);

    }
    @Override
    protected void populateView(View view, signature signature) {
        // Map a Chat object to an entry in our listview
        String signatureName = signature.getSignatureName();
        String signatureBase64=signature.getSignatureBase64();
        byte[] temp=Base64.decode(signatureBase64, Base64.DEFAULT);
        Bitmap img= BitmapFactory.decodeByteArray(temp, 0, temp.length);
        ImageView signaturePic=(ImageView)view.findViewById(R.id.selectSignatureBase64);
        signaturePic.setImageBitmap(img);
        TextView signatureText = (TextView) view.findViewById(R.id.selectSignatureName);
        signatureText.setText(signatureName + ": ");
        // If the message was sent by this user, color it differently
        /*if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }*/
        ((TextView) view.findViewById(R.id.selectSignatureID)).setText(signature.getSignerID());
    }

    /*private final LayoutInflater mInflater;
    private List<signature> mSignatures;
    private Firebase mFirebase;
    public SignatureListArrayAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mSignatures = new ArrayList<signature>();
        Firebase.setAndroidContext(context);
        mFirebase = new Firebase ("https://torrid-heat-4458.firebaseio.com/signature");
    }

    @Override
    public int getCount() {
        return mSignatures.size();
    }

    public void removeItem(signature signature) {
        //TODO: Remove data from Firebase
        mFirebase.child(signature.getKey()).removeValue();
    }

    public void addItem(signature signature) {
        //TODO: Push new data to Firebase
        Map<String, String> newSignature = new HashMap<String, String>();
        newSignature.put("signatureName",signature.getSignatureName());
        newSignature.put("signatureBase64", signature.getSignatureBase64());
        newSignature.put("signerID", signature.getSignerID());
        mFirebase.push().setValue(newSignature);
    }
/////////////////////////
    public void updateItem(signature signature,String N,String I) {
        //TODO: Push changes to Firebase
        Map<String, String> newSignature = new HashMap<String, String>();
        newSignature.put("signatureName",N);
        newSignature.put("signatureBase64", signature.getSignatureBase64());
        newSignature.put("signerID", I);
        mFirebase.push().setValue(newSignature);
    }

    @Override
    public signature getItem(int position) {
        return mSignatures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflater.inflate(android.R.layout.simple_expandable_list_item_2, parent, false);
        } else {
            view = convertView;
        }
        TextView titleTextView = (TextView) view.findViewById(android.R.id.text2);
        signature signature = getItem(position);
        titleTextView.setText(signature.getSignatureName());
        TextView quoteTextView = (TextView) view.findViewById(android.R.id.text1);
        quoteTextView.setText(signature.getSignatureBase64());
        return view;
    }
    */
}
