package com.example.calvarez.mapmaster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by calvarez on 12/1/2016.
 */
public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        final ImageView splash = (ImageView) findViewById(R.id.app_icon);
        RelativeLayout root = (RelativeLayout) findViewById(R.id.relative_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView loading = (TextView) findViewById(R.id.loading);

        setSupportActionBar(toolbar);




        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(SPLASH_TIME_OUT);

        // Start animating the image

        splash.startAnimation(anim);

        new CountDownTimer(SPLASH_TIME_OUT,100){
            @Override
            public void onTick(long millisUntilFinished) {
                int millis = (int)millisUntilFinished;
                if(millis%500 <= 100){
                    loading.append(".");
                }


            }

            @Override
            public void onFinish() {
                // This method will be executed once the timer is over
                // Start your app main activity
                splash.setAnimation(null);
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }.start();

    }
}
