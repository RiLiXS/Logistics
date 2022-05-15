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
 * @description: 首页任务adapter
 * @date :27.9.21
 */
public class HomeTaskAdapter extends RecyclerView.Adapter<HomeTaskAdapter.BaseViewHolder> {
    private List<HomeFunctionBean> mData;
    private Context context;

    public HomeTaskAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_task_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

//        holder.tv_task_num.setText(mData.get(position).getName());
//        holder.tv_task_name.setText(mData.get(position).getName())
//        holder.tv_task_time.setText(mData.get(position).getName());
//        holder.tv_task_input_output.setText(mData.get(position).getName());


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
        return 5;
//        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_task_num, tv_task_name,tv_task_time,tv_task_input_output;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_task_num = itemView.findViewById(R.id.tv_task_num);//任务单号
            tv_task_name = itemView.findViewById(R.id.tv_task_name);//任务名称
            tv_task_time = itemView.findViewById(R.id.tv_task_time);//完工交期
            tv_task_input_output = itemView.findViewById(R.id.tv_task_input_output);//投入/产出



        }
    }
}