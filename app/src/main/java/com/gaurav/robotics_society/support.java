package com.gaurav.robotics_society;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class support extends AppCompatActivity {

    private String content_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Use Gmail in next window to get support.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Toast.makeText(Home_Page.this, "Updating!!!" + urlApp, Toast.LENGTH_SHORT).show();
                        user_support();
                    }
                }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void user_support() {
        String details = "VERSION.RELEASE : " + Build.VERSION.RELEASE
                + "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
                + "\nBRAND : " + Build.BRAND
                + "\nDISPLAY : " + Build.DISPLAY
                + "\nFINGERPRINT : " + Build.FINGERPRINT
                + "\nID : " + Build.ID
                + "\nMANUFACTURER : " + Build.MANUFACTURER
                + "\nMODEL : " + Build.MODEL
                + "\nTIME : " + Build.TIME;

        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String user_id = (mSharedPreference.getString("user_id", "id"));
        String user_email = (mSharedPreference.getString("user_email", "email"));

        String[] TO = {"Secretary.roboticsvssut@gmail.com"};
        String[] CC = {"Gaurav.kedia18@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        content_email = "User_id :" + user_id;
        content_email = content_email + "\n" + "User_email :" + user_email;
        content_email = content_email + "\n" + "Details: " + details;
        content_email = content_email + "\n\n\n" + "Enter your message: ";

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support: Robotics_Society :" + user_id);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content_email);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }
}
