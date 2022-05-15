package com.example.logistics.home.activity.defects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.logistics.App;
import com.example.logistics.LoginActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.ControlListBean;
import com.example.logistics.bean.DefectDealBean;
import com.example.logistics.bean.FeedingSubmitBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RecordsBadItemListBean;
import com.example.logistics.bean.RejectDisposeDtl;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.TotalSubmitBean;
import com.example.logistics.home.adapter.DefectProductDealAdapter;
import com.example.logistics.home.adapter.DefectProductListAdapter;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 不合格品处理
 */
public class DefectProductDealActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//不合格品任务列表
    RecyclerView rcy_task;
    @BindView(R.id.tv_submit)//提交
    TextView tv_submit;
    @BindView(R.id.et_deal_opinions)//处理意见
    EditText et_deal_opinions;
    @BindView(R.id.rg_group)//处理方式
    RadioGroup rg_group;
    @BindView(R.id.rb_rework)//返工
    RadioButton rb_rework;
    @BindView(R.id.rb_repair)//返修
    RadioButton rb_repair;
    @BindView(R.id.rb_acceptance)//让步接收
    RadioButton rb_acceptance;
    @BindView(R.id.rb_scarp)//报废
    RadioButton rb_scarp;
    private LoadingDialog loadingDialog;
    private List<RecordsBadItemListBean> productListAdapterList = null;
    private int dealNun = 1;
    private DefectProductDealAdapter defectProductDealAdapter;


    @Override
    protected int initLayout() {
        return R.layout.activity_defect_product_deal;
    }

    @Override
    protected void initView() {
        productListAdapterList = (List<RecordsBadItemListBean>) getIntent().getSerializableExtra(C.task.DEFECT_BEAN);
        img_return.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        loadingDialog = new LoadingDialog(this);
        initRcy();//初始化列表
    }


    @Override
    protected void initData() {
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_rework:
                        dealNun = 1;
                        break;
                    case R.id.rb_repair:
                        dealNun = 2;
                        break;
                    case R.id.rb_acceptance:
                        dealNun = 3;
                        break;
                    case R.id.rb_scarp:
                        dealNun = 4;
                        break;
                }
            }
        });
    }


    private void initRcy() {
        defectProductDealAdapter = new DefectProductDealAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(defectProductDealAdapter);
        if (productListAdapterList == null) return;
        defectProductDealAdapter.addList(productListAdapterList);
        defectProductDealAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://返回上一层
                finish();
                break;
            case R.id.tv_submit://提交
                getSubmit();//提交
                break;
        }
    }

    private void getSubmit() {
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .show();
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        List<RecordsBadItemListBean> defectProductDealAdapterList = defectProductDealAdapter.getList();
        List<RejectDisposeDtl> rejectDisposeDtls = new ArrayList<>();
        for (int i = 0; i < defectProductDealAdapterList.size(); i++) {
            if (defectProductDealAdapterList.get(i).isPeople()) {
                rejectDisposeDtls.add(i, new RejectDisposeDtl(defectProductDealAdapterList.get(i).getWorkCenterBadItemId(), "1"));
            } else {
                rejectDisposeDtls.add(i, new RejectDisposeDtl(defectProductDealAdapterList.get(i).getWorkCenterBadItemId(), "0"));
            }
        }
        DefectDealBean defectDealBean = new DefectDealBean();
        defectDealBean.setActionResults(dealNun+"");
        defectDealBean.setHandlingSuggestion(TextUtils.isEmpty(et_deal_opinions.getText().toString()) ? "" : et_deal_opinions.getText().toString());
        defectDealBean.setRejectDisposeDtlList(rejectDisposeDtls);


        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(defectDealBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/workCenter/rejectdispose")
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "onFailure: 失败");
                    loadingDialog.loadFailed();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //代码逻辑
                            if (string.contains("用户凭证已过期")) {
                                UserLocalData.closeUser();
                                App.getAppContext().startActivity(new Intent(App.getAppContext(), LoginActivity.class));
                                Toast.makeText(App.getAppContext(), "用户凭证已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                TotalSubmitBean submitBean = new Gson().fromJson(string, TotalSubmitBean.class);
                                if (submitBean == null) return;
                                if (submitBean.getCode() == 0) {
                                    Toast.makeText(App.getAppContext(), "提交成功", Toast.LENGTH_SHORT).show();
                                    loadingDialog.loadSuccess();
                                    finish();

                                } else {
                                    Toast.makeText(App.getAppContext(), "" + submitBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    loadingDialog.loadFailed();
                                }
                            }

                        }
                    });


                    Log.i(TAG, "onResponse: 成功 " + string);
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.close();
    }
}