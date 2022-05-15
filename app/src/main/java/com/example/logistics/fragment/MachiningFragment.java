package com.example.logistics.fragment;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.base.BaseFragment;
import com.example.logistics.bean.CardBean;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestProcessCardBean;
import com.example.logistics.home.HomeFunctionAdapter;
import com.example.logistics.machining.MachiningManageAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.plan.PlanManageAdapter;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * 加工
 */
public class MachiningFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rcy_process,rcy_function;
    private EditText et_search;
    private ImageView img_search;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<HomeFunctionBean> functionList = new ArrayList<>();
    private int page=1;
    private String access_token;
    private     MachiningManageAdapter machiningManageAdapter;
    private boolean isSearch = true;
    @Override
    protected int initLayout() {
        return R.layout.fragment_machining;
    }

    @Override
    protected void initView(View view) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        access_token = login.getAccess_token();//用户token
        rcy_process = view.findViewById(R.id.rcy_process);//流程卡列表
        rcy_function = view.findViewById(R.id.rcy_function);//功能列表

        et_search=view.findViewById(R.id.et_search);//搜索框
        img_search=view.findViewById(R.id.img_search);
        img_search.setOnClickListener(this);
        refreshLayout=view.findViewById(R.id.refreshLayout);//刷新
        machiningManageAdapter = new MachiningManageAdapter(getActivity());
        initProcessCard(page);//获取流程卡列表
    }

    private void initProcessCard(int page) {
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/page")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestProcessCardBean requestProcessCardBean = new Gson().fromJson(data, RequestProcessCardBean.class);
                            if (requestProcessCardBean == null) return;
                            if (requestProcessCardBean.getCode() == 0) {
                                List<CardBean> records = requestProcessCardBean.getData().getRecords();
                                if (page == 1) {
                                    machiningManageAdapter.initList(records);
                                    refreshLayout.finishRefresh();

                                } else {
                                    machiningManageAdapter.addList(records);
                                    refreshLayout.finishLoadMore();
                                }
                                machiningManageAdapter.notifyDataSetChanged();

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            refreshLayout.finishRefresh();
                            refreshLayout.finishLoadMore();
                        }


                    });
        }
    }

    private void initSearchProcessCard(int page,String str) {
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/processcard/page")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("current", page + "")
                    .addParam("barcode", str + "")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestProcessCardBean requestProcessCardBean = new Gson().fromJson(data, RequestProcessCardBean.class);
                            if (requestProcessCardBean == null) return;
                            if (requestProcessCardBean.getCode() == 0) {
                                List<CardBean> records = requestProcessCardBean.getData().getRecords();
                                if (page == 1) {
                                    machiningManageAdapter.initList(records);
                                    refreshLayout.finishRefresh();

                                } else {
                                    machiningManageAdapter.addList(records);
                                    refreshLayout.finishLoadMore();
                                }
                                machiningManageAdapter.notifyDataSetChanged();

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            refreshLayout.finishRefresh();
                            refreshLayout.finishLoadMore();
                        }


                    });
        }
    }

    @Override
    protected void initData(Context mContext) {
        initFunction();
        initProcess();
        /**
         * 刷新
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //请求数据
                initProcessCard(page);



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
                if (isSearch){
                    initProcessCard(page);
                }else{
                    initSearchProcessCard(page,et_search.getText().toString());
                }


            }
        });

        /***
         * 监听输入框是否点击了搜索
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                    page = 1;
                    //请求数据
                    isSearch = false;
                    initSearchProcessCard(page,et_search.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    private void initProcess() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcy_process.setLayoutManager(linearLayoutManager);
        rcy_process.setAdapter(machiningManageAdapter);
    }

    private void initFunction() {
        HomeFunctionAdapter homeFunctionAdapter = new HomeFunctionAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        rcy_function.setLayoutManager(gridLayoutManager);
        rcy_function.setAdapter(homeFunctionAdapter);

        functionList.add(new HomeFunctionBean(R.drawable.produce_icon, "投料", C.IntentionType.FEED));
        functionList.add(new HomeFunctionBean(R.drawable.feed_icon, "产出", C.IntentionType.PRODUCE));
        functionList.add(new HomeFunctionBean(R.drawable.split_icon, "拆分", C.IntentionType.SPLIT));
        functionList.add(new HomeFunctionBean(R.drawable.merge_icon, "合并", C.IntentionType.MERGE));
        functionList.add(new HomeFunctionBean(R.drawable.turnover_icon, "周转", C.IntentionType.TURNOVER));
        functionList.add(new HomeFunctionBean(R.drawable.out_assist_icon, "外协", C.IntentionType.OUT_ASSIST));
        functionList.add(new HomeFunctionBean(R.drawable.merge_icon, "转码合并", C.IntentionType.MERGE_CODE));

        homeFunctionAdapter.initList(functionList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_search://搜索
                //请求数据
                page = 1;
                //请求数据
                isSearch = false;
                initSearchProcessCard(page,et_search.getText().toString());
                break;
        }
    }
}