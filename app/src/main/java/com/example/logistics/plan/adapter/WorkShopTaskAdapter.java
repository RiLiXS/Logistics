package com.example.logistics.plan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.RecordsWorkShopListBean;
import com.example.logistics.machining.activity.MergeActivity;
import com.example.logistics.machining.activity.ProcessCardDetailActivity;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 车间任务管理adapter
 * @date :27.9.21
 */
public class WorkShopTaskAdapter extends RecyclerView.Adapter<WorkShopTaskAdapter.BaseViewHolder> {
    private List<RecordsWorkShopListBean> mData = new ArrayList<>();
    private Context context;

    public WorkShopTaskAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_shop_task_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        RecordsWorkShopListBean recordsWorkShopListBean = mData.get(position);

        holder.tv_task_num.setText(context.getString(R.string.mach_process_card, recordsWorkShopListBean.getBarcode()+""));
        holder.tv_task_name.setText(context.getString(R.string.plan_matter_name, recordsWorkShopListBean.getMaterialName()+""));
        holder.tv_task_plan.setText(context.getString(R.string.plan_num, MathUtils.getNum(recordsWorkShopListBean.getPlanNum())));
        holder.tv_task_success.setText(context.getString(R.string.num_task, MathUtils.getNum(recordsWorkShopListBean.getNum())));
        holder.tv_task_date.setText(context.getString(R.string.plan_order_date, recordsWorkShopListBean.getCreateTime()+""));
        holder.tv_self_task_date.setText(context.getString(R.string.plan_self_date, recordsWorkShopListBean.getUpdateTime()+""));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProcessCardDetailActivity.class);
                intent.putExtra(C.mach.PROCESS_ID, recordsWorkShopListBean.getBarcode() + "");
                context.startActivity(intent);//跳转流程卡详情
            }
        });


    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<RecordsWorkShopListBean> list) {
        if (list != null) {
            mData.clear();
            mData.addAll(list);

        }
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void addList(List<RecordsWorkShopListBean> list) {
        if (list != null) {
            mData.addAll(list);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_task_num, tv_task_name, tv_task_plan, tv_task_success, tv_task_date,tv_self_task_date;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_task_num = itemView.findViewById(R.id.tv_task_num);//自制件号
            tv_task_name = itemView.findViewById(R.id.tv_task_name);//物料名称
            tv_task_plan = itemView.findViewById(R.id.tv_task_plan);//计划数
            tv_task_success = itemView.findViewById(R.id.tv_task_success);//完成数
            tv_task_date = itemView.findViewById(R.id.tv_task_date);//交期
            tv_self_task_date=itemView.findViewById(R.id.tv_self_task_date);


        }
    }
}