package com.example.logistics.machining.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.BatchChargingDtl;
import com.example.logistics.bean.sonPackagingBoxEncodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 转码合并adapter
 * @date :27.9.21
 */
public class MergeCodeAddAdapter extends RecyclerView.Adapter<MergeCodeAddAdapter.BaseViewHolder> {
    private List<sonPackagingBoxEncodeList> mData = new ArrayList<>();
    private Context context;

    public MergeCodeAddAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merge_code_add_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        sonPackagingBoxEncodeList feedingAddBean = mData.get(position);
        if (feedingAddBean == null) return;
        holder.et_bar_code.setText(feedingAddBean.getBarcode());
        holder.et_batchNumber.setText(feedingAddBean.getBatchNo());
        holder.et_matter_code.setText(feedingAddBean.getMaterialCode());
        holder.et_matter_name.setText(feedingAddBean.getMaterialName());
        holder.et_matterNum.setText(feedingAddBean.getMatterNum());
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
    public void initList(List<sonPackagingBoxEncodeList> list) {
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
    public void addList(List<sonPackagingBoxEncodeList> list) {
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
    public List<sonPackagingBoxEncodeList> getFeedData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private TextView et_bar_code, et_matter_code, et_matter_name, et_batchNumber, et_matterNum;
        private TextView tv_delete;


        public BaseViewHolder(View itemView) {
            super(itemView);
            et_bar_code = itemView.findViewById(R.id.et_bar_code);//条码
            et_matter_code = itemView.findViewById(R.id.et_matter_code);//物料编码
            et_matter_name = itemView.findViewById(R.id.et_matter_name);//物料名称
            et_batchNumber = itemView.findViewById(R.id.et_batchNumber);//批号
            et_matterNum = itemView.findViewById(R.id.et_matterNum);//投料数
            tv_delete = itemView.findViewById(R.id.tv_delete);//删除


        }
    }
}