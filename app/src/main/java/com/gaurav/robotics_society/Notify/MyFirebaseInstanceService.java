package com.gaurav.robotics_society.Notify;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by GAURAV on 09-02-2019.
 */

public class MyFirebaseInstanceService extends FirebaseInstanceIdService {

    final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    final String user_id = (mSharedPreference.getString("user_id", "id"));
    private Firebase mfire;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        String user_email = (mSharedPreference.getString("user_email", "email"));
        if (!(user_email.equalsIgnoreCase("email"))) {
            mfire = new Firebase("https://robotics-society-99fe7.firebaseio.com/Users/" + user_id);
            Firebase Fullname = mfire.child("token");
            Fullname.setValue(token);
        }
    }


}
