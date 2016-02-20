package com.signononlinesignatureapp.signon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class IntroActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(80,23,140));
        }


        setContentView(R.layout.activity_intro);


        Button introActivityLoginButton=(Button)findViewById(R.id.introActivityLoginButton);
        Button introActivityRegisterButton=(Button) findViewById(R.id.introActivityRegisterButton);

        EditText email, password;
        email = (EditText) findViewById(R.id.introEmailEditText);
        password = (EditText) findViewById(R.id.introPasswordEditText);

        introActivityLoginButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        startActivity(new Intent(IntroActivity.this, HomeActivity.class));

                    }

                }

        );

        introActivityRegisterButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(IntroActivity.this, RigesterActivity.class));

                    }

                }

        );
    }
    public void storeSignatureButtonClick(View v){
        startActivity(new Intent(IntroActivity.this, CaptureSignatureActivity.class));

    }
    public void sha512ButtonClick(View v){
        startActivity(new Intent(IntroActivity.this, hash.class));


    }
    public void picButtonClick(View v){

        startActivity(new Intent(IntroActivity.this, SignDocument.class));

    }
}
