package com.example.logistics.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.CardBean;
import com.example.logistics.bean.ControlListBean;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.QualityTypeListBean;
import com.example.logistics.home.activity.special_test.SpecialInspectionActivity;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 首页检验任务adapter
 * @date :27.9.21
 */
public class HomeTestTaskAdapter extends RecyclerView.Adapter<HomeTestTaskAdapter.BaseViewHolder> {
    private List<ControlListBean> mData=new ArrayList<>();
    private Context context;
    private List<QualityTypeListBean> typeList=new ArrayList<>();

    public HomeTestTaskAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_test_task_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (mData.size()==0)return;
        ControlListBean controlListBean = mData.get(position);
        holder.tv_mach_name.setText(context.getString(R.string.procedure_card,controlListBean.getBarcode()+""));
        holder.tv_task_num.setText(context.getString(R.string.self_made,controlListBean.getMaterialName()+""));
        holder.tv_mach_operation.setText(context.getString(R.string.quantity_num, MathUtils.getNum(controlListBean.getReportQty())));
        holder.tv_time_working.setText(context.getString(R.string.inspection_time,controlListBean.getCreateTime()+""));
        holder.tv_process.setText(context.getString(R.string.mach_current_operation,controlListBean.getProcessName()+""));

        for (int i=0;i<typeList.size();i++){
            if (controlListBean.getCheckoutType().equals(typeList.get(i).getValue())){
                holder.tv_state.setText(typeList.get(i).getLabel()+"");
            }
        }

        switch (Integer.valueOf(controlListBean.getCheckoutState())){
            case 0://未完成
                holder.tv_state.setBackgroundResource(R.drawable.bg_gray_radius_10dp);
                break;
            case 1://已完成
                holder.tv_state.setBackgroundResource(R.drawable.bg_a4c99f_radius_5dp);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(controlListBean.getCheckoutState())==0){
                    Intent intent = new Intent(context, SpecialInspectionActivity.class);//跳转质检上报
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(C.home.QUALITY_BEAN, (Serializable) controlListBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });



//        holder.tv_device_manage.setText(mData.get(position).getName());


    }

    /***
     * 返回列表中数据
     * @return
     */
    public void  getList(List<QualityTypeListBean> list) {
        if (list != null) {
            typeList.clear();
            typeList.addAll(list);

        }
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<ControlListBean> list) {
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
    public void addList(List<ControlListBean> list) {
        if (list != null) {
            mData.addAll(list);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mach_name, tv_task_num,tv_mach_operation,tv_time_working,tv_state,tv_process;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name=itemView.findViewById(R.id.tv_mach_name);//工序流程卡
            tv_task_num=itemView.findViewById(R.id.tv_task_num);//自制件名称（物料名称）
            tv_mach_operation=itemView.findViewById(R.id.tv_mach_operation);//待检数量（报工数）
            tv_time_working=itemView.findViewById(R.id.tv_time_working);//待检时间
            tv_state=itemView.findViewById(R.id.tv_state);//检验类型
            tv_process=itemView.findViewById(R.id.tv_process);//当前工序


        }
    }
}