package com.example.a.party_booker;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FlashScreen extends AppCompatActivity {

    private static boolean splashLoaded = false;
    AppPref appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPref=new AppPref(getApplicationContext());
        setContentView(R.layout.activity_flash_screen);

//        if (!splashLoaded) {
//
//
//        int secondsDelayed = 3;
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                startActivity(new Intent(FlashScreen.this, Login.class));
//                finish();
//            }
//        }, secondsDelayed * 500);
//
//        splashLoaded = true;
//    }
//    else {
//        Intent goToMainActivity = new Intent(FlashScreen.this, Register.class);
//        goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(goToMainActivity);
//        finish();
//    }

        new CountDownTimer(5000,4200) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

                //Log.e("abacss",""+prefs.getis_verify().toString());

                if(appPref.isLoggedIn())
                {
                    if(appPref.isSelected())
                    {
                        Intent in3 = new Intent(FlashScreen.this,MainActivity.class);
                        startActivity(in3);
                        finish();
                    }
                    else
                    {
                        Intent in3 = new Intent(FlashScreen.this,SelectCity.class);
                        startActivity(in3);
                        finish();
                    }

                }
                else {
                    Intent in3 = new Intent(FlashScreen.this,Login.class);
                    startActivity(in3);
                    finish();
                }


//                if(prefs.getis_verify().toString().equalsIgnoreCase("")) {
//                    Intent iGo = new Intent(SplashScreen.this, Activity_Login.class);
//                    iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(iGo);
//                    SplashScreen.this.finish();
//                }else if(prefs.getis_verify().toString().equalsIgnoreCase("1")) {
//                    Intent iGo = new Intent(SplashScreen.this, MainScreen.class);
//                    iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(iGo);
//                    SplashScreen.this.finish();
//                }

            }

        }.start();
    }
}
