package com.ivsom.ops.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.ivsom.ops.R;
import com.ivsom.ops.app.AppCounts;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;



public class SplashActivity extends BaseActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String sessionId = SPUtils.getInstance().getString(AppCounts.SESSION_ID);

        if (!TextUtils.isEmpty(sessionId)) {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }

    }


}
