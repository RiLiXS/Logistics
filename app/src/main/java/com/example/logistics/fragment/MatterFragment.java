package com.example.logistics.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.base.BaseFragment;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.home.HomeFunctionAdapter;
import com.example.logistics.home.adapter.HomeMatterAdapter;
import com.example.logistics.matter.MatterProductAdapter;
import com.example.logistics.matter.activity.PickingWorkActivity;
import com.example.logistics.matter.activity.StockWorkActivity;
import com.example.logistics.matter.adapter.MatterStockAdapter;
import com.example.logistics.utils.C;

import java.util.ArrayList;


/**
 * 物料
 */
public class MatterFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rcy_product, rcy_stock, rcy_line_matter,rcy_function;
    private TextView tv_pick_work, tv_stock_work, tv_line_matter_more;
    private ArrayList<HomeFunctionBean> functionList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.fragment_matter;
    }

    @Override
    protected void initView(View view) {
        tv_line_matter_more = view.findViewById(R.id.tv_line_matter_more);//线边库更多
        tv_stock_work = view.findViewById(R.id.tv_stock_work);//入库作业
        tv_pick_work = view.findViewById(R.id.tv_pick_work);//领料作业

        rcy_line_matter = view.findViewById(R.id.rcy_line_matter);//线边库列表
        rcy_stock = view.findViewById(R.id.rcy_stock);//入库管理列表
        rcy_product = view.findViewById(R.id.rcy_product);//生产领料列表
        rcy_function=view.findViewById(R.id.rcy_function);//功能列表

        tv_line_matter_more.setOnClickListener(this);
        tv_pick_work.setOnClickListener(this);
        tv_stock_work.setOnClickListener(this);
    }

    @Override
    protected void initData(Context mContext) {
        initFunction();
        initProduct();
        initStock();
        initMatter();
    }

    /**
     * 功能列表初始化
     */
    private void initFunction() {
        HomeFunctionAdapter homeFunctionAdapter = new HomeFunctionAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        rcy_function.setLayoutManager(gridLayoutManager);
        rcy_function.setAdapter(homeFunctionAdapter);


        functionList.add(new HomeFunctionBean(R.drawable.first_inspection_icon, "质检上报", 5));
        functionList.add(new HomeFunctionBean(R.drawable.onsite_inspection_icon, "不合格品", 6));
        functionList.add(new HomeFunctionBean(R.drawable.produce_icon, "投料", C.IntentionType.FEED));
        functionList.add(new HomeFunctionBean(R.drawable.feed_icon, "产出", C.IntentionType.PRODUCE));
        functionList.add(new HomeFunctionBean(R.drawable.split_icon, "拆分", C.IntentionType.SPLIT));
        functionList.add(new HomeFunctionBean(R.drawable.merge_icon, "转码合并", C.IntentionType.MERGE_CODE));
        functionList.add(new HomeFunctionBean(R.drawable.merge_icon, "合并", C.IntentionType.MERGE));
        functionList.add(new HomeFunctionBean(R.drawable.turnover_icon, "周转", C.IntentionType.TURNOVER));
        functionList.add(new HomeFunctionBean(R.drawable.out_assist_icon, "外协", C.IntentionType.OUT_ASSIST));

        homeFunctionAdapter.initList(functionList);
    }

    /**
     * 我的任务列表初始化
     */
    private void initProduct() {
        MatterProductAdapter matterProductAdapter = new MatterProductAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcy_product.setLayoutManager(linearLayoutManager);
        rcy_product.setAdapter(matterProductAdapter);
    }

    /**
     * 滞留物料列表初始化
     */
    private void initStock() {
        MatterStockAdapter matterStockAdapter = new MatterStockAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcy_stock.setLayoutManager(linearLayoutManager);
        rcy_stock.setAdapter(matterStockAdapter);
    }

    /**
     * 延期工单列表初始化
     */
    private void initMatter() {
        HomeMatterAdapter homeMatterAdapter = new HomeMatterAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcy_line_matter.setLayoutManager(linearLayoutManager);
        rcy_line_matter.setAdapter(homeMatterAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_line_matter_more://线边库更多

                break;
            case R.id.tv_pick_work://领料作业
                startActivity(new Intent(getActivity(), PickingWorkActivity.class));
                break;
            case R.id.tv_stock_work://入库作业
                startActivity(new Intent(getActivity(), StockWorkActivity.class));
                break;
        }
    }
}