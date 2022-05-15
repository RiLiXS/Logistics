package com.example.logistics.machining.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.WorkCenterListBean;
import com.example.logistics.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 流程卡详情Adapter
 * @date :27.9.21
 */
public class ProcessCardDetailAdapter extends RecyclerView.Adapter<ProcessCardDetailAdapter.BaseViewHolder> {
    private List<WorkCenterListBean> mData = new ArrayList<>();
    private Context context;

    public ProcessCardDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process_card_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        WorkCenterListBean workCenterListBean = mData.get(position);
        holder.item_group.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
      //  holder.tv_num.setText(TextUtils.isEmpty(workCenterListBean.getWorkCenterCode())?"":workCenterListBean.getWorkCenterCode());
        holder.tv_name.setText(TextUtils.isEmpty(workCenterListBean.getProcessName())?"":workCenterListBean.getProcessName());
        holder.tv_time.setText(TextUtils.isEmpty(workCenterListBean.getCreateTime())?"":workCenterListBean.getCreateTime());
        holder.tv_people.setText(TextUtils.isEmpty(workCenterListBean.getWorkerName())?"":workCenterListBean.getWorkerName());
        holder.tv_qualifiedNum.setText(TextUtils.isEmpty(workCenterListBean.getReportQty())?"0": MathUtils.getNum(workCenterListBean.getReportQty()));
        holder.tv_unqualifiedNum.setText(TextUtils.isEmpty(workCenterListBean.getRejectQty())?"0": MathUtils.getNum(workCenterListBean.getRejectQty()));


    }


    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<WorkCenterListBean> list) {
        if (list != null) {
            mData.clear();
            mData=list;
            notifyDataSetChanged();

        }
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void addList(List<WorkCenterListBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private Group item_group;
        private TextView tv_num, tv_name, tv_time, tv_people, tv_qualifiedNum, tv_unqualifiedNum;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);//编号
            tv_name = itemView.findViewById(R.id.tv_name);//名称
            tv_time = itemView.findViewById(R.id.tv_time);//时间
            tv_people = itemView.findViewById(R.id.tv_people);//人员
            tv_qualifiedNum = itemView.findViewById(R.id.tv_qualifiedNum);//合格
            tv_unqualifiedNum = itemView.findViewById(R.id.tv_unqualifiedNum);//不合格
            item_group = itemView.findViewById(R.id.item_group);//项目名


        }
    }
}