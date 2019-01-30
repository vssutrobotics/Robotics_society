package com.gaurav.robotics_society;

import android.support.annotation.NonNull;

import com.gaurav.robotics_society.app_update_checker.UpdateHelper;
import com.gaurav.robotics_society.utils.Prefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GAURAV on 22-01-2019.
 */

public class Application extends android.app.Application{

    private static Application app;
    private Prefs prefs;

    public static Application getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        prefs = new Prefs(this);


        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        Map<String, Object> defaultValue = new HashMap<>();
        defaultValue.put(UpdateHelper.KEY_UPDATE_ENABLE, false);
        defaultValue.put(UpdateHelper.KEY_UPDATE_VERSION, "1.0");
        defaultValue.put(UpdateHelper.KEY_UPDATE_URL, "APP PLAY STORE URL");

        remoteConfig.setDefaults(defaultValue);
        remoteConfig.fetch(5)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            remoteConfig.activateFetched();
                        }
                    }
                });

    }

    public Prefs getPrefs() {
        return prefs;
    }

    public void setPrefs(Prefs prefs) {
        this.prefs = prefs;
    }

}
