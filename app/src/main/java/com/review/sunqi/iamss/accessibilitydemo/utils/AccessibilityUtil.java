package com.review.sunqi.iamss.accessibilitydemo.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.review.sunqi.iamss.accessibilitydemo.service.MyAccessibilityService;

/**
 * 辅助功能相关检查的帮助类
 */
public class AccessibilityUtil {
    private static final String ACCESSIBILITY_SERVICE_PATH = MyAccessibilityService.class.getCanonicalName();
    /**
     * 判断是否有辅助功能权限
     *
     * @param context
     * @return
     */
    public static boolean isAccessibilitySettingsOn(Context context) {
        if (context == null) {
            return false;
        }

        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        String packageName = context.getPackageName();
        final String serviceStr = packageName + "/" + ACCESSIBILITY_SERVICE_PATH;
        AccessibilityLog.printLog("serviceStr: " + serviceStr);
        if (accessibilityEnabled == 1) {
            TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    if (accessabilityService.equalsIgnoreCase(serviceStr)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Intent getAccessibilitySettingPageIntent(Context context) {
        // 一些品牌的手机可能不是这个Intent,需要适配
        return new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    }

    private static String mAllNodeInfo = "";                                                          // 用于收集整个页面节点的信息

    /**
     * 通过递归，可以打印页面结构
     * @param node
     * @return
     */
    public static String getAllNodeInfo(AccessibilityNodeInfo node) {
        mAllNodeInfo = "";
        getAllNodeInfo("", node);

        if (mAllNodeInfo != null && !mAllNodeInfo.isEmpty()) {
            mAllNodeInfo = "TypeId:" + "\n" + mAllNodeInfo;
        }
        return mAllNodeInfo;
    }

    private static void getAllNodeInfo(String blank, AccessibilityNodeInfo node) {
        if (node == null) {
            return;
        }
        String className = node.getClassName().toString();
        className = className.replace("android.widget.", "");
        className = className.replace("android.view.", "");
        String nodeText = node.getText() != null ? node.getText().toString() : "";
        mAllNodeInfo += blank + className + ":" + nodeText + "\n";

        blank += "  ";
        if (node.getChildCount() > 0) {
            for (int i = 0; i < node.getChildCount(); i++) {
                getAllNodeInfo(blank, node.getChild(i));
            }
        }
    }
}
