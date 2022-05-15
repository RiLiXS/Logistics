package com.example.logistics.machining.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.FeedingAddBean;
import com.example.logistics.bean.HomeFunctionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 新增投料adapter
 * @date :27.9.21
 */
public class MachiningMatterAddAdapter extends RecyclerView.Adapter<MachiningMatterAddAdapter.BaseViewHolder> {
    private List<BatchChargingDtl> mData = new ArrayList<>();
    private Context context;

    public MachiningMatterAddAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feeding_add_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        BatchChargingDtl feedingAddBean = mData.get(position);
        if (feedingAddBean == null) return;
        holder.et_bar_code.setText(feedingAddBean.getBarcode());
        holder.et_batchNumber.setText(feedingAddBean.getBatchNo());
        holder.et_matter_code.setText(feedingAddBean.getMaterialCode());
        holder.et_matter_name.setText(feedingAddBean.getMaterialName());
        holder.et_matterNum.setText(feedingAddBean.getFeedingNum());
        holder.et_totalNum.setText(feedingAddBean.getTotalQuantity());
        holder.et_unit.setText(feedingAddBean.getUnit());
        holder.et_matter_specs.setText(feedingAddBean.getSpecs());
        holder.et_supplier.setText(feedingAddBean.getSupplier());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData(position);
            }
        });

    }

    public void removeData(int position) {
        mData.remove(position);//集合移除该条
        notifyItemRemoved(position);//通知移除该条
        notifyItemRangeChanged(position, mData.size() - position);//更新适配器这条后面列表的变化


    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<BatchChargingDtl> list) {
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
    public void addList(List<BatchChargingDtl> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取投料数据
     *
     * @return
     */
    public List<BatchChargingDtl> getFeedData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private TextView et_bar_code, et_matter_code, et_matter_name, et_batchNumber, et_unit, et_totalNum, et_matterNum,et_matter_specs,et_supplier;
        private TextView tv_delete;


        public BaseViewHolder(View itemView) {
            super(itemView);
            et_bar_code = itemView.findViewById(R.id.et_bar_code);//条码
            et_matter_code = itemView.findViewById(R.id.et_matter_code);//物料编码
            et_matter_name = itemView.findViewById(R.id.et_matter_name);//物料名称
            et_batchNumber = itemView.findViewById(R.id.et_batchNumber);//批号
            et_matter_specs = itemView.findViewById(R.id.et_matter_specs);//物料型号
            et_supplier = itemView.findViewById(R.id.et_supplier);//供应商
            et_unit = itemView.findViewById(R.id.et_unit);//单位
            et_totalNum = itemView.findViewById(R.id.et_totalNum);//总数量
            et_matterNum = itemView.findViewById(R.id.et_matterNum);//投料数
            tv_delete = itemView.findViewById(R.id.tv_delete);//删除


        }
    }
}