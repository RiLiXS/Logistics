package com.example.logistics.machining.activity.turnover;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.logistics.App;
import com.example.logistics.LoginActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.DefectStateCount;
import com.example.logistics.bean.DeptListBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RecordsTurnOverListBean;
import com.example.logistics.bean.RequestDefectStateCount;
import com.example.logistics.bean.RequestDeptListBean;
import com.example.logistics.bean.RequestTurnOverListBean;
import com.example.logistics.bean.RequestTurnOverStateCount;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.bean.TurnOverCount;
import com.example.logistics.home.activity.defects.DefectProductActivity;
import com.example.logistics.machining.adapter.TurnOverListAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;

import org.jetbrains.annotations.NotNull;

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
 * 周转
 */
public class TurnOverActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//周转任务列表
    RecyclerView rcy_task;
    @BindView(R.id.et_process_card)//流程卡号
    EditText et_process_card;
    @BindView(R.id.img_process_card)//流程卡号扫码
    ImageView img_process_card;
    @BindView(R.id.et_self_parts)//自制件
    EditText et_self_parts;
    @BindView(R.id.img_self_parts)//自制件搜索
    ImageView img_self_parts;
    @BindView(R.id.spinner_turnover_workshop)//转出车间
    Spinner spinner_turnover_workshop;
    @BindView(R.id.spinner_out_workshop)//转入车间
    Spinner spinner_out_workshop;
    @BindView(R.id.tv_pending)//待周转
    TextView tv_pending;
    @BindView(R.id.tv_completed)//已周转
    TextView tv_completed;
    @BindView(R.id.rb_select_all)//全选按钮
    ImageView rb_select_all;
    @BindView(R.id.refreshLayout)//刷新
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_submit)//提交
    TextView tv_submit;
    @BindView(R.id.linear_all)//全选
    LinearLayoutCompat linear_all;
    private boolean isCheck = false;
    private int page = 1;
    private String turnoverState = "";
    private String rollOutLocationId = "";
    private String shiftToLocationId = "";
    private int REQUEST_CODE_SCAN = 100;
    private TurnOverListAdapter turnOverListAdapter;
    private List<DeptListBean> deptListBeanData = null;
    private ArrayList<String> deptList = new ArrayList<>();
    private int deptOutPosition = 0;
    private int deptEnterPosition = 0;
    private boolean isSpinnerFirst = true;
    private boolean isDeptSpinnerFirst = true;
    private LoadingDialog loadingDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_turn_over;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        img_return.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
        tv_completed.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        img_self_parts.setOnClickListener(this);
        img_process_card.setOnClickListener(this);
        linear_all.setOnClickListener(this);
        initRcy();//初始化列表
        getTurnOverList();//获取周转列表
        getDeptList();//获取地点
        getTurnOverState();//获取周转状态
    }


    @Override
    protected void initData() {
        /**
         * 刷新
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //请求数据
                getTurnOverList();


            }
        });
        /**
         * 加载更多
         */
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                page++;
                //请求数据
                getTurnOverList();

            }
        });
        et_process_card.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    page = 1;
                    getTurnOverList();
                    getTurnOverState();
                }
            }
        });

        /***
         * 监听输入框是否点击了搜索
         */
        et_self_parts.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                    page = 1;
                    getTurnOverList();
                    getTurnOverState();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取周转状态
     */
    private void getTurnOverState() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/turnoverinvoices/getCount/byTurnoverState")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("barcode",TextUtils.isEmpty(et_process_card.getText().toString())?"":et_process_card.getText().toString()+"")
                    .addParam("planNo",TextUtils.isEmpty(et_self_parts.getText().toString())?"":et_self_parts.getText().toString()+"")
                    .addParam("rollOutLocationId",rollOutLocationId+"")
                    .addParam("shiftToLocationId",shiftToLocationId+"")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestTurnOverStateCount requestTurnOverStateCount = new Gson().fromJson(data, RequestTurnOverStateCount.class);
                            if (requestTurnOverStateCount.getCode() == 0) {
                                List<TurnOverCount> turnOverStateCountData = requestTurnOverStateCount.getData();
                                if (turnOverStateCountData.size() == 0){
                                    tv_pending.setText("待周转\n" + "(0)");
                                    tv_completed.setText("已周转\n" + "(0)");
                                    return;
                                }
                                for (int i = 0; i < turnOverStateCountData.size(); i++) {
                                    if (turnOverStateCountData.get(i).getTurnoverState().equals("0")) {//待处理
                                        tv_pending.setText("待周转\n" + "(" + turnOverStateCountData.get(i).getCount() + ")");
                                    } else if (turnOverStateCountData.get(i).getTurnoverState().equals("1")) {//已处理
                                        tv_completed.setText("已周转\n" + "(" + turnOverStateCountData.get(i).getCount() + ")");
                                    }
                                }
                            } else {
                                Toast.makeText(TurnOverActivity.this, "" + requestTurnOverStateCount.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(TurnOverActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    private void initRcy() {
        turnOverListAdapter = new TurnOverListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(turnOverListAdapter);
    }

    private void getDeptList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/dept/list")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestDeptListBean requestDeptListBean = new Gson().fromJson(data, RequestDeptListBean.class);
                            if (requestDeptListBean.getCode() == 0) {
                                List<DeptListBean> requestDeptListBeanData = requestDeptListBean.getData();
                                if (requestDeptListBeanData == null) return;
                                deptListBeanData = requestDeptListBeanData;
                                initDeptList(requestDeptListBeanData);//初始化地点
                            } else {
                                Toast.makeText(TurnOverActivity.this, "" + requestDeptListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(TurnOverActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    /**
     * 初始化地点
     *
     * @param requestDeptListBeanData
     */
    private void initDeptList(List<DeptListBean> requestDeptListBeanData) {
        deptList.clear();
        for (int i = 0; i < requestDeptListBeanData.size(); i++) {
            deptList.add(requestDeptListBeanData.get(i).getName());
        }

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(TurnOverActivity.this, R.layout.item_spinner, deptList);
        spinner_turnover_workshop.setAdapter(typeAdapter);
        spinner_turnover_workshop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isDeptSpinnerFirst) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    //  view.setVisibility(View.INVISIBLE);
                    isDeptSpinnerFirst = false;
                    return;
                }

                deptOutPosition = position;


                //请求数据
                rollOutLocationId = requestDeptListBeanData.get(position).getDeptId();
                page = 1;
                getTurnOverList();
                getTurnOverState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> enterAdapter = new ArrayAdapter<>(TurnOverActivity.this, R.layout.item_spinner, deptList);
        spinner_out_workshop.setAdapter(enterAdapter);
        spinner_out_workshop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerFirst) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    //  view.setVisibility(View.INVISIBLE);
                    isSpinnerFirst = false;
                    return;
                }

                //   deptOutPosition = position;


                //请求数据
                shiftToLocationId = requestDeptListBeanData.get(position).getDeptId();
                page = 1;
                getTurnOverList();
                getTurnOverState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取周转列表
     */
    private void getTurnOverList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/turnoverinvoices/page")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .addParam("rollOutLocationId", rollOutLocationId + "")
                    .addParam("shiftToLocationId", shiftToLocationId + "")
                    .addParam("turnoverState", turnoverState + "")
                    .addParam("planNo", TextUtils.isEmpty(et_self_parts.getText().toString()) ? "" : et_self_parts.getText().toString())
                    .addParam("barcode", TextUtils.isEmpty(et_process_card.getText().toString()) ? "" : et_process_card.getText().toString())
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestTurnOverListBean requestTurnOverListBean = new Gson().fromJson(data, RequestTurnOverListBean.class);
                            if (requestTurnOverListBean.getCode() == 0) {
                                List<RecordsTurnOverListBean> recordsTurnOverListBeans = requestTurnOverListBean.getData().getRecords();
                                if (recordsTurnOverListBeans == null) return;
                                if (page == 1) {
                                    turnOverListAdapter.initList(recordsTurnOverListBeans);
                                    refreshLayout.finishRefresh();

                                } else {
                                    turnOverListAdapter.addList(recordsTurnOverListBeans);
                                    refreshLayout.finishLoadMore();
                                }
                                turnOverListAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(TurnOverActivity.this, "" + requestTurnOverListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(TurnOverActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://返回上一层
                finish();
                break;
            case R.id.tv_pending://待周转
                turnoverState = "0";
                page = 1;
                getTurnOverList();
                break;
            case R.id.tv_completed://已周转
                turnoverState = "1";
                page = 1;
                getTurnOverList();
                break;
            case R.id.tv_submit://提交
                getSubmit();
                break;
            case R.id.img_self_parts://自制件搜索
                page = 1;
                getTurnOverList();
                getTurnOverState();
                break;
            case R.id.img_process_card://扫码
                Intent intent = new Intent(TurnOverActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.linear_all://全选
                if (!isCheck) {
                    rb_select_all.setImageResource(R.drawable.rb_check);
                    isCheck = true;
                    turnOverListAdapter.CheckAll(true);
                } else {
                    rb_select_all.setImageResource(R.drawable.rb_unchekck);
                    isCheck = false;
                    turnOverListAdapter.CheckAll(false);
                }
                turnOverListAdapter.notifyDataSetChanged();
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                page = 1;
                getTurnOverList();
                getTurnOverState();
            }
        }
    }

    /**
     * 提交
     */
    private void getSubmit() {
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .show();
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        List<RecordsTurnOverListBean> turnOverListAdapterList = turnOverListAdapter.getList();
        List<Integer> turnOverList = new ArrayList<>();
        for (int i = 0; i < turnOverListAdapterList.size(); i++) {
            if (turnOverListAdapterList.get(i).isChecked()) {
                turnOverList.add(turnOverListAdapterList.get(i).getTurnoverInvoicesId());
            }
        }
        if (!TextUtils.isEmpty(access_token)) {

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            String s = JSON.toJSONString(turnOverList);
            RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(turnOverList));
            Request request = new Request.Builder()
                    .url(C.BASE_HOST + "/workCenter/turnoverinvoices/turnover")
                    .put(requestBody)
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
                                SubmitBean submitBean = new Gson().fromJson(string, SubmitBean.class);
                                if (submitBean == null) return;
                                if (submitBean.getCode() == 0) {
                                    page=1;
                                    getTurnOverState();
                                    getTurnOverList();
                                    Toast.makeText(App.getAppContext(), "提交成功", Toast.LENGTH_SHORT).show();
                                    loadingDialog.loadSuccess();


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
}