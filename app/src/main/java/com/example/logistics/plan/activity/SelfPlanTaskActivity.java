package com.example.logistics.plan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RecordsWorkShopListBean;
import com.example.logistics.bean.RequestSelfPlanListBean;
import com.example.logistics.bean.RequestSelfPlanTotalListBean;
import com.example.logistics.bean.RequestTotalCountBean;
import com.example.logistics.bean.RequestWorkShopListBean;
import com.example.logistics.bean.SelfPlanListBean;
import com.example.logistics.bean.SelfPlanTotalListBean;
import com.example.logistics.bean.TimeBean;
import com.example.logistics.bean.TotalCountBean;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.plan.adapter.SelfPlanTaskAdapter;
import com.example.logistics.plan.adapter.WorkShopTaskAdapter;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utilsf.SelfPlanTimeUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * ???????????????????????????
 */
public class SelfPlanTaskActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//????????????
    RecyclerView rcy_task;
    @BindView(R.id.refreshLayout)//??????
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.et_search)//???????????????
    EditText et_search;
    @BindView(R.id.et_matter_search)//????????????
    EditText et_matter_search;
    @BindView(R.id.et_time_search)//??????
    TextView et_time_search;
    @BindView(R.id.tv_pending)//?????????
    TextView tv_pending;
    @BindView(R.id.tv_delay)//?????????
    TextView tv_delay;
    @BindView(R.id.tv_completed)//?????????
    TextView tv_completed;
    @BindView(R.id.img_search)//???????????????
    ImageView img_search;
    @BindView(R.id.img_matter_search)//????????????
    ImageView img_matter_search;

    private int REQUEST_CODE_SCAN = 100;
    private SelfPlanTaskAdapter selfPlanTaskAdapter;
    private int page = 1;
    private String productionSpState = "";
    private String delivery = "";
    private boolean isFirst = true;

    @Override
    protected int initLayout() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return R.layout.activity_self_plan_task;
    }

    @Override
    protected void initView() {
        img_matter_search.setOnClickListener(this);
        img_search.setOnClickListener(this);
        tv_completed.setOnClickListener(this);
        tv_delay.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
        et_time_search.setOnClickListener(this);
        img_return.setOnClickListener(this);


        initRcy();//???????????????
        getTaskList();//??????????????????
        getTotalList();//??????????????????
    }


    @Override
    protected void initData() {
        /**
         * ??????
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //????????????
                getTaskList();


            }
        });
        /**
         * ????????????
         */
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                page++;
                //????????????
                getTaskList();

            }
        });

        /***
         * ????????????????????????????????????
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//???????????????
                    page = 1;
                    getTaskList();
                    getTotalList();
                    return true;
                }
                return false;
            }
        });

        /***
         * ????????????????????????????????????
         */
        et_matter_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//???????????????
                    page = 1;
                    getTaskList();
                    getTotalList();
                    return true;
                }
                return false;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFirst == false) return;
                if (!TextUtils.isEmpty(s)) {
                    page = 1;
                    getTaskList();
                    getTotalList();
                    isFirst = false;
                }
            }
        });
    }

    private void initRcy() {
        selfPlanTaskAdapter = new SelfPlanTaskAdapter(SelfPlanTaskActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelfPlanTaskActivity.this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(selfPlanTaskAdapter);
    }

    /**
     * ??????????????????
     */
    private void getTotalList() {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/plansp/appCount")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("materialCodeName", TextUtils.isEmpty(et_matter_search.getText().toString()) ? "" : et_matter_search.getText().toString())
                    .addParam("planSpCode", TextUtils.isEmpty(et_search.getText().toString()) ? "" : et_search.getText().toString())
                    .addParam("endTime", TextUtils.isEmpty(et_time_search.getText().toString()) ? "" : et_time_search.getText().toString())
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestSelfPlanTotalListBean requestSelfPlanTotalListBean = new Gson().fromJson(data, RequestSelfPlanTotalListBean.class);
                            if (requestSelfPlanTotalListBean.getCode() == 0) {
                                List<SelfPlanTotalListBean> requestSelfPlanTotalListBeanData = requestSelfPlanTotalListBean.getData();
                                if (requestSelfPlanTotalListBeanData.size() == 0) {
                                    tv_pending.setText("?????????\n(0)");
                                    tv_delay.setText("?????????\n(0)");
                                    tv_completed.setText("?????????\n(0)");
                                    return;
                                }
                                for (int i = 0; i < requestSelfPlanTotalListBeanData.size(); i++) {
                                    if (requestSelfPlanTotalListBeanData.get(i).getType().equals("0")) {//??????
                                        tv_pending.setText("?????????\n(" + requestSelfPlanTotalListBeanData.get(i).getCount() + ")");

                                    } else if (requestSelfPlanTotalListBeanData.get(i).getType().equals("1")) {//?????????
                                        tv_delay.setText("?????????\n(" + requestSelfPlanTotalListBeanData.get(i).getCount() + ")");
                                    } else if (requestSelfPlanTotalListBeanData.get(i).getType().equals("2")) {//?????????
                                        tv_completed.setText("?????????\n(" + requestSelfPlanTotalListBeanData.get(i).getCount() + ")");
                                    }
                                }

                            } else {
                                Toast.makeText(SelfPlanTaskActivity.this, "" + requestSelfPlanTotalListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(SelfPlanTaskActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }


    /**
     * ??????????????????
     */
    private void getTaskList() {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/plansp/page")
                    // ?????????????????????
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .addParam("materialCodeName", TextUtils.isEmpty(et_matter_search.getText().toString()) ? "" : et_matter_search.getText().toString())
                    .addParam("planSpCode", TextUtils.isEmpty(et_search.getText().toString()) ? "" : et_search.getText().toString())//
                    .addParam("endTime", TextUtils.isEmpty(et_time_search.getText().toString()) ? "" : et_time_search.getText().toString())
                    .addParam("productionSpState", productionSpState + "")//0?????????  1?????????  2?????????
                    .addParam("delivery", delivery + "")//1#??????????????????  #  0????????????(
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestSelfPlanListBean requestSelfPlanListBean = new Gson().fromJson(data, RequestSelfPlanListBean.class);
                            if (requestSelfPlanListBean.getCode() == 0) {
                                List<SelfPlanListBean> selfPlanListBeans = requestSelfPlanListBean.getData().getRecords();
                                if (selfPlanListBeans == null) return;
                                if (page == 1) {
                                    selfPlanTaskAdapter.initList(selfPlanListBeans);
                                    refreshLayout.finishRefresh();

                                } else {
                                    selfPlanTaskAdapter.addList(selfPlanListBeans);
                                    refreshLayout.finishLoadMore();
                                }
                                selfPlanTaskAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(SelfPlanTaskActivity.this, "" + requestSelfPlanListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(SelfPlanTaskActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.img_matter_search://????????????
                page = 1;
                getTaskList();
                getTotalList();
                break;
            case R.id.img_search://???????????????
                Intent intent = new Intent(SelfPlanTaskActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.tv_completed://?????????
                page = 1;
                productionSpState = "2";
                delivery = "";
                getTaskList();
                break;
            case R.id.tv_delay://?????????
                page = 1;
                delivery = "1";
                productionSpState = "";
                getTaskList();
                break;
            case R.id.tv_pending://?????????
                page = 1;
                delivery = "0";
                productionSpState = "";
                getTaskList();
                break;
            case R.id.et_time_search://??????
                SelfPlanTimeUtil.getInstance(this).showBirthdayDate(this, "????????????", 2, 0);
                break;
        }
    }

    @Subscribe  //????????????
    public void onEventMainThread(TimeBean evBean) {
        switch (evBean.getType()) {
            case 0:
                page = 1;
                et_time_search.setText("" + evBean.getTime());
                getTotalList();
                getTaskList();
                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ???????????????/????????????
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("??????????????????" + content);
                et_search.setText(content+"");
                page = 1;
                getTaskList();
                getTotalList();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}