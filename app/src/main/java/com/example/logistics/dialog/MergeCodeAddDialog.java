package com.example.logistics.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.logistics.R;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.MatterDetails;
import com.example.logistics.bean.RequestMatterDetails;
import com.example.logistics.bean.RequestTransCodeMergeBean;
import com.example.logistics.bean.TransCodeMergeBean;
import com.example.logistics.bean.sonPackagingBoxEncodeList;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.machining.activity.TransCodeMergeActivity;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import okhttp3.Call;


/**
 * @author : jyx
 * @description:
 * @date :29.11.21
 */
public class MergeCodeAddDialog extends Dialog {
    private EditText et_bar_code, et_matter_code, et_matter_name, et_batchNumber, et_matterNum;
    private TextView tv_save, tv_cancel;
    private ImageView img_task_code;
    private OnSaveListen onSaveListen;
    private OnScanListen onScanListen;
    private Context context;
    private TransCodeMergeBean beanData;

    public MergeCodeAddDialog(@NonNull Context context) {
        super(context);
    }

    public MergeCodeAddDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
//        Window window = this.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.CENTER;
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;//???????????????????????????
//        this.getWindow().setAttributes(lp);

    }

    protected MergeCodeAddDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_merge_code_add);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
        initView();
    }

    @Override
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    private void initView() {
        et_bar_code = findViewById(R.id.et_bar_code);//??????
        et_matter_code = findViewById(R.id.et_matter_code);//????????????
        et_matter_name = findViewById(R.id.et_matter_name);//????????????
        et_batchNumber = findViewById(R.id.et_batchNumber);//??????
        et_matterNum = findViewById(R.id.et_matterNum);//?????????
        tv_save = findViewById(R.id.tv_save);//??????
        img_task_code = findViewById(R.id.img_task_code);//??????
        tv_cancel = findViewById(R.id.tv_cancel);//??????
        et_bar_code.setSingleLine();
        et_matter_code.setSingleLine();
        et_matter_name.setSingleLine();
        et_batchNumber.setSingleLine();

        et_matterNum.setSingleLine();

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_batchNumber.getText().toString())||TextUtils.isEmpty(et_matter_name.getText().toString())){
                    ViewShowUtils.showShortToast(context,"???????????????????????????");
                    return;
                }
                getStringData();
                if (onSaveListen != null) {
                    onSaveListen.onClick(v);
                    dismiss();
                }

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        img_task_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onScanListen != null) {
                    onScanListen.onClick(v);

                }
                //????????????
                // getScan();
            }
        });
        et_bar_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    if (!TextUtils.isEmpty(et_bar_code.getText().toString())){
                        getPackCode(et_bar_code.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @Subscribe //???????????? ??????
    public void onEventMainThread(MessageEvent event) {
        switch (event.getAction()) {
            case EventBusAction.UPDATED_MERGE:
                String content = event.getMsg();
                et_bar_code.setText(content + "");
                getPackCode(content);
                break;
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
                                Toast.makeText(context, "" + requestBarcodeBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                beanData = requestBarcodeBean.getData();
                                if (beanData != null) {
                                    getCode(beanData);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }

    }


    private void getCode(TransCodeMergeBean matterInfoBeanData) {
        et_matterNum.setText(TextUtils.isEmpty(matterInfoBeanData.getCurrentNum())?"0":matterInfoBeanData.getCurrentNum());
        et_matter_name.setText(TextUtils.isEmpty(matterInfoBeanData.getMaterialName()) ? "" : matterInfoBeanData.getMaterialName());
        et_matter_code.setText(TextUtils.isEmpty(matterInfoBeanData.getMaterialName()) ? "" : matterInfoBeanData.getMaterialName());
        et_batchNumber.setText(TextUtils.isEmpty(matterInfoBeanData.getPackagingBoxEncode()) ? "" : matterInfoBeanData.getPackagingBoxEncode());
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public sonPackagingBoxEncodeList getStringData() {

        return new sonPackagingBoxEncodeList(et_bar_code.getText().toString(), et_batchNumber.getText().toString(),
                et_matter_name.getText().toString(), et_matter_code.getText().toString(),beanData.getPackagingBoxEncode(),
                beanData.getBoxStatus(),et_matterNum.getText().toString());
    }


    private void getScan() {


    }

    //??????
    public interface OnSaveListen {
        void onClick(View view);
    }

    public void setSaveListener(OnSaveListen onSaveListen) {
        this.onSaveListen = onSaveListen;
    }

    //??????
    public interface OnScanListen {
        void onClick(View view);
    }

    public void setScanListener(OnScanListen onScanListen) {
        this.onScanListen = onScanListen;
    }


}
