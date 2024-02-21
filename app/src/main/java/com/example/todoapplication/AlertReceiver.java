package com.example.todoapplication;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class AlertReceiver extends BroadcastReceiver {

    private static final int MY_PERMISSION_REQUEST_CODE = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlertReceiver", "Received broadcast");
        showNotification(context);
    }

    private void showNotification(Context context) {
        // Notification channel
        {
            NotificationChannel channel = new NotificationChannel(
                    "default_channel_id",
                    "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Creating the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default_channel_id")
                .setSmallIcon(R.drawable.notificationico)
                .setContentTitle("Task Reminder")
                .setContentText("Your task is scheduled to start soon!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                     notificationManager.notify(1, builder.build());
        } else {

            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET},    MY_PERMISSION_REQUEST_CODE
);
        }
    }
}
