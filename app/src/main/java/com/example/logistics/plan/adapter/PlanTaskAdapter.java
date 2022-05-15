package com.example.logistics.plan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.TotalSelfPlanBean;
import com.example.logistics.bean.WorkCenterListBean;
import com.example.logistics.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 自制件首页Adapter
 * @date :27.9.21
 */
public class PlanTaskAdapter extends RecyclerView.Adapter<PlanTaskAdapter.BaseViewHolder> {
    private List<TotalSelfPlanBean> mData = new ArrayList<>();
    private Context context;

    public PlanTaskAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_task_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        TotalSelfPlanBean totalSelfPlanBean = mData.get(position);
        holder.item_group2.setVisibility(position == getItemCount() - 1 ? View.VISIBLE : View.GONE);
        holder.tv_state.setText(Integer.valueOf(totalSelfPlanBean.getDelivery()) == 0 ? "延期" : "正常");
        holder.tv_name.setText("全部");
        holder.tv_time.setText("全部");
        holder.tv_people.setText(TextUtils.isEmpty(totalSelfPlanBean.getPlanNumSum()) ? "0" : MathUtils.getNum(totalSelfPlanBean.getPlanNumSum()));
        holder.tv_qualifiedNum.setText(TextUtils.isEmpty(totalSelfPlanBean.getCompletionNumSum()) ? "0" : MathUtils.getNum(totalSelfPlanBean.getCompletionNumSum()));
        holder.tv_unqualifiedNum.setText(TextUtils.isEmpty(totalSelfPlanBean.getDate()) ? "" : totalSelfPlanBean.getDate());
        int plan = 0;
        int task = 0;
        for (int i = 0; i < mData.size(); i++) {
            plan += Double.valueOf(mData.get(i).getPlanNumSum());
            task += Double.valueOf(mData.get(i).getCompletionNumSum());
        }
        holder.tv_total_plan_num.setText(MathUtils.getNum(plan + ""));
        holder.tv_total_task_num.setText(MathUtils.getNum(task + ""));

    }


    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<TotalSelfPlanBean> list) {
        if (list != null) {
            mData.clear();
            mData = list;
            notifyDataSetChanged();

        }
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void addList(List<TotalSelfPlanBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private Group item_group2;
        private TextView tv_state, tv_name, tv_time, tv_people, tv_qualifiedNum, tv_unqualifiedNum, tv_total_plan_num, tv_total_task_num;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_state = itemView.findViewById(R.id.tv_state);//状态
            tv_name = itemView.findViewById(R.id.tv_name);//编号
            tv_time = itemView.findViewById(R.id.tv_time);//名称
            tv_people = itemView.findViewById(R.id.tv_people);//计划
            tv_qualifiedNum = itemView.findViewById(R.id.tv_qualifiedNum);//完工
            tv_unqualifiedNum = itemView.findViewById(R.id.tv_unqualifiedNum);//交期
            item_group2 = itemView.findViewById(R.id.item_group2);//
            tv_total_plan_num = itemView.findViewById(R.id.tv_total_plan_num);
            tv_total_task_num = itemView.findViewById(R.id.tv_total_task_num);


        }
    }
}