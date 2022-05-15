package com.example.logistics.machining.activity;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.example.logistics.MainActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BadItemListBean;
import com.example.logistics.bean.BarcodeBean;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.CodeBean;
import com.example.logistics.bean.DeviceListBean;
import com.example.logistics.bean.FeedingSubmitBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestBadItemListBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestDeviceListBean;
import com.example.logistics.bean.RequestUserListBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.TotalSubmitBean;
import com.example.logistics.bean.UserInfoBean;
import com.example.logistics.bean.UserListBean;
import com.example.logistics.bean.WorkCenterBadItem;
import com.example.logistics.bean.WorkReportBean;
import com.example.logistics.dialog.DefectiveDialog;
import com.example.logistics.dialog.FeedAddDialog;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.machining.activity.job.ProduceJobHistoryActivity;
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
 * 产出报工
 */
public class ProduceJobActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.tv_reason_report)//原因上报
    TextView tv_reason_report;
    @BindView(R.id.img_task_code)//扫流程卡码
    ImageView img_task_code;
    @BindView(R.id.tv_work_report)//报工上报
    TextView tv_work_report;
    @BindView(R.id.tv_completion_report)//完工上报
    TextView tv_completion_report;
    @BindView(R.id.et_task_code)//任务单号
    EditText et_task_code;
    @BindView(R.id.et_matter_type)//物料类别
    EditText et_matter_type;
    @BindView(R.id.et_matter_name)//物料名称
    EditText et_matter_name;
    @BindView(R.id.et_planNum)//计划数
    EditText et_planNum;
    @BindView(R.id.et_task_num)//任务数
    EditText et_task_num;
    @BindView(R.id.et_completion_status)//完工状态 0 未完工  1已完工
    EditText et_completion_status;
    @BindView(R.id.et_trial)//试制  0非试制 1试制
    EditText et_trial;
    @BindView(R.id.et_procedure_working)//报工工序
    Spinner et_procedure_working;
    @BindView(R.id.et_billing_method)//计费方式 1计时   2计件  前端默认2
    Spinner et_billing_method;
    @BindView(R.id.et_jockey)//操作工
    Spinner et_jockey;
    @BindView(R.id.et_device)//设备
    Spinner et_device;
    @BindView(R.id.et_working_hours)//工时
    EditText et_working_hours;
    @BindView(R.id.et_small_pieces)//小片数
    EditText et_small_pieces;
    @BindView(R.id.et_multiple)//倍数
    EditText et_multiple;
    @BindView(R.id.et_unqualifiedNum)//不合格数
    EditText et_unqualifiedNum;
    @BindView(R.id.et_qualifiedNum)//合格数
    EditText et_qualifiedNum;
    @BindView(R.id.et_small_pieces_unqualifiedNum)//合格数
    EditText et_small_pieces_unqualifiedNum;
    @BindView(R.id.tv_job_history)//报工记录
    TextView tv_job_history;
    private int REQUEST_CODE_SCAN = 100;
    private LoadingDialog loadingDialog;
    private CodeBean codeBean = null;
    private List<BarcodeBean> processPriceVos = null;
    private List<BarcodeBean> processPriceVos2 = new ArrayList<>();
    private ArrayList<String> processList = new ArrayList<>();
    private int processPosition = 0;
    private int peoplePosition = 0;
    private int devicePosition = 0;
    private int costPosition = 0;
    private ArrayList<String> deviceList = new ArrayList<>();
    private ArrayList<String> peopleList = new ArrayList<>();
    private List<UserListBean> userData;
    private List<DeviceListBean> deviceData;
    private ArrayList<String> costList = new ArrayList<>();
    private boolean isFirst = true;
    private static List<BadItemListBean> badListBeanData = null;
    private DefectiveDialog defectiveDialog;
    private List<WorkCenterBadItem> badList = new ArrayList<>();


    @Override
    protected int initLayout() {
        return R.layout.activity_produce_job;
    }

    @Override
    protected void initView() {

        tv_reason_report.setOnClickListener(this);
        img_task_code.setOnClickListener(this);
        tv_work_report.setOnClickListener(this);
        img_return.setOnClickListener(this);
        tv_completion_report.setOnClickListener(this);
        loadingDialog = new LoadingDialog(this);
        tv_job_history.setOnClickListener(this);
        et_task_code.setSingleLine();
        et_matter_type.setSingleLine();
        et_matter_name.setSingleLine();
        et_planNum.setSingleLine();
        et_task_num.setSingleLine();
        et_completion_status.setSingleLine();
        et_trial.setSingleLine();
        et_working_hours.setSingleLine();
        et_small_pieces.setSingleLine();
        et_unqualifiedNum.setSingleLine();
        et_qualifiedNum.setSingleLine();
        et_small_pieces_unqualifiedNum.setSingleLine();
        costList.add("计件");
        costList.add("计时");
        getSpinner();//获取设备 操作工列表  计费方式

        //任务单号手动输入搜索
        et_task_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //此处做逻辑处理
                    getBarcode(String.valueOf(et_task_code.getText().toString()));//查询流程卡信息
                    return true;
                }
                return false;
            }
        });
        et_task_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (isFirst == false) return;
