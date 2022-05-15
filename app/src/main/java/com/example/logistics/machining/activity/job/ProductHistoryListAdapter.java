package com.example.logistics.machining.activity.job;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.ProductJobHistoryBean;
import com.example.logistics.bean.RecordsBadItemListBean;
import com.example.logistics.bean.RequestProductJobHistoryBean;
import com.example.logistics.bean.SubmitBean;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * @author : jyx
 * @description: 报工记录adapter
 * @date :27.9.21
 */
public class ProductHistoryListAdapter extends RecyclerView.Adapter<ProductHistoryListAdapter.BaseViewHolder> {
    private List<ProductJobHistoryBean> mData = new ArrayList<>();
    private Context context;
    private boolean isCheckAll = false;


    public ProductHistoryListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_history_list, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mData.size() == 0) return;
        ProductJobHistoryBean productJobHistoryBean = mData.get(position);
        holder.tv_mach_name.setText(context.getString(R.string.job_code, productJobHistoryBean.getWorkCenterCode() + ""));
        holder.tv_matter_num.setText(context.getString(R.string.jockey_name, productJobHistoryBean.getWorkerName() + ""));
        holder.tv_mach_operation.setText(context.getString(R.string.procedure, productJobHistoryBean.getProcessName() + ""));
        holder.tv_mach_plan.setText(context.getString(R.string.plan_matter_name, productJobHistoryBean.getMaterialName() + ""));
        holder.tv_mach_num.setText(context.getString(R.string.mach_process_card, productJobHistoryBean.getBarcode() + ""));
        holder.tv_device.setText(context.getString(R.string.device_name, productJobHistoryBean.getEqptName() + ""));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(productJobHistoryBean.getWorkCenterId())) return;
                isCheckJob(productJobHistoryBean.getWorkCenterId(), productJobHistoryBean.getBarcode());//检验能否修改


            }
        });
        holder.tv_state.setVisibility(position == mData.size() - 1 ? View.VISIBLE : View.GONE);
        holder.tv_state.setOnClickListener(new View.OnClickListener() {//删除条目
            @Override
            public void onClick(View v) {
                deleteItem(productJobHistoryBean.getWorkCenterId(),position);//删除条目
            }
        });


    }


    /**
     * 删除条目
     * @param workCenterId
     * @param position
     */
    private void deleteItem(String workCenterId, int position) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/workcenter/" + workCenterId)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .delete()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            SubmitBean submitBean = new Gson().fromJson(data, SubmitBean.class);
                            if (submitBean.getCode() == 0) {
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                mData.remove(position);
                                notifyDataSetChanged();

                            } else {
                                Toast.makeText(context, "" + submitBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    /**
     * 检验能否修改
     *
     * @param barCode
     * @param workCenterId
     */
    private void isCheckJob(String workCenterId, String barCode) {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/workcenter/checkoutWorkCenter/" + workCenterId)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            SubmitBean submitBean = new Gson().fromJson(data, SubmitBean.class);
                            if (submitBean.getCode() == 0) {

                                //传输数据
                                Intent intent = new Intent(context, ProduceJobUpdateActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable(C.mach.PRODUCT_JOB_BEAN,productJobHistoryBean);
//                                intent.putExtras(bundle);
                                intent.putExtra(C.mach.WORK_CENTER_ID, workCenterId);
                                intent.putExtra(C.mach.PROCESS_CODE, barCode);
                                context.startActivity(intent);


                            } else {
                                Toast.makeText(context, "" + submitBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }

    /***
     * 返回列表中数据
     * @return
     */
    public List<ProductJobHistoryBean> getList() {
        return mData;
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void initList(List<ProductJobHistoryBean> list) {
        if (list != null) {
            mData.clear();
            mData.addAll(list);

        }
    }

    /**
     * 全选 取消 确定
     *
     * @param checkAll
     * @return
     */
    public Boolean CheckAll(boolean checkAll) {
        isCheckAll = checkAll;
        return checkAll;
    }

    /**
     * 添加数据源
     *
     * @param list
     */
    public void addList(List<ProductJobHistoryBean> list) {
        if (list != null) {
            mData.addAll(list);
        }
    }


    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mach_name, tv_mach_operation, tv_matter_num, tv_mach_num, tv_mach_plan, tv_device, tv_state;


        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_mach_name = itemView.findViewById(R.id.tv_mach_name);//物料名称
            tv_mach_operation = itemView.findViewById(R.id.tv_mach_operation);//工序
            tv_matter_num = itemView.findViewById(R.id.tv_matter_num);//物料编号
            tv_mach_plan = itemView.findViewById(R.id.tv_mach_plan);//任务数
            tv_mach_num = itemView.findViewById(R.id.tv_mach_num);//流程卡号
            tv_device = itemView.findViewById(R.id.tv_device);//设备
            tv_state = itemView.findViewById(R.id.tv_state);//删除


        }
    }
}