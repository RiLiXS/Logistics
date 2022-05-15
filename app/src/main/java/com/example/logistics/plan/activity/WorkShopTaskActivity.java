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
import com.example.logistics.bean.RecordsPlanListBean;
import com.example.logistics.bean.RecordsWorkShopListBean;
import com.example.logistics.bean.RequestPlanListBean;
import com.example.logistics.bean.RequestSelfPlanTotalListBean;
import com.example.logistics.bean.RequestWorkShopListBean;
import com.example.logistics.bean.RequestWorkShopTotalBean;
import com.example.logistics.bean.SelfPlanTotalListBean;
import com.example.logistics.bean.WorkShopTotalBean;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.plan.PlanManageAdapter;
import com.example.logistics.plan.adapter.WorkShopTaskAdapter;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;


/**
 * 车间任务通用Activity
 */
public class WorkShopTaskActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//任务列表
    RecyclerView rcy_task;
    @BindView(R.id.tv_title)//标题
    TextView tv_title;
    @BindView(R.id.refreshLayout)//刷新
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.et_search)//流程卡号
    EditText et_search;
    @BindView(R.id.et_matter_search)//物料名称
    EditText et_matter_search;
    @BindView(R.id.img_search)//流程卡扫码
    ImageView img_search;
    @BindView(R.id.img_matter_search)//物料搜索
    ImageView img_matter_search;
    @BindView(R.id.tv_pending)//未完工
    TextView tv_pending;
    @BindView(R.id.tv_completed)//已完工
    TextView tv_completed;
    private int type = 0;
    private WorkShopTaskAdapter workShopTaskAdapter;
    private int page = 1;
    private String isFinish = "";
    private int REQUEST_CODE_SCAN = 100;
    private boolean isFirst = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_work_shop_task;
    }

    @Override
    protected void initView() {
        tv_completed.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
        img_return.setOnClickListener(this);
        img_matter_search.setOnClickListener(this);
        img_search.setOnClickListener(this);
        type = getIntent().getIntExtra(C.plan.PLAN_TYPE, 0);
        switch (type) {
            case 1://清洗
                tv_title.setText("清洗任务");
                break;
            case 2://冷加工
                tv_title.setText("冷加工任务");
                break;
            case 3://镀膜
                tv_title.setText("镀膜任务");
                break;
            case 4://光刻
                tv_title.setText("光刻任务");
                break;
            case 5://全检
                tv_title.setText("全检任务");
                break;
            case 6://棱镜
                tv_title.setText("棱镜任务");
                break;
            default:
                break;
        }
        initRcy();//初始化列表
    }

    @Override
    protected void initData() {
        getTaskList();
        getTotalList();//统计
        /**
         * 刷新
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //请求数据
                getTaskList();



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
                getTaskList();


            }
        });


        /***
         * 监听输入框是否点击了搜索
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                    page = 1;
                    getTaskList();
                    getTotalList();
                    return true;
                }
                return false;
            }
        });

        /***
         * 监听输入框是否点击了搜索
         */
        et_matter_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
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

    /**
     * 统计
     */
    private void getTotalList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/app/countByisFinish")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("materialName", TextUtils.isEmpty(et_matter_search.getText().toString()) ? "" : et_matter_search.getText().toString())
                    .addParam("barcode", TextUtils.isEmpty(et_search.getText().toString()) ? "" : et_search.getText().toString())
                    .addParam("locationId", type + "")//
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestWorkShopTotalBean requestWorkShopTotalBean = new Gson().fromJson(data, RequestWorkShopTotalBean.class);
                            if (requestWorkShopTotalBean.getCode() == 0) {
                                List<WorkShopTotalBean> requestWorkShopTotalBeanData = requestWorkShopTotalBean.getData();
                                if (requestWorkShopTotalBeanData.size() == 0){
                                    tv_pending.setText("未完工\n(0)");
                                    tv_completed.setText("已完工\n(0)");
                                    return;
                                }
                                for (int i = 0; i < requestWorkShopTotalBeanData.size(); i++) {
                                    if (requestWorkShopTotalBeanData.get(i).getIsFinish().equals("0")) {//延期
                                        tv_pending.setText("未完工\n(" + requestWorkShopTotalBeanData.get(i).getCount() + ")");

                                    } else if (requestWorkShopTotalBeanData.get(i).getIsFinish().equals("1")) {//未延期
                                        tv_completed.setText("已完工\n(" + requestWorkShopTotalBeanData.get(i).getCount() + ")");
                                    }
                                }

                            } else {
                                Toast.makeText(WorkShopTaskActivity.this, "" + requestWorkShopTotalBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(WorkShopTaskActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }

    /**
     * 获取任务列表
     */
    private void getTaskList() {

        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/page")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .addParam("locationId", type + "")//
                    .addParam("materialName", TextUtils.isEmpty(et_matter_search.getText().toString()) ? "" : et_matter_search.getText().toString())//
                    .addParam("isFinish", isFinish + "")//
                    .addParam("barcode", TextUtils.isEmpty(et_search.getText().toString()) ? "" : et_search.getText().toString())//
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestWorkShopListBean requestWorkShopListBean = new Gson().fromJson(data, RequestWorkShopListBean.class);
                            if (requestWorkShopListBean.getCode() == 0) {
                                List<RecordsWorkShopListBean> recordsWorkShopListBeans = requestWorkShopListBean.getData().getRecords();
                                if (recordsWorkShopListBeans == null) return;
                                if (page == 1) {
                                    workShopTaskAdapter.initList(recordsWorkShopListBeans);
                                    refreshLayout.finishRefresh();

                                } else {
                                    workShopTaskAdapter.addList(recordsWorkShopListBeans);
                                    refreshLayout.finishLoadMore();
                                }
                                workShopTaskAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(WorkShopTaskActivity.this, "" + requestWorkShopListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(WorkShopTaskActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    private void initRcy() {
        workShopTaskAdapter = new WorkShopTaskAdapter(WorkShopTaskActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WorkShopTaskActivity.this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(workShopTaskAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://返回上一层
                finish();
                break;
            case R.id.tv_completed://已完工
                page=1;
                isFinish="1";
                getTotalList();//统计
                getTaskList();
                break;
            case R.id.tv_pending://未完工
                page=1;
                isFinish="0";
                getTotalList();//统计
                getTaskList();
                break;
            case R.id.img_matter_search://物料搜索
                page=1;
                getTotalList();//统计
                getTaskList();
                break;
            case R.id.img_search://流程卡扫码
                Intent intent = new Intent(WorkShopTaskActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("扫描结果为：" + content);
                et_search.setText(content+"");
                page = 1;
                getTaskList();
                getTotalList();
            }
        }
    }
}