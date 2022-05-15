package com.example.logistics.machining;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.CardBean;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.machining.activity.ProcessCardDetailActivity;
import com.example.logistics.utils.C;
import com.example.logistics.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 流程卡管理adapter
 * @date :27.9.21
 */
public class MachiningManageAdapter extends RecyclerView.Adapter<MachiningManageAdapter.BaseViewHolder> {
    private List<CardBean> mData = new ArrayList<>();
    private Context context;

    public MachiningManageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_machining_manage_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        CardBean cardBean = mData.get(position);
        holder.tv_mach_num.setText(context.getString(R.string.mach_process_card, cardBean.getBarcode() + ""));
        holder.tv_mach_name.setText(context.getString(R.string.plan_matter_name, cardBean.getMaterialName() + ""));
        holder.tv_mach_plan.setText(context.getString(R.string.num_task, TextUtils.isEmpty(cardBean.getNum()) ? "0" : cardBean.getNum()));
        holder.tv_mach_operation.setText(context.getString(R.string.mach_current_operation, TextUtils.isEmpty(cardBean.getProcessName()) ? "无" : cardBean.getProcessName()));
        holder.tv_mach_qualified.setText(context.getString(R.string.mach_qualified_num_operation, TextUtils.isEmpty(cardBean.getCurrentReportNum()) ? "0" : MathUtils.getNum(cardBean.getCurrentReportNum())));
        holder.tv_mach_state.setText(context.getString(R.string.mach_state, Integer.valueOf(cardBean.getIsFinish()) == 0 ? "未完工" : "已完工"));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProcessCardDetailActivity.class);
                intent.putExtra(C.mach.PROCESS_ID, cardBean.getBarcode() + "");
                context.startActivity(intent);//跳转流程卡详情
            }
        });


    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<CardBean> list) {
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
    public void addList(List<CardBean> list) {
        if (list != null) {
            mData.addAll(list);
        }


    }

    @Override
    public int getItemCount() {


        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_mach_num, tv_mach_name, tv_mach_plan, tv_mach_operation, tv_mach_qualified, tv_mach_state;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//计划数
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//当前工序
            tv_mach_qualified = itemView.findViewById(R.id.tv_mach_qualified);//合格数
            tv_mach_state = itemView.findViewById(R.id.tv_mach_state);//状态


        }
    }
}