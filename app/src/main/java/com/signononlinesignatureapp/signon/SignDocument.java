package com.signononlinesignatureapp.signon;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
public class SignDocument extends AppCompatActivity {

    public String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/word.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, MyPdfViewerActivity.class);
        i.putExtra(Pdftry.EXTRA_PDFFILENAME, newPath);
        startActivity(i);

    }
}