package com.signononlinesignatureapp.signon;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

//push 2
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Snap";
    ImageView signatureImageView;
    ImageView imageView;
    private static final int FILE_SELECT_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Firebase.setAndroidContext(this);
        Bundle extras = getIntent().getExtras();
        session.userkey = extras.getString("key");
        imageView = (ImageView) findViewById(R.id.imageButton);
        signatureImageView=(ImageView)findViewById(R.id.homeSignatureImageView);
        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users/"+session.userkey+"/username/");
        Query queryRef = ref.orderByValue();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView usernametext = (TextView)findViewById(R.id.textView15);

                usernametext.setText((String) dataSnapshot.getValue());
                 personalImageSearch();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

queryRef.addValueEventListener(listener);



       session.userkey = extras.getString("key");

    }
    public void uploadDocument(View v){



            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(
                        Intent.createChooser(intent, "Select a File to Upload"),
                        FILE_SELECT_CODE);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.",
                        Toast.LENGTH_SHORT).show();
            }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    try{
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path

                        String uriString=uri.getPath();
                        Log.d(TAG, "String Path: " + uriString);
                    String path = FileUtils.getPath(this, uri);

                    Log.d(TAG, "File Path: " + path);



                        // Get the file instance
                        new HDWFTP_Upload().execute(path);

                        // File file = new File(path);
                        // Initiate the upload
                    }
                    catch (URISyntaxException e){
                    Toast.makeText(this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();}

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void testOn2(View v){
        startActivity(new Intent(HomeActivity.this, CaptureSignatureActivity.class));


    }

    public void testOn3(View v){
        startActivity(new Intent(HomeActivity.this, SettingActivity.class));


    }

    public void testOn4(View v){
        startActivity(new Intent(HomeActivity.this, Request_Signture.class));


    }

    public void imageViewfromURL (String imageUrl){
        final String URL = imageUrl;
        /** Called when the activity is first created. */


            // Create an object for subclass of AsyncTask
            GetXMLTask task = new GetXMLTask();
            // Execute the task
            task.execute(new String[]{URL});
        }

        private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... urls) {
                Bitmap map = null;
                for (String url : urls) {
                    map = downloadImage(url);
                }
                return map;
            }

            // Sets the Bitmap returned by doInBackground
            @Override
            protected void onPostExecute(Bitmap result) {
                imageView.setImageBitmap(result);
            }

            // Creates Bitmap from InputStream and returns it
            private Bitmap downloadImage(String url) {
                Bitmap bitmap = null;
                InputStream stream = null;
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 1;

                try {
                    stream = getHttpConnection(url);
                    bitmap = BitmapFactory.
                            decodeStream(stream, null, bmOptions);
                    stream.close();
                } catch (IOException e1) {
                    Toast toast = Toast.makeText(HomeActivity.this, "ERROR", Toast.LENGTH_LONG);
                    toast.show();


                }
                return bitmap;
            }

            // Makes HttpURLConnection and returns InputStream
            private InputStream getHttpConnection(String urlString)
                    throws IOException {
                InputStream stream = null;
                URL url = new URL(urlString);
                URLConnection connection = url.openConnection();

                try {
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    httpConnection.setRequestMethod("GET");
                    httpConnection.connect();

                    if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        stream = httpConnection.getInputStream();
                    }
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(HomeActivity.this, "ERROR", Toast.LENGTH_LONG);
                    toast.show();
                }
                return stream;
            }
        }

public void personalImageSearch(){

    Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users/"+session.userkey+"/imageURL/");
    Query queryRef = ref.orderByValue();

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
/*
            if(!(dataSnapshot.getValue().toString()=="0"))
                imageViewfromURL((String)dataSnapshot.getValue());

*/
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    };

    queryRef.addValueEventListener(listener);


}
    @Override
    public void onResume(){
        changeImageView();
        super.onResume();

    }
    public void changeImageView(){
        final ImageView test=(ImageView)findViewById(R.id.homeSignatureImageView);
        Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/signature");
        Query queryRef2 = ref.orderByChild("signerID").equalTo(session.userkey);
        ValueEventListener listener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        session.base64=child.getKey();
                            byte[] temp= Base64.decode(child.child("signatureBase64").getValue(String.class), Base64.NO_WRAP);
                            Bitmap img= BitmapFactory.decodeByteArray(temp, 0, temp.length);
                            test.setImageBitmap(img);
                    }
                }
                else
                    test.setImageResource(R.drawable.signature);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        queryRef2.addValueEventListener(listener2);
    }
}

