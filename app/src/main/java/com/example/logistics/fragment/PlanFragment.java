package com.example.logistics.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.base.BaseFragment;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RecordsPlanListBean;
import com.example.logistics.bean.RecordsTurnOverListBean;
import com.example.logistics.bean.RequestPlanListBean;
import com.example.logistics.bean.RequestTotalSelfPlanBean;
import com.example.logistics.bean.RequestTurnOverListBean;
import com.example.logistics.bean.TotalSelfPlanBean;
import com.example.logistics.home.HomeFunctionAdapter;
import com.example.logistics.home.adapter.HomeTaskAdapter;
import com.example.logistics.machining.activity.turnover.TurnOverActivity;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.plan.PlanManageAdapter;
import com.example.logistics.plan.activity.SelfPlanTaskActivity;
import com.example.logistics.plan.adapter.PlanTaskAdapter;
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
 * 计划
 */
public class PlanFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rcy_task, rcy_function;
    private EditText et_search;
    private ImageView img_search;
    private TextView tv_pick_work;

    private ArrayList<HomeFunctionBean> functionList = new ArrayList<>();
    private int page = 1;
    private PlanTaskAdapter planTaskAdapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_plan;
    }

    @Override
    protected void initView(View view) {
        rcy_task = view.findViewById(R.id.rcy_task);//当前任务列表
        rcy_function = view.findViewById(R.id.rcy_function);//功能列表

        et_search = view.findViewById(R.id.et_search);//搜索框
        img_search = view.findViewById(R.id.img_search);
        img_search.setOnClickListener(this);
        tv_pick_work=view.findViewById(R.id.tv_pick_work);
        tv_pick_work.setOnClickListener(this);


    }

    @Override
    protected void initData(Context mContext) {
        initFunction();
        initTask();
        getTaskList();//获取任务列表


        /*     *//***
         * 监听输入框是否点击了搜索
         *//*
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                    page = 1;
                    //请求数据
                    getTaskList();
                    return true;
                }
                return false;
            }
        });*/


    }


    private void initTask() {
        planTaskAdapter = new PlanTaskAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(planTaskAdapter);

    }

    private void initFunction() {
        HomeFunctionAdapter homeFunctionAdapter = new HomeFunctionAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        rcy_function.setLayoutManager(gridLayoutManager);
        rcy_function.setAdapter(homeFunctionAdapter);


        functionList.add(new HomeFunctionBean(R.drawable.clean_task_icon, "清洗", C.IntentionType.CLEAN_TASK));
        functionList.add(new HomeFunctionBean(R.drawable.cold_work_task_icon, "冷加工", C.IntentionType.COLD_WORK_TASK));
        functionList.add(new HomeFunctionBean(R.drawable.coating_task_icon, "镀膜", C.IntentionType.COATING_TASK));
        functionList.add(new HomeFunctionBean(R.drawable.lithography_task_icon, "光刻", C.IntentionType.LITHOGRAPHY_TASK));
        functionList.add(new HomeFunctionBean(R.drawable.safe_task_icon, "全检", C.IntentionType.SAFE_TASK));
        functionList.add(new HomeFunctionBean(R.drawable.prism_task_icon, "棱镜", C.IntentionType.PRISM_TASK));

        homeFunctionAdapter.initList(functionList);
    }

    /**
     * 获取任务列表
     */
    private void getTaskList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/plansp/plansp/deliveryStatistical")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestTotalSelfPlanBean requestTotalSelfPlanBean = new Gson().fromJson(data, RequestTotalSelfPlanBean.class);
                            if (requestTotalSelfPlanBean.getCode() == 0) {
                                List<TotalSelfPlanBean> totalSelfPlanBeanData = requestTotalSelfPlanBean.getData();
                                if (totalSelfPlanBeanData == null) return;
                                planTaskAdapter.initList(totalSelfPlanBeanData);
                            } else {
                                Toast.makeText(getActivity(), "" + requestTotalSelfPlanBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(getActivity(), "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search://搜索
                //请求数据
//                page = 1;
//                getTaskList();
                break;

            case R.id.tv_pick_work:
                startActivity(new Intent(getActivity(), SelfPlanTaskActivity.class));
                break;

        }
    }
}