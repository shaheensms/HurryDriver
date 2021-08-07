package com.hubit.hurrydriver;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.hubit.hurrydriver.Constants.constants;
import com.onesignal.OneSignal;

public class myApplicaiton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Enable verbose OneSignal logging to debug issues if needed.
        //  OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(constants.APP_KEY);
    }
}
