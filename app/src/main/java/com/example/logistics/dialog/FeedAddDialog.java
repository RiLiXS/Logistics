package com.example.logistics.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.logistics.R;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.FeedingAddBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.MatterDetails;
import com.example.logistics.bean.MatterInfoBean;
import com.example.logistics.bean.RequestBarcodeBean;
import com.example.logistics.bean.RequestMatterDetails;
import com.example.logistics.bean.RequestMatterInfoBean;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.machining.activity.FeedingAddActivity;
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
public class FeedAddDialog extends Dialog {
    private EditText et_bar_code, et_matter_code, et_matter_name, et_batchNumber, et_unit, et_totalNum, et_matterNum,et_matter_specs,et_matter_supplier;
    private TextView tv_save, tv_cancel;
    private ImageView img_task_code;
    private OnSaveListen onSaveListen;
    private OnScanListen onScanListen;
    private Context context;

    public FeedAddDialog(@NonNull Context context) {
        super(context);
    }

    public FeedAddDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
//        Window window = this.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.CENTER;
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高可设置具体大小
//        this.getWindow().setAttributes(lp);

    }

    protected FeedAddDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_feed_add);
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
        et_bar_code = findViewById(R.id.et_bar_code);//条码
        et_matter_code = findViewById(R.id.et_matter_code);//物料编码
        et_matter_name = findViewById(R.id.et_matter_name);//物料名称
        et_matter_specs = findViewById(R.id.et_matter_specs);//物料规格
        et_matter_supplier = findViewById(R.id.et_matter_supplier);//供应商
        et_batchNumber = findViewById(R.id.et_batchNumber);//批号
        et_unit = findViewById(R.id.et_unit);//单位
        et_totalNum = findViewById(R.id.et_totalNum);//总数量
        et_matterNum = findViewById(R.id.et_matterNum);//投料数
        tv_save = findViewById(R.id.tv_save);//保存
        img_task_code = findViewById(R.id.img_task_code);//扫码
        tv_cancel = findViewById(R.id.tv_cancel);//取消
        et_bar_code.setSingleLine();
        et_matter_code.setSingleLine();
        et_matter_name.setSingleLine();
        et_batchNumber.setSingleLine();
        et_unit.setSingleLine();
        et_totalNum.setSingleLine();
        et_matterNum.setSingleLine();
        et_matter_supplier.setSingleLine();
        et_matter_specs.setSingleLine();
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_batchNumber.getText().toString())||TextUtils.isEmpty(et_totalNum.getText().toString())){
                    ViewShowUtils.showShortToast(context,"批号和数量不能为空");
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
                //扫码请求
                // getScan();
            }
        });
        et_bar_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //此处做逻辑处理
                String content = et_bar_code.getText().toString();
                String[] split = content.split("/");
                String matter_code=split[0];//物料内码
                String batch_code = split[1];//批次
                String num_code = split[2];//数量
                String serial_code = split[3];//外购入库单号
                String supplier=split[7];//供应商
                et_batchNumber.setText(batch_code + "");
                et_totalNum.setText(num_code + "");
                et_matter_supplier.setText(supplier+"");
                if (!TextUtils.isEmpty(matter_code)) {
                    getMatterInfo(matter_code);
                }
                    return true;
                }
                return false;
            }
        });
        et_bar_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                String content = String.valueOf(s);
//                String[] split = content.split("/");
//                String matter_code=split[0];//物料内码
//                String batch_code = split[1];//批次
//                String num_code = split[2];//数量
//                String serial_code = split[3];//外购入库单号
//                String supplier=split[7];//供应商
//                et_batchNumber.setText(batch_code + "");
//                et_totalNum.setText(num_code + "");
//                et_matter_supplier.setText(supplier+"");
//                if (!TextUtils.isEmpty(matter_code)) {
//                    getMatterInfo(matter_code);
//                }
            }
        });
    }

    @Subscribe //订阅事件 回调
    public void onEventMainThread(MessageEvent event) {
        switch (event.getAction()) {
            case EventBusAction.UPDATED_FEED:
                String content = event.getMsg();
//                String matter_code = content.substring(0, 9);//物料编码
//                String batch_code = content.substring(9, 17);//批次
//                String num_code = content.substring(17, 22);//数量
//                String serial_code = content.substring(22, 27);//流水号
                et_bar_code.setText(content + "");
//                et_matter_code.setText(matter_code + "");
//                et_batchNumber.setText(batch_code + "");
//                et_totalNum.setText(num_code + "");

//                if (!TextUtils.isEmpty(matter_code)) {
//                    getMatterInfo(matter_code);
//                }

                break;
        }
    }

    private void getMatterInfo(String matter_code) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/mdmmaterial/material/insideCode/" + matter_code)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestMatterDetails requestMatterDetails = new Gson().fromJson(data, RequestMatterDetails.class);
                            if (requestMatterDetails.getCode() == 1) {
                                Toast.makeText(context, "" + requestMatterDetails.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                MatterDetails matterDetailsData = requestMatterDetails.getData();
                                if (matterDetailsData != null) {
                                    getCode(matterDetailsData);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getCode(MatterDetails matterInfoBeanData) {
        et_unit.setText(TextUtils.isEmpty(matterInfoBeanData.getMaterialUnit()) ? "" : matterInfoBeanData.getMaterialUnit());
        et_matter_name.setText(TextUtils.isEmpty(matterInfoBeanData.getMaterialName()) ? "" : matterInfoBeanData.getMaterialName());
        et_matter_code.setText(TextUtils.isEmpty(matterInfoBeanData.getMaterialCode()) ? "" : matterInfoBeanData.getMaterialCode());
        et_matter_specs.setText(TextUtils.isEmpty(matterInfoBeanData.getMaterialModel()) ? "" : matterInfoBeanData.getMaterialModel());
    }

    /**
     * 保存新增的数据
     *
     * @return
     */
    public BatchChargingDtl getStringData() {

        return new BatchChargingDtl(et_bar_code.getText().toString(), et_batchNumber.getText().toString(),
                et_matter_name.getText().toString(), et_matter_code.getText().toString(), et_unit.getText().toString(),
                et_totalNum.getText().toString(), et_matterNum.getText().toString(),et_matter_specs.getText().toString(),et_matter_supplier.getText().toString());
    }


    private void getScan() {


    }

    //保存
    public interface OnSaveListen {
        void onClick(View view);
    }

    public void setSaveListener(OnSaveListen onSaveListen) {
        this.onSaveListen = onSaveListen;
    }

    //扫码
    public interface OnScanListen {
        void onClick(View view);
    }

    public void setScanListener(OnScanListen onScanListen) {
        this.onScanListen = onScanListen;
    }


}
