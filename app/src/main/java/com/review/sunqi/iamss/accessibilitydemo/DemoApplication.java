package com.review.sunqi.iamss.accessibilitydemo;

import android.app.Application;
import android.content.Context;

public class DemoApplication extends Application {

    private static Context mContext = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getInstance() {
        return mContext;
    }
}
