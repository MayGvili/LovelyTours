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
        lottie.animate().translationX(1000).setDuration(3000).setStartDelay(1200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();

            }
        },2000);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //countDownTimer.start();
    }
        }
    //};
//}
