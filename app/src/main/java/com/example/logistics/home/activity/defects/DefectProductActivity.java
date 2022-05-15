package com.example.logistics.home.activity.defects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.example.logistics.MainActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.BadItemBean;
import com.example.logistics.bean.DefectStateCount;
import com.example.logistics.bean.DeptListBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.ProcessListBean;
import com.example.logistics.bean.RecordsBadItemListBean;
import com.example.logistics.bean.RequestBadItemBean;
import com.example.logistics.bean.RequestDefectStateCount;
import com.example.logistics.bean.RequestDeptListBean;
import com.example.logistics.bean.RequestProcessListBean;
import com.example.logistics.bean.RequestUserListBean;
import com.example.logistics.bean.RequestWorkBadItemListBean;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.home.activity.finall_test.FinalInspectionActivity;
import com.example.logistics.home.activity.special_test.SpecialInspectionActivity;
import com.example.logistics.home.adapter.DefectProductListAdapter;
import com.example.logistics.home.adapter.FirstListTaskAdapter;
import com.example.logistics.machining.activity.FeedingAddActivity;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 不合格品
 */
public class DefectProductActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//不合格品任务列表
    RecyclerView rcy_task;
    @BindView(R.id.et_process_card)//流程卡号
    EditText et_process_card;
    @BindView(R.id.img_process_card)//流程卡号扫码
    ImageView img_process_card;
    @BindView(R.id.et_self_parts)//自制件
    EditText et_self_parts;
    @BindView(R.id.img_self_parts)//自制件搜索
    ImageView img_self_parts;
    @BindView(R.id.spinner_turnover_workshop)//生产车间
    Spinner spinner_turnover_workshop;
    @BindView(R.id.spinner_out_workshop)//工序
    Spinner spinner_out_workshop;
    @BindView(R.id.spinner_defect_reason)//不合格原因
    Spinner spinner_defect_reason;
    @BindView(R.id.tv_pending)//待处理
    TextView tv_pending;
    @BindView(R.id.tv_completed)//已处理
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
    private DefectProductListAdapter defectProductListAdapter;
    private List<ProcessListBean> processListBeanData = null;
    private List<DeptListBean> deptListBeanData = null;
    private List<BadItemBean> badItemBeanData = null;
    private ArrayList<String> processList = new ArrayList<>();
    private int processPosition = 0;
    private boolean isSpinnerFirst = true;
    private ArrayList<String> deptList = new ArrayList<>();
    private int deptPosition = 0;
    private boolean isDeptSpinnerFirst = true;
    private ArrayList<String> badList = new ArrayList<>();
    private int badPosition = 0;
    private boolean isBadSpinnerFirst = true;
    private int page = 1;
    private List<RecordsBadItemListBean> productList = new ArrayList<>();
    private String processState = "";
    private String processId = "";
    private String locationId = "";
    private String badItemId = "";
    private int REQUEST_CODE_SCAN = 100;

    @Override
    protected int initLayout() {
        return R.layout.activity_defect_product;
    }

    @Override
    protected void initView() {
        img_return.setOnClickListener(this);
        img_self_parts.setOnClickListener(this);
        linear_all.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_completed.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
        img_process_card.setOnClickListener(this);
        initRcy();//初始化列表
        getDefectList();//获取不合格列表
        getDefectState();//获取不良品状态
        /**
         * 刷新
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //请求数据
                getDefectList();


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
                getDefectList();

            }
        });


    }


    @Override
    protected void initData() {
        getProcessList();//获取工序
        getDeptList();//获取地点
        getBadItem();//获取不合格原因
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
                    page=1;
                    getDefectList();
                    getDefectState();

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
                    page=1;
                    getDefectList();
                    getDefectState();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取不良状态
     */
    private void getDefectState() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/workcenterbaditem/getCount/byProcessState")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("barcode",TextUtils.isEmpty(et_process_card.getText().toString())?"":et_process_card.getText().toString()+"")
                    .addParam("planNo",TextUtils.isEmpty(et_self_parts.getText().toString())?"":et_self_parts.getText().toString()+"")
                    .addParam("processId",processId+"")
                    .addParam("locationId",locationId+"")
                    .addParam("badItemId",badItemId+"")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestDefectStateCount requestDefectStateCount = new Gson().fromJson(data, RequestDefectStateCount.class);
                            if (requestDefectStateCount.getCode() == 0) {
                                List<DefectStateCount> defectStateCountData = requestDefectStateCount.getData();
                                if (defectStateCountData.size()==0 ){
                                    tv_pending.setText("待处理\n" + "(0)");
                                    tv_completed.setText("已处理\n" + "(0)");
                                    return;
                                }
                                for (int i = 0; i < defectStateCountData.size(); i++) {
                                    if (defectStateCountData.get(i).getProcessState().equals("1")) {//待处理
                                        tv_pending.setText("待处理\n" + "(" + defectStateCountData.get(i).getCount() + ")");
                                    } else if (defectStateCountData.get(i).getProcessState().equals("2")) {//已处理
                                        tv_completed.setText("已处理\n" + "(" + defectStateCountData.get(i).getCount() + ")");
                                    }
                                }
                            } else {
                                Toast.makeText(DefectProductActivity.this, "" + requestDefectStateCount.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(DefectProductActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    /**
     * 不合格列表
     */
    private void getDefectList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/workcenterbaditem/page")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .addParam("processState", processState + "")
                    .addParam("processId", processId + "")
                    .addParam("locationId", locationId + "")
                    .addParam("badItemId", badItemId + "")
                    .addParam("planNo", TextUtils.isEmpty(et_self_parts.getText().toString()) ? "" : et_self_parts.getText().toString())
                    .addParam("barcode", TextUtils.isEmpty(et_process_card.getText().toString()) ? "" : et_process_card.getText().toString())
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestWorkBadItemListBean requestWorkBadItemListBean = new Gson().fromJson(data, RequestWorkBadItemListBean.class);
                            if (requestWorkBadItemListBean.getCode() == 0) {
                                List<RecordsBadItemListBean> recordsBadItemListBeans = requestWorkBadItemListBean.getData().getRecords();
                                if (recordsBadItemListBeans == null) return;
                                if (page == 1) {
                                    defectProductListAdapter.initList(recordsBadItemListBeans);
                                    refreshLayout.finishRefresh();

                                } else {
                                    defectProductListAdapter.addList(recordsBadItemListBeans);
                                    refreshLayout.finishLoadMore();
                                }
                                defectProductListAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(DefectProductActivity.this, "" + requestWorkBadItemListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(DefectProductActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    private void getBadItem() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/process/baditem/list?type=1")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestBadItemBean requestBadItemBean = new Gson().fromJson(data, RequestBadItemBean.class);
                            if (requestBadItemBean.getCode() == 0) {
                                List<BadItemBean> requestBadItemBeanData = requestBadItemBean.getData();
                                if (requestBadItemBeanData == null) return;
                                badItemBeanData = requestBadItemBeanData;
                                initBadList(requestBadItemBeanData);//初始化不良原因
                            } else {
                                Toast.makeText(DefectProductActivity.this, "" + requestBadItemBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(DefectProductActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
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
                                Toast.makeText(DefectProductActivity.this, "" + requestDeptListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(DefectProductActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }


    private void getProcessList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/process/process/list")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestProcessListBean requestProcessListBean = new Gson().fromJson(data, RequestProcessListBean.class);
                            if (requestProcessListBean.getCode() == 0) {
                                List<ProcessListBean> requestProcessListBeanData = requestProcessListBean.getData();
                                if (requestProcessListBeanData == null) return;
                                processListBeanData = requestProcessListBeanData;
                                initProcessList(requestProcessListBeanData);//初始化工序
                            } else {
                                Toast.makeText(DefectProductActivity.this, "" + requestProcessListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(DefectProductActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    /**
     * 初始化不良原因
     *
     * @param requestBadItemBeanData
     */
    private void initBadList(List<BadItemBean> requestBadItemBeanData) {
        badList.clear();
        for (int i = 0; i < requestBadItemBeanData.size(); i++) {
            badList.add(requestBadItemBeanData.get(i).getItemName());
        }

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(DefectProductActivity.this, R.layout.item_spinner, badList);
        spinner_defect_reason.setAdapter(typeAdapter);
        spinner_defect_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isBadSpinnerFirst) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    //  view.setVisibility(View.INVISIBLE);
                    isBadSpinnerFirst = false;
                    return;
                }

                badPosition = position;

                //请求数据
                badItemId = requestBadItemBeanData.get(position).getBadItemId();
                page=1;
                getDefectList();
                getDefectState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(DefectProductActivity.this, R.layout.item_spinner, deptList);
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

                deptPosition = position;


                //请求数据
                locationId = requestDeptListBeanData.get(position).getDeptId();
                page=1;
                getDefectList();
                getDefectState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 初始化工序
     *
     * @param requestProcessListBeanData
     */
    private void initProcessList(List<ProcessListBean> requestProcessListBeanData) {
        processList.clear();
        for (int i = 0; i < requestProcessListBeanData.size(); i++) {
            processList.add(requestProcessListBeanData.get(i).getProcessName());
        }

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(DefectProductActivity.this, R.layout.item_spinner, processList);
        spinner_out_workshop.setAdapter(typeAdapter);
        spinner_out_workshop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerFirst) {
                    //第一次初始化spinner时，不显示默认被选择的第一项即可
                    //  view.setVisibility(View.INVISIBLE);
                    isSpinnerFirst = false;
                    return;
                }

                processPosition = position;


                //请求数据
                processId = requestProcessListBeanData.get(position).getProcessId();
                page=1;
                getDefectList();
                getDefectState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRcy() {
        defectProductListAdapter = new DefectProductListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(defectProductListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return://返回上一层
                finish();
                break;
            case R.id.img_self_parts://自制件搜索
                page=1;
                getDefectList();
                getDefectState();
                break;
            case R.id.img_process_card://流程卡扫码
                Intent intent = new Intent(DefectProductActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.linear_all://全选
                if (!isCheck) {
                    rb_select_all.setImageResource(R.drawable.rb_check);
                    isCheck = true;
                    defectProductListAdapter.CheckAll(true);
                } else {
                    rb_select_all.setImageResource(R.drawable.rb_unchekck);
                    isCheck = false;
                    defectProductListAdapter.CheckAll(false);
                }
                defectProductListAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_submit://提交
                productList.clear();
                List<RecordsBadItemListBean> productListAdapterList = defectProductListAdapter.getList();
                for (int i = 0; i < productListAdapterList.size(); i++) {
                    if (productListAdapterList.get(i).isChecked()) {
                        productList.add(productListAdapterList.get(i));
                    }
                }
                if (productList.size() == 0) {
                    ViewShowUtils.showShortToast(DefectProductActivity.this, "请先选中不合格品");
                } else {
                    Intent intent2 = new Intent(DefectProductActivity.this, DefectProductDealActivity.class);//跳转不合格处理
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(C.task.DEFECT_BEAN, (Serializable) productList);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                }


                break;
            case R.id.tv_pending://待处理
                processState = "1";
                page=1;
                getDefectList();
                break;
            case R.id.tv_completed://已处理
                processState = "2";
                page=1;
                getDefectList();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String content = data.getStringExtra(Constant.CODED_CONTENT);
        et_process_card.setText(content+"");
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                page=1;
                getDefectList();
                getDefectState();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        getDefectList();
        getDefectState();
    }
}