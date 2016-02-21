package com.signononlinesignatureapp.signon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import android.support.v7.app.AppCompatActivity;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//push 2
public class HomeActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Firebase.setAndroidContext(this);
        Bundle extras = getIntent().getExtras();
        session.userkey = extras.getString("key");
        imageView = (ImageView) findViewById(R.id.imageButton);
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
    public void testOn(View v){
        startActivity(new Intent(HomeActivity.this, SignatureSelectActivity.class));


    }
    public void testOn2(View v){
        startActivity(new Intent(HomeActivity.this, CaptureSignatureActivity.class));


    }

    public void testOn3(View v){
        startActivity(new Intent(HomeActivity.this, SettingActivity.class));


    }

    public void imageViewfromURL (String imageUrl){
        final String URL = imageUrl;
        /** Called when the activity is first created. */


            // Create an object for subclass of AsyncTask
            GetXMLTask task = new GetXMLTask();
            // Execute the task
            task.execute(new String[] { URL });
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

            if(!(dataSnapshot.getValue().toString()=="0"))
                imageViewfromURL((String)dataSnapshot.getValue());


        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    };

    queryRef.addValueEventListener(listener);


}

}