//                if (!TextUtils.isEmpty(s)) {
//                    getBarcode(String.valueOf(s));//查询流程卡信息
//                    isFirst = false;
//                }
            }
        });

        et_qualifiedNum.addTextChangedListener(new TextWatcher() {//合格数
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s + "")) return;
                et_small_pieces.setText(Integer.valueOf(et_qualifiedNum.getText().toString()) * Integer.valueOf(et_multiple.getText().toString()) + "");
            }
        });

        et_unqualifiedNum.addTextChangedListener(new TextWatcher() {//不合格数
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s + "")) return;
                et_small_pieces_unqualifiedNum.setText(Integer.valueOf(et_unqualifiedNum.getText().toString()) * Integer.valueOf(et_multiple.getText().toString()) + "");
            }
        });


        //getBarcode("F2202170021-00160");//查询流程卡信息


    }

    /**
     * 获取用户列表
     */
    private void getUserList(String locationId) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/user/list")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("deptId", locationId + "")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestUserListBean requestUserListBean = new Gson().fromJson(data, RequestUserListBean.class);
                            if (requestUserListBean.getCode() == 1) {
                                Toast.makeText(ProduceJobActivity.this, "" + requestUserListBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                if (requestUserListBean == null) return;
                                getUserSpinner(requestUserListBean);
                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getUserSpinner(RequestUserListBean requestUserListBean) {
        peopleList.clear();
        List<UserListBean> requestUserListBeanData = requestUserListBean.getData();
        userData = requestUserListBeanData;
        for (int i = 0; i < requestUserListBeanData.size(); i++) {
            peopleList.add(requestUserListBeanData.get(i).getUsername());
        }


        ArrayAdapter<String> peopleAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, peopleList);
        et_jockey.setAdapter(peopleAdapter);
        et_jockey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                peoplePosition = position;


                //  Toast.makeText(FeedingAddActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取设备列表
     */
    private void getDevice(String locationId) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/mdmeqpt/eqpt/list")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("locationId", locationId + "")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestDeviceListBean requestDeviceListBean = new Gson().fromJson(data, RequestDeviceListBean.class);
                            if (requestDeviceListBean.getCode() == 1) {
                                Toast.makeText(ProduceJobActivity.this, "" + requestDeviceListBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                if (requestDeviceListBean == null) return;
                                getDeviceSpinner(requestDeviceListBean);

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getDeviceSpinner(RequestDeviceListBean requestDeviceListBean) {
        deviceList.clear();
        List<DeviceListBean> requestDeviceListBeanData = requestDeviceListBean.getData();
        deviceData = requestDeviceListBeanData;
        for (int i = 0; i < requestDeviceListBeanData.size(); i++) {
            deviceList.add(requestDeviceListBeanData.get(i).getEqptName());
        }
        ArrayAdapter<String> deviceAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, deviceList);
        et_device.setAdapter(deviceAdapter);
        et_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                devicePosition = position;


                //  Toast.makeText(FeedingAddActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getSpinner() {


        ArrayAdapter<String> costAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, costList);
        et_billing_method.setAdapter(costAdapter);
        et_billing_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                costPosition = position;

                if (position == 0) {
                    et_working_hours.setEnabled(false);
                } else {
                    et_working_hours.setEnabled(true);
                }

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
            case R.id.img_return://返回上一层
                finish();
                break;
            case R.id.tv_completion_report://完工上报
                getWorkCenterAndFinish();
                break;
            case R.id.tv_reason_report://跳转原因上报

                if (defectiveDialog == null) return;
                defectiveDialog.show();
                defectiveDialog.setSaveListener(new DefectiveDialog.OnSaveListen() {
                    @Override
                    public void onClick(View view, List<WorkCenterBadItem> badItemList) {
                        badList = badItemList;
                        Integer s1 = 0;
                        for (int i = 0; i < badItemList.size(); i++) {
                            s1 += Integer.valueOf(TextUtils.isEmpty(badItemList.get(i).getNum()) ? "0" : badItemList.get(i).getNum());
                        }
                        et_unqualifiedNum.setText(s1 + "");
                    }
                });
                break;
            case R.id.img_task_code://扫码
                Intent intent = new Intent(ProduceJobActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.tv_work_report://报工上报
                getWorkReport();
                break;
            case R.id.tv_job_history://报工记录
                if (codeBean == null) {
                    ViewShowUtils.showShortToast(ProduceJobActivity.this, "请先扫码");
                    return;
                }
                Intent intent1 = new Intent(ProduceJobActivity.this, ProduceJobHistoryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(C.mach.PRODUCT_CODE_BEAN, (Serializable) codeBean);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }
    }

    private void getWorkCenterAndFinish() {
        if (codeBean == null) {
            ViewShowUtils.showShortToast(ProduceJobActivity.this, "请先扫码");
            return;
        }
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .show();

        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();

        WorkReportBean workReportBean = new WorkReportBean();
        workReportBean.setEqptId(TextUtils.isEmpty(deviceData.get(devicePosition).getId()) ? "" : deviceData.get(devicePosition).getId());
        workReportBean.setEqptName(TextUtils.isEmpty(deviceData.get(devicePosition).getEqptName()) ? "" : deviceData.get(devicePosition).getEqptName());
        workReportBean.setMeansCost(costPosition == 0 ? "2" : "1");
        workReportBean.setMinimumPieces(TextUtils.isEmpty(String.valueOf(Integer.valueOf(processPriceVos2.get(processPosition).getMultiple()) * Integer.valueOf(et_qualifiedNum.getText().toString()))) ? "0" : String.valueOf(Integer.valueOf(processPriceVos2.get(processPosition).getMultiple()) * Integer.valueOf(et_qualifiedNum.getText().toString())));
        workReportBean.setProcessCardId(TextUtils.isEmpty(codeBean.getProcessCardId()) ? "" : codeBean.getProcessCardId() + "");
        workReportBean.setProcessId(TextUtils.isEmpty(processPriceVos2.get(processPosition).getProcessId()) ? "" : processPriceVos2.get(processPosition).getProcessId());
        workReportBean.setProcessName(TextUtils.isEmpty(processPriceVos2.get(processPosition).getProcessName()) ? "" : processPriceVos2.get(processPosition).getProcessName());
        workReportBean.setProcessSortNo(TextUtils.isEmpty(processPriceVos2.get(processPosition).getProcessSeq()) ? "" : processPriceVos2.get(processPosition).getProcessSeq());
        workReportBean.setRejectQty(et_unqualifiedNum.getText().toString() + "");
        workReportBean.setWorker(TextUtils.isEmpty(userData.get(peoplePosition).getUserId()) ? "" : userData.get(peoplePosition).getUserId());
        workReportBean.setWorkerName(TextUtils.isEmpty(userData.get(peoplePosition).getUsername()) ? "" : userData.get(peoplePosition).getUsername());
        workReportBean.setRemark(TextUtils.isEmpty(codeBean.getRemark()) ? "" : codeBean.getRemark() + "");
        workReportBean.setReportQty(et_qualifiedNum.getText().toString() + "");
        workReportBean.setWorkTime(et_working_hours.getText().toString() + "");
        workReportBean.setLocationId(deviceData.get(devicePosition).getLocationId() + "");
        workReportBean.setWorkCenterBadItemList(badList);
        String s = JSON.toJSONString(workReportBean);
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(workReportBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/workCenter/workcenter/saveWorkCenterAndFinish")
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

    private void getWorkReport() {
        if (codeBean == null) {
            ViewShowUtils.showShortToast(ProduceJobActivity.this, "请先扫码");
            return;
        }
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .show();

        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();

        WorkReportBean workReportBean = new WorkReportBean();
        workReportBean.setEqptId(TextUtils.isEmpty(deviceData.get(devicePosition).getId()) ? "" : deviceData.get(devicePosition).getId());
        workReportBean.setEqptName(TextUtils.isEmpty(deviceData.get(devicePosition).getEqptName()) ? "" : deviceData.get(devicePosition).getEqptName());
        workReportBean.setMeansCost(costPosition == 0 ? "2" : "1");
        workReportBean.setMinimumPieces(TextUtils.isEmpty(String.valueOf(Integer.valueOf(processPriceVos2.get(processPosition).getMultiple()) * Integer.valueOf(et_qualifiedNum.getText().toString()))) ? "0" : String.valueOf(Integer.valueOf(processPriceVos2.get(processPosition).getMultiple()) * Integer.valueOf(et_qualifiedNum.getText().toString())));
        workReportBean.setProcessCardId(TextUtils.isEmpty(codeBean.getProcessCardId()) ? "" : codeBean.getProcessCardId() + "");
        workReportBean.setProcessId(TextUtils.isEmpty(processPriceVos2.get(processPosition).getProcessId()) ? "" : processPriceVos2.get(processPosition).getProcessId());
        workReportBean.setProcessName(TextUtils.isEmpty(processPriceVos2.get(processPosition).getProcessName()) ? "" : processPriceVos2.get(processPosition).getProcessName());
        workReportBean.setProcessSortNo(TextUtils.isEmpty(processPriceVos2.get(processPosition).getProcessSeq()) ? "" : processPriceVos2.get(processPosition).getProcessSeq());
        workReportBean.setRejectQty(et_unqualifiedNum.getText().toString() + "");
        workReportBean.setWorker(TextUtils.isEmpty(userData.get(peoplePosition).getUserId()) ? "" : userData.get(peoplePosition).getUserId());
        workReportBean.setWorkerName(TextUtils.isEmpty(userData.get(peoplePosition).getUsername()) ? "" : userData.get(peoplePosition).getUsername());
        workReportBean.setRemark(TextUtils.isEmpty(codeBean.getRemark()) ? "" : codeBean.getRemark() + "");
        workReportBean.setReportQty(et_qualifiedNum.getText().toString() + "");
        workReportBean.setWorkTime(et_working_hours.getText().toString() + "");
        workReportBean.setLocationId(deviceData.get(devicePosition).getLocationId() + "");
        workReportBean.setWorkCenterBadItemList(badList);
        String s = JSON.toJSONString(workReportBean);
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(workReportBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/workCenter/workcenter/saveWorkCenter")
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
                et_task_code.setEnabled(false);

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
                                Toast.makeText(ProduceJobActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

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
        processPriceVos = codeBean.getProcessPriceVos();
        et_task_code.setText(TextUtils.isEmpty(codeBean.getBarcode()) ? "" : codeBean.getBarcode() + "");
        et_matter_name.setText(TextUtils.isEmpty(codeBean.getMaterialsCodeName()) ? "" : codeBean.getMaterialsCodeName() + "");
        et_completion_status.setText(Integer.valueOf(codeBean.getIsFinish()) == 0 ? "未完工" : "已完工");
        et_trial.setText(Integer.valueOf(codeBean.getIsTrial()) == 0 ? "非试制" : "试制");
        et_task_num.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(codeBean.getNum()));
        tv_work_report.setVisibility(Integer.valueOf(codeBean.getIsFinish()) == 0 ? View.VISIBLE : View.GONE);
        tv_completion_report.setVisibility(Integer.valueOf(codeBean.getIsFinish()) == 0 ? View.VISIBLE : View.GONE);
        //   et_small_pieces.setText(TextUtils.isEmpty(String.valueOf(Integer.valueOf(processPriceVos.get(processPosition).getMultiple()) * Integer.valueOf(et_qualifiedNum.getText().toString()))) ? "0" : String.valueOf(Integer.valueOf(processPriceVos.get(processPosition).getMultiple()) * Integer.valueOf(et_qualifiedNum.getText().toString())));
        if (processPriceVos == null) return;

        processList.clear();
        processPriceVos2.clear();
        for (int i = 0; i < processPriceVos.size(); i++) {
            if ("1".equals(processPriceVos.get(i).getIsReport())){
                processPriceVos2.add(processPriceVos.get(i));
             //   processList.add((i + 1) + "-" + processPriceVos.get(i).getProcessName() + "-" + MathUtils.getNum(processPriceVos.get(i).getReportQty()));
            }
        }
        for (int i = 0; i < processPriceVos2.size(); i++){
            processList.add((i + 1) + "-" + processPriceVos2.get(i).getProcessName() + "-" + MathUtils.getNum(processPriceVos2.get(i).getReportQty()));
        }
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, processList);
        et_procedure_working.setAdapter(adapter);
      //  et_procedure_working.setSelection(Integer.valueOf(codeBean.getProcessSortNo()));
        et_procedure_working.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                processPosition = position;
                //  TextView tv = (TextView) view;
                //   tv.setTextColor(Color.BLACK);
                // tv_work_report.setVisibility(processPriceVos.get(position).getIsReport().equals("0") ? View.GONE : View.VISIBLE);
                et_multiple.setText(processPriceVos2.get(position).getMultiple() + "");
                if (processPriceVos2.get(position).getMultiple().equals("0")) {
                    et_planNum.setText("0");
                } else {
                    et_planNum.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(MathUtils.getScale3(codeBean.getNum(), processPriceVos2.get(position).getMultiple())));

                }
                getDefectiveList(processPosition);//获取不良品
                getDevice(processPriceVos2.get(position).getLocationId() + "");
                getUserList(processPriceVos2.get(position).getLocationId() + "");
                et_qualifiedNum.setText(TextUtils.isEmpty(processPriceVos2.get(position).getReportQty()) ? "0" : MathUtils.getNum(processPriceVos2.get(position).getReportQty()));
                //   et_unqualifiedNum.setText("0");
                //  Toast.makeText(TaskOverActivity.this,""+position,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取不良品
     *
     * @param processPosition
     */
    private void getDefectiveList(int processPosition) {
        String processId = processPriceVos2.get(processPosition).getProcessId();
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
                                Toast.makeText(ProduceJobActivity.this, "" + requestBadItemListBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                List<BadItemListBean> badItemListBeanData = requestBadItemListBean.getData();
                                if (badItemListBeanData != null) {
                                    badListBeanData = badItemListBeanData;
                                    getBadItemList();
                                    defectiveDialog = new DefectiveDialog(ProduceJobActivity.this, R.style.feed_dialog, badItemListBeanData);
                                    // defectiveDialog.getScan(badItemListBeanData);
                                    //  EventBus.getDefault().post(new MessageEvent(EventBusAction.UPDATED_REASON));
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }

    }

    /**
     * 获取不良品
     *
     * @param
     * @return
     */
    public static List<BadItemListBean> getBadItemList() {

        return badListBeanData;
    }
}