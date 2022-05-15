package com.example.logistics.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.CardBean;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.RecordsBadItemListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 不合格任务adapter
 * @date :27.9.21
 */
public class DefectProductListAdapter extends RecyclerView.Adapter<DefectProductListAdapter.BaseViewHolder> {
    private List<RecordsBadItemListBean> mData = new ArrayList<>();
    private Context context;
    private boolean isCheckAll = false;


    public DefectProductListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_defect_product_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        RecordsBadItemListBean recordsBadItemListBean = mData.get(position);
        holder.tv_mach_name.setText(context.getString(R.string.self_made, recordsBadItemListBean.getMaterialName() + ""));
        holder.tv_matter_num.setText(context.getString(R.string.defect_reason, recordsBadItemListBean.getItemName() + ""));
        holder.tv_mach_operation.setText(context.getString(R.string.unqualified_num, recordsBadItemListBean.getNum() + ""));
        holder.tv_mach_plan.setText(context.getString(R.string.task_time, recordsBadItemListBean.getCreateTime() + ""));
        holder.tv_mach_num.setText(context.getString(R.string.mach_process_card, recordsBadItemListBean.getBarcode() + ""));
        switch (Integer.valueOf(recordsBadItemListBean.getProcessState())) {
            case 1://待处理
                holder.tv_state.setBackgroundResource(R.drawable.bg_gray_radius_10dp);
                holder.tv_state.setText("待处理");
                holder.rb_checked.setVisibility(View.VISIBLE);
                holder.tv_check.setVisibility(View.VISIBLE);
                if (isCheckAll) {
                    holder.rb_checked.setImageResource(R.drawable.rb_check);
                    recordsBadItemListBean.setChecked(true);

                } else {
                    holder.rb_checked.setImageResource(R.drawable.rb_unchekck);
                    recordsBadItemListBean.setChecked(false);

                }
                break;
            case 2://已处理
                holder.tv_state.setBackgroundResource(R.drawable.bg_a4c99f_radius_5dp);
                holder.tv_state.setText("已处理");
                holder.rb_checked.setVisibility(View.GONE);
                holder.tv_check.setVisibility(View.GONE);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recordsBadItemListBean.isChecked()) {
                    holder.rb_checked.setImageResource(R.drawable.rb_check);
                    recordsBadItemListBean.setChecked(true);
                } else {
                    holder.rb_checked.setImageResource(R.drawable.rb_unchekck);
                    recordsBadItemListBean.setChecked(false);
                }

            }
        });


    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<RecordsBadItemListBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<RecordsBadItemListBean> list) {
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
    public void addList(List<RecordsBadItemListBean> list) {
        if (list != null) {
            mData.addAll(list);
        }
    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mach_name, tv_mach_operation, tv_matter_num, tv_mach_num, tv_mach_plan, tv_state,tv_check;
        private ImageView rb_checked;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_matter_num = itemView.findViewById(R.id.tv_matter_num);//物料编号
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号
            tv_state = itemView.findViewById(R.id.tv_state);//状态
            rb_checked = itemView.findViewById(R.id.rb_checked);//选中
            tv_check=itemView.findViewById(R.id.tv_check);


        }
    }
}