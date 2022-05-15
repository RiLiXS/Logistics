package com.example.logistics;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import androidx.multidex.MultiDex;

import com.example.logistics.utils.ACache;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.Random;

/**
 * @author jyx
 * @description:
 * @date :2020-10-14
 */
public class App extends Application {

    private static Context context;
    private static Random random = null;
    private static ACache mACache;
    public static Handler mHandler;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        mACache = ACache.get(context);  // 本地缓存 data目录下
        mHandler = new Handler();
        // 初始化MultiDex
        MultiDex.install(this);
        initLoading();//初始化loading


    }

    private void initLoading() {
        StyleManager s = new StyleManager();

//在这里调用方法设置s的属性
//code here...
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true);

        LoadingDialog.initStyle(s);
    }

    public static synchronized Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    public static ACache getACache() {
        if (mACache == null) {
            mACache = ACache.get(context);
        }
        return mACache;
    }

}
