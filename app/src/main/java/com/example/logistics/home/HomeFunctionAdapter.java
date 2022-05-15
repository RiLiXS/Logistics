package com.example.logistics.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.logistics.R;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.machining.activity.FeedingListActivity;
import com.example.logistics.utils.C;
import com.example.logistics.utils.IntentUtils;
import com.example.logistics.utils.ViewShowUtils;

import java.util.List;

/**
 * @author : jyx
 * @description: 首页功能adapter
 * @date :27.9.21
 */
public class HomeFunctionAdapter extends RecyclerView.Adapter<HomeFunctionAdapter.BaseViewHolder> {
    private List<HomeFunctionBean> mData;
    private Context context;

    public HomeFunctionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_function_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.item_img.setImageResource(mData.get(position).getImg());
        holder.item_tv.setText(mData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {//点击事件
            @Override
            public void onClick(View v) {
                IntentUtils.gotoAction((Activity) context,mData.get(position).getId());
//                if (mData.get(position).getName().equals("新增任务")){
//                    context.startActivity(new Intent(context, TaskAddActivity.class));
//                }
            }
        });


    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<HomeFunctionBean> getList(){
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

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        //TextView tv_alarm_category,tv_alarm_state,tv_alarm_content,tv_alarm_time;

        private ImageView item_img;
        private TextView item_tv;


        public BaseViewHolder(View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.item_tv);
            item_img = itemView.findViewById(R.id.item_img);


        }
    }
}