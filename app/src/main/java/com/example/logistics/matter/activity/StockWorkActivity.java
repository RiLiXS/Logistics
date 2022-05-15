package com.example.logistics.matter.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.matter.adapter.PickingWorkAdapter;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 入库作业
 */
public class StockWorkActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;//返回
    @BindView(R.id.img_search)
    ImageView img_search;//返回
    @BindView(R.id.et_search)
    EditText et_search;//搜索
    @BindView(R.id.type_deal)
    Spinner type_deal;//下拉选项
    @BindView(R.id.tv_scan)
    TextView tv_scan;//扫描二维码
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;//刷新
    @BindView(R.id.rcy_stock)
    RecyclerView rcy_stock;//入库列表
    private int page = 1;
    private ArrayList<String> typeList = new ArrayList<>();//类型

    @Override
    protected int initLayout() {
        return R.layout.activity_stock_work;
    }

    @Override
    protected void initView() {
        img_return.setOnClickListener(this);
        img_search.setOnClickListener(this);
        tv_scan.setOnClickListener(this);
        typeList.add("全部");
        typeList.add("领料");
        typeList.add("投料");
        typeList.add("产出");
    }

    @Override
    protected void initData() {
        initPickWork();//初始化列表
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner_text, typeList);
        type_deal.setAdapter(adapter);
        type_deal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//分配状态
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (isSpinnerFirst2) {
//                    isSpinnerFirst2 = false;
//                    return;
//                }
//                statePosition = position;
//                page = 1;
//                switch (position) {
//                    case 0:
//                        initTaskList(1, isSource == true ? "" : sourceData.get(sourcePosition).getValue(), et_search.getText().toString(), "");//获取任务列表
//                        break;
//                    case 1:
//
//                        initTaskList(1, isSource == true ? "" : sourceData.get(sourcePosition).getValue(), et_search.getText().toString(), "0");//获取任务列表
//                        break;
//                    case 2:
//                        initTaskList(1, isSource == true ? "" : sourceData.get(sourcePosition).getValue(), et_search.getText().toString(), "1");//获取任务列表
//                        break;
//                }

                //  Toast.makeText(TaskOverActivity.this,""+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * 刷新
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //请求数据
                refreshLayout.finishRefresh();


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
                refreshLayout.finishLoadMore();

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
                    //请求数据
                    return true;
                }
                return false;
            }
        });
    }

    private void initPickWork() {
        PickingWorkAdapter pickingWorkAdapter = new PickingWorkAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_stock.setLayoutManager(linearLayoutManager);
        rcy_stock.setAdapter(pickingWorkAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return:
                finish();//返回上一层
                break;
            case R.id.img_search://搜索

                break;
            case R.id.tv_scan://扫描二维码

                break;
        }
    }

}
