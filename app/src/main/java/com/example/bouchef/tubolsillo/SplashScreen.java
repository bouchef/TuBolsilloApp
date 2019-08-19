package com.example.bouchef.tubolsillo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.generics.GlobalClass;

public class SplashScreen extends AppCompatActivity {
    Intent intent;
    View mDecorView;
    ImageView imageView;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mDecorView = getWindow().getDecorView();

        hideSystemUI();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    intent = new Intent(SplashScreen.this,MainActivity2.class);
                    //intent = new Intent(SplashScreen.this,BotoneraInicialAyudante.class);
                    startActivity(intent);
                  finish();
                } catch (Exception e) {

                }
            }
        }, 3000);
    }

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
