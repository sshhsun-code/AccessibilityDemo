package com.review.sunqi.iamss.accessibilitydemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.review.sunqi.iamss.accessibilitydemo.OperateHelper.AccessibilityOperator;
import com.review.sunqi.iamss.accessibilitydemo.R;
import com.review.sunqi.iamss.accessibilitydemo.utils.AccessibilityLog;
import com.review.sunqi.iamss.accessibilitydemo.utils.AccessibilityUtil;

public class AccessibilityActionActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_action);

        if (!AccessibilityUtil.isAccessibilitySettingsOn(this)) {
            finish();
            return;
        }

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessibilityLog.printLog("执行了点击事件");
                Toast.makeText(AccessibilityActionActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {//为什么要延时？？？直接调用的时候，mAccessibilityService.getRootInActiveWindow()为空？为什么？
            @Override
            public void run() {
                AccessibilityOperator.getInstance().clickByText("点击按钮");
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AccessibilityOperator.getInstance().clickById(R.id.btn_click + "");
            }
        }, 4000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
