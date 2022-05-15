package com.example.logistics.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.HomeFunctionBean;

import java.util.List;

/**
 * @author : jyx
 * @description: 线边库物料adapter
 * @date :27.9.21
 */
public class HomeMatterAdapter extends RecyclerView.Adapter<HomeMatterAdapter.BaseViewHolder> {
    private List<HomeFunctionBean> mData;
    private Context context;

    public HomeMatterAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_matter_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

//        holder.tv_matter_name.setText(mData.get(position).getName());
//        holder.tv_matter_num.setText(mData.get(position).getName());
//        holder.tv_matter_code.setText(mData.get(position).getName());
//        holder.tv_matter_stock_num.setText(mData.get(position).getName());
//        holder.tv_matter_stock_time.setText(mData.get(position).getName());



    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<HomeFunctionBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<HomeFunctionBean> list) {
        this.mData = list;
    }


    @Override
    public int getItemCount() {
        return 0;
//        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_matter_stock_time, tv_matter_stock_num,tv_matter_code,tv_matter_num,tv_matter_name;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_matter_stock_time = itemView.findViewById(R.id.tv_matter_stock_time);//库存时间
            tv_matter_stock_num = itemView.findViewById(R.id.tv_matter_stock_num);//库存数量
            tv_matter_code = itemView.findViewById(R.id.tv_matter_code);//批次号
            tv_matter_num = itemView.findViewById(R.id.tv_matter_num);//物料编码
            tv_matter_name = itemView.findViewById(R.id.tv_matter_name);//物料名称



        }
    }
}