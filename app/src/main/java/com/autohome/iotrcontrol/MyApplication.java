package com.autohome.iotrcontrol;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication sInstance;
    private static Context sContext;

    public static MyApplication getInstance() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
    }
}
