package com.signononlinesignatureapp.signon;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
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

        introActivityLoginButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(IntroActivity.this, SettingActivity.class));

                    }

                }

        );

        introActivityRegisterButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(IntroActivity.this, SignDocument.class));

                    }

                }

        );
    }
}
