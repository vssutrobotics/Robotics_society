package com.gaurav.robotics_society;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.gaurav.robotics_society.Notify.NotificationHelper;
import com.gaurav.robotics_society.app_update_checker.UpdateHelper;
import com.gaurav.robotics_society.user.forget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by GAURAV on 23-01-2019.
 */

public class HomeActivity extends AppCompatActivity implements UpdateHelper.onUpdateCheckListener {


    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    ConstraintLayout rootl;
    private Snackbar snackbar, snackbar1, snackbar2;
    private boolean internetConnected = true;

    public AlertDialog alertDialog_not_connected;

    private static final int NOTI_SECONDARY1 = 1201;
    private TextView btn1;
    private NotificationHelper noti;

    private Button Signin_button;
    /**
     * Runtime Broadcast receiver inner class to capture internet connectivity events
     */
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status, false);
        }
    };
    private ProgressBar signin_pb;
    private LinearLayout sigin_layout, Forget_pass;
    private FirebaseAuth mAuth;
    private EditText email_UI;
    private EditText pass_UI;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(isNetworkAvailable()){
            network_alert();
        }*/

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("signIn", false)) {
            finish();
            Firebase.setAndroidContext(this);
            send_token_to_server();
            send_time_to_server();
            Intent inte = new Intent(HomeActivity.this, Home_Page.class);
            startActivity(inte);
        }

        setContentView(R.layout.login_main);
        getSupportActionBar().hide();
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();

        rootl = (ConstraintLayout) findViewById(R.id.rootview);
        email_UI = (EditText) findViewById(R.id.email);
        pass_UI = (EditText) findViewById(R.id.password);
        Signin_button = (Button) findViewById(R.id.email_sign_in_button);
        btn1 = (TextView) findViewById(R.id.Button1);
        signin_pb = (ProgressBar) findViewById(R.id.progress_barr_signin);
        signin_pb.setVisibility(View.GONE);
        sigin_layout = (LinearLayout) findViewById(R.id.email_login_form);
        Forget_pass = (LinearLayout) findViewById(R.id.forget_password);

        String status = getConnectivityStatusString(this);
        setSnackbarMessage(status, false);

        Signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sigin_layout.setVisibility(View.GONE);
                Forget_pass.setVisibility(View.GONE);
                signin_pb.setVisibility(View.VISIBLE);
                signin_user();
                /*if(!isNetworkAvailable()) {
                    signin_user();
                }
                else {

                }*/
            }
        });

        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), forget.class);
                startActivity(intent);
            }
        });

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void send_time_to_server() {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        String user_id = (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("user_id", "id"));
        Firebase mfire = new Firebase("https://robotics-society-99fe7.firebaseio.com/Users/" + user_id);

        Firebase Fullname = mfire.child("last_login");
        Fullname.setValue(date);
    }

    private void send_token_to_server() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String user_id = (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("user_id", "id"));
                        String token = task.getResult().getToken();
                        //SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        //mSharedPreference.edit().putString("user_token", token).commit();
                        Firebase mfire = new Firebase("https://robotics-society-99fe7.firebaseio.com/Users/" + user_id);

                        Firebase Fullname = mfire.child("token");
                        Fullname.setValue(token);
                    }
                });
        // [END retrieve_current_token]
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnectedOrConnecting())) {
            return true;
        } else {
            return false;
        }
    }

    private void signin_user() {
        final String email = email_UI.getText().toString().trim();
        final String password = pass_UI.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            pass_UI.setError(getString(R.string.error_invalid_password));
            focusView = pass_UI;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            email_UI.setError("This field is required");
            focusView = email_UI;
            cancel = true;
        } else if (!isEmailValid(email)) {
            email_UI.setError("This email address is invalid");
            focusView = email_UI;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            pass_UI.setError("This field is required");
            focusView = pass_UI;
            cancel = true;
        }
        if (cancel) {
            sigin_layout.setVisibility(View.VISIBLE);
            Forget_pass.setVisibility(View.VISIBLE);
            signin_pb.setVisibility(View.GONE);
            focusView.requestFocus();// There was an error; don't attempt login and focus the first
        } else {
            final boolean[] check = new boolean[1];

            mAuth.fetchProvidersForEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                            check[0] = !task.getResult().getProviders().isEmpty();
                            //Toast.makeText(HomeActivity.this, "check :" + check[0], Toast.LENGTH_SHORT).show();
                            sigininORcreate(check[0], email, password);
                        }
                    });
        }
    }

    private void sigininORcreate(boolean check, final String email, String password) {
        if (check) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if ((user.isEmailVerified())) {
                                    finish();
                                    Toast.makeText(HomeActivity.this, "Welcome to Robotics Society", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(), Home_Page.class);

                                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).edit();
                                    editor.putString("user_id", user.getUid().toString().trim());
                                    editor.putString("user_email", email.toString().trim());
                                    editor.putBoolean("signIn", true);
                                    editor.putInt("noti_id", 9994);
                                    editor.commit();


                                    /*intent.putExtra("user_id", (user.getUid()).toString().trim());
                                    intent.putExtra("user_email", email.toString().trim());*/
                                    startActivity(intent);

                                } else if (!(user.isEmailVerified())) {
                                    sigin_layout.setVisibility(View.VISIBLE);
                                    Forget_pass.setVisibility(View.VISIBLE);
                                    signin_pb.setVisibility(View.GONE);

                                    snackbar1 = Snackbar
                                            .make(rootl, "Please verify your email to proceed", Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE);
                                    snackbar1.show();

                                    //Toast.makeText(HomeActivity.this, "Please verify your email to proceed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                sigin_layout.setVisibility(View.VISIBLE);
                                Forget_pass.setVisibility(View.VISIBLE);
                                signin_pb.setVisibility(View.GONE);
                                pass_UI.setError("Invalid Credentials");
                                View focusView = pass_UI;
                                focusView.requestFocus();
                            }
                        }
                    });

        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                sigin_layout.setVisibility(View.VISIBLE);
                                Forget_pass.setVisibility(View.VISIBLE);
                                signin_pb.setVisibility(View.GONE);

                                //Toast.makeText(HomeActivity.this, "User Created, Now you can SignIn", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Firebase mfire = new Firebase("https://robotics-society-99fe7.firebaseio.com/Users/" + user.getUid());

                                Firebase name = mfire.child("name");
                                name.setValue("Default");

                                Firebase role = mfire.child("email");
                                role.setValue(email);

                                Firebase user_role = mfire.child("role");
                                user_role.setValue("Student");

                                Firebase rol = mfire.child("roll");
                                rol.setValue("none");

                                Firebase token = mfire.child("token");
                                token.setValue(FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken());

                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    sigin_layout.setVisibility(View.VISIBLE);
                                                    Forget_pass.setVisibility(View.VISIBLE);
                                                    signin_pb.setVisibility(View.GONE);
                                                    snackbar2 = Snackbar
                                                            .make(rootl, "Email verification sent, Please Verify to SignIn", Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE);
                                                    snackbar2.show();
                                                    //Toast.makeText(HomeActivity.this, "Email verification sent, Please Verify to SignIn", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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
                        // Toast.makeText(HomeActivity.this, "Updating!!!" + urlApp, Toast.LENGTH_SHORT).show();

                        Intent i1 = new Intent(android.content.Intent.ACTION_VIEW);
                        i1.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gaurav.robotics_society"));
                        finish();
                        startActivity(i1);

                    }
                }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noti = new NotificationHelper(this);
            Notification.Builder nb = noti.getNotification2();
            nb.setContentText("Please visit PlayStore to Update");
            nb.setContentTitle("A Update is available");
            noti.notify(NOTI_SECONDARY1, nb);

            Intent notificationIntent = new Intent(android.content.Intent.ACTION_VIEW);
            notificationIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gaurav.robotics_society"));

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            nb.setContentIntent(contentIntent);

        } else {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setContentTitle("A Update is available")
                            .setContentText("Please visit PlayStore to Update");

            Intent notificationIntent = new Intent(android.content.Intent.ACTION_VIEW);
            notificationIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gaurav.robotics_society"));

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }

    private void setSnackbarMessage(String status, boolean showBar) {
        String internetStatus = "";
        if (status.equalsIgnoreCase("Wifi enabled") || status.equalsIgnoreCase("Mobile data enabled")) {
            internetStatus = "Internet Connected";
            Signin_button.setClickable(true);
        } else {
            internetStatus = "No Internet Connection";
            Signin_button.setClickable(false);
        }

        if (internetStatus.equalsIgnoreCase("No Internet Connection")) {
            snackbar = Snackbar
                    .make(rootl, internetStatus, Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
        } else {
            snackbar = Snackbar
                    .make(rootl, internetStatus, Snackbar.LENGTH_LONG)
                    .setAction("Well Done!", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
        }

        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        if (internetStatus.equalsIgnoreCase("No Internet Connection")) {
            if (internetConnected) {
                snackbar.show();
                internetConnected = false;
            }
        } else {
            if (!internetConnected) {
                internetConnected = true;
                snackbar.show();
            }
        }
    }
}
