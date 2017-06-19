package tech.touch.threeds.android.sampleApp;

import android.app.Application;

import tech.touch.threeds.android.sdk.TTEnv;
import tech.touch.threeds.android.sdk.TTOptions;
import tech.touch.threeds.android.sdk.Touchtech;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TTOptions options = new TTOptions(this, "androidVers1Status0", TTEnv.STAGING);
        Touchtech.configure(options);
    }

}