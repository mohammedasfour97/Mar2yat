package com.mriat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.mriat.Auth.Views.LoginActivity;
import com.mriat.Views.HomeActivity;


public class Splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);



        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if (new TinyDB(Splash.this).getString("username").equals("")){
                    Intent mainIntent = new Intent(Splash.this, HomeActivity.class);
                    mainIntent.putExtra("app_name" , "");
                    startActivity(mainIntent);
                }
                else {
                    startActivity(new Intent(Splash.this , LoginActivity.class));

                }
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}