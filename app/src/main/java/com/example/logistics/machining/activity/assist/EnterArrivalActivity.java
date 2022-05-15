package com.example.logistics.machining.activity.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.logistics.App;
import com.example.logistics.LoginActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BadItemListBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.bean.QualitySubmitBean;
import com.example.logistics.bean.RequestArrivalGoods;
import com.example.logistics.bean.RequestBadItemListBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.TimeBean;
import com.example.logistics.bean.WorkCenterBadItem;
import com.example.logistics.dialog.DefectiveDialog;
import com.example.logistics.home.adapter.SpecialDefectAdapter;
import com.example.logistics.machining.activity.ProduceJobActivity;
import com.example.logistics.machining.adapter.OutDefectAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.example.logistics.utilsf.SelfPlanTimeUtil;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
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
 * 录入到货
 */
public class EnterArrivalActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_defect)//缺陷列表
    RecyclerView rcy_defect;
    @BindView(R.id.et_time_create)//到货日期
    TextView et_time_create;
    @BindView(R.id.et_process_multiple)//工序倍数
    TextView et_process_multiple;
    @BindView(R.id.et_arrival_num)//到货数量大片
    EditText et_arrival_num;
    @BindView(R.id.et_arrival_little)//到货数量小片
    TextView et_arrival_little;
    @BindView(R.id.et_defect_num)//不合格数量大片
    TextView et_defect_num;
    @BindView(R.id.et_defect_little)//不合格数小片
    TextView et_defect_little;
    @BindView(R.id.tv_submit)//提交
    TextView tv_submit;
    private LoadingDialog loadingDialog;

    private OutDefectAdapter outDefectAdapter;
    private String processId = "";
    private String processMultiple="0";
    private String out_work_id="";

    @Override
    protected int initLayout() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return R.layout.activity_enter_arrival;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        tv_submit.setOnClickListener(this);
        img_return.setOnClickListener(this);
        et_time_create.setOnClickListener(this);
        processId = getIntent().getStringExtra(C.mach.PROCESS_ID);
        processMultiple = getIntent().getStringExtra(C.mach.PROCESS_MULTIPLE);
        out_work_id = getIntent().getStringExtra(C.mach.OUT_WORK_ID);
        initRcy();//初始化列表

        getDefectList();//获取不良品
        et_process_multiple.setText(processMultiple+"");
        et_arrival_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)){
                    et_arrival_little.setText(String.valueOf(Integer.valueOf(String.valueOf(s))*Integer.valueOf(processMultiple))+"");
                }
            }
        });

    }


    @Override
    protected void initData() {
        outDefectAdapter.setOnAddClickListener(new OutDefectAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int position) {
                totalNum();
            }

            @Override
            public void onReduceClick(int position) {
                totalNum();
            }

            @Override
            public void onEditClick(int position) {
                totalNum();
            }
        });
    }

    /**
     * 合计数量
     */
    private void totalNum() {
        List<BadItemListBean> list = outDefectAdapter.getList();
        int s = 0;
        for (int i = 0; i < list.size(); i++) {
            int num = Integer.parseInt(list.get(i).getNum());
            s = s + num;
        }

        et_defect_num.setText(s+"");
        et_defect_little.setText(String.valueOf(Integer.valueOf(et_defect_num.getText().toString())*Integer.valueOf(processMultiple)));
    }

    private void initRcy() {
        outDefectAdapter = new OutDefectAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_defect.setLayoutManager(linearLayoutManager);
        rcy_defect.setAdapter(outDefectAdapter);
    }


    private void getDefectList() {
        if (TextUtils.isEmpty(processId)) return;
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/process/process/badItemList/" + processId)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestBadItemListBean requestBadItemListBean = new Gson().fromJson(data, RequestBadItemListBean.class);
                            if (requestBadItemListBean.getCode() == 1) {
                                Toast.makeText(EnterArrivalActivity.this, "" + requestBadItemListBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                List<BadItemListBean> badItemListBeanData = requestBadItemListBean.getData();
                                if (badItemListBeanData != null) {
                                    outDefectAdapter.initList(badItemListBeanData);
                                    outDefectAdapter.notifyDataSetChanged();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                getSubmit();
                break;
            case R.id.img_return:
                finish();
                break;
            case R.id.et_time_create:
                SelfPlanTimeUtil.getInstance(this).showBirthdayDate(this, "时间选择", 2, 5);
                break;
        }
    }


    @Subscribe  //订阅事件
    public void onEventMainThread(TimeBean evBean) {
        switch (evBean.getType()) {
            case 5:
                et_time_create.setText("" + evBean.getTime());
                break;
        }


    }


    private void getSubmit() {
        if (TextUtils.isEmpty(et_time_create.getText().toString())||TextUtils.isEmpty(et_arrival_num.getText().toString())){
            ViewShowUtils.showShortToast(EnterArrivalActivity.this,"到货大片数或到货日期不能为空");
            return;
        }
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .show();
        List<BadItemListBean> list = outDefectAdapter.getList();
        List<WorkCenterBadItem> workCenterBadItems=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            workCenterBadItems.add(i,new WorkCenterBadItem(list.get(i).getBadItemId(),list.get(i).getItemName(),list.get(i).getNum()));
        }
        RequestArrivalGoods arrivalGoods=new RequestArrivalGoods();
        arrivalGoods.setArrivalNum(Integer.valueOf(et_arrival_little.getText().toString()+""));
        arrivalGoods.setArrivalTime(et_time_create.getText().toString()+"");
        arrivalGoods.setBadNum(Integer.valueOf(et_defect_little.getText().toString()+""));
        arrivalGoods.setBigArrivalNum(Integer.valueOf(et_arrival_num.getText().toString()+""));
        arrivalGoods.setBigBadNum(Integer.valueOf(et_defect_num.getText().toString()+""));
        arrivalGoods.setWorkCenterBadItemList(workCenterBadItems);
        arrivalGoods.setOutsourceWorkOrderdId(Integer.valueOf(out_work_id));

        String s = JSON.toJSONString(arrivalGoods);
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(arrivalGoods));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/plansp/outsourcearrival/arrivalGoods")
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
                                SubmitBean submitBean = new Gson().fromJson(string, SubmitBean.class);
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
        EventBus.getDefault().unregister(this);
    }
}