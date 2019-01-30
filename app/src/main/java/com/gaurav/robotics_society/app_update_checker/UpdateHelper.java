package com.gaurav.robotics_society.app_update_checker;

import android.content.Context;

/**
 * Created by GAURAV on 31-01-2019.
 */

public class UpdateHelper {

    public static String KEY_UPDATE_ENABLE = "force_update_required";
    public static String KEY_UPDATE_VERSION = "force_update_current_version";
    public static String KEY_UPDATE_URL = "force_update_store_url";

    public interface onUpdateCheckListener{
        void onUpdateCheckListener(String urlApp);
    }

    public static Builder with(Context context){
        return new Builder(context);
    }

    private onUpdateCheckListener onUpdateCheckListener;


    public static class Builder{

        private Context context;
        private onUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder onUpdateCheck(onUpdateCheckListener onUpdateCheckListener){
            this.onUpdateCheckListener = onUpdateCheckListener;
            return this;
        }
    }
}
