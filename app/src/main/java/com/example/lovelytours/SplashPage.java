package com.example.lovelytours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

public class SplashPage extends AppCompatActivity {
    Intent intent;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        lottie=findViewById(R.id.lottie);
        lottie.animate().translationX(2000).setDuration(1200).setStartDelay(2900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        },2000);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //countDownTimer.start();
    }

    //CountDownTimer countDownTimer = new CountDownTimer(4500, 70) {
        //@Override
        //public void onTick(long l) {

        //}

        //@Override
        //public void onFinish() {
            //intent = new Intent(SplashPage.this, MainActivity.class);
            //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                //startActivity(intent);
            //}

        }
    //};
//}
