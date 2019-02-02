package com.gaurav.robotics_society;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.robotics_society.Notify.NotificationHelper;
import com.gaurav.robotics_society.app_update_checker.UpdateHelper;

/**
 * Created by GAURAV on 23-01-2019.
 */

public class HomeActivity extends AppCompatActivity implements UpdateHelper.onUpdateCheckListener {
    private static final int NOTI_SECONDARY1 = 1201;
    private TextView btn1;
    private NotificationHelper noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().hide();

        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();

        btn1 = (TextView) findViewById(R.id.Button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Home_Page.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onUpdateCheckListener(final String urlApp) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Newer Version is Available")
                .setMessage("Please Update to continue")
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HomeActivity.this, "Updating!!!" + urlApp, Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noti = new NotificationHelper(this);
            Notification.Builder nb = noti.getNotification2();
            noti.notify(NOTI_SECONDARY1, nb);
        } else {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.rslogo)
                            .setContentTitle("A Update is available")
                            .setContentText("Please visit PlayStore to Update");

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

    }
}
