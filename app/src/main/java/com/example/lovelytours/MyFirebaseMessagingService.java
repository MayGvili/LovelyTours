package com.example.lovelytours;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (message.getNotification() != null) {
                    Toast.makeText(MyFirebaseMessagingService.this, message.getNotification().getTitle(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
