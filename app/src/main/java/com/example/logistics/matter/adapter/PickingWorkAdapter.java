package com.example.logistics.matter.adapter;

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
 * @description: 领料作业adapter
 * @date :27.9.21
 */
public class PickingWorkAdapter extends RecyclerView.Adapter<PickingWorkAdapter.BaseViewHolder> {
    private List<HomeFunctionBean> mData;
    private Context context;

    public PickingWorkAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picking_work_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.tv_pick_card.setText(context.getString(R.string.picking_card,"LL001"));
        holder.tv_pick_part.setText(context.getString(R.string.part_number,"HRPJS-421TS"));
        holder.tv_pick_name.setText(context.getString(R.string.part_name,"活塞1"));
        holder.tv_pick_collect_num.setText(context.getString(R.string.collect_num,"2000"));
        holder.tv_pick_collect_time.setText(context.getString(R.string.collect_time,"2020-02-02 19：00：00"));
        holder.tv_pick_collect_people.setText(context.getString(R.string.collect_people,"张三"));
        holder.tv_pick_task_card.setText(context.getString(R.string.task_card,"scrwd-001"));
        holder.tv_pick_quota_num.setText(context.getString(R.string.quota_num,"10000"));
        holder.tv_pick_add_num.setText(context.getString(R.string.add_num,"3000"));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, PatrolDetailActivity.class);
//                intent.putExtra(C.task.TASK_INFO_ID, recordsDTOXXX.getTaskInfoId());
//                context.startActivity(intent);//跳转任务详情
            }
        });


    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<HomeFunctionBean> list) {
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
    public void addList(List<HomeFunctionBean> list) {
        if (list != null) {
            mData.addAll(list);
        }


    }

    @Override
    public int getItemCount() {

        return 10;
       // return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_pick_card, tv_pick_part, tv_pick_name, tv_pick_collect_num, tv_pick_collect_time,tv_pick_collect_people,
                tv_pick_task_card,tv_pick_quota_num,tv_pick_add_num;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_pick_card = itemView.findViewById(R.id.tv_pick_card);//领料单号
            tv_pick_part = itemView.findViewById(R.id.tv_pick_part);//零件编号
            tv_pick_name = itemView.findViewById(R.id.tv_pick_name);//零件名称
            tv_pick_collect_num = itemView.findViewById(R.id.tv_pick_collect_num);//领用数量
            tv_pick_collect_time = itemView.findViewById(R.id.tv_pick_collect_time);//领用时间
            tv_pick_collect_people = itemView.findViewById(R.id.tv_pick_collect_people);//领用人
            tv_pick_task_card = itemView.findViewById(R.id.tv_pick_task_card);//任务单号
            tv_pick_quota_num = itemView.findViewById(R.id.tv_pick_quota_num);//定额数量
            tv_pick_add_num = itemView.findViewById(R.id.tv_pick_add_num);//累计领用数量



        }
    }
}