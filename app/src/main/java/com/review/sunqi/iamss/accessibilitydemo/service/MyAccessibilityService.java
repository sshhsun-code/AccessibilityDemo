package com.review.sunqi.iamss.accessibilitydemo.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.review.sunqi.iamss.accessibilitydemo.OperateHelper.AccessibilityOperator;
import com.review.sunqi.iamss.accessibilitydemo.utils.AccessibilityLog;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        String pkgName = accessibilityEvent.getPackageName().toString();
        int eventType = accessibilityEvent.getEventType();

        AccessibilityLog.printLog("eventType: " + eventType + " pkgName: " + pkgName);
        AccessibilityOperator.getInstance().updateEvent(this, accessibilityEvent);
    }

    @Override
    public void onInterrupt() {

    }
}
