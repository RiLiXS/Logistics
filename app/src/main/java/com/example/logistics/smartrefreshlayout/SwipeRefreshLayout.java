
package com.example.logistics.smartrefreshlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.logistics.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * @author : jyx
 * @description: 自定义刷新头部
 * @date :12/24/20
 */
public class SwipeRefreshLayout extends SmartRefreshLayout {
    public boolean isAutoRefresh = true;//是否自动刷新 (修改时:需要在构造方法前使用)

    BaseHeadRefresh headRefresh;
    OnRefreshListener mOnRefreshListener;
    boolean isFirst = true;
    ClassicsHeader header;

    public SwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (getBackground() == null) { //添加默认背景色
            setBackgroundResource(R.color.transparent);
        }

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // TODO: 12/24/20 自定义刷新头部用这个
        // headRefresh = new BaseHeadRefresh(context);
        // headRefresh.setLayoutParams(layoutParams);
        // addView(headRefresh, 0);
        header = new ClassicsHeader(context);
        header.setLayoutParams(layoutParams);
        addView(header, 0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefresh);
        isAutoRefresh = typedArray.getBoolean(R.styleable.SwipeRefresh_is_auto_refresh, true);

        if (isAutoRefresh) {
            autoRefresh();
        } else {
            isFirst = false;
            isAutoRefresh = true;
        }
    }


    @Override
    public boolean autoRefresh() {
        return autoRefresh(50, 100, 1, false);
    }

    public void setRefreshing(boolean isRefreshing) {
        super.finishRefresh(true);

    }

    public boolean isRefreshing() {

        return false;
    }

    //设置颜色
    public void setColorSchemeResources(int a, int b, int c, int d) {

    }



    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
        super.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (isFirst) {
                    isFirst = false;
                    setRefreshing(true);
                    return;
                }
                mOnRefreshListener.onRefresh();
            }
        });

    }

    public interface OnRefreshListener {
        public void onRefresh();
    }
}