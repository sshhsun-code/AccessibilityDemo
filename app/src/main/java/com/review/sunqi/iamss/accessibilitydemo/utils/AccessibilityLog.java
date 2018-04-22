package com.review.sunqi.iamss.accessibilitydemo.utils;

import android.util.Log;

import com.review.sunqi.iamss.accessibilitydemo.BuildConfig;

/**
 * Created by popfisher on 2017/7/11.
 */

public class AccessibilityLog {

    private static final String TAG = "AccessibilityService";
    public static void printLog(String logMsg) {
        if (!BuildConfig.DEBUG) return;
        Log.d(TAG, logMsg);
    }
}
