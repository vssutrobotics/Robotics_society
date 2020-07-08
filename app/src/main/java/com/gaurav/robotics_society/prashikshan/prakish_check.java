package com.gaurav.robotics_society.prashikshan;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gaurav.robotics_society.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class prakish_check extends AppCompatActivity {


    private String USGS;
    private ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_eligible_prakish);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        final String user_id = (mSharedPreference.getString("user_id", "id"));
        getRoll(user_id);

    }

    private void getRoll(String user_id) {
        USGS = "https://robotics-society-99fe7.firebaseio.com/Users" + "/" + user_id + "/roll" + ".json";
        httpAsyncTask task = new httpAsyncTask();
        task.execute();
    }

    private void updateui(String s) {
        String edited;
        //TextView text = (TextView) findViewById(R.id.roll_view);
        if (s.equalsIgnoreCase("")) {
            s = null;
        }
        if (s != null) {
            edited = s.substring(1, 3);
        } else edited = null;
        //text.setText(edited);
        String eligible = "19";

        if (edited.equalsIgnoreCase(eligible) && (edited != null)) {
            setContentView(R.layout.eligible_prakish);
        }
        progress.hide();
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
            updateui(s);
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
