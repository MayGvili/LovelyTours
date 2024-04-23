package com.example.lovelytours.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.lovelytours.R;
import com.example.lovelytours.activities.MainActivity;

public class SplashPageActivity extends AppCompatActivity {
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
