package com.example.logistics.machining.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.ReasonReportBean;
import com.example.logistics.machining.adapter.MachiningMatterAddAdapter;
import com.example.logistics.machining.adapter.ReasonReportAdapter;
import com.example.logistics.utils.AppUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 原因上报
 */
public class ReasonReportActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.linear_reason)//原因列表
    LinearLayoutCompat linear_reason;
    @BindView(R.id.tv_add)//新增
    TextView tv_add;
    @BindView(R.id.img_return)//返回
    ImageView img_return;
    private  ReasonReportAdapter reasonReportAdapter;
    private List<ReasonReportBean> list=new ArrayList<>();
    @Override
    protected int initLayout() {
        return R.layout.activity_reason_report;
    }

    @Override
    protected void initView() {
        tv_add.setOnClickListener(this);
        img_return.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        View reason_list=View.inflate(ReasonReportActivity.this,R.layout.item_reason_list,null);
        View   tv_delete=reason_list.findViewById(R.id.tv_delete);
        tv_delete.setVisibility(View.GONE);
        tv_delete.setTag(new Date().getTime()+"");
        linear_reason.addView(reason_list);
    }

@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add://添加条目
               AppUtil.hintKeyBoard(ReasonReportActivity.this);
                View tab1 = View.inflate(ReasonReportActivity.this,R.layout.item_reason_list, null);
                View tv_delete = tab1.findViewById(R.id.tv_delete);
                tv_delete.setTag(new Date().getTime() + "");
                tv_delete.setOnClickListener(btn -> removeChild((String) btn.getTag()));
                linear_reason.addView(tab1);
                AppUtil.hintKeyBoard(ReasonReportActivity.this);
                break;
            case R.id.img_return://返回
                finish();
                break;
        }
    }

    /**
     * 删除条目
     * @param tag
     */
    private void removeChild(String tag) {

            for (int i = 0; i < linear_reason.getChildCount(); i++) {
                View childAt = linear_reason.getChildAt(i);
                String tag1 = (String) childAt.findViewById(R.id.tv_delete).getTag();
                if (tag.equals(tag1)) {
                    linear_reason.removeViewAt(i);
                }

            }
    }


}