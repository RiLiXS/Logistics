package com.example.logistics.machining.activity.assist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.logistics.App;
import com.example.logistics.LoginActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BarcodeBean;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.CodeBean;
import com.example.logistics.bean.FeedingSubmitBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.OutAssisOrderBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestSupplierBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.SupplierBean;
import com.example.logistics.bean.TimeBean;
import com.example.logistics.machining.activity.FeedingAddActivity;
import com.example.logistics.machining.adapter.OutAssistListAdapter;
import com.example.logistics.machining.adapter.TurnOverListAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.plan.activity.SelfPlanTaskActivity;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.example.logistics.utilsf.SelfPlanTimeUtil;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

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
 * ???????????????
 */
public class OutAssistOrderActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.et_process_search)//????????????
    EditText et_process_search;
    @BindView(R.id.img_process_search)//???????????????
    ImageView img_process_search;
    @BindView(R.id.et_search)//????????????
    TextView et_search;
    @BindView(R.id.et_matter_search)//????????????
    TextView et_matter_search;
    @BindView(R.id.et_procedure_now)//??????
    Spinner et_procedure_now;
    @BindView(R.id.et_plan_num)//?????????
    TextView et_plan_num;
    @BindView(R.id.et_out_task)//????????????
    TextView et_out_task;
    @BindView(R.id.tv_task_num)//???????????????
    TextView tv_task_num;
    @BindView(R.id.et_task_little)//???????????????
    TextView et_task_little;
    @BindView(R.id.tv_work_report)//????????????
    TextView tv_work_report;
    @BindView(R.id.tv_process_multiple)//????????????
    TextView tv_process_multiple;
    @BindView(R.id.tv_send_goods)//???????????????
    TextView tv_send_goods;
    @BindView(R.id.tv_send_goods_little)//???????????????
    TextView tv_send_goods_little;
    @BindView(R.id.et_out_supplier)//?????????
    Spinner et_out_supplier;
    @BindView(R.id.et_out_goods)//????????????
    TextView et_out_goods;
    @BindView(R.id.tv_submit)//??????
    TextView tv_submit;
    private LoadingDialog loadingDialog;
    private boolean isFirst = true;
    private CodeBean codeBean = null;
    private ArrayList<String> processList = new ArrayList<>();
    private ArrayList<String> supplierList = new ArrayList<>();
    private int processPosition = 0;
    private int supplierPosition = 0;
    private List<BarcodeBean> processPriceVos = null;
    private List<SupplierBean> supplierPriceVos = null;
    private int REQUEST_CODE_SCAN = 100;


    @Override
    protected int initLayout() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return R.layout.activity_out_assist_order;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        tv_submit.setOnClickListener(this);
        img_return.setOnClickListener(this);
        et_out_goods.setOnClickListener(this);
        img_process_search.setOnClickListener(this);
        getGoodsSpinner();//?????????


    }


    @Override
    protected void initData() {
        et_process_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFirst == false) return;
                if (!TextUtils.isEmpty(s)) {
                    getBarcode(String.valueOf(s));//?????????????????????
                    isFirst = false;
                    et_process_search.setEnabled(false);
                }
            }
        });
    }

    private void getGoodsSpinner() {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/mdmcustomer/supplier/list")
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestSupplierBean requestSupplierBean = new Gson().fromJson(data, RequestSupplierBean.class);
                            if (requestSupplierBean.getCode() == 1) {
                                Toast.makeText(OutAssistOrderActivity.this, "" + requestSupplierBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                List<SupplierBean> requestSupplierBeanData = requestSupplierBean.getData();
                                if (requestSupplierBeanData.size() == 0) return;
                                supplierPriceVos = requestSupplierBeanData;
                                getSupplier(requestSupplierBeanData);

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getSupplier(List<SupplierBean> requestSupplierBeanData) {
        supplierList.clear();
        for (int i=0;i<requestSupplierBeanData.size();i++){
            supplierList.add(requestSupplierBeanData.get(i).getCustomerName()+"");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, supplierList);
        et_out_supplier.setAdapter(adapter);
        et_out_supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                supplierPosition = position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getBarcode(String content) {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/barcode/" + content)
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestBarcodeBean requestBarcodeBean = new Gson().fromJson(data, RequestBarcodeBean.class);
                            if (requestBarcodeBean.getCode() == 1) {
                                Toast.makeText(OutAssistOrderActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                codeBean = requestBarcodeBean.getData();
                                if (codeBean != null) {
                                    getCode(codeBean);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }

    }

    private void getCode(CodeBean codeBean) {
        et_search.setText(TextUtils.isEmpty(codeBean.getPlanNo()) ? "" : codeBean.getPlanNo() + "");
        et_matter_search.setText(TextUtils.isEmpty(codeBean.getMaterialsCodeName()) ? "" : codeBean.getMaterialsCodeName() + "");
        tv_task_num.setText(TextUtils.isEmpty(codeBean.getLocation()) ? "" : codeBean.getLocation() + "");
        et_plan_num.setText(TextUtils.isEmpty(codeBean.getPlanNum()) ? "0" : MathUtils.getNum(codeBean.getPlanNum()) + "");
        et_out_task.setText(TextUtils.isEmpty(codeBean.getMultiple()) ? "1" : MathUtils.getNum(codeBean.getMultiple()) + "");
        tv_work_report.setText(Integer.valueOf(codeBean.getIsFinish()) == 0 ? "?????????" : "?????????");
        et_task_little.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(codeBean.getNum()) + "");
        tv_task_num.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(MathUtils.getScale3(codeBean.getNum(), codeBean.getMultiple())));
        processPriceVos = codeBean.getProcessPriceVos();
        processList.clear();
        for (int i = 0; i < processPriceVos.size(); i++) {
            processList.add((i + 1) + "-" + processPriceVos.get(i).getProcessName() + "-" + MathUtils.getNum(processPriceVos.get(i).getReportQty()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, processList);
        et_procedure_now.setAdapter(adapter);
        et_procedure_now.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                processPosition = position;

                tv_process_multiple.setText(processPriceVos.get(position).getMultiple() + "");

                if (position == 0) {
                    tv_send_goods_little.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getSubtract2(codeBean.getNum(), processPriceVos.get(position).getReportQty()) + "");
                    tv_send_goods.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(MathUtils.getScale3(tv_send_goods_little.getText().toString(), processPriceVos.get(position).getMultiple())));
                } else {
                    tv_send_goods_little.setText(MathUtils.getSubtract2(processPriceVos.get(position - 1).getReportQty(), MathUtils.getSum2(processPriceVos.get(position).getReportQty(), processPriceVos.get(position).getRejectQty())) + "");
                    tv_send_goods.setText(TextUtils.isEmpty(tv_send_goods_little.getText().toString()) ? "0" : MathUtils.getNum(MathUtils.getScale3(tv_send_goods_little.getText().toString(), processPriceVos.get(position).getMultiple())));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.close();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://??????
                finish();
                break;
            case R.id.et_out_goods://????????????
                SelfPlanTimeUtil.getInstance(this).showBirthdayDate(this, "????????????", 2, 1);
                break;
            case R.id.img_process_search://???????????????
                Intent intent = new Intent(OutAssistOrderActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.tv_submit://??????
                    getSubmit();
                break;
        }
    }

    private void getSubmit() {
        if (codeBean==null){
            ViewShowUtils.showShortToast(OutAssistOrderActivity.this,"????????????");
            return;
        }
        loadingDialog.setLoadingText("?????????")
                .setSuccessText("????????????")
                .show();
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        OutAssisOrderBean outAssisOrderBean = new OutAssisOrderBean();
        outAssisOrderBean.setBarcode(TextUtils.isEmpty(codeBean.getBarcode()) ? "" : codeBean.getBarcode());
        outAssisOrderBean.setCustomerId(supplierPriceVos.get(supplierPosition).getId());
        outAssisOrderBean.setCustomerName(supplierPriceVos.get(supplierPosition).getCustomerName());
        outAssisOrderBean.setOutsourceDeliveryTime(TextUtils.isEmpty(et_out_goods.getText().toString()) ? "" : et_out_goods.getText().toString());
        outAssisOrderBean.setProcessSortNo(Integer.valueOf(codeBean.getProcessSortNo()));
        String s = JSON.toJSONString(outAssisOrderBean);
        if (!TextUtils.isEmpty(access_token)) {
            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(outAssisOrderBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/plansp/outsourceworkorderd")
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "onFailure: ??????");
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
                            //????????????
                            if (string.contains("?????????????????????")) {
                                UserLocalData.closeUser();
                                App.getAppContext().startActivity(new Intent(App.getAppContext(), LoginActivity.class));
                                Toast.makeText(App.getAppContext(), "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                SubmitBean submitBean = new Gson().fromJson(string, SubmitBean.class);
                                if (submitBean == null) return;
                                if (submitBean.getCode() == 0) {
                                    Toast.makeText(App.getAppContext(), "????????????", Toast.LENGTH_SHORT).show();
                                    loadingDialog.loadSuccess();
                                    finish();

                                } else {
                                    Toast.makeText(App.getAppContext(), ""+submitBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    loadingDialog.loadFailed();
                                }
                            }

                        }
                    });


                    Log.i(TAG, "onResponse: ?????? " + string);
                }
            });
        }

    }

    @Subscribe  //????????????
    public void onEventMainThread(TimeBean evBean) {
        switch (evBean.getType()) {
            case 1:
                et_out_goods.setText("" + evBean.getTime());
                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ???????????????/????????????
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("??????????????????" + content);
                et_process_search.setText(content + "");
                getBarcode(content);
            }
        }
    }
}