package com.example.logistics.machining.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.ArrivalDetailBean;
import com.example.logistics.bean.OutWorkOrderBean;
import com.example.logistics.machining.activity.assist.EnterArrivalActivity;
import com.example.logistics.utils.C;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 外协任务明细adapter
 * @date :27.9.21
 */
public class ArrivalDetailsListAdapter extends RecyclerView.Adapter<ArrivalDetailsListAdapter.BaseViewHolder> {
    private List<ArrivalDetailBean> mData = new ArrayList<>();
    private Context context;



    public ArrivalDetailsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arrival_detail_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        ArrivalDetailBean arrivalDetailBean = mData.get(position);
        holder.tv_mach_name.setText(context.getString(R.string.job_code, arrivalDetailBean.getWorkCenterCode() + ""));
        holder.tv_mach_operation.setText(context.getString(R.string.goods_little,  TextUtils.isEmpty(arrivalDetailBean.getArrivalNum())?"0":arrivalDetailBean.getArrivalNum()+""));
        holder.tv_mach_plan.setText(context.getString(R.string.goods_big,  TextUtils.isEmpty(arrivalDetailBean.getBigArrivalNum())?"0":arrivalDetailBean.getBigArrivalNum() + ""));
        holder.tv_mach_num.setText(context.getString(R.string.out_assist_card, arrivalDetailBean.getSerialNumber() + ""));
        holder.tv_workShop.setText(context.getString(R.string.defect_big, TextUtils.isEmpty(arrivalDetailBean.getBigBadNum())?"0":arrivalDetailBean.getBigBadNum() + ""));
        holder.tv_defect_little.setText(context.getString(R.string.defect_little, TextUtils.isEmpty(arrivalDetailBean.getBadNum())?"0":arrivalDetailBean.getBadNum() + ""));
        holder.tv_time.setText(context.getString(R.string.goods_time, arrivalDetailBean.getArrivalTime()+""));
        holder.tv_name.setText(context.getString(R.string.create_people, arrivalDetailBean.getCreateByName() + ""));



    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<ArrivalDetailBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<ArrivalDetailBean> list) {
        if (list != null) {
            mData.clear();
            mData.addAll(list);

        }
    }


    /**
     * 添加数据源
     *
     * @param list
     */
    public void addList(List<ArrivalDetailBean> list) {
        if (list != null) {
            mData.addAll(list);
        }
    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mach_name, tv_mach_operation, tv_mach_num, tv_mach_plan,
                tv_time, tv_name, tv_workShop,tv_defect_little;



        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号
            tv_time = itemView.findViewById(R.id.tv_time);//状态
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_workShop = itemView.findViewById(R.id.tv_workShop);//车间
            tv_defect_little=itemView.findViewById(R.id.tv_defect_little);


        }
    }
}