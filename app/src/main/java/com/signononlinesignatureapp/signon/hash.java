package com.signononlinesignatureapp.signon;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class hash extends AppCompatActivity {
    public TextView checksum;
    public Button hashButton;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash);
        checksum= (TextView) findViewById(R.id.checksum);
        hashButton=(Button) findViewById(R.id.hashButton);
        File dir = new File(path);
        dir.mkdirs();
    }
    public void hashButtonClick(View v){
        File file = new File (path + "/word.pdf");
        checksum.setText(SHA512.calculateSHA512(file));

    }
}
