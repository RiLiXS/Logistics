package com.example.logistics.home.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.DefectBean;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.utils.ViewShowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 专检缺陷adapter
 * @date :27.9.21
 */
public class SpecialDefectAdapter extends RecyclerView.Adapter<SpecialDefectAdapter.BaseViewHolder> {
    private List<QualityControlDtl> mData = new ArrayList<>();
    private Context context;

    public SpecialDefectAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_defect_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        QualityControlDtl qualityControlDtl = mData.get(position);
        holder.tv_name.setText(mData.get(position).getItemName());
        holder.et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer text = Integer.valueOf(TextUtils.isEmpty(holder.et_num.getText().toString())?"0":holder.et_num.getText().toString());
                if (text<=0){
                    return;
                }
                List<Double> recordList=new ArrayList<>();
                mData.set(position,new QualityControlDtl(mData.get(position).getBadItemId(),
                        mData.get(position).getItemName(),
                        text+"","",mData.get(position).getType(),recordList));
                onItemAddClick.onEditClick(position);
            }
        });
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer text = Integer.valueOf(holder.et_num.getText().toString());
                text=text+1;
                holder.et_num.setText(text+"");
                List<Double> recordList=new ArrayList<>();
                mData.set(position,new QualityControlDtl(mData.get(position).getBadItemId(),
                        mData.get(position).getItemName(),
                        text+"","",mData.get(position).getType(),recordList));
                onItemAddClick.onAddClick(position);
            }
        });
        holder.tv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer text = Integer.valueOf(holder.et_num.getText().toString());
                if (text==0){
                    ViewShowUtils.showShortToast(context,"数值已为0");
                    return;
                }
                text=text-1;
                holder.et_num.setText(text+"");
                List<Double> recordList=new ArrayList<>();
                mData.set(position,new QualityControlDtl(mData.get(position).getBadItemId(),
                        mData.get(position).getItemName(),
                        text+"","",mData.get(position).getType(),recordList));
                onItemAddClick.onReduceClick(position);
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

        private TextView tv_name, tv_reduce, tv_add;
        private EditText et_num;


        public BaseViewHolder(View itemView) {
            super(itemView);
            et_num = itemView.findViewById(R.id.et_num);//数量
            tv_name = itemView.findViewById(R.id.tv_name);//名称
            tv_reduce = itemView.findViewById(R.id.tv_reduce);//减
            tv_add = itemView.findViewById(R.id.tv_add);//加
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