package com.example.logistics.machining.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.OutWorkOrderBean;
import com.example.logistics.bean.RecordsTurnOverListBean;
import com.example.logistics.machining.activity.assist.ArrivalDetailsActivity;
import com.example.logistics.machining.activity.assist.EnterArrivalActivity;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 外协任务adapter
 * @date :27.9.21
 */
public class OutAssistListAdapter extends RecyclerView.Adapter<OutAssistListAdapter.BaseViewHolder> {
    private List<OutWorkOrderBean> mData = new ArrayList<>();
    private Context context;
    private boolean isCheckAll = false;


    public OutAssistListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_out_assist_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        OutWorkOrderBean outWorkOrderBean = mData.get(position);
        holder.tv_mach_name.setText(context.getString(R.string.plan_matter_name, outWorkOrderBean.getMaterialName() + ""));
        holder.tv_mach_operation.setText(context.getString(R.string.arrival_num, outWorkOrderBean.getArrivalNum()+""));
        holder.tv_mach_plan.setText(context.getString(R.string.task_time, outWorkOrderBean.getProcessName() + ""));
        holder.tv_mach_num.setText(context.getString(R.string.out_assist_card, outWorkOrderBean.getSerialNumber() + ""));
        holder.tv_workShop.setText(context.getString(R.string.plan_out_num, outWorkOrderBean.getConsignmentNum() + ""));
        switch (Integer.valueOf(outWorkOrderBean.getState())) {
            case 0://待发货
                holder.tv_state.setBackgroundResource(R.drawable.bg_gray_radius_10dp);
                holder.tv_state.setText("待发货");
                holder.rb_checked.setVisibility(View.VISIBLE);
                holder.tv_check.setVisibility(View.VISIBLE);
                holder.tv_details.setVisibility(View.GONE);
                if (isCheckAll) {
                    holder.rb_checked.setImageResource(R.drawable.rb_check);
                    outWorkOrderBean.setCheck(true);

                } else {
                    holder.rb_checked.setImageResource(R.drawable.rb_unchekck);
                    outWorkOrderBean.setCheck(false);

                }
                break;
            case 1://已发货
                holder.tv_state.setBackgroundResource(R.drawable.bg_43a6ea_radius_5dp);
                holder.tv_state.setText("已发货");
                holder.rb_checked.setVisibility(View.GONE);
                holder.tv_check.setVisibility(View.GONE);
                holder.tv_details.setVisibility(View.GONE);
                break;
            case 2://部分到货
                holder.tv_state.setBackgroundResource(R.drawable.bg_a4c99f_radius_5dp);
                holder.tv_state.setText("部分到货");
                holder.rb_checked.setVisibility(View.GONE);
                holder.tv_check.setVisibility(View.GONE);
                holder.tv_details.setVisibility(View.VISIBLE);
                break;
            case 3://全部到货
                holder.tv_state.setBackgroundResource(R.drawable.bg_f28d34_radius_5dp);
                holder.tv_state.setText("全部到货");
                holder.rb_checked.setVisibility(View.GONE);
                holder.tv_check.setVisibility(View.GONE);
                holder.tv_details.setVisibility(View.GONE);
                break;
        }
        holder.tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ArrivalDetailsActivity.class);
                intent.putExtra(C.mach.OUT_WORK_ID,outWorkOrderBean.getOutsourceWorkOrderdId()+"");
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Integer.valueOf(outWorkOrderBean.getState())){
                    case 0:
                        if (!outWorkOrderBean.getCheck()) {
                            holder.rb_checked.setImageResource(R.drawable.rb_check);
                            outWorkOrderBean.setCheck(true);
                        } else {
                            holder.rb_checked.setImageResource(R.drawable.rb_unchekck);
                            outWorkOrderBean.setCheck(false);
                        }
                        break;

                    case 1://录入到货
                    case 2:
                        Intent intent=new Intent(context, EnterArrivalActivity.class);
                        intent.putExtra(C.mach.PROCESS_ID,outWorkOrderBean.getProcessId()+"");
                        intent.putExtra(C.mach.PROCESS_MULTIPLE,outWorkOrderBean.getMultiple()+"");
                        intent.putExtra(C.mach.OUT_WORK_ID,outWorkOrderBean.getOutsourceWorkOrderdId()+"");
                        context.startActivity(intent);
                        break;
                    case 3://到货明细
                        Intent intent2=new Intent(context, ArrivalDetailsActivity.class);
                        intent2.putExtra(C.mach.OUT_WORK_ID,outWorkOrderBean.getOutsourceWorkOrderdId()+"");
                        context.startActivity(intent2);
                        break;
                }


            }
        });


    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<OutWorkOrderBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<OutWorkOrderBean> list) {
        if (list != null) {
            mData.clear();
            mData.addAll(list);

        }
    }

    /**
     * 全选 取消 确定
     *
     * @param checkAll
     * @return
     */
    public Boolean CheckAll(boolean checkAll) {
        isCheckAll = checkAll;
        return checkAll;
    }

    /**
     * 添加数据源
     *
     * @param list
     */
    public void addList(List<OutWorkOrderBean> list) {
        if (list != null) {
            mData.addAll(list);
        }
    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mach_name, tv_mach_operation, tv_mach_num, tv_mach_plan, tv_state, tv_check, tv_workShop,tv_details;
        private ImageView rb_checked;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号
            tv_state = itemView.findViewById(R.id.tv_state);//状态
            rb_checked = itemView.findViewById(R.id.rb_checked);//选中
            tv_check = itemView.findViewById(R.id.tv_check);
            tv_workShop = itemView.findViewById(R.id.tv_workShop);//车间
            tv_details=itemView.findViewById(R.id.tv_details);


        }
    }
}