package com.example.logistics.machining.activity.job;

import android.content.Intent;
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
import com.example.logistics.bean.BadItemListBean;
import com.example.logistics.bean.BarcodeBean;
import com.example.logistics.bean.CodeBean;
import com.example.logistics.bean.DeviceListBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestBadItemListBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestDeviceListBean;
import com.example.logistics.bean.RequestUserListBean;
import com.example.logistics.bean.RequestWorkCenterBean;
import com.example.logistics.bean.RequestWorkCenterDetailsBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.TotalSubmitBean;
import com.example.logistics.bean.UserListBean;
import com.example.logistics.bean.WorkCenterBadItem;
import com.example.logistics.bean.WorkCenterBadItemListBean;
import com.example.logistics.bean.WorkReportBean;
import com.example.logistics.dialog.DefectiveDialog;
import com.example.logistics.dialog.DefectiveDialog2;
import com.example.logistics.machining.activity.ProduceJobActivity;
import com.example.logistics.ok.OkHttpUtils;
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
 * ??????????????????
 */
public class ProduceJobUpdateActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.tv_reason_report)//????????????
    TextView tv_reason_report;
    @BindView(R.id.tv_work_report)//????????????
    TextView tv_work_report;
    @BindView(R.id.et_task_code)//????????????
    EditText et_task_code;
    @BindView(R.id.et_matter_type)//????????????
    EditText et_matter_type;
    @BindView(R.id.et_matter_name)//????????????
    EditText et_matter_name;
    @BindView(R.id.et_planNum)//?????????
    EditText et_planNum;
    @BindView(R.id.et_task_num)//?????????
    EditText et_task_num;
    @BindView(R.id.et_completion_status)//???????????? 0 ?????????  1?????????
    EditText et_completion_status;
    @BindView(R.id.et_trial)//??????  0????????? 1??????
    EditText et_trial;
    @BindView(R.id.et_procedure_working)//????????????
    EditText et_procedure_working;
    @BindView(R.id.et_billing_method)//???????????? 1??????   2??????  ????????????2
    Spinner et_billing_method;
    @BindView(R.id.et_jockey)//?????????
    Spinner et_jockey;
    @BindView(R.id.et_device)//??????
    Spinner et_device;
    @BindView(R.id.et_working_hours)//??????
    EditText et_working_hours;
    @BindView(R.id.et_small_pieces)//?????????
    EditText et_small_pieces;
    @BindView(R.id.et_multiple)//??????
    EditText et_multiple;
    @BindView(R.id.et_unqualifiedNum)//????????????
    EditText et_unqualifiedNum;
    @BindView(R.id.et_qualifiedNum)//?????????
    EditText et_qualifiedNum;
    @BindView(R.id.et_small_pieces_unqualifiedNum)//?????????
    EditText et_small_pieces_unqualifiedNum;

    private int REQUEST_CODE_SCAN = 100;
    private LoadingDialog loadingDialog;
    private CodeBean codeBean = null;
    private List<BarcodeBean> processPriceVos = null;
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
    private DefectiveDialog2 defectiveDialog;
    private List<WorkCenterBadItem> badList = new ArrayList<>();
    private String workCenterId = null;
    private String barCode = null;
    private RequestWorkCenterBean workCenterDetailsBeanData;


    @Override
    protected int initLayout() {
        return R.layout.activity_produce_update_job;
    }

    @Override
    protected void initView() {
        workCenterId = getIntent().getStringExtra(C.mach.WORK_CENTER_ID);
        barCode = getIntent().getStringExtra(C.mach.PROCESS_CODE);
        getBarcode(barCode);//?????????????????????
        getDetails(workCenterId);//??????????????????
        tv_reason_report.setOnClickListener(this);

        tv_work_report.setOnClickListener(this);
        img_return.setOnClickListener(this);

        loadingDialog = new LoadingDialog(this);

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
        costList.add("??????");
        costList.add("??????");
        getSpinner();//???????????? ???????????????  ????????????


        et_qualifiedNum.addTextChangedListener(new TextWatcher() {//?????????
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

        et_unqualifiedNum.addTextChangedListener(new TextWatcher() {//????????????
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


        //getBarcode("F2202170021-00160");//?????????????????????


    }

    /**
     * ??????????????????
     *
     * @param workCenterId
     */
    private void getDetails(String workCenterId) {
        if (TextUtils.isEmpty(workCenterId)) return;
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/workcenter/" + workCenterId)
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestWorkCenterDetailsBean requestWorkCenterDetailsBean = new Gson().fromJson(data, RequestWorkCenterDetailsBean.class);
                            if (requestWorkCenterDetailsBean.getCode() == 0) {
                                workCenterDetailsBeanData = requestWorkCenterDetailsBean.getData();
                                if (workCenterDetailsBeanData == null) return;
                                initDetails(workCenterDetailsBeanData);

                            } else {
                                Toast.makeText(ProduceJobUpdateActivity.this, "" + requestWorkCenterDetailsBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(ProduceJobUpdateActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }

    }

    private void initDetails(RequestWorkCenterBean workCenterDetailsBeanData) {

        et_task_code.setText(TextUtils.isEmpty(workCenterDetailsBeanData.getBarcode()) ? "" : workCenterDetailsBeanData.getBarcode() + "");
        et_matter_name.setText(TextUtils.isEmpty(workCenterDetailsBeanData.getMaterialName()) ? "" : workCenterDetailsBeanData.getMaterialName() + "");
        et_procedure_working.setText(workCenterDetailsBeanData.getProcessName() + "");//????????????
        et_multiple.setText(workCenterDetailsBeanData.getMultiple() + "");//??????
        et_qualifiedNum.setText(MathUtils.getNum(workCenterDetailsBeanData.getReportQty()) + "");//????????????
        et_unqualifiedNum.setText(MathUtils.getNum(workCenterDetailsBeanData.getRejectQty()) + "");//???????????????
        et_small_pieces.setText(MathUtils.getNum(workCenterDetailsBeanData.getMinimumPieces()) + "");//??????????????????
        et_small_pieces_unqualifiedNum.setText(Integer.valueOf(et_unqualifiedNum.getText().toString()) * Integer.valueOf(et_multiple.getText().toString()) + "");//?????????????????????

        List<WorkCenterBadItem> badItemList = new ArrayList<>();

        getDevice(workCenterDetailsBeanData.getLocationId() + "");
        getUserList(workCenterDetailsBeanData.getLocationId() + "");
        List<BadItemListBean> badItemListBeanData = new ArrayList<>();
        List<WorkCenterBadItemListBean> workCenterBadItemList = workCenterDetailsBeanData.getWorkCenterBadItemList();

        if (workCenterBadItemList.size()==0){
            getDefectiveList(workCenterDetailsBeanData.getProcessId());
            return;
        }
        for (int i = 0; i < workCenterBadItemList.size(); i++) {
            badItemListBeanData.add(i, new BadItemListBean(workCenterBadItemList.get(i).getBadItemId(), workCenterBadItemList.get(i).getItemName(), workCenterBadItemList.get(i).getNum()));
            badItemList.add(i, new WorkCenterBadItem(workCenterBadItemList.get(i).getBadItemId(), workCenterBadItemList.get(i).getItemName(), workCenterBadItemList.get(i).getNum()));

        }
        badList = badItemList;
        badListBeanData = badItemListBeanData;

        defectiveDialog = new DefectiveDialog2(ProduceJobUpdateActivity.this, R.style.feed_dialog, badItemListBeanData);
    }

    /**
     * ???????????????
     *
     * @param processId
     */
    private void getDefectiveList(String processId) {

        if (TextUtils.isEmpty(processId)) return;
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/process/process/badItemList/" + processId)
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestBadItemListBean requestBadItemListBean = new Gson().fromJson(data, RequestBadItemListBean.class);
                            if (requestBadItemListBean.getCode() == 1) {
                                Toast.makeText(ProduceJobUpdateActivity.this, "" + requestBadItemListBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                List<BadItemListBean> badItemListBeanData = requestBadItemListBean.getData();
                                List<WorkCenterBadItem> badItemList = new ArrayList<>();
                                if (badItemListBeanData != null) {
                                    for (int i = 0; i < badItemListBeanData.size(); i++) {
                                        badItemList.add(i, new WorkCenterBadItem(badItemListBeanData.get(i).getBadItemId(), badItemListBeanData.get(i).getItemName(), badItemListBeanData.get(i).getNum()));

                                    }
                                    badList = badItemList;

                                    badListBeanData = badItemListBeanData;
                                    getBadItemList();
                                    defectiveDialog = new DefectiveDialog2(ProduceJobUpdateActivity.this, R.style.feed_dialog, badItemListBeanData);
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
     * ??????????????????
     */
    private void getUserList(String locationId) {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/user/list")
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("deptId", locationId + "")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestUserListBean requestUserListBean = new Gson().fromJson(data, RequestUserListBean.class);
                            if (requestUserListBean.getCode() == 1) {
                                Toast.makeText(ProduceJobUpdateActivity.this, "" + requestUserListBean.getMsg(), Toast.LENGTH_SHORT).show();

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
        int userId = 0;
        peopleList.clear();
        List<UserListBean> requestUserListBeanData = requestUserListBean.getData();
        userData = requestUserListBeanData;
        for (int i = 0; i < requestUserListBeanData.size(); i++) {
            if (workCenterDetailsBeanData.getWorker().equals(requestUserListBeanData.get(i).getUserId())) {
                userId = i;
            }
            peopleList.add(requestUserListBeanData.get(i).getUsername());
        }


        ArrayAdapter<String> peopleAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, peopleList);
        et_jockey.setAdapter(peopleAdapter);
        et_jockey.setSelection(userId);
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
     * ??????????????????
     */
    private void getDevice(String locationId) {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/mdmeqpt/eqpt/list")
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("locationId", locationId + "")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestDeviceListBean requestDeviceListBean = new Gson().fromJson(data, RequestDeviceListBean.class);
                            if (requestDeviceListBean.getCode() == 1) {
                                Toast.makeText(ProduceJobUpdateActivity.this, "" + requestDeviceListBean.getMsg(), Toast.LENGTH_SHORT).show();

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
        int deviceId = 0;
        deviceList.clear();
        List<DeviceListBean> requestDeviceListBeanData = requestDeviceListBean.getData();
        deviceData = requestDeviceListBeanData;
        for (int i = 0; i < requestDeviceListBeanData.size(); i++) {
            if (workCenterDetailsBeanData.getEqptId().equals(requestDeviceListBeanData.get(i).getId())) {
                deviceId = i;
            }
            deviceList.add(requestDeviceListBeanData.get(i).getEqptName());
        }
        ArrayAdapter<String> deviceAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, deviceList);
        et_device.setAdapter(deviceAdapter);
        et_device.setSelection(deviceId);
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
            case R.id.img_return://???????????????
                finish();
                break;
            case R.id.tv_reason_report://??????????????????

                if (defectiveDialog == null) return;
                defectiveDialog.show();
                defectiveDialog.setSaveListener(new DefectiveDialog2.OnSaveListen() {
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

            case R.id.tv_work_report://????????????
                getWorkReport();
                break;

        }
    }


    private void getWorkReport() {


        loadingDialog.setLoadingText("?????????")
                .setSuccessText("????????????")
                .show();

        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();

        WorkReportBean workReportBean = new WorkReportBean();
        workReportBean.setEqptId(TextUtils.isEmpty(deviceData.get(devicePosition).getId()) ? "" : deviceData.get(devicePosition).getId());
        workReportBean.setEqptName(TextUtils.isEmpty(deviceData.get(devicePosition).getEqptName()) ? "" : deviceData.get(devicePosition).getEqptName());
        workReportBean.setMeansCost(costPosition == 0 ? "2" : "1");
        workReportBean.setMinimumPieces(TextUtils.isEmpty(et_small_pieces.getText().toString()) ? "0" : et_small_pieces.getText().toString());
        workReportBean.setProcessCardId(TextUtils.isEmpty(codeBean.getProcessCardId()) ? "" : codeBean.getProcessCardId() + "");
        workReportBean.setProcessId(TextUtils.isEmpty(workCenterDetailsBeanData.getProcessId()) ? "" : workCenterDetailsBeanData.getProcessId());
        workReportBean.setProcessName(TextUtils.isEmpty(workCenterDetailsBeanData.getProcessName()) ? "" : workCenterDetailsBeanData.getProcessName());
        workReportBean.setProcessSortNo(TextUtils.isEmpty(workCenterDetailsBeanData.getProcessSortNo()) ? "" : workCenterDetailsBeanData.getProcessSortNo());
        workReportBean.setRejectQty(et_unqualifiedNum.getText().toString() + "");
        workReportBean.setWorker(TextUtils.isEmpty(userData.get(peoplePosition).getUserId()) ? "" : userData.get(peoplePosition).getUserId());
        workReportBean.setWorkerName(TextUtils.isEmpty(userData.get(peoplePosition).getUsername()) ? "" : userData.get(peoplePosition).getUsername());
        workReportBean.setRemark(TextUtils.isEmpty(codeBean.getRemark()) ? "" : codeBean.getRemark() + "");
        workReportBean.setReportQty(et_qualifiedNum.getText().toString() + "");
        workReportBean.setWorkTime(et_working_hours.getText().toString() + "");
        workReportBean.setLocationId(deviceData.get(devicePosition).getLocationId() + "");
        workReportBean.setWorkCenterBadItemList(badList);
        workReportBean.setWorkCenterId(workCenterDetailsBeanData.getWorkCenterId() + "");
        String s = JSON.toJSONString(workReportBean);
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(workReportBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/workCenter/workcenter/updateWorkCenter")
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
                                TotalSubmitBean submitBean = new Gson().fromJson(string, TotalSubmitBean.class);
                                if (submitBean == null) return;
                                if (submitBean.getCode() == 0) {
                                    Toast.makeText(App.getAppContext(), "????????????", Toast.LENGTH_SHORT).show();
                                    loadingDialog.loadSuccess();
                                    finish();

                                } else {
                                    Toast.makeText(App.getAppContext(), "" + submitBean.getMsg(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.close();
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
                                Toast.makeText(ProduceJobUpdateActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

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

        et_completion_status.setText(Integer.valueOf(codeBean.getIsFinish()) == 0 ? "?????????" : "?????????");
        et_trial.setText(Integer.valueOf(codeBean.getIsTrial()) == 0 ? "?????????" : "??????");
        et_task_num.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(codeBean.getNum()));
        et_planNum.setText(TextUtils.isEmpty(codeBean.getNum()) ? "0" : MathUtils.getNum(MathUtils.getScale3(codeBean.getNum(), workCenterDetailsBeanData.getMultiple())));


    }


    /**
     * ???????????????
     *
     * @param
     * @return
     */
    public static List<BadItemListBean> getBadItemList() {

        return badListBeanData;
    }
}