package com.example.logistics.machining.adapter;

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
import com.example.logistics.bean.BadItemListBean;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.bean.WorkCenterBadItem;
import com.example.logistics.utils.ViewShowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 不良缺陷adapter
 * @date :27.9.21
 */
public class OutDefectAdapter extends RecyclerView.Adapter<OutDefectAdapter.BaseViewHolder> {
    private List<BadItemListBean> mData = new ArrayList<>();
    private Context context;

    public OutDefectAdapter(Context context) {
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
        BadItemListBean badItemListBean = mData.get(position);
        holder.tv_name.setText(mData.get(position).getItemName());
        //先移除监听
        if (holder.et_num.getTag() instanceof TextWatcher) {
            holder.et_num.removeTextChangedListener((TextWatcher) holder.et_num.getTag());
            holder.et_num.clearFocus();
        }
        holder.et_num.setText(badItemListBean.getNum());//移除后设置数据
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.et_num.hasFocus()) {
                    Integer text = Integer.valueOf(TextUtils.isEmpty(holder.et_num.getText().toString())?"0":holder.et_num.getText().toString());
                    if (text<=0){
                        return;
                    }
                    mData.set(position,new BadItemListBean(mData.get(position).getBadItemId(),
                            mData.get(position).getItemName(),
                            text+""));
                    onItemAddClick.onEditClick(position);

                }
            }
        };

        //重新添加
        holder.et_num.addTextChangedListener(watcher);
        //设置tag
        holder.et_num.setTag(watcher);
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer text = Integer.valueOf(holder.et_num.getText().toString());
                text=text+1;
                holder.et_num.setText(text+"");
                List<Double> recordList=new ArrayList<>();
                mData.set(position,new BadItemListBean(mData.get(position).getBadItemId(),
                        mData.get(position).getItemName(),
                        text+""));
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
                mData.set(position,new BadItemListBean(mData.get(position).getBadItemId(),
                        mData.get(position).getItemName(),
                        text+""));
                onItemAddClick.onReduceClick(position);
            }
        });




    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<BadItemListBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<BadItemListBean> list) {
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