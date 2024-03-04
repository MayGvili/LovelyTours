package com.example.lovelytours;

import android.app.AlertDialog;
import android.content.Context;

import com.airbnb.lottie.animation.content.Content;

public class AlertDialogManager {

    public static void showMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
