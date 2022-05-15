package com.example.logistics.machining.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;

import butterknife.BindView;

/**
 * 拆分列表
 */
public class SplitListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.tv_add)//新增投料
    TextView tv_add;
    @BindView(R.id.et_search)//搜索
    EditText et_search;
    @BindView(R.id.img_search)
    ImageView img_search;
    @BindView(R.id.refreshLayout)//刷新
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rcy_feedList)//投料列表
    RecyclerView rcy_feedList;

    @Override
    protected int initLayout() {
        return R.layout.activity_split_list;
    }

    @Override
    protected void initView() {
        tv_add.setOnClickListener(this);
        img_return.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add://新增投料
                startActivity(new Intent(this, SplitActivity.class));
                break;
            case R.id.img_return://返回上层
                finish();
                break;
        }
    }
}