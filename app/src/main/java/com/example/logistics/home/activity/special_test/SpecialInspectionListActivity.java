package com.example.logistics.home.activity.special_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logistics.R;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.home.adapter.HomeTestTaskAdapter;
import com.example.logistics.home.adapter.SpecialListTaskAdapter;
import com.example.logistics.machining.activity.MergeActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;


/**
 * 专检列表
 */
public class SpecialInspectionListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_return)
    ImageView img_return;
    @BindView(R.id.rcy_task)//专检列表
    RecyclerView rcy_task;
    @BindView(R.id.et_num)//卡号
    EditText et_num;
    @BindView(R.id.et_search)//输入框搜索
    EditText et_search;
    @BindView(R.id.img_task_code)//扫码
    ImageView img_task_code;
    @BindView(R.id.img_search)//搜索
    ImageView img_search;
    private  SpecialListTaskAdapter specialListTaskAdapter;
    private boolean isFirst=true;
    private int REQUEST_CODE_SCAN = 100;
    @Override
    protected int initLayout() {
        return R.layout.activity_special_inspection_list;
    }

    @Override
    protected void initView() {
        initTask();
        img_return.setOnClickListener(this);
        img_task_code.setOnClickListener(this);
        img_search.setOnClickListener(this);
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFirst==false)return;
                if (!TextUtils.isEmpty(s)){
                    getTaskData(String.valueOf(s));//查询流程卡列表
                    isFirst=false;
                }
            }
        });

    }



    @Override
    protected void initData() {
        getTaskData("");//获取任务数据
        /***
         * 监听输入框是否点击了搜索
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                    getTaskData(et_search.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取任务数据
     */
    private void getTaskData(String str) {

    }

    /**
     * 专检任务列表初始化
     */
    private void initTask() {
        specialListTaskAdapter = new SpecialListTaskAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(specialListTaskAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("扫描结果为：" + content);

                getTaskData(content);//查询流程卡信息
            }
        }
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_task_code://扫码
                Intent intent = new Intent(SpecialInspectionListActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.img_return://返回
                finish();
                break;
            case R.id.img_search://搜索
                getTaskData(et_search.getText().toString());
                break;
        }
    }
}