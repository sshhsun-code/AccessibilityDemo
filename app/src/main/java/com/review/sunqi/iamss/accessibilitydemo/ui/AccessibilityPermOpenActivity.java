package com.review.sunqi.iamss.accessibilitydemo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.review.sunqi.iamss.accessibilitydemo.utils.AccessibilityUtil;

import java.util.Timer;
import java.util.TimerTask;

public class AccessibilityPermOpenActivity extends Activity {

    public static final String ACTION_OPEN_ACCESSIBILITY = "action_open_accessibility";
    public static final String ACTION_FINISH_SELF = "action_finish_self";
    public static final String ACTION_NONE = "action_none";
    public static final String ACTION = "action";


    private Timer timer;
    private TimerTask timerTask;
    private int mTimeoutCounter = 0;

    private int TIMEOUT_MAX_INTERVAL = 60 * 2; // 2 min

    private long TIMER_CHECK_INTERVAL = 1000;

    private String taskAction = ACTION_NONE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            taskAction = getIntent().getStringExtra(ACTION);
            if (ACTION_FINISH_SELF.equals(taskAction)) {
                finishCurrentActivity();
            } else if (ACTION_OPEN_ACCESSIBILITY.equals(taskAction)) {
                jumpActivities();
                startCheckAccessibilityOpen();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void jumpActivities() {
        try {
            Intent intent = AccessibilityUtil.getAccessibilitySettingPageIntent(this);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void jumpToSettingPage(Context context) {
        try {
            Intent intent = new Intent(context,  AccessibilityPermOpenActivity.class);
            intent.putExtra(ACTION, ACTION_OPEN_ACCESSIBILITY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception ignore) {}
    }

    private void finishCurrentActivity() {
        freeTimeTask();
        finish();
    }


    private void startCheckAccessibilityOpen() {
        freeTimeTask();
        initTimeTask();
        timer.schedule(timerTask, 0, TIMER_CHECK_INTERVAL);
    }

    private void initTimeTask() {
        timer = new Timer();
        mTimeoutCounter = 0;
        timerTask = new TimerTask() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                if (AccessibilityUtil.isAccessibilitySettingsOn(AccessibilityPermOpenActivity.this)) {
                    freeTimeTask();
                    Looper.prepare();
                    try {
                        AccessibilityPermOpenActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AccessibilityPermOpenActivity.this, "辅助功能开启成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent intent = new Intent();
                        intent.putExtra(ACTION, ACTION_FINISH_SELF);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(AccessibilityPermOpenActivity.this, AccessibilityPermOpenActivity.this.getClass());
                        startActivity(intent);
                        Intent intent1 = new Intent(AccessibilityPermOpenActivity.this, AccessibilityActionActivity.class);
                        startActivity(intent1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
                // 超过2分钟超时，就释放timer。
                mTimeoutCounter++;
                if (mTimeoutCounter > TIMEOUT_MAX_INTERVAL) {
                    freeTimeTask();
                }
            }
        };
    }

    private void freeTimeTask() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
