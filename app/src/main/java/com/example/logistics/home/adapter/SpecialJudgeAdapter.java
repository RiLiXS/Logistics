package com.example.logistics.home.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.utils.ViewShowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 判断adapter
 * @date :27.9.21
 */
public class SpecialJudgeAdapter extends RecyclerView.Adapter<SpecialJudgeAdapter.BaseViewHolder> {
    private List<QualityControlDtl> mData = new ArrayList<>();
    private Context context;

    public SpecialJudgeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_judge_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        QualityControlDtl qualityControlDtl = mData.get(position);
        holder.tv_test_project.setText("项目："+mData.get(position).getItemName());
        holder.rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_checked:
                        mData.set(position,new QualityControlDtl(mData.get(position).getBadItemId(),
                                mData.get(position).getItemName(),mData.get(position).getType(),
                                "1"));
                        break;
                    case R.id.rb_unchecked:
                        mData.set(position,new QualityControlDtl(mData.get(position).getBadItemId(),
                                mData.get(position).getItemName(),mData.get(position).getType(),
                                "0"));
                        break;
                }
            }
        });




    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<QualityControlDtl> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<QualityControlDtl> list) {
        if (list != null) {
            mData.clear();
            mData.addAll(list);
        }

    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_test_project;
        private RadioGroup rg_group;


        public BaseViewHolder(View itemView) {
            super(itemView);
            rg_group = itemView.findViewById(R.id.rg_group);//数量
            tv_test_project = itemView.findViewById(R.id.tv_test_project);//名称

        }
    }

    public static interface OnAddClickListener {

        public void onAddClick(int position);
        public void onReduceClick(int position);
        public void onEditClick(int position);
    }

    OnAddClickListener onItemAddClick;

    public void setOnAddClickListener(OnAddClickListener onItemAddClick) {
        this.onItemAddClick = onItemAddClick;
    }
}