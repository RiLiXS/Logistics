package com.example.logistics.machining.activity;

import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.logistics.App;
import com.example.logistics.LoginActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BarcodeBean;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.CodeBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestBoxTransMergeBean;
import com.example.logistics.bean.RequestMergeBean;
import com.example.logistics.bean.RequestTransCodeMergeBean;
import com.example.logistics.bean.RequestTransMergeBean;
import com.example.logistics.bean.TotalSubmitBean;
import com.example.logistics.bean.TransCodeMergeBean;
import com.example.logistics.bean.ViceCard;
import com.example.logistics.bean.processCardList;
import com.example.logistics.bean.sonPackagingBoxEncodeList;
import com.example.logistics.dialog.FeedAddDialog;
import com.example.logistics.dialog.MergeCodeAddDialog;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.machining.adapter.MachiningMatterAddAdapter;
import com.example.logistics.machining.adapter.MergeCodeAddAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.AppUtil;
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
 * ????????????
 */
public class TransCodeMergeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.img_task_code)//??????
    ImageView img_task_code;
    @BindView(R.id.linear_merge)//????????????
    LinearLayoutCompat linear_merge;
    @BindView(R.id.tv_add)//??????
    TextView tv_add;
    @BindView(R.id.et_master_card)//???????????????
    EditText et_master_card;
    @BindView(R.id.et_matter_name)//????????????
    TextView et_matter_name;
    @BindView(R.id.et_procedure_now)//????????????
    TextView et_procedure_now;
    @BindView(R.id.et_planNum)//????????????
    TextView et_planNum;
    @BindView(R.id.et_trial)//?????????
    TextView et_trial;
    @BindView(R.id.tv_save)//??????
    TextView tv_save;
    @BindView(R.id.tv_submit)//???????????????
    TextView tv_submit;
    @BindView(R.id.tv_details)//??????
    TextView tv_details;
    @BindView(R.id.rg_group)
    RadioGroup rg_group;
    @BindView(R.id.rcy_big)
    RecyclerView rcy_big;
    @BindView(R.id.tv_add_big)
    TextView tv_add_big;
    private int REQUEST_CODE_SCAN = 100;
    private int CARD_CODE_SCAN = 200;
    private int REQUEST_MATTER_CODE_SCAN = 300;
    private TransCodeMergeBean codeBean = null;
    private List<BarcodeBean> processPriceVos = null;
    private ArrayList<String> processList = new ArrayList<>();
    private int processPosition = 0;
    private LoadingDialog loadingDialog;
    private EditText et_merge_card_one;
    private TextView et_splitNum;
    private int pack = 0;
    private MergeCodeAddAdapter mergeCodeAddAdapter;
    private List<sonPackagingBoxEncodeList> feedingAddBeanList = new ArrayList<>();


    @Override
    protected int initLayout() {
        return R.layout.activity_merge_trans_code;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        tv_add.setOnClickListener(this);
        tv_add_big.setOnClickListener(this);
        img_return.setOnClickListener(this);
        img_task_code.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_details.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        et_trial.setSingleLine();
        et_planNum.setSingleLine();
        et_matter_name.setSingleLine();
        et_master_card.setSingleLine();
        initFeedList();

        /**
         * boxstatus 0????????? 1?????????
         */
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_big://?????????
                        pack = 0;
                        rcy_big.setVisibility(View.VISIBLE);
                        tv_add_big.setVisibility(View.VISIBLE);
                        linear_merge.setVisibility(View.GONE);
                        tv_add.setVisibility(View.GONE);
                        break;
                    case R.id.rb_little://?????????
                        pack = 1;
                        rcy_big.setVisibility(View.GONE);
                        tv_add_big.setVisibility(View.GONE);
                        linear_merge.setVisibility(View.VISIBLE);
                        tv_add.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        et_master_card.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //?????????????????????
                    if (!TextUtils.isEmpty(et_master_card.getText().toString())) {
                        getPackCode(String.valueOf(et_master_card.getText().toString()));//?????????????????????
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
//        View reason_list = View.inflate(TransCodeMergeActivity.this, R.layout.item_merge_list2, null);
////        View   tv_delete=reason_list.findViewById(R.id.tv_delete);
////        tv_delete.setVisibility(View.GONE);
////        tv_delete.setTag(new Date().getTime()+"");
//        linear_merge.addView(reason_list);
//
//        et_merge_card_one = reason_list.findViewById(R.id.et_merge_card);
//        et_splitNum = reason_list.findViewById(R.id.et_splitNum);
//        ImageView img_merge_card = reason_list.findViewById(R.id.img_merge_card);
//        et_merge_card_one.setTag(new Date().getTime() + "");
//        et_merge_card_one.setSingleLine();
//        et_merge_card_one.setTag(new Date().getTime() + "");
//        et_splitNum.setTag(new Date().getTime() + "");
//        img_merge_card.setTag(new Date().getTime() + "");
//        img_merge_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TransCodeMergeActivity.this, CaptureActivity.class);
//                intent.putExtra("tag", (String) img_merge_card.getTag());
//                startActivityForResult(intent, CARD_CODE_SCAN);
//            }
//        });
//        et_merge_card_one.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!TextUtils.isEmpty(s)) {
//                    getProcessBarcode(String.valueOf(s), (String) et_splitNum.getTag());//?????????????????????
//                }
//            }
//        });
        //  getContext();
        /*  (reason_list.findViewById(R.id.img_merge_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagOne = "1";
                Intent intent = new Intent(TransCodeMergeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, CARD_CODE_SCAN);
            }
        });
      et_merge_card_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    tagOne = "1";
                    getProcessBarcode(String.valueOf(s));//?????????????????????
                }
            }
        });*/

    }

    /**
     * ?????????????????????
     */
    private void initFeedList() {
        mergeCodeAddAdapter = new MergeCodeAddAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_big.setLayoutManager(linearLayoutManager);
        rcy_big.setAdapter(mergeCodeAddAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://????????????
            case R.id.tv_details://??????
                finish();
                break;
            case R.id.tv_save:
            case R.id.tv_submit://??????
                if (pack==0){
                    getBoxSubmit();
                }else{
                    getProcessSubmit();
                }

                break;
            case R.id.tv_add_big://????????????

                MergeCodeAddDialog feedAddDialog = new MergeCodeAddDialog(this, R.style.feed_dialog);
                feedAddDialog.show();
                feedAddDialog.setSaveListener(new MergeCodeAddDialog.OnSaveListen() {
                    @Override
                    public void onClick(View view) {
                        AppUtil.hintKeyBoard(TransCodeMergeActivity.this);
                        //????????????
                        feedingAddBeanList.clear();
                        feedingAddBeanList.add(feedAddDialog.getStringData());//????????????
                        mergeCodeAddAdapter.addList(feedingAddBeanList);
                        ViewShowUtils.showShortToast(TransCodeMergeActivity.this, "????????????");

                    }
                });

                feedAddDialog.setScanListener(new MergeCodeAddDialog.OnScanListen() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TransCodeMergeActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_MATTER_CODE_SCAN);
                    }
                });

                break;
            case R.id.img_task_code://??????
                Intent intent = new Intent(TransCodeMergeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.tv_add://????????????
                AppUtil.hintKeyBoard(TransCodeMergeActivity.this);
                View tab1 = View.inflate(TransCodeMergeActivity.this, R.layout.item_merge_list2, null);
                TextView et_splitNum = tab1.findViewById(R.id.et_splitNum);
                EditText et_merge_card = tab1.findViewById(R.id.et_merge_card);
                et_merge_card.setSingleLine();
                et_merge_card.setTag(new Date().getTime() + "");
                et_splitNum.setTag(new Date().getTime() + "");
                ImageView img_merge_card = tab1.findViewById(R.id.img_merge_card);
                img_merge_card.setTag(new Date().getTime() + "");
                img_merge_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //getContext((String)img_merge_card.getTag());
                        Intent intent = new Intent(TransCodeMergeActivity.this, CaptureActivity.class);
                        intent.putExtra("tag", (String) et_splitNum.getTag());
                        startActivityForResult(intent, CARD_CODE_SCAN);
                    }
                });
                et_merge_card.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                            //?????????????????????
                            if (!TextUtils.isEmpty(et_merge_card.getText().toString())) {
                                getProcessBarcode(et_merge_card.getText().toString() + "", (String) et_splitNum.getTag());
                            }
                            return true;
                        }
                        return false;
                    }
                });


                linear_merge.addView(tab1);
                AppUtil.hintKeyBoard(TransCodeMergeActivity.this);
                break;
            default:
                break;

        }
    }

    /**
     * ????????????
     */
    private void getContext(String tag) {

        for (int i = 0; i < linear_merge.getChildCount(); i++) {
            View childAt = linear_merge.getChildAt(i);
            String tag1 = (String) childAt.findViewById(R.id.et_merge_card).getTag();
            String tag2 = (String) childAt.findViewById(R.id.img_merge_card).getTag();
            EditText tv = (EditText) childAt.findViewById(R.id.et_merge_card);
            TextView num = (TextView) childAt.findViewById(R.id.et_splitNum);
            ImageView img_merge_card = (ImageView) childAt.findViewById(R.id.img_merge_card);
            if (tag.equals(tag2)) {

            }


        }
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

                getPackCode(content);//?????????????????????
            }
        } else if (requestCode == CARD_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                String tag = data.getStringExtra("tag");
                getProcessBarcode(content, tag);
                for (int i = 0; i < linear_merge.getChildCount(); i++) {
                    View childAt = linear_merge.getChildAt(i);
                    String tag1 = (String) childAt.findViewById(R.id.et_merge_card).getTag();
                    EditText code = (EditText) childAt.findViewById(R.id.et_merge_card);
                    if (tag.equals(tag1)) {
                        code.setText(content + "");
                    }
                }

            }
        } else if (requestCode == REQUEST_MATTER_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                EventBus.getDefault().post(new MessageEvent(EventBusAction.UPDATED_MERGE, content));


            }
        }
    }


    /**
     * ?????????????????????
     */
    private List<processCardList> getSubBean() {
        List<processCardList> viceCardList = new ArrayList<>();
        for (int i = 0; i < linear_merge.getChildCount(); i++) {
            View child = linear_merge.getChildAt(i);
            String merge_card = ((TextView) child.findViewById(R.id.et_merge_card)).getText().toString();
            String splitNum = ((TextView) child.findViewById(R.id.et_splitNum)).getText().toString();
            processCardList viceCard = new processCardList(merge_card, splitNum);
            viceCardList.add(viceCard);
        }
        return viceCardList;
    }


    /**
     * ???????????????
     */
    private void getProcessSubmit() {
        if (codeBean == null) {
            ViewShowUtils.showShortToast(TransCodeMergeActivity.this, "????????????");
            return;
        }
        loadingDialog.setLoadingText("?????????")
                .setSuccessText("????????????")
                .show();

        List<processCardList> subBean = getSubBean();
        RequestTransMergeBean mergeBean = new RequestTransMergeBean();
        //    mergeBean.setProcessCardId(TextUtils.isEmpty(codeBean.getProcessCardId()) ? "" : codeBean.getProcessCardId());
//        mergeBean.setProcessSeq(TextUtils.isEmpty(processPriceVos.get(processPosition).getProcessSeq()) ? "" : processPriceVos.get(processPosition).getProcessSeq());
        mergeBean.setProcessCardListList(subBean);
        mergeBean.setMaterialName(TextUtils.isEmpty(codeBean.getMaterialName()) ? "" : codeBean.getMaterialName() + "");
        mergeBean.setPackagingBoxEncode(TextUtils.isEmpty(codeBean.getPackagingBoxEncode()) ? "" : codeBean.getPackagingBoxEncode() + "");
        //feedingSubmitBean.setBatchChargingId("");

        String s = JSON.toJSONString(mergeBean);
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(mergeBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/plansp/packagingboxprocesscard/processCardIntoPackagingBox/notLimit")
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

    /**
     * ???????????????
     */
    private void getBoxSubmit() {
        if (codeBean == null) {
            ViewShowUtils.showShortToast(TransCodeMergeActivity.this, "????????????");
            return;
        }
        loadingDialog.setLoadingText("?????????")
                .setSuccessText("????????????")
                .show();

        List<sonPackagingBoxEncodeList> feedData = mergeCodeAddAdapter.getFeedData();
        RequestBoxTransMergeBean mergeBean = new RequestBoxTransMergeBean();
        mergeBean.setSonPackagingBoxEncodeLists(feedData);
        mergeBean.setBoxStatus(codeBean.getBoxStatus());
        mergeBean.setPackagingBoxEncode(TextUtils.isEmpty(codeBean.getPackagingBoxEncode()) ? "" : codeBean.getPackagingBoxEncode() + "");
        //feedingSubmitBean.setBatchChargingId("");

        String s = JSON.toJSONString(mergeBean);
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(mergeBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/plansp/packagingboxprocesscard/processCardIntoPackagingBox")
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

    /**
     * ?????????????????????
     *
     * @param content
     * @param tag
     */
    private void getProcessBarcode(String content, String tag) {
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
                                Toast.makeText(TransCodeMergeActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                CodeBean codeBean = requestBarcodeBean.getData();
                                if (codeBean != null) {
                                    getBarCode(codeBean, tag);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }

    }

    private void getBarCode(CodeBean codeBean, String tag) {
        for (int i = 0; i < linear_merge.getChildCount(); i++) {
            View childAt = linear_merge.getChildAt(i);
            String tag2 = (String) childAt.findViewById(R.id.et_splitNum).getTag();
            TextView num2 = (TextView) childAt.findViewById(R.id.et_splitNum);
            if (tag.equals(tag2)) {
                num2.setText(TextUtils.isEmpty(codeBean.getCurrentReportNum()) ? "0" : MathUtils.getNum(codeBean.getCurrentReportNum()) + "");
            }
        }

    }

    private void getPackCode(String content) {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/packagingbox/box/" + content)
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestTransCodeMergeBean requestBarcodeBean = new Gson().fromJson(data, RequestTransCodeMergeBean.class);
                            if (requestBarcodeBean.getCode() == 1) {
                                Toast.makeText(TransCodeMergeActivity.this, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                codeBean = requestBarcodeBean.getData();
                                if (codeBean != null) {
                                    getPackingCode(codeBean);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }

    }

    private void getPackingCode(TransCodeMergeBean codeBean) {

        if (et_matter_name == null || et_trial == null
                || et_planNum == null || et_master_card == null
        ) return;
        et_matter_name.setText(TextUtils.isEmpty(codeBean.getMaterialName()) ? "" : codeBean.getMaterialName() + "");
        et_master_card.setText(TextUtils.isEmpty(codeBean.getPackagingBoxEncode()) ? "" : codeBean.getPackagingBoxEncode());
        et_planNum.setText(TextUtils.isEmpty(codeBean.getCurrentNum()) ? "0" : codeBean.getCurrentNum() + "");
        et_procedure_now.setText(TextUtils.isEmpty(codeBean.getParentPackagingBoxEncode()) ? "" : codeBean.getParentPackagingBoxEncode() + "");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.close();
    }

}