package com.review.sunqi.iamss.accessibilitydemo.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

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
        AccessibilityNodeInfo info = AccessibilityOperator.getInstance().getRootNodeInfo();
        AccessibilityLog.printLog("sunqi2", "eventType: " + eventType + " info: " + info);
    }

    @Override
    public void onInterrupt() {

    }
}
