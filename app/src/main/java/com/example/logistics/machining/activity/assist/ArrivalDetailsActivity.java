package com.example.logistics.machining.activity.assist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.ArrivalDetailBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.OutWorkOrderBean;
import com.example.logistics.bean.RequestArrivalDetailBean;
import com.example.logistics.bean.RequestOutSourceWorkOrderBean;
import com.example.logistics.machining.adapter.ArrivalDetailsListAdapter;
import com.example.logistics.machining.adapter.OutAssistListAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 到货明细
 */
public class ArrivalDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//明细
    RecyclerView rcy_task;
    @BindView(R.id.refreshLayout)//刷新
    SwipeRefreshLayout refreshLayout;
    private ArrivalDetailsListAdapter arrivalDetailsListAdapter;
    private int page = 1;
    private String out_work_id="";

    @Override
    protected int initLayout() {
        return R.layout.activity_arrival_details;
    }

    @Override
    protected void initView() {
        out_work_id = getIntent().getStringExtra(C.mach.OUT_WORK_ID);

        initRcy();//初始化列表
        img_return.setOnClickListener(this);

    }

    @Override
    protected void initData() {
            getDetailsList();//
        /**
         * 刷新
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //请求数据
                getDetailsList();//


            }
        });
        /**
         * 加载更多
         */
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                page++;
                //请求数据
                getDetailsList();//

            }
        });
    }

    private void getDetailsList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/outsourcearrival/page")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .addParam("outsourceWorkOrderdId", out_work_id + "")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestArrivalDetailBean requestArrivalDetailBean = new Gson().fromJson(data, RequestArrivalDetailBean.class);
                            if (requestArrivalDetailBean.getCode() == 0) {
                                List<ArrivalDetailBean> arrivalDetailBeans = requestArrivalDetailBean.getData().getRecords();
                                if (arrivalDetailBeans == null) return;
                                if (page == 1) {
                                    arrivalDetailsListAdapter.initList(arrivalDetailBeans);
                                    refreshLayout.finishRefresh();

                                } else {
                                    arrivalDetailsListAdapter.addList(arrivalDetailBeans);
                                    refreshLayout.finishLoadMore();
                                }
                                arrivalDetailsListAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(ArrivalDetailsActivity.this, "" + requestArrivalDetailBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(ArrivalDetailsActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    private void initRcy() {
        arrivalDetailsListAdapter = new ArrivalDetailsListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(arrivalDetailsListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_return:
                finish();
                break;
        }
    }
}