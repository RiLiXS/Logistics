package com.example.logistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.utils.UserLocalData;

/**
 * APP启动页
 */
public class AppStartActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        setTheme(R.style.AppStart);
        return R.layout.activity_app_start;


    }

    @Override
    protected void initView() {
        try {
            initRun();//初始化
        } catch (Exception e) {
            Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void initData() {

    }
    private void initRun() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginBean login = UserLocalData.getLogin();
                String access_token = login.getAccess_token();
                if (!TextUtils.isEmpty(access_token)){
                    startActivity(new Intent(AppStartActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(AppStartActivity.this, LoginActivity.class));
                }

            }
        }, 300);
    }


}