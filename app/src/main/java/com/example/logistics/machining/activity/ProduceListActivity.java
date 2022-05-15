package com.example.logistics.machining.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;

import butterknife.BindView;

/**
 * 产出列表
 */
public class ProduceListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.tv_add)//产出报工
    TextView tv_add;
    @BindView(R.id.et_search)//搜索
    EditText et_search;
    @BindView(R.id.img_search)
    ImageView img_search;
    @BindView(R.id.refreshLayout)//刷新
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rcy_produceList)//产出列表
    RecyclerView rcy_produceList;

    @Override
    protected int initLayout() {
        return R.layout.activity_produce_list;
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
            case R.id.tv_add://产出报工
                startActivity(new Intent(this, ProduceJobActivity.class));
                break;
            case R.id.img_return://返回上层
                finish();
                break;
        }
    }
}