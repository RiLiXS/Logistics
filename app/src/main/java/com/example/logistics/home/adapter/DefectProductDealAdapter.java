package com.example.logistics.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.RecordsBadItemListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 不合格任务处置adapter
 * @date :27.9.21
 */
public class DefectProductDealAdapter extends RecyclerView.Adapter<DefectProductDealAdapter.BaseViewHolder> {
    private List<RecordsBadItemListBean> mData = new ArrayList<>();
    private Context context;


    public DefectProductDealAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_defect_product_deal_list, parent, false);
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

        holder.rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_people:
                        recordsBadItemListBean.setPeople(true);
                        break;
                    case R.id.rb_unPeople:
                        recordsBadItemListBean.setPeople(false);
                        break;

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

        private TextView tv_mach_name, tv_mach_operation, tv_matter_num, tv_mach_num, tv_mach_plan, tv_state;
        private RadioGroup rg_group;
        private RadioButton rb_people,rb_unPeople;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_matter_num = itemView.findViewById(R.id.tv_matter_num);//物料编号
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号
            tv_state = itemView.findViewById(R.id.tv_state);//状态
            rg_group = itemView.findViewById(R.id.rg_group);//选中
            rb_people = itemView.findViewById(R.id.rb_people);//选中
            rb_unPeople = itemView.findViewById(R.id.rb_unPeople);//非选中


        }
    }
}