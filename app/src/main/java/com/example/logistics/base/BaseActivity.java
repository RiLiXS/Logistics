package com.example.logistics.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;


import com.example.logistics.utils.ActivityLifeHelper;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jyx
 * @description: 基础activity
 * @date :12/23/20
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbind;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用 横屏

        //设置布局
        setContentView(initLayout());
        mUnbind=ButterKnife.bind(this);
        //初始化控件
        initView();
        //设置数据
        initData();

    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mUnbind = ButterKnife.bind(this);

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mUnbind = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mUnbind = ButterKnife.bind(this);
    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract int initLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置数据
     */
    protected abstract void initData();
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.init();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mUnbind != null) {
            mUnbind.unbind();
        }
        if (ImmersionBar.with(this) != null) //一个Activity只有一个ImmersionBar对象, 即使在子类初始化, 在这里也能销毁.
            ImmersionBar.with(this).destroy();  //在BaseActivity里销毁


        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        ActivityLifeHelper.getInstance().removeActivity(this);
    }



}
