package com.example.logistics.machining.activity.job;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.CodeBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.ProductJobHistoryBean;
import com.example.logistics.bean.RecordsWorkShopListBean;
import com.example.logistics.bean.RequestProductJobHistoryBean;
import com.example.logistics.bean.RequestWorkShopListBean;
import com.example.logistics.home.adapter.DefectProductListAdapter;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.plan.activity.WorkShopTaskActivity;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;


/**
 * 报工记录列表
 */
public class ProduceJobHistoryActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//任务列表
    RecyclerView rcy_task;
    private CodeBean codeBean = null;
    private ProductHistoryListAdapter productHistoryListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_produce_job_history;
    }

    @Override
    protected void initView() {
        img_return.setOnClickListener(this);
        codeBean = (CodeBean) getIntent().getSerializableExtra(C.mach.PRODUCT_CODE_BEAN);
    }

    @Override
    protected void initData() {
        initRcy();//初始化列表
        getJobHistory();//获取报工记录
    }



    private void initRcy() {
        productHistoryListAdapter = new ProductHistoryListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(productHistoryListAdapter);
    }

    /**
     * 获取报工记录
     */
    private void getJobHistory() {
        if (codeBean==null){
            return;
        }
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/workCenter/workcenter/listByProcessCardId/"+codeBean.getProcessCardId())
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestProductJobHistoryBean requestProductJobHistoryBean = new Gson().fromJson(data, RequestProductJobHistoryBean.class);
                            if (requestProductJobHistoryBean.getCode() == 0) {
                                List<ProductJobHistoryBean> productJobHistoryBeans = requestProductJobHistoryBean.getData();
                                if (productJobHistoryBeans == null) return;
                                productHistoryListAdapter.initList(productJobHistoryBeans);
                                productHistoryListAdapter.notifyDataSetChanged();



                            } else {
                                Toast.makeText(ProduceJobHistoryActivity.this, "" + requestProductJobHistoryBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(ProduceJobHistoryActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }


                    });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return:
                finish();
                break;

        }
    }
}