package com.example.logistics.machining.activity.assist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.DefectStateCount;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.OutAssistCountBean;
import com.example.logistics.bean.OutWorkOrderBean;
import com.example.logistics.bean.RecordsBadItemListBean;
import com.example.logistics.bean.RequestDefectStateCount;
import com.example.logistics.bean.RequestOutAssistStateCountBean;
import com.example.logistics.bean.RequestOutSourceWorkOrderBean;
import com.example.logistics.bean.RequestWorkBadItemListBean;
import com.example.logistics.bean.TimeBean;
import com.example.logistics.bean.ViceCard;
import com.example.logistics.dialog.DefectiveDialog;
import com.example.logistics.dialog.OutAssistDialog;
import com.example.logistics.home.activity.defects.DefectProductActivity;
import com.example.logistics.home.activity.defects.DefectProductDealActivity;
import com.example.logistics.home.adapter.DefectProductListAdapter;
import com.example.logistics.machining.activity.MergeActivity;
import com.example.logistics.machining.activity.ProduceJobActivity;
import com.example.logistics.machining.adapter.OutAssistListAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.AppUtil;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.example.logistics.utilsf.SelfPlanTimeUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 外协
 */
public class OutAssistActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.tv_add)//新增
    TextView tv_add;
    @BindView(R.id.tv_submit)//发货
    TextView tv_submit;
    @BindView(R.id.et_process_card)//外协单号
    EditText et_process_card;
    @BindView(R.id.img_process_card)//外协扫码
    ImageView img_process_card;
    @BindView(R.id.et_self_parts)//物料
    EditText et_self_parts;
    @BindView(R.id.img_self_parts)//物料搜索
    ImageView img_self_parts;
    @BindView(R.id.et_time_create)//创建日期
    TextView et_time_create;
    @BindView(R.id.et_time_delivery)//外协交期
    TextView et_time_delivery;
    @BindView(R.id.tv_pending)//待发货
    TextView tv_pending;
    @BindView(R.id.tv_processing)//已发货
    TextView tv_processing;
    @BindView(R.id.tv_completed)//部分到货
    TextView tv_completed;
    @BindView(R.id.tv_delayed)//全部到货
    TextView tv_delayed;
    @BindView(R.id.linear_all)//全选
    LinearLayoutCompat linear_all;
    @BindView(R.id.rb_select_all)//
    ImageView rb_select_all;
    @BindView(R.id.refreshLayout)//刷新
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rcy_task)//外协列表
    RecyclerView rcy_task;
    private int REQUEST_CODE_SCAN = 100;
    private boolean isFirst = true;
    private LoadingDialog loadingDialog;
    private String state = "";//状态 0待发货  1已发货  2部分到货   3全部到货
    private OutAssistListAdapter outAssistListAdapter;
    private int page = 1;
    private boolean isCheck = false;
    private List<OutWorkOrderBean> productList = new ArrayList<>();

    @Override
    protected int initLayout() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return R.layout.activity_out_assist;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        tv_add.setOnClickListener(this);
        img_return.setOnClickListener(this);
        img_process_card.setOnClickListener(this);
        img_self_parts.setOnClickListener(this);
        et_time_create.setOnClickListener(this);
        et_time_delivery.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
        tv_processing.setOnClickListener(this);
        tv_completed.setOnClickListener(this);
        tv_delayed.setOnClickListener(this);
        linear_all.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


        initRcy();

    }


    @Override
    protected void initData() {

        getOutAssisList();
        getOutAssisState();
        /**
         * 刷新
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //请求数据
                getOutAssisList();


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
                getOutAssisList();

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
                    getOutAssisList();
                    getOutAssisState();

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
                    getOutAssisList();
                    getOutAssisState();
                    return true;
                }
                return false;
            }
        });
    }

    private void getOutAssisState() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/outsourceworkorderd/app/countByState")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("state", state + "")
                    .addParam("outsourceDeliveryTime", TextUtils.isEmpty(et_time_delivery.getText().toString()) ? "" : et_time_delivery.getText().toString())
                    .addParam("createTime", TextUtils.isEmpty(et_time_create.getText().toString()) ? "" : et_time_create.getText().toString())
                    .addParam("serialNumber", TextUtils.isEmpty(et_process_card.getText().toString()) ? "" : et_process_card.getText().toString())
                    .addParam("materialName", TextUtils.isEmpty(et_self_parts.getText().toString()) ? "" : et_self_parts.getText().toString())
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestOutAssistStateCountBean requestOutAssistStateCountBean = new Gson().fromJson(data, RequestOutAssistStateCountBean.class);
                            if (requestOutAssistStateCountBean.getCode() == 0) {
                                List<OutAssistCountBean> stateCountBeanData = requestOutAssistStateCountBean.getData();
                                if (stateCountBeanData.size() == 0) {
                                    tv_pending.setText("待发货\n" + "(0)");
                                    tv_completed.setText("部分到货\n" + "(0)");
                                    tv_processing.setText("已发货\n" + "(0)");
                                    tv_delayed.setText("全部到货\n" + "(0)");
                                    return;
                                }
                                for (int i = 0; i < stateCountBeanData.size(); i++) {
                                    if (stateCountBeanData.get(i).getState().equals("0")) {//待发货
                                        tv_pending.setText("待发货\n" + "(" + stateCountBeanData.get(i).getCount() + ")");
                                    } else if (stateCountBeanData.get(i).getState().equals("1")) {//已发货
                                        tv_processing.setText("已发货\n" + "(" + stateCountBeanData.get(i).getCount() + ")");
                                    } else if (stateCountBeanData.get(i).getState().equals("2")) {//部分到货
                                        tv_completed.setText("部分到货\n" + "(" + stateCountBeanData.get(i).getCount() + ")");
                                    } else if (stateCountBeanData.get(i).getState().equals("3")) {//全部到货
                                        tv_delayed.setText("全部到货\n" + "(" + stateCountBeanData.get(i).getCount() + ")");
                                    }
                                }
                            } else {
                                Toast.makeText(OutAssistActivity.this, "" + requestOutAssistStateCountBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(OutAssistActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    private void getOutAssisList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        String s = et_time_delivery.getText().toString();
        String s1 = et_time_create.getText().toString();

        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/outsourceworkorderd/page")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .addParam("state", state + "")
                    .addParam("outsourceDeliveryTime", TextUtils.isEmpty(et_time_delivery.getText().toString()) ? "" : et_time_delivery.getText().toString())
                    .addParam("createTime", TextUtils.isEmpty(et_time_create.getText().toString()) ? "" : et_time_create.getText().toString())
                    .addParam("serialNumber", TextUtils.isEmpty(et_process_card.getText().toString()) ? "" : et_process_card.getText().toString())
                    .addParam("materialName", TextUtils.isEmpty(et_self_parts.getText().toString()) ? "" : et_self_parts.getText().toString())
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestOutSourceWorkOrderBean requestOutSourceWorkOrderBean = new Gson().fromJson(data, RequestOutSourceWorkOrderBean.class);
                            if (requestOutSourceWorkOrderBean.getCode() == 0) {
                                List<OutWorkOrderBean> outWorkOrderBeans = requestOutSourceWorkOrderBean.getData().getRecords();
                                if (outWorkOrderBeans == null) return;
                                if (page == 1) {
                                    outAssistListAdapter.initList(outWorkOrderBeans);
                                    refreshLayout.finishRefresh();

                                } else {
                                    outAssistListAdapter.addList(outWorkOrderBeans);
                                    refreshLayout.finishLoadMore();
                                }
                                outAssistListAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(OutAssistActivity.this, "" + requestOutSourceWorkOrderBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(OutAssistActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    private void initRcy() {
        outAssistListAdapter = new OutAssistListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(outAssistListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://返回上层
                finish();
                break;
            case R.id.tv_submit://发货
                productList.clear();
                List<OutWorkOrderBean> outAssistListAdapterList = outAssistListAdapter.getList();
                for (int i = 0; i < outAssistListAdapterList.size(); i++) {
                    if (outAssistListAdapterList.get(i).getCheck()) {
                        productList.add(outAssistListAdapterList.get(i));
                    }
                }
                if (productList.size() == 0) {
                    ViewShowUtils.showShortToast(OutAssistActivity.this, "请先选中待发货状态");
                } else {//发货
                    OutAssistDialog assistDialog = new OutAssistDialog(OutAssistActivity.this, R.style.feed_dialog, productList);
                    assistDialog.show();
                    assistDialog.setSaveListener(new OutAssistDialog.OnSaveListen() {
                        @Override
                        public void onClick(View view) {
                            page = 1;
                            getOutAssisList();
                            getOutAssisState();
                        }
                    });

                }
                break;
            case R.id.img_process_card://扫码
                Intent intent = new Intent(OutAssistActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.tv_add://新增外协单
                startActivity(new Intent(OutAssistActivity.this, OutAssistOrderActivity.class));
                break;
            case R.id.img_self_parts://物料搜索
                page = 1;
                getOutAssisList();
                break;
            case R.id.et_time_create://创建日期
                SelfPlanTimeUtil.getInstance(this).showBirthdayDate(this, "时间选择", 2, 3);
                break;
            case R.id.et_time_delivery://外协交期
                SelfPlanTimeUtil.getInstance(this).showBirthdayDate(this, "时间选择", 2, 2);
                break;
            case R.id.tv_pending://待发货
                state = "0";
                page = 1;
                getOutAssisList();
                linear_all.setVisibility(View.VISIBLE);
                tv_submit.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_processing://已发货
                state = "1";
                page = 1;
                getOutAssisList();
                linear_all.setVisibility(View.GONE);
                tv_submit.setVisibility(View.GONE);
                break;
            case R.id.tv_completed://部分到货
                state = "2";
                page = 1;
                getOutAssisList();
                linear_all.setVisibility(View.GONE);
                tv_submit.setVisibility(View.GONE);
                break;
            case R.id.tv_delayed://全部到货
                state = "3";
                page = 1;
                getOutAssisList();
                linear_all.setVisibility(View.GONE);
                tv_submit.setVisibility(View.GONE);
                break;
            case R.id.linear_all://全选
                if (!isCheck) {
                    rb_select_all.setImageResource(R.drawable.rb_check);
                    isCheck = true;
                    outAssistListAdapter.CheckAll(true);
                } else {
                    rb_select_all.setImageResource(R.drawable.rb_unchekck);
                    isCheck = false;
                    outAssistListAdapter.CheckAll(false);
                }
                outAssistListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }


    @Subscribe  //订阅事件
    public void onEventMainThread(TimeBean evBean) {
        switch (evBean.getType()) {
            case 2:
                et_time_delivery.setText("" + evBean.getTime());
                page = 1;
                getOutAssisList();
                getOutAssisState();
                break;
            case 3:
                et_time_create.setText("" + evBean.getTime());
                page = 1;
                getOutAssisList();
                getOutAssisState();
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String content = data.getStringExtra(Constant.CODED_CONTENT);
        et_process_card.setText(content + "");
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                page = 1;
                getOutAssisList();
                getOutAssisState();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        getOutAssisList();
        getOutAssisState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.close();
        EventBus.getDefault().unregister(this);
    }
}