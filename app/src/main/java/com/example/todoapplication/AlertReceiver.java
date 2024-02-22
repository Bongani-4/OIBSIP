package com.example.todoapplication;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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


        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingintent = PendingIntent.getActivity(context,0,i, PendingIntent.FLAG_IMMUTABLE);




        Log.d("AlertReceiver", "Received broadcast");



        // Creating the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ToDo")
                .setSmallIcon(R.drawable.notificationico)
                .setContentTitle("Task Reminder")
                .setAutoCancel(true)
                .setContentText("Your task is scheduled to start soon!")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingintent);



        // Show the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(123, builder.build());

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
        notificationManagerCompat.notify(123, builder.build());
    } else {

        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET},    MY_PERMISSION_REQUEST_CODE
        );
    }
}

    }



