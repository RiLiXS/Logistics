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
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.QualityControlDtl;
import com.example.logistics.bean.ViceCard;
import com.example.logistics.machining.activity.MergeActivity;
import com.example.logistics.utils.MathUtils;
import com.example.logistics.utils.ViewShowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyx
 * @description: 专检测量adapter
 * @date :27.9.21
 */
public class SpecialMeasureAdapter extends RecyclerView.Adapter<SpecialMeasureAdapter.BaseViewHolder> {
    private List<QualityControlDtl> mData = new ArrayList<>();
    private Context context;
    private int linearSize = 0;

    public SpecialMeasureAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_measure_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (mData.size() == 0) return;
        QualityControlDtl qualityControlDtl = mData.get(position);
        holder.tv_title.setText(qualityControlDtl.getItemName() + "");
        holder.tv_desc.setText("标准范围：" + qualityControlDtl.getLowValue() + "~" + qualityControlDtl.getUpValue() + "（单位：" + qualityControlDtl.getUnit() + "）");
        View reason_list = View.inflate(context, R.layout.item_special_history_list, null);
        TextView tv_unit = reason_list.findViewById(R.id.tv_unit);
        EditText et_num = reason_list.findViewById(R.id.et_num);
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double s1 = 0.0;
                List<Double> recordList = new ArrayList<>();
                for (int i = 0; i < holder.linear_item.getChildCount(); i++) {
                    View childAt = holder.linear_item.getChildAt(i);
                    EditText et_num = (EditText) childAt.findViewById(R.id.et_num);
                    recordList.add(Double.valueOf(TextUtils.isEmpty(et_num.getText().toString()) ? "0" : et_num.getText().toString()));
                    s1 += Double.valueOf(TextUtils.isEmpty(et_num.getText().toString()) ? "0" : et_num.getText().toString());
                }
                holder.tv_average.setText(MathUtils.getNum(String.valueOf(s1)));
                mData.set(position, new QualityControlDtl(mData.get(position).getBadItemId(),
                        mData.get(position).getItemName(),
                        "0", holder.tv_average.getText().toString(), mData.get(position).getType(), recordList));
            }
        });
        tv_unit.setText("（" + qualityControlDtl.getUnit() + "）");
        holder.linear_item.addView(reason_list);
        holder.tv_add.setOnClickListener(new View.OnClickListener() {//新增
            @Override
            public void onClick(View v) {
                View tab1 = View.inflate(context, R.layout.item_special_history_list, null);
                TextView tv_unit = tab1.findViewById(R.id.tv_unit);
                tv_unit.setText("（" + qualityControlDtl.getUnit() + "）");
                EditText et_num2 = tab1.findViewById(R.id.et_num);
                et_num2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        List<Double> recordList = new ArrayList<>();
                        Double s1 = 0.0;
                        for (int i = 0; i < holder.linear_item.getChildCount(); i++) {
                            View childAt = holder.linear_item.getChildAt(i);
                            EditText et_num = (EditText) childAt.findViewById(R.id.et_num);
                            recordList.add(Double.valueOf(TextUtils.isEmpty(et_num.getText().toString()) ? "0" : et_num.getText().toString()));
                            s1 += Double.valueOf(TextUtils.isEmpty(et_num.getText().toString()) ? "0" : et_num.getText().toString());
                        }

                        holder.tv_average.setText(MathUtils.roundByScale((s1 / holder.linear_item.getChildCount()), 2));
                        mData.set(position, new QualityControlDtl(mData.get(position).getBadItemId(),
                                mData.get(position).getItemName(),
                                "0", holder.tv_average.getText().toString(), mData.get(position).getType(), recordList));

                    }
                });
                holder.linear_item.addView(tab1);
                for (int i = 0; i < holder.linear_item.getChildCount(); i++) {
                    View childAt = holder.linear_item.getChildAt(i);
                    TextView viewById = (TextView) childAt.findViewById(R.id.tv_content);
                    viewById.setText("记录" + (i + 1));

                }

            }
        });
        if (linearSize == 0) return;
        holder.linear_item.removeAllViews();
        refreshDate(holder, qualityControlDtl);

        for (int i = 0; i < holder.linear_item.getChildCount(); i++) {
            View childAt = holder.linear_item.getChildAt(i);
            EditText et_num2 = childAt.findViewById(R.id.et_num);
            et_num2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                    List<Double> recordList = new ArrayList<>();
                    Double s1 = 0.0;
                    for (int i = 0; i < holder.linear_item.getChildCount(); i++) {
                        View childAt = holder.linear_item.getChildAt(i);
                        EditText et_num = (EditText) childAt.findViewById(R.id.et_num);
                        recordList.add(Double.valueOf(TextUtils.isEmpty(et_num.getText().toString()) ? "0" : et_num.getText().toString()));
                        s1 += Double.valueOf(TextUtils.isEmpty(et_num.getText().toString()) ? "0" : et_num.getText().toString());
                    }

                    holder.tv_average.setText(MathUtils.roundByScale((s1 / holder.linear_item.getChildCount()), 2));
                    mData.set(position, new QualityControlDtl(mData.get(position).getBadItemId(),
                            mData.get(position).getItemName(),
                            "0", holder.tv_average.getText().toString(), mData.get(position).getType(), recordList));

                }
            });
        }

    }

    private void refreshDate(BaseViewHolder holder, QualityControlDtl qualityControlDtl) {
        for (int i = 0; i < linearSize; i++) {
            View tab1 = View.inflate(context, R.layout.item_special_history_list, null);
            TextView tv_unit = tab1.findViewById(R.id.tv_unit);
            tv_unit.setText("（" + qualityControlDtl.getUnit() + "）");
            holder.linear_item.addView(tab1);
            View childAt = holder.linear_item.getChildAt(i);
            TextView viewById = (TextView) childAt.findViewById(R.id.tv_content);
            viewById.setText("记录" + (i + 1));

        }


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
        this.mData = list;
    }

    /**
     * 刷新数据源
     */
    public void refreshList(int str) {
        this.linearSize = str;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_add, tv_title, tv_desc, tv_average;
        private LinearLayoutCompat linear_item;


        public BaseViewHolder(View itemView) {
            super(itemView);
            linear_item = itemView.findViewById(R.id.linear_item);
            tv_title = itemView.findViewById(R.id.tv_title);//添加条目
            tv_add = itemView.findViewById(R.id.tv_add);//添加条目
            tv_desc = itemView.findViewById(R.id.tv_desc);//添加条目
            tv_average = itemView.findViewById(R.id.tv_average);//平均值
//            tv_task_input_output = itemView.findViewById(R.id.tv_task_input_output);//投入/产出


        }
    }
}