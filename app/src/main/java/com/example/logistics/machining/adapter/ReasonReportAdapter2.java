package com.example.logistics.machining.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.BadItemListBean;
import com.example.logistics.bean.WorkCenterBadItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 原因上报adapter
 * @date :27.9.21
 */
public class ReasonReportAdapter2 extends RecyclerView.Adapter<ReasonReportAdapter2.BaseViewHolder> {
    private List<BadItemListBean> mData = new ArrayList<>();
    private Context context;
    private List<WorkCenterBadItem> workCenterBadItem = new ArrayList<>();

    public ReasonReportAdapter2(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reason_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData == null) return;
        BadItemListBean badItemListBean = mData.get(position);
        if (badItemListBean == null) return;
//        WorkCenterBadItem workCenterBadItem = this.workCenterBadItem.get(position);
        holder.title_unqualifiedNum.setText(badItemListBean.getItemName());
        holder.et_unqualifiedNum.setText(badItemListBean.getNum());
//        workCenterBadItem.setBadItemId(badItemListBean.getBadItemId());
//        workCenterBadItem.setItemName(badItemListBean.getItemName());
        //   workCenterBadItem.add(position, new WorkCenterBadItem(badItemListBean.getBadItemId(), badItemListBean.getItemName(), holder.et_unqualifiedNum.getText().toString()));
       // workCenterBadItem.setNum(holder.et_unqualifiedNum.getText().toString() + "");

        //先移除监听
        if (holder.et_unqualifiedNum.getTag() instanceof TextWatcher) {
            holder.et_unqualifiedNum.removeTextChangedListener((TextWatcher) holder.et_unqualifiedNum.getTag());
            holder.et_unqualifiedNum.clearFocus();
        }
     //   holder.et_unqualifiedNum.setText(workCenterBadItem.getNum());//移除后设置数据
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.et_unqualifiedNum.hasFocus()) {
                    //写你editText的数据改变逻辑
                    workCenterBadItem.set(position,new WorkCenterBadItem(badItemListBean.getBadItemId(),badItemListBean.getItemName(),s+""));


                }
            }
        };
        //重新添加
        holder.et_unqualifiedNum.addTextChangedListener(watcher);
        //设置tag
        holder.et_unqualifiedNum.setTag(watcher);








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
            for (int i = 0; i < mData.size(); i++) {
                workCenterBadItem.add(new WorkCenterBadItem(list.get(i).getBadItemId(),list.get(i).getItemName(),list.get(i).getNum()));
            }
            notifyDataSetChanged();


        }
    }


    /**
     * 获取不良品数据
     *
     * @return
     */
    public List<WorkCenterBadItem> getBadData() {
        return workCenterBadItem;
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private EditText et_unqualifiedNum;
        private TextView title_unqualifiedNum;


        public BaseViewHolder(View itemView) {
            super(itemView);
            et_unqualifiedNum = itemView.findViewById(R.id.et_unqualifiedNum);//不良项目
            title_unqualifiedNum = itemView.findViewById(R.id.title_unqualifiedNum);//不良数值


        }
    }
}