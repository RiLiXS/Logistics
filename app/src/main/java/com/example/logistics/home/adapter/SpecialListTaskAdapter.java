package com.example.logistics.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.home.activity.special_test.SpecialInspectionActivity;

import java.util.List;

/**
 * @author : jyx
 * @description: 专检任务adapter
 * @date :27.9.21
 */
public class SpecialListTaskAdapter extends RecyclerView.Adapter<SpecialListTaskAdapter.BaseViewHolder> {
    private List<HomeFunctionBean> mData;
    private Context context;

    public SpecialListTaskAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_task_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.tv_mach_name.setText(context.getString(R.string.plan_matter_name, "小马盘"));
        holder.tv_matter_num.setText(context.getString(R.string.matter_num, "光刻"));
        holder.tv_mach_operation.setText(context.getString(R.string.mach_current_operation, "48"));
        holder.tv_mach_plan.setText(context.getString(R.string.num_task, "3:23"));
        holder.tv_mach_num.setText(context.getString(R.string.mach_process_card, "15:00:30"));
        holder.tv_special_model.setText(context.getString(R.string.special_model, "3:23"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SpecialInspectionActivity.class));
            }
        });


    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<HomeFunctionBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<HomeFunctionBean> list) {
        this.mData = list;
    }


    @Override
    public int getItemCount() {
        return 5;
//        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mach_name, tv_special_model, tv_mach_operation, tv_matter_num, tv_mach_num, tv_mach_plan;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_matter_num = itemView.findViewById(R.id.tv_matter_num);//物料编号
            tv_special_model = itemView.findViewById(R.id.tv_special_model);//规格型号
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号


        }
    }
}