package com.example.logistics.home.activity.special_test;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.logistics.bean.DefectBean;
import com.example.logistics.bean.FeedingSubmitBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.ProcessTypeListBean;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.bean.QualitySubmitBean;
import com.example.logistics.bean.RequestProcessTypeListBean;
import com.example.logistics.bean.RequestQualityControlListBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.TotalSubmitBean;
import com.example.logistics.bean.UserInfoBean;
import com.example.logistics.dialog.ExitTestDialog;
import com.example.logistics.home.adapter.SpecialDefectAdapter;
import com.example.logistics.home.adapter.SpecialJudgeAdapter;
import com.example.logistics.home.adapter.SpecialMeasureAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
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
 * ??????
 */
public class SpecialInspectionActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_defect)//????????????
    RecyclerView rcy_defect;
    @BindView(R.id.rcy_measure)//????????????
    RecyclerView rcy_measure;
    @BindView(R.id.rcy_judge)//????????????
    RecyclerView rcy_judge;
    @BindView(R.id.tv_total_num)//????????????
    TextView tv_total_num;
    @BindView(R.id.tv_sure)//??????
    TextView tv_sure;
    @BindView(R.id.tv_cancel)//??????
    TextView tv_cancel;
    @BindView(R.id.et_sample)//?????????
    EditText et_sample;
    @BindView(R.id.tv_mach_operation)//????????????
    TextView tv_mach_operation;
    @BindView(R.id.tv_mach_num)//????????????
    TextView tv_mach_num;
    @BindView(R.id.tv_mach_name)//????????????
    TextView tv_mach_name;
    @BindView(R.id.tv_mach_plan)//?????????
    TextView tv_mach_plan;
    @BindView(R.id.tv_name)//?????????
    TextView tv_name;
    @BindView(R.id.tv_submit)//??????
    TextView tv_submit;
    @BindView(R.id.rg_group)//??????
    RadioGroup rg_group;
    @BindView(R.id.tv_state)//??????
    TextView tv_state;
    private List<QualityControlDtl> defectBeanList = new ArrayList<>();
    private List<QualityControlDtl> meaSureBeanList = new ArrayList<>();
    private List<QualityControlDtl> judgeBeanList = new ArrayList<>();
    private SpecialDefectAdapter specialDefectAdapter;
    private SpecialMeasureAdapter specialMeasureAdapter;
    private ControlListBean controlListBean = null;
    private LoadingDialog loadingDialog;
    private int result = 1;// 1??????   0?????????
    private SpecialJudgeAdapter specialJudgeAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_special_inspection;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        controlListBean = (ControlListBean) getIntent().getSerializableExtra(C.home.QUALITY_BEAN);
        switch (Integer.valueOf(  controlListBean.getCheckoutType())){
            case 1:
                tv_state.setText("??????");
                break;
            case 2:
                tv_state.setText("??????");
                break;
            case 3:
                tv_state.setText("?????????");
                break;
        }

        UserInfoBean user = UserLocalData.getUser();
        String username = user.getData().getSysUser().getUsername();
        initDefect();
        initMeasure();
        initJudge();
        tv_total_num.setText(getString(R.string.total_num, "0"));
        tv_sure.setOnClickListener(this);
        img_return.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_name.setText(getString(R.string.test_people, username + ""));
        tv_mach_name.setText(getString(R.string.plan_matter_name, controlListBean.getMaterialName() + ""));
        tv_mach_num.setText(getString(R.string.mach_process_card, controlListBean.getBarcode() + ""));
        tv_mach_operation.setText(getString(R.string.mach_current_operation, controlListBean.getProcessName() + ""));
        tv_mach_plan.setText(getString(R.string.num_task, MathUtils.getNum(controlListBean.getReportQty())));
        initProcessType();//????????????

        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_checked://??????
                        result = 1;
                        break;
                    case R.id.rb_unchecked://?????????
                        result = 0;
                        break;
                }
            }
        });
    }

    private void initJudge() {
        specialJudgeAdapter = new SpecialJudgeAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_judge.setLayoutManager(linearLayoutManager);
        rcy_judge.setAdapter(specialJudgeAdapter);
    }

    private void initProcessType() {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        String processRouteTempDtlId = controlListBean.getProcessRouteTempDtlId();
        String materialId = controlListBean.getMaterialId();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/process/processroutetempdtl/getRouteDtlCheckoutDtl?processRouteTempDtlId=" + controlListBean.getProcessRouteTempDtlId() + "&materialId=" + controlListBean.getMaterialId())
//                    .addParam("processRouteTempDtlIdint",controlListBean.getProcessRouteTempDtlId() + "")
//                    .addParam("materialId", controlListBean.getMaterialId() + "")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestProcessTypeListBean requestProcessTypeListBean = new Gson().fromJson(data, RequestProcessTypeListBean.class);
                            if (requestProcessTypeListBean.getCode() == 0) {
                                List<ProcessTypeListBean> requestProcessTypeListBeanData = requestProcessTypeListBean.getData();
                                getProcessType(requestProcessTypeListBeanData);

                            } else {
                                Toast.makeText(SpecialInspectionActivity.this, "" + requestProcessTypeListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(SpecialInspectionActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }

    private void getProcessType(List<ProcessTypeListBean> requestProcessTypeListBeanData) {
        defectBeanList.clear();
        meaSureBeanList.clear();
        List<Double> recordList = new ArrayList<>();
        for (int i = 0; i < requestProcessTypeListBeanData.size(); i++) {
            if (Integer.valueOf(requestProcessTypeListBeanData.get(i).getType()) == 1) {
                defectBeanList.add(new QualityControlDtl(requestProcessTypeListBeanData.get(i).getBadItemId(),
                        requestProcessTypeListBeanData.get(i).getItemName(), requestProcessTypeListBeanData.get(i).getNum(),
                        "0", requestProcessTypeListBeanData.get(i).getType(), recordList));
            } else if (Integer.valueOf(requestProcessTypeListBeanData.get(i).getType()) == 2) {
                meaSureBeanList.add(new QualityControlDtl(requestProcessTypeListBeanData.get(i).getBadItemId(),
                        requestProcessTypeListBeanData.get(i).getItemName(), requestProcessTypeListBeanData.get(i).getNum(),
                        "0", requestProcessTypeListBeanData.get(i).getType(),
                        requestProcessTypeListBeanData.get(i).getUpValue(),
                        requestProcessTypeListBeanData.get(i).getLowValue(),
                        requestProcessTypeListBeanData.get(i).getUnit(),
                        recordList));
            } else if (Integer.valueOf(requestProcessTypeListBeanData.get(i).getType()) == 3) {
                judgeBeanList.add(new QualityControlDtl(requestProcessTypeListBeanData.get(i).getBadItemId(),
                        requestProcessTypeListBeanData.get(i).getItemName(), requestProcessTypeListBeanData.get(i).getType(), "1"));
            }
        }
        specialDefectAdapter.initList(defectBeanList);
        specialDefectAdapter.notifyDataSetChanged();
        specialMeasureAdapter.initList(meaSureBeanList);
        specialMeasureAdapter.notifyDataSetChanged();
        specialJudgeAdapter.initList(judgeBeanList);
        specialJudgeAdapter.notifyDataSetChanged();
        specialDefectAdapter.setOnAddClickListener(new SpecialDefectAdapter.OnAddClickListener() {
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

    @Override
    protected void initData() {


    }

    /**
     * ????????????
     */
    private void totalNum() {
        List<QualityControlDtl> list = specialDefectAdapter.getList();
        int s = 0;
        for (int i = 0; i < list.size(); i++) {
            int num = Integer.parseInt(list.get(i).getNum());
            s = s + num;
        }
        tv_total_num.setText(getString(R.string.total_num, s + ""));
    }

    /**
     * ?????????????????????
     */
    private void initDefect() {
        specialDefectAdapter = new SpecialDefectAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_defect.setLayoutManager(linearLayoutManager);
        rcy_defect.setAdapter(specialDefectAdapter);
    }

    /**
     * ?????????????????????
     */
    private void initMeasure() {
        specialMeasureAdapter = new SpecialMeasureAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_measure.setLayoutManager(linearLayoutManager);
        rcy_measure.setAdapter(specialMeasureAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                if (TextUtils.isEmpty(et_sample.getText().toString())){
                    ViewShowUtils.showShortToast(SpecialInspectionActivity.this,"?????????????????????");
                    return;
                }
                specialMeasureAdapter.refreshList(Integer.parseInt(et_sample.getText().toString()));
                break;
            case R.id.tv_cancel:
            case R.id.img_return:
                ExitTestDialog testDialog = new ExitTestDialog(this);
                testDialog.setOnClickListen(new ExitTestDialog.onClickListen() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                testDialog.show();
                break;

            case R.id.tv_submit:
                getSubmit();//??????
                break;
        }
    }

    private void getSubmit() {
        loadingDialog.setLoadingText("?????????")
                .setSuccessText("????????????")
                .show();
        List<QualityControlDtl> qualityControlDtlList = new ArrayList<>();
        List<QualityControlDtl> specialDefectList = specialDefectAdapter.getList();
        List<QualityControlDtl> measureAdapterList = specialMeasureAdapter.getList();
        List<QualityControlDtl> judgeAdapterList = specialJudgeAdapter.getList();
        qualityControlDtlList.addAll(specialDefectList);
        qualityControlDtlList.addAll(measureAdapterList);
        qualityControlDtlList.addAll(judgeAdapterList);
        QualitySubmitBean qualitySubmitBean = new QualitySubmitBean();
        qualitySubmitBean.setBarcode(controlListBean.getBarcode() + "");
        qualitySubmitBean.setCheckoutType(controlListBean.getCheckoutType() + "");
        qualitySubmitBean.setProcessCardId(controlListBean.getProcessCardId() + "");
        qualitySubmitBean.setProcessCode(controlListBean.getProcessCode() + "");
        qualitySubmitBean.setProcessId(controlListBean.getProcessId() + "");
        qualitySubmitBean.setProcessName(controlListBean.getProcessName() + "");
        qualitySubmitBean.setProcessRouteTempDtlId(controlListBean.getProcessRouteTempDtlId() + "");
        qualitySubmitBean.setProcessRouteTempId(controlListBean.getProcessRouteTempId() + "");
        qualitySubmitBean.setProcessSeq(controlListBean.getProcessSeq() + "");
        qualitySubmitBean.setQualityControlTaskId(controlListBean.getQualityControlTaskId() + "");
        qualitySubmitBean.setResult(result + "");
        qualitySubmitBean.setSampleNum(et_sample.getText().toString() + "");
        qualitySubmitBean.setQualityControlDtlList(qualityControlDtlList);


        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(qualitySubmitBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/qualitycontrol/qualitycontrol")
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
}