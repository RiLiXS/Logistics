package com.example.logistics.machining.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.example.logistics.bean.DeviceListBean;
import com.example.logistics.bean.FeedingAddBean;
import com.example.logistics.bean.FeedingSubmitBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestDeviceListBean;
import com.example.logistics.bean.RequestUserListBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.UserListBean;
import com.example.logistics.dialog.FeedAddDialog;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.machining.MachiningManageAdapter;
import com.example.logistics.machining.adapter.MachiningMatterAddAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.AppUtil;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;

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
 * ????????????
 */
public class FeedingAddActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.et_task_code)//????????????
    EditText et_task_code;
    @BindView(R.id.img_task_code)//??????????????????
    ImageView img_task_code;
    @BindView(R.id.et_matter_type)//????????????
    EditText et_matter_type;
    @BindView(R.id.et_matter_name)//????????????
    EditText et_matter_name;
    @BindView(R.id.et_address)//??????
    EditText et_address;
    @BindView(R.id.et_procedure_name)//????????????
    Spinner et_procedure_name;
    @BindView(R.id.et_task_num)//?????????
    EditText et_task_num;
    @BindView(R.id.et_feeding_device)//????????????
    Spinner et_feeding_device;
    @BindView(R.id.img_feeding_device)//??????????????????
    ImageView img_feeding_device;
    @BindView(R.id.et_feeding_people)//?????????
    Spinner et_feeding_people;
    @BindView(R.id.img_feeding_people)//???????????????
    ImageView img_feeding_people;
    @BindView(R.id.rcy_feed)//????????????
    RecyclerView rcy_feed;
    @BindView(R.id.tv_add)//????????????
    TextView tv_add;
    @BindView(R.id.tv_submit)//??????
    TextView tv_submit;
    private CodeBean codeBean = null;
    private List<BatchChargingDtl> feedingAddBeanList = new ArrayList<>();
    private MachiningMatterAddAdapter machiningMatterAddAdapter;
    private int REQUEST_CODE_SCAN = 100;
    private int REQUEST_MATTER_CODE_SCAN = 200;
    private String access_token;
    private LoadingDialog loadingDialog;
    private ArrayList<String> processList = new ArrayList<>();
    private int processPosition = 0;
    private int peoplePosition = 0;
    private int devicePosition = 0;
    private List<BarcodeBean> processPriceVos = null;
    private ArrayList<String> deviceList = new ArrayList<>();
    private ArrayList<String> peopleList = new ArrayList<>();
    private List<UserListBean> userData;
    private List<DeviceListBean> deviceData;
    private boolean isFirst=true;


    @Override
    protected int initLayout() {
        return R.layout.activity_feeding_add;
    }

    @Override
    protected void initView() {
        loadingDialog= new LoadingDialog(this);
        tv_add.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        img_return.setOnClickListener(this);
        img_task_code.setOnClickListener(this);
        img_feeding_device.setOnClickListener(this);
        img_feeding_people.setOnClickListener(this);
        et_matter_name.setSingleLine();
        et_task_num.setSingleLine();
        et_address.setSingleLine();
        et_matter_type.setSingleLine();
        et_task_code.setSingleLine();
        LoginBean login = UserLocalData.getLogin();//??????????????????
        access_token = login.getAccess_token();//??????tokens

        getSpinner();//?????????????????? ???????????????
        et_task_code.addTextChangedListener(new TextWatcher() {
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
                    getBarcode(String.valueOf(s));//?????????????????????
                 isFirst=false;
                    et_task_code.setEnabled(false);
                }
            }
        });
       // getBarcode("F2202120001-1440");//?????????????????????
    }

    private void getSpinner() {

        RequestDeviceListBean device = UserLocalData.getDeviceList();
        deviceData = device.getData();
        RequestUserListBean userList = UserLocalData.getUserList();
        userData = userList.getData();
        for (int i = 0; i < deviceData.size(); i++) {
            deviceList.add(deviceData.get(i).getEqptName());
        }
        for (int i = 0; i < userData.size(); i++) {
            peopleList.add(userData.get(i).getUsername());
        }

        ArrayAdapter<String> deviceAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, deviceList);
        et_feeding_device.setAdapter(deviceAdapter);
        et_feeding_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                devicePosition = position;


              //  Toast.makeText(FeedingAddActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> peopleAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, peopleList);
        et_feeding_people.setAdapter(peopleAdapter);
        et_feeding_people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @Override
    protected void initData() {
        initFeedList();
    }

    /**
     * ?????????????????????
     */
    private void initFeedList() {
        machiningMatterAddAdapter = new MachiningMatterAddAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_feed.setLayoutManager(linearLayoutManager);
        rcy_feed.setAdapter(machiningMatterAddAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://???????????????
                finish();
                break;
            case R.id.tv_add://????????????
                FeedAddDialog feedAddDialog = new FeedAddDialog(this, R.style.feed_dialog);
                feedAddDialog.show();
                feedAddDialog.setSaveListener(new FeedAddDialog.OnSaveListen() {
                    @Override
                    public void onClick(View view) {
                        AppUtil.hintKeyBoard(FeedingAddActivity.this);
                        //????????????
                        feedingAddBeanList.clear();
                        feedingAddBeanList.add(feedAddDialog.getStringData());//????????????
                        machiningMatterAddAdapter.addList(feedingAddBeanList);
                        ViewShowUtils.showShortToast(FeedingAddActivity.this, "????????????");

                    }
                });

                feedAddDialog.setScanListener(new FeedAddDialog.OnScanListen() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FeedingAddActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_MATTER_CODE_SCAN);
                    }
                });

                break;
            case R.id.tv_submit://??????
                getSubmit();
                break;
            case R.id.img_task_code://??????????????????
                getScan();
                break;
            case R.id.img_feeding_device://??????????????????

                break;
            case R.id.img_feeding_people://???????????????

                break;


        }
    }

    /**
     * ??????
     */
    private void getSubmit() {
        if (codeBean==null){
            ViewShowUtils.showShortToast(FeedingAddActivity.this,"????????????");
            return;
        }
        loadingDialog.setLoadingText("?????????")
                .setSuccessText("????????????")
                .show();

        List<BatchChargingDtl> feedData = machiningMatterAddAdapter.getFeedData();
        FeedingSubmitBean feedingSubmitBean = new FeedingSubmitBean();
        feedingSubmitBean.setBarcode(TextUtils.isEmpty(codeBean.getBarcode()) ? "" : codeBean.getBarcode());
        feedingSubmitBean.setBatchChargingDtlList(feedData);
        feedingSubmitBean.setBatchChargingUserId(TextUtils.isEmpty(userData.get(peoplePosition).getUserId())?"":userData.get(peoplePosition).getUserId());
        feedingSubmitBean.setBatchChargingUserName(TextUtils.isEmpty(userData.get(peoplePosition).getUsername())?"":userData.get(peoplePosition).getUsername());
        feedingSubmitBean.setEqptId(TextUtils.isEmpty(deviceData.get(devicePosition).getId())?"":deviceData.get(devicePosition).getId());
        feedingSubmitBean.setMaterialId(TextUtils.isEmpty(codeBean.getMaterialId()) ? "" : codeBean.getMaterialId());
        feedingSubmitBean.setMaterialName(TextUtils.isEmpty(codeBean.getMaterialName()) ? "" : codeBean.getMaterialName());
        feedingSubmitBean.setProcessId(TextUtils.isEmpty(processPriceVos.get(processPosition).getProcessId()) ? "" : processPriceVos.get(processPosition).getProcessId());
        feedingSubmitBean.setProcessName(TextUtils.isEmpty(processPriceVos.get(processPosition).getProcessName()) ? "" : processPriceVos.get(processPosition).getProcessName());


        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(feedingSubmitBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/plansp/batchcharging")
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


    /**
     * ????????????
     */
    private void getPurview() {
        if (ContextCompat.checkSelfPermission(FeedingAddActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FeedingAddActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            getScan();
        }
    }

    /**
     * ??????
     */
    private void getScan() {
        Intent intent = new Intent(FeedingAddActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
//        ZxingConfig config = new ZxingConfig();
//        config.setPlayBeep(true);//???????????????????????? ?????????true
//        config.setShake(true);//????????????  ?????????true
//        config.setDecodeBarCode(true);//????????????????????? ?????????true
//        config.setReactColor(R.color.colorAccent);//????????????????????????????????? ???????????????
//        config.setFrameLineColor(R.color.colorAccent);//??????????????????????????? ????????????
//        config.setScanLineColor(R.color.colorAccent);//???????????????????????? ????????????
//        config.setFullScreenScan(true);//??????????????????  ?????????true  ??????false??????????????????????????????
//        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
//        startActivityForResult(intent, REQUEST_CODE_SCAN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // ???????????????/????????????
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
               // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("??????????????????" + content);

                 getBarcode(content);//?????????????????????
                et_task_code.setEnabled(false);
            }
        } else if (requestCode == REQUEST_MATTER_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                EventBus.getDefault().post(new MessageEvent(EventBusAction.UPDATED_FEED, content));


            }
        }
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
                                Toast.makeText(FeedingAddActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

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
        et_task_code.setText(TextUtils.isEmpty(codeBean.getBarcode()) ? "" : codeBean.getBarcode() + "");
        et_matter_name.setText(TextUtils.isEmpty(codeBean.getMaterialsCodeName()) ? "" : codeBean.getMaterialsCodeName() + "");
        // et_address.setText(TextUtils.isEmpty(codeBean.getLocation())?"":codeBean.getLocation()+"");
        //  et_procedure_name.setText(TextUtils.isEmpty(codeBean.getProcessPriceVos().get(0).getProcessName())?"":codeBean.getProcessPriceVos().get(0).getProcessName()+"");
        et_task_num.setText(TextUtils.isEmpty(codeBean.getNum()) ? "" : codeBean.getNum() + "");
        processPriceVos = codeBean.getProcessPriceVos();
        processList.clear();
        for (int i = 0; i < processPriceVos.size(); i++) {
            processList.add(processPriceVos.get(i).getProcessName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, processList);
        et_procedure_name.setAdapter(adapter);
        et_procedure_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                processPosition = position;


                //  Toast.makeText(TaskOverActivity.this,""+position,Toast.LENGTH_SHORT).show();
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
    }

    /**
     * ????????????????????????????????????
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ????????????
                } else {
                    Toast.makeText(FeedingAddActivity.this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ????????????
                    getScan();
                } else {
                    Toast.makeText(FeedingAddActivity.this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}