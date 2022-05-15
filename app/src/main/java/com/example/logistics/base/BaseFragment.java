package com.example.logistics.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jyx
 * @description:
 * @date :2020-10-12
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbind;
    //是否可见状态
    private boolean isVisible;
    //获取TAG的fragment名称
    protected final String TAG = this.getClass().getSimpleName();
    public Context context;


    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        context = ctx;
    }
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("打开了界面----", this.getClass().getSimpleName());
    }

    /**
     * The Fragment's UI is just a simple text view showing its instance
     * number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(initLayout(), container, false);
        initView(rootView);
        initData(context);
        return rootView;

    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract int initLayout();

    /**
     * 初始化控件
     *
     * @param view 布局View
     */
    protected abstract void initView(final View view);

    /**
     * 初始化、绑定数据
     *
     * @param mContext 上下文
     */
    protected abstract void initData(Context mContext);



    @Override
    public void onResume() {
        super.onResume();
      //  MobclickAgent.onPageStart(getClass().getSimpleName());

    }

    @Override
    public void onPause() {
        super.onPause();
       // MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbind = ButterKnife.bind(this, view);
    }

    /**
     * 保证同一按钮在1秒内只响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮的最小间隔，目前为1000
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(v);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbind != null)
            mUnbind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered){
            EventBus.getDefault().unregister(true);
        }

    }








    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){

    }

    protected void onInvisible(){}


}
