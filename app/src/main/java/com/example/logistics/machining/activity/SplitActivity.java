package com.example.logistics.machining.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.logistics.App;
import com.example.logistics.LoginActivity;
import com.example.logistics.MainActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BarcodeBean;
import com.example.logistics.bean.CodeBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestMergeBean;
import com.example.logistics.bean.SplitBean;
import com.example.logistics.bean.SplitSubmitBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.ViceCard;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.AesUtils;
import com.example.logistics.utils.AppUtil;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
 * 拆分
 */
public class SplitActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.img_task_code)//扫码
    ImageView img_task_code;
    @BindView(R.id.linear_split)//拆分列表
    LinearLayoutCompat linear_split;
    @BindView(R.id.tv_add)//新增
    TextView tv_add;
    @BindView(R.id.et_master_card)//主卡单号
    EditText et_master_card;
    @BindView(R.id.et_matter_type)//物料类别
    EditText et_matter_type;
    @BindView(R.id.et_matter_name)//物料名称
    EditText et_matter_name;
    @BindView(R.id.et_address)//地点
    EditText et_address;
    @BindView(R.id.et_procedure_now)//当前工序
    Spinner et_procedure_now;
    @BindView(R.id.et_task_num)//任务数
    EditText et_task_num;
    @BindView(R.id.et_planNum)//计划数
    EditText et_planNum;
    @BindView(R.id.et_trial)//试制
    EditText et_trial;
    @BindView(R.id.et_completion_status)//完工状态
    EditText et_completion_status;
    @BindView(R.id.et_qualifiedNum)//合格数
    EditText et_qualifiedNum;
    @BindView(R.id.et_total_splitNum)//拆分总数
    EditText et_total_splitNum;
    @BindView(R.id.et_max_splitNum)//单卡最大数
    EditText et_max_splitNum;
    @BindView(R.id.tv_submit)//提交
    TextView tv_submit;
    @BindView(R.id.tv_details)//流程卡详情
    TextView tv_details;
    private int REQUEST_CODE_SCAN = 100;
    private CodeBean codeBean = null;
    private List<BarcodeBean> processPriceVos = null;
    private ArrayList<String> processList = new ArrayList<>();
    private int processPosition = 0;
    private LoadingDialog loadingDialog;
    private boolean isFirst=true;
    @Override
    protected int initLayout() {
        return R.layout.activity_split;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        img_return.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        img_task_code.setOnClickListener(this);
        tv_details.setOnClickListener(this);
        et_max_splitNum.setSingleLine();
        et_task_num.setSingleLine();
        et_total_splitNum.setSingleLine();
        et_qualifiedNum.setSingleLine();
        et_completion_status.setSingleLine();
        et_trial.setSingleLine();
        et_planNum.setSingleLine();
        et_address.setSingleLine();
        et_matter_name.setSingleLine();
        et_matter_type.setSingleLine();
        et_master_card.setSingleLine();
        et_master_card.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFirst==false)return;
                if (!TextUtils.isEmpty(s)){
                    getBarcode(String.valueOf(s));//查询流程卡信息
                    isFirst=false;
                    et_master_card.setEnabled(false);
                }
            }
        });

    }

    @Override
    protected void initData() {
//        View reason_list=View.inflate(SplitActivity.this,R.layout.item_split_list,null);
//        View   tv_delete=reason_list.findViewById(R.id.tv_delete);
//        tv_delete.setVisibility(View.GONE);
//        tv_delete.setTag(new Date().getTime()+"");
//        linear_split.addView(reason_list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_task_code://扫码
                Intent intent = new Intent(SplitActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.tv_submit://提交
                getSubmit();
                break;
            case R.id.tv_details://流程卡详情
                if (codeBean == null) {
                    ViewShowUtils.showShortToast(SplitActivity.this, "请先扫码");
                    return;
                }
                Intent intent2 = new Intent(SplitActivity.this, ProcessCardDetailActivity.class);
                intent2.putExtra(C.mach.PROCESS_ID, codeBean.getBarcode()+"");
                startActivity(intent2);//跳转流程卡详情
                break;
            case R.id.img_return://返回上层
                finish();
                break;
            case R.id.tv_add://添加条目
//                AppUtil.hintKeyBoard(SplitActivity.this);
//                View tab1 = View.inflate(SplitActivity.this,R.layout.item_split_list, null);
//                View tv_delete = tab1.findViewById(R.id.tv_delete);
//                tv_delete.setTag(new Date().getTime() + "");
//                tv_delete.setOnClickListener(btn -> removeChild((String) btn.getTag()));
//                linear_split.addView(tab1);
//                AppUtil.hintKeyBoard(SplitActivity.this);
                break;
        }
    }

    /**
     * 提交
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getSubmit() {
        if (TextUtils.isEmpty(et_total_splitNum.getText().toString())||TextUtils.isEmpty(et_max_splitNum.getText().toString())){
            ViewShowUtils.showShortToast(SplitActivity.this,"拆分总数和单卡最大数不能为空");
            return;
        }
        if (Integer.valueOf(et_total_splitNum.getText().toString())<=0||Integer.valueOf(et_max_splitNum.getText().toString())<=0){
            ViewShowUtils.showShortToast(SplitActivity.this,"拆分总数和单卡最大数必须大于等于1");
            return;
        }

        if (codeBean==null){
            ViewShowUtils.showShortToast(SplitActivity.this,"请先扫码");
            return;
        }
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .show();

        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        SplitBean splitBean=new SplitBean();
        splitBean.setCardNum(TextUtils.isEmpty(et_max_splitNum.getText().toString())?"1":et_max_splitNum.getText().toString()+"");
        splitBean.setNum(TextUtils.isEmpty(et_total_splitNum.getText().toString())?"1":et_total_splitNum.getText().toString()+"");
        splitBean.setProcessCardId(TextUtils.isEmpty(codeBean.getProcessCardId()) ? "" : codeBean.getProcessCardId());
        splitBean.setSeq(TextUtils.isEmpty(processPriceVos.get(processPosition).getProcessSeq()) ? "" : processPriceVos.get(processPosition).getProcessSeq());
        if (!TextUtils.isEmpty(access_token)) {
            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(splitBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/plansp/processcard/splitProcessCard")
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
                                SplitSubmitBean submitBean = new Gson().fromJson(string, SplitSubmitBean.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("扫描结果为：" + content);

                getBarcode(content);//查询流程卡信息
                et_master_card.setEnabled(false);
            }
        }
    }
    private void getBarcode(String content) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/barcode/" + content)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestBarcodeBean requestBarcodeBean = new Gson().fromJson(data, RequestBarcodeBean.class);
                            if (requestBarcodeBean.getCode() == 1) {
                                Toast.makeText(SplitActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

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
        et_master_card.setText(TextUtils.isEmpty(codeBean.getBarcode()) ? "" : codeBean.getBarcode() + "");
        et_matter_name.setText(TextUtils.isEmpty(codeBean.getMaterialsCodeName()) ? "" : codeBean.getMaterialsCodeName() + "");
//        // et_address.setText(TextUtils.isEmpty(codeBean.getLocation())?"":codeBean.getLocation()+"");
//        //  et_procedure_name.setText(TextUtils.isEmpty(codeBean.getProcessPriceVos().get(0).getProcessName())?"":codeBean.getProcessPriceVos().get(0).getProcessName()+"");
        et_task_num.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(codeBean.getNum()));
        et_planNum.setText(TextUtils.isEmpty(codeBean.getPlanNum()) ? "0" : MathUtils.getNum(codeBean.getPlanNum()));
        et_completion_status.setText(Integer.valueOf(codeBean.getIsFinish()) == 0 ? "未完工" : "已完工");
        et_trial.setText(Integer.valueOf(codeBean.getIsTrial()) == 0 ? "非试制" : "试制");
        processPriceVos = codeBean.getProcessPriceVos();
        processList.clear();
        for (int i = 0; i < processPriceVos.size(); i++) {
            processList.add(processPriceVos.get(i).getProcessName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, processList);
        et_procedure_now.setAdapter(adapter);
        et_procedure_now.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                processPosition = position;

                et_qualifiedNum.setText(MathUtils.getNum(processPriceVos.get(processPosition).getReportQty()));//合格数
                //  Toast.makeText(TaskOverActivity.this,""+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}