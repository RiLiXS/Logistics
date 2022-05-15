package com.example.logistics.machining.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.RecordsBadItemListBean;
import com.example.logistics.bean.RecordsTurnOverListBean;
import com.example.logistics.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 周转任务adapter
 * @date :27.9.21
 */
public class TurnOverListAdapter extends RecyclerView.Adapter<TurnOverListAdapter.BaseViewHolder> {
    private List<RecordsTurnOverListBean> mData = new ArrayList<>();
    private Context context;
    private boolean isCheckAll = false;


    public TurnOverListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turn_over_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        RecordsTurnOverListBean recordsTurnOverListBean = mData.get(position);
        holder.tv_mach_name.setText(context.getString(R.string.self_made, recordsTurnOverListBean.getMaterialName() + ""));
        holder.tv_mach_operation.setText(context.getString(R.string.num_turn_over, MathUtils.getNum(recordsTurnOverListBean.getTurnoverNum())));
        holder.tv_mach_plan.setText(context.getString(R.string.task_time, recordsTurnOverListBean.getCreateTime() + ""));
        holder.tv_mach_num.setText(context.getString(R.string.mach_process_card, recordsTurnOverListBean.getBarcode() + ""));
        holder.tv_workShop.setText(context.getString(R.string.work_shop, recordsTurnOverListBean.getRollOutLocationName() + "-"+recordsTurnOverListBean.getShiftToLocationName()));
        switch (Integer.valueOf(recordsTurnOverListBean.getTurnoverState())) {
            case 0://待处理
                holder.tv_state.setBackgroundResource(R.drawable.bg_gray_radius_10dp);
                holder.tv_state.setText("待周转");
                holder.rb_checked.setVisibility(View.VISIBLE);
                holder.tv_check.setVisibility(View.VISIBLE);
                if (isCheckAll) {
                    holder.rb_checked.setImageResource(R.drawable.rb_check);
                    recordsTurnOverListBean.setChecked(true);

                } else {
                    holder.rb_checked.setImageResource(R.drawable.rb_unchekck);
                    recordsTurnOverListBean.setChecked(false);

                }
                break;
            case 1://已处理
                holder.tv_state.setBackgroundResource(R.drawable.bg_a4c99f_radius_5dp);
                holder.tv_state.setText("已周转");
                holder.rb_checked.setVisibility(View.GONE);
                holder.tv_check.setVisibility(View.GONE);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recordsTurnOverListBean.isChecked()) {
                    holder.rb_checked.setImageResource(R.drawable.rb_check);
                    recordsTurnOverListBean.setChecked(true);
                } else {
                    holder.rb_checked.setImageResource(R.drawable.rb_unchekck);
                    recordsTurnOverListBean.setChecked(false);
                }

            }
        });


    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<RecordsTurnOverListBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<RecordsTurnOverListBean> list) {
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
    public void addList(List<RecordsTurnOverListBean> list) {
        if (list != null) {
            mData.addAll(list);
        }
    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mach_name, tv_mach_operation, tv_mach_num, tv_mach_plan, tv_state,tv_check,tv_workShop;
        private ImageView rb_checked;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号
            tv_state = itemView.findViewById(R.id.tv_state);//状态
            rb_checked = itemView.findViewById(R.id.rb_checked);//选中
            tv_check=itemView.findViewById(R.id.tv_check);
            tv_workShop=itemView.findViewById(R.id.tv_workShop);//车间


        }
    }
}