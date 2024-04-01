package com.example.lovelytours;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.airbnb.lottie.animation.content.Content;

public class AlertDialogManager {

    public static void showMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    public static void showConfirmMessage(Context context, String title,
                                          String message, DialogInterface.OnClickListener approve,
                                          DialogInterface.OnClickListener cancel) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel,cancel)
                .setPositiveButton(R.string.yes, approve)
                .show();
    }
}
