package com.example.logistics.machining.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BarcodeBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.ProcessDetailBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestProcessDetailBean;
import com.example.logistics.bean.WorkCenterListBean;
import com.example.logistics.machining.adapter.MachiningMatterAddAdapter;
import com.example.logistics.machining.adapter.ProcessCardDetailAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;


public class ProcessCardDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.process_rcy)//流程卡列表
    RecyclerView process_rcy;
    @BindView(R.id.img_return)//返回上一层
    ImageView img_return;
    @BindView(R.id.tv_process_card)//流程卡号
    TextView tv_process_card;
    @BindView(R.id.tv_number_task)//任务数
    TextView tv_number_task;
    @BindView(R.id.et_matter_name)//物料名称
    TextView et_matter_name;
    @BindView(R.id.tv_completion_status)//完工状态
    TextView tv_completion_status;
    private String barcode;
    private ProcessCardDetailAdapter  processCardDetailAdapter;
    @Override
    protected int initLayout() {
        return R.layout.activity_process_card_detail;
    }

    @Override
    protected void initView() {
        barcode = getIntent().getStringExtra(C.mach.PROCESS_ID);
        img_return.setOnClickListener(this);
        initProcessList();
        getProcessCard(barcode);
    }

    private void getProcessCard(String barcode) {
        if (TextUtils.isEmpty(barcode))return;
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/getCardWorkByBarcode/" + barcode)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestProcessDetailBean requestProcessDetailBean = new Gson().fromJson(data, RequestProcessDetailBean.class);
                            if (requestProcessDetailBean.getCode() == 1) {
                                Toast.makeText(ProcessCardDetailActivity.this, "" + requestProcessDetailBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                ProcessDetailBean processDetailBeanData = requestProcessDetailBean.getData();
                                getCode(processDetailBeanData);
                                List<WorkCenterListBean> workCenterList = requestProcessDetailBean.getData().getWorkCenterList();
                                processCardDetailAdapter.initList(workCenterList);

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }

    }

    private void getCode(ProcessDetailBean processDetailBeanData) {
        if (processDetailBeanData==null)return;
        tv_process_card.setText(TextUtils.isEmpty(processDetailBeanData.getBarcode())?"":processDetailBeanData.getBarcode());
        tv_number_task.setText(TextUtils.isEmpty(processDetailBeanData.getNum())?"0":processDetailBeanData.getNum());
        et_matter_name.setText(TextUtils.isEmpty(processDetailBeanData.getMaterialName())?"":processDetailBeanData.getMaterialName());
        tv_completion_status.setText(Integer.valueOf(processDetailBeanData.getIsFinish()) == 0 ? "未完工" : "已完工");

    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化投料列表
     */
    private void initProcessList() {
        processCardDetailAdapter = new ProcessCardDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        process_rcy.setLayoutManager(linearLayoutManager);
        process_rcy.setAdapter(processCardDetailAdapter);
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