package com.gaurav.robotics_society.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.client.Firebase;
import com.gaurav.robotics_society.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by GAURAV on 07-02-2019.
 */

public class profile extends AppCompatActivity {

    public String USGS;

    EditText full_ui;
    EditText email_ui;
    EditText roll_ui;
    Button updatebtn;
    LinearLayout roll_linear;
    String Fullname;
    CharSequence role;
    /*private DatabaseReference db;*/
    private Firebase mfire;
    private user_data user_d;
    private ProgressDialog pd;
    private RadioGroup RadioGroup;
    private RadioButton rba, rbs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        full_ui = findViewById(R.id.fullname);
        email_ui = findViewById(R.id.emaill);
        roll_ui = findViewById(R.id.rollnum);
        updatebtn = findViewById(R.id.email_sign_in_button);
        RadioGroup = findViewById(R.id.radiog);
        rba = findViewById(R.id.alumni);
        rbs = findViewById(R.id.student);
        roll_linear = findViewById(R.id.roll_linear);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String email = (mSharedPreference.getString("user_email", "email"));
        final String user_id = (mSharedPreference.getString("user_id", "id"));
        email_ui.setText(email);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait!");
        pd.show();
        do_the_changes_for_the_users(user_id);
        /*db = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference mydb = db.child(user_id);
        mydb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_d = dataSnapshot.getValue(user_data.class);
                if (user_d != null) {
                    full_ui.setText(String.valueOf(user_d.name));
                    role = user_d.role;
                    if(((String) role).equalsIgnoreCase("Alumni")){
                        rba.setChecked(true);
                        roll_ui.setVisibility(View.GONE);
                        roll_linear.setVisibility(View.GONE);
                    }
                    else {
                        rbs.setChecked(true);
                        roll_linear.setVisibility(View.VISIBLE);
                        roll_ui.setVisibility(View.VISIBLE);
                        roll_ui.setText(String.valueOf(user_d.roll));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        rba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "Alumni";
                roll_ui.setVisibility(View.GONE);
                roll_linear.setVisibility(View.GONE);
            }
        });

        rbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "Student";
                roll_ui.setVisibility(View.VISIBLE);
                roll_linear.setVisibility(View.VISIBLE);
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_user_details(user_id);
            }
        });
    }

    private void update_user_details(String user_id) {
        Firebase.setAndroidContext(this);
        mfire = new Firebase("https://robotics-society-99fe7.firebaseio.com/Users/" + user_id);

        int role_id = RadioGroup.getCheckedRadioButtonId();
        RadioButton radioB = findViewById(role_id);
        role = radioB.getText();

        Firebase Fullname = mfire.child("name");
        Fullname.setValue(full_ui.getText().toString().trim());

        if (((String) role).equalsIgnoreCase("Alumni")) {
            Firebase Roll = mfire.child("roll");
            Roll.setValue("none");

            Firebase rolee = mfire.child("role");
            rolee.setValue(String.valueOf(role));
        } else {
            Firebase Roll = mfire.child("roll");
            Roll.setValue(roll_ui.getText().toString().trim());

            Firebase rolee = mfire.child("role");
            rolee.setValue(String.valueOf(role));
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Details updated")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void do_the_changes_for_the_users(String user_id) {
        USGS = "https://robotics-society-99fe7.firebaseio.com/Users" + "/" + user_id + ".json";
        httpAsyncTask task = new httpAsyncTask();
        task.execute();
    }

    private void updateui(String s) {
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(String.valueOf(s));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String user_name = jObject.getString("name");
            full_ui.setText(String.valueOf(user_name));

            String role = jObject.getString("role");
            if (role.equalsIgnoreCase("Alumni")) {
                rba.setChecked(true);
                roll_ui.setVisibility(View.GONE);
                roll_linear.setVisibility(View.GONE);
            } else {
                rbs.setChecked(true);
                roll_linear.setVisibility(View.VISIBLE);
                roll_ui.setVisibility(View.VISIBLE);
                String roll = jObject.getString("roll");
                roll_ui.setText(String.valueOf(roll));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        pd.hide();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pd.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pd.hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pd.hide();
    }

    public class httpAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            URL url = null;
            try {
                url = new URL(USGS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                updateui(s);
            }
        }


        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection Connection = null;
            InputStream inputStream = null;
            try {
                Connection = (HttpURLConnection) url.openConnection();
                Connection.setRequestMethod("GET");
                Connection.setReadTimeout(10000);
                Connection.setConnectTimeout(15000);
                Connection.connect();
                inputStream = Connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                //Res = String.valueOf(Connection.getResponseCode());
            } catch (IOException e) {

            } finally {
                if (Connection != null) {
                    Connection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
    }
}
