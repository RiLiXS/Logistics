package com.example.logistics.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.logistics.App;
import com.example.logistics.LoginActivity;
import com.example.logistics.R;
import com.example.logistics.bean.BadItemListBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.OutWorkOrderBean;
import com.example.logistics.bean.RecordsTurnOverListBean;
import com.example.logistics.bean.RequestDeliverGoodsBean;
import com.example.logistics.bean.RequestUserListBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.TimeBean;
import com.example.logistics.bean.TotalSubmitBean;
import com.example.logistics.bean.UserListBean;
import com.example.logistics.bean.WorkCenterBadItem;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.machining.activity.ProduceJobActivity;
import com.example.logistics.machining.adapter.ReasonReportAdapter;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utilsf.SelfPlanTimeUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author : jyx
 * @description:外协发货上报
 * @date :29.11.21
 */
public class OutAssistDialog extends Dialog {
    private Spinner et_jockey;
    private TextView tv_save, tv_cancel, et_matter_name;
    private OnSaveListen onSaveListen;
    private OnScanListen onScanListen;
    private Context context;
    private List<OutWorkOrderBean> badItemList;
    private int peoplePosition = 0;
    private ArrayList<String> peopleList = new ArrayList<>();
    private List<UserListBean> userData;

    public OutAssistDialog(@NonNull Context context) {
        super(context);
    }

    public OutAssistDialog(@NonNull Context context, int themeResId, List<OutWorkOrderBean> badItemList) {
        super(context, themeResId);
        this.context = context;
        this.badItemList = badItemList;
    }

    protected OutAssistDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_out_assist);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
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

        et_jockey = findViewById(R.id.et_jockey);//发货人员
        et_matter_name = findViewById(R.id.et_matter_name);//发货时间

        tv_save = findViewById(R.id.tv_save);//保存
        tv_cancel = findViewById(R.id.tv_cancel);//取消
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSubmit(v);
                //  dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        et_matter_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelfPlanTimeUtil.getInstance(context).showBirthdayDate(context, "时间选择", 2, 4);
            }
        });
        getSpinner();

    }

    private void getSpinner() {
        RequestUserListBean userList = UserLocalData.getUserList();
        userData = userList.getData();
        for (int i = 0; i < userData.size(); i++) {
            peopleList.add(userData.get(i).getUsername());
        }
        ArrayAdapter<String> peopleAdapter = new ArrayAdapter<>(context, R.layout.item_spinner, peopleList);
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
     * 提交
     *
     * @param v
     */
    private void getSubmit(View v) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        RequestDeliverGoodsBean goodsBean = new RequestDeliverGoodsBean();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < badItemList.size(); i++) {
            if (badItemList.get(i).getCheck()) {
                ids.add(badItemList.get(i).getOutsourceWorkOrderdId());
            }
        }
        goodsBean.setIds(ids);
        goodsBean.setConsigner(Integer.valueOf(userData.get(peoplePosition).getUserId()));
        goodsBean.setConsignerName(TextUtils.isEmpty(userData.get(peoplePosition).getUsername()) ? "" : userData.get(peoplePosition).getUsername());
        goodsBean.setDeliveryTime(TextUtils.isEmpty(et_matter_name.getText().toString()) ? "" : et_matter_name.getText().toString());
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            String s = JSON.toJSONString(goodsBean);
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(goodsBean));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/plansp/outsourceworkorderd/deliverGoods")
                    .put(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Log.i(TAG, "onFailure: 失败");

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
                                    if (onSaveListen != null) {

                                        onSaveListen.onClick(v);
                                        dismiss();
                                    }
                                    Toast.makeText(App.getAppContext(), "提交成功", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(App.getAppContext(), "" + submitBean.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });


                    //  Log.i(TAG, "onResponse: 成功 " + string);
                }
            });
        }
    }

    @Subscribe  //订阅事件
    public void onEventMainThread(TimeBean evBean) {
        switch (evBean.getType()) {
            case 4:
                et_matter_name.setText("" + evBean.getTime());
                break;
        }


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
