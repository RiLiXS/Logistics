package com.example.logistics.home.activity.finall_test;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.logistics.bean.CodeBean;
import com.example.logistics.bean.ControlListBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.bean.QualitySubmitBean;
import com.example.logistics.bean.QualityTaskAddBean;
import com.example.logistics.bean.QualityTypeListBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestQualitySuccessBean;
import com.example.logistics.bean.RequestQualityTypeListBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.UserInfoBean;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.home.activity.special_test.SpecialInspectionActivity;
import com.example.logistics.machining.activity.FeedingAddActivity;
import com.example.logistics.machining.activity.MergeActivity;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;

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
 * 质检上报
 */
public class FinalInspectionActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)//返回
    ImageView img_return;
    @BindView(R.id.img_search)//扫码
    ImageView img_search;
    @BindView(R.id.et_search)//搜索
    EditText et_search;
    @BindView(R.id.tv_mach_num)//流程卡号
    TextView tv_mach_num;
    @BindView(R.id.tv_mach_name)//物料名称
    TextView tv_mach_name;
    @BindView(R.id.tv_mach_plan)//任务数
    TextView tv_mach_plan;
    @BindView(R.id.tv_name)//检验人
    TextView tv_name;
    @BindView(R.id.spinner_type)//检验类型
    Spinner spinner_type;
    @BindView(R.id.process_type)//工序类型
    Spinner process_type;
    @BindView(R.id.tv_submit)//保存任务
    TextView tv_submit;
    @BindView(R.id.tv_cancel)//记录上报
    TextView tv_cancel;
    private String access_token;
    private boolean isFirst = true;
    private CodeBean codeBean = null;
    private List<BarcodeBean> processPriceVos = new ArrayList<>();
    private  List<BarcodeBean> processPriceVos2=new ArrayList<>();
    private ArrayList<String> processList = new ArrayList<>();
    private int processPosition = 0;
    private ArrayList<String> typeList = new ArrayList<>();
    private int typePosition = 0;
    private List<QualityTypeListBean> qualityTypeList = null;
    private List<QualityTypeListBean> qualityTypeList2 = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private int REQUEST_CODE_SCAN = 100;

    @Override
    protected int initLayout() {
        return R.layout.activity_final_inspection;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        access_token = login.getAccess_token();//用户tokens
        img_return.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        img_search.setOnClickListener(this);
        UserInfoBean user = UserLocalData.getUser();
        String username = user.getData().getSysUser().getUsername();
        tv_name.setText(getString(R.string.test_people, username + ""));
        initType();
        et_search.addTextChangedListener(new TextWatcher() {
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
                    getBarcode(String.valueOf(s));//查询流程卡信息
                    isFirst = false;
                }
            }
        });
    }

    private void initType() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/dict/type/checkout_type")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestQualityTypeListBean requestQualityTypeListBean = new Gson().fromJson(data, RequestQualityTypeListBean.class);
                            if (requestQualityTypeListBean.getCode() == 0) {
                                List<QualityTypeListBean> qualityTypeListBeanData = requestQualityTypeListBean.getData();
                                if (qualityTypeListBeanData == null) return;
                                qualityTypeList = qualityTypeListBeanData;
                                getQualityTypeList(qualityTypeListBeanData);
                            } else {
                                Toast.makeText(FinalInspectionActivity.this, "" + requestQualityTypeListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(FinalInspectionActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }

    /**
     * 质检类型
     *
     * @param qualityTypeListBeanData
     */
    private void getQualityTypeList(List<QualityTypeListBean> qualityTypeListBeanData) {



    }


    /**
     * 获取流程卡信息
     *
     * @param barCode
     */
    private void getBarcode(String barCode) {
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/barcode/" + barCode)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestBarcodeBean requestBarcodeBean = new Gson().fromJson(data, RequestBarcodeBean.class);
                            if (requestBarcodeBean.getCode() == 1) {
                                Toast.makeText(FinalInspectionActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

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
        tv_mach_num.setText(TextUtils.isEmpty(codeBean.getBarcode()) ? "流程卡号：" : "流程卡号：" + codeBean.getBarcode() + "");
        tv_mach_name.setText(TextUtils.isEmpty(codeBean.getMaterialsCodeName()) ? "物料名称：" : "物料名称：" + codeBean.getMaterialsCodeName() + "");
        // et_address.setText(TextUtils.isEmpty(codeBean.getLocation())?"":codeBean.getLocation()+"");
        //  et_procedure_name.setText(TextUtils.isEmpty(codeBean.getProcessPriceVos().get(0).getProcessName())?"":codeBean.getProcessPriceVos().get(0).getProcessName()+"");
        tv_mach_plan.setText(TextUtils.isEmpty(codeBean.getNum()) ? "任务数：" : "任务数：" + codeBean.getNum() + "");
        processPriceVos = codeBean.getProcessPriceVos();
        processList.clear();
        processPriceVos2.clear();
        for (int i = 0; i < processPriceVos.size(); i++) {
            if (!TextUtils.isEmpty(processPriceVos.get(i).getCheckout())) {
                processList.add(processPriceVos.get(i).getProcessName());
                processPriceVos2.add(processPriceVos.get(i));
            }

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, processList);
        process_type.setAdapter(adapter);
        process_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                processPosition = position;

                getTypeBean(position);
                //  Toast.makeText(TaskOverActivity.this,""+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getTypeBean(int position) {
        typeList.clear();
        qualityTypeList2.clear();
        String checkout = processPriceVos2.get(processPosition).getCheckout();
        List<String> photoList = new ArrayList<>();

        String[] str = checkout.split(",");
        for (String string : str) {
            photoList.add(string);
        }

        for (int i = 0; i < photoList.size(); i++) {
            for (int j=0;j<qualityTypeList.size();j++){
                if (photoList.get(i).equals(qualityTypeList.get(j).getValue())){
                    typeList.add(qualityTypeList.get(j).getLabel());
                    qualityTypeList2.add(qualityTypeList.get(j));
                }
            }


        }
        for (int i=0;i<qualityTypeList2.size();i++){
            if (qualityTypeList2.get(i).getValue().equals("3")){
                typeList.remove(i);
                qualityTypeList2.remove(i);
            }
        }

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(FinalInspectionActivity.this, R.layout.item_spinner, typeList);
        spinner_type.setAdapter(typeAdapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (isSpinnerFirst) {
//                    //第一次初始化spinner时，不显示默认被选择的第一项即可
//                    //  view.setVisibility(View.INVISIBLE);
//                    isSpinnerFirst = false;
//                    return;
//                }

                typePosition = position;

                //  getQualityTestList(qualityTypeListBeanData.get(typePosition).getValue(), 1, "");
                //请求数据
                //   getQualityTestList(qualityTypeListBeanData.get(typePosition).getValue(), 1, "");
                //  Toast.makeText(FeedingAddActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.tv_submit://保存任务
                getTaskPost();
                break;
            case R.id.tv_cancel://记录上报
                if (codeBean == null) {
                    ViewShowUtils.showShortToast(FinalInspectionActivity.this, "请先扫码");
                    return;
                }
                if (qualityTypeList2.size()==0){
                    ViewShowUtils.showShortToast(FinalInspectionActivity.this, "质检类型不能为空");
                    return;
                }
                ControlListBean controlListBean=new ControlListBean();
                controlListBean.setBarcode(codeBean.getBarcode() + "");
                controlListBean.setCheckoutType(qualityTypeList2.get(typePosition).getValue() + "");
                controlListBean.setMaterialId(codeBean.getMaterialId() + "");
                controlListBean.setMaterialName(codeBean.getMaterialName() + "");
                controlListBean.setProcessId(codeBean.getProcessId() + "");
                controlListBean.setProcessCardId(codeBean.getProcessCardId() + "");
                controlListBean.setProcessCode(processPriceVos2.get(processPosition).getProcessCode() + "");
                controlListBean.setProcessName(processPriceVos2.get(processPosition).getProcessName() + "");
                controlListBean.setProcessRouteTempDtlId(processPriceVos2.get(processPosition).getProcessRouteTempDtlId()+ "");
                controlListBean.setProcessRouteTempId(processPriceVos2.get(processPosition).getProcessRouteTempId()+"");
                controlListBean.setProcessSeq(processPriceVos2.get(processPosition).getProcessSeq() + "");
                controlListBean.setReportQty(processPriceVos2.get(processPosition).getReportQty() + "");
                Intent intent2 = new Intent(FinalInspectionActivity.this, SpecialInspectionActivity.class);//跳转质检上报
                Bundle bundle = new Bundle();
                bundle.putSerializable(C.home.QUALITY_BEAN, (Serializable) controlListBean);
                intent2.putExtras(bundle);
           startActivity(intent2);
                break;
            case R.id.img_search://扫码
                Intent intent = new Intent(FinalInspectionActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
        }
    }

    /**
     * 保存任务
     */
    private void getTaskPost() {
        if (codeBean == null) {
            ViewShowUtils.showShortToast(FinalInspectionActivity.this, "请先扫码");
            return;
        }
        if (qualityTypeList2.size()==0){
            ViewShowUtils.showShortToast(FinalInspectionActivity.this, "质检类型不能为空");
            return;
        }
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .show();
        QualityTaskAddBean qualitySubmitBean = new QualityTaskAddBean();
        qualitySubmitBean.setBarcode(codeBean.getBarcode() + "");
        qualitySubmitBean.setCheckoutType(qualityTypeList2.get(typePosition).getValue() + "");
        qualitySubmitBean.setMaterialId(codeBean.getMaterialId() + "");
        qualitySubmitBean.setMaterialName(codeBean.getMaterialName() + "");
        qualitySubmitBean.setProcessCardId(codeBean.getProcessCardId() + "");
        qualitySubmitBean.setProcessCode(processPriceVos2.get(processPosition).getProcessCode() + "");
        qualitySubmitBean.setProcessRouteTempDtlId(processPriceVos2.get(processPosition).getProcessRouteTempDtlId()+ "");
        qualitySubmitBean.setProcessId(codeBean.getProcessId() + "");
        qualitySubmitBean.setProcessSeq(processPriceVos2.get(processPosition).getProcessSeq() + "");
        qualitySubmitBean.setReportQty(processPriceVos2.get(processPosition).getReportQty() + "");
        qualitySubmitBean.setSource("1");
        qualitySubmitBean.setProcessRouteTempId(processPriceVos2.get(processPosition).getCardProcessRouteId()+"");
        qualitySubmitBean.setProcessName(processPriceVos2.get(processPosition).getProcessName() + "");

        String s = JSON.toJSONString(qualitySubmitBean);
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(qualitySubmitBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/qualitycontrol/qualitycontroltask")
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
                                RequestQualitySuccessBean submitBean = new Gson().fromJson(string, RequestQualitySuccessBean.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("扫描结果为：" + content);

                getBarcode(content);//查询流程卡信息
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.close();
    }
}