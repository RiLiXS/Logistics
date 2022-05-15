package com.example.logistics.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.HomeFunctionBean;

import java.util.List;

/**
 * @author : jyx
 * @description: 末检任务adapter
 * @date :27.9.21
 */
public class FinalListTaskAdapter extends RecyclerView.Adapter<FinalListTaskAdapter.BaseViewHolder> {
    private List<HomeFunctionBean> mData;
    private Context context;

    public FinalListTaskAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_final_task_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.tv_mach_name.setText(context.getString(R.string.plan_matter_name,"小马盘"));
        holder.tv_matter_num.setText(context.getString(R.string.matter_num,"光刻"));
        holder.tv_mach_operation.setText(context.getString(R.string.mach_current_operation,"48"));
        holder.tv_mach_plan.setText(context.getString(R.string.num_task,"3:23"));
        holder.tv_mach_num.setText(context.getString(R.string.mach_process_card,"15:00:30"));
        holder.tv_special_model.setText(context.getString(R.string.special_model,"3:23"));

//        holder.tv_device_manage.setText(mData.get(position).getName());


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

        private TextView tv_mach_name, tv_special_model,tv_mach_operation,tv_matter_num,tv_mach_num,tv_mach_plan;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name=itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation=itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_matter_num=itemView.findViewById(R.id.tv_matter_num);//物料编号
            tv_special_model=itemView.findViewById(R.id.tv_special_model);//规格型号
            tv_mach_plan=itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num=itemView.findViewById(R.id.tv_mach_num);//流程卡号


        }
    }
}