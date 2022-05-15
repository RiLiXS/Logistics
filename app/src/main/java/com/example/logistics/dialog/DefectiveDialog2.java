package com.example.logistics.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logistics.R;
import com.example.logistics.bean.BadItemListBean;
import com.example.logistics.bean.WorkCenterBadItem;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.machining.activity.ProduceJobActivity;
import com.example.logistics.machining.adapter.ReasonReportAdapter;
import com.example.logistics.machining.adapter.ReasonReportAdapter2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


/**
 * @author : jyx
 * @description:不良品上报
 * @date :29.11.21
 */
public class DefectiveDialog2 extends Dialog {
    private RecyclerView rcy_defective;
    private TextView tv_save, tv_cancel;
    private OnSaveListen onSaveListen;
    private OnScanListen onScanListen;
    private Context context;
    private static ReasonReportAdapter2 reasonReportAdapter;
    private List<BadItemListBean> badItemList;

    public DefectiveDialog2(@NonNull Context context) {
        super(context);
    }

    public DefectiveDialog2(@NonNull Context context, int themeResId, List<BadItemListBean> badItemList) {
        super(context, themeResId);
        this.context = context;
        this.badItemList=badItemList;
//        Window window = this.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.CENTER;
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高可设置具体大小
//        this.getWindow().setAttributes(lp);

    }

    protected DefectiveDialog2(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_defective);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
        initView();
    }

    @Override
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    private void initView() {

        rcy_defective = findViewById(R.id.rcy_defective);//物料编码
        reasonReportAdapter = new ReasonReportAdapter2(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcy_defective.setLayoutManager(linearLayoutManager);
        rcy_defective.setAdapter(reasonReportAdapter);
        reasonReportAdapter.initList(badItemList);

        tv_save = findViewById(R.id.tv_save);//保存
        tv_cancel = findViewById(R.id.tv_cancel);//取消
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  getStringData();
                if (onSaveListen != null) {
                    List<WorkCenterBadItem> badData = reasonReportAdapter.getBadData();
                    onSaveListen.onClick(v,badData);
                    dismiss();
                }

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    @Subscribe //订阅事件 回调
    public void onEventMainThread(MessageEvent event) {
        switch (event.getAction()) {
            case EventBusAction.UPDATED_REASON:
                List<BadItemListBean> badItemList = ProduceJobActivity.getBadItemList();
                reasonReportAdapter.initList(badItemList);
                break;
        }
    }


    /**
     * 保存新增的数据
     *
     * @return
     */
//    public BatchChargingDtl getStringData() {
//
//        return new BatchChargingDtl(et_bar_code.getText().toString(), et_batchNumber.getText().toString(),
//                et_matter_name.getText().toString(), et_matter_code.getText().toString(), et_unit.getText().toString(),
//                et_totalNum.getText().toString(), et_matterNum.getText().toString());
//    }


    public static void getScan(List<BadItemListBean> badItemList) {
        if (badItemList==null)return;
        reasonReportAdapter.initList(badItemList);

    }

    //保存
    public interface OnSaveListen {
        void onClick(View view,List<WorkCenterBadItem> badItemList);
    }

    public void setSaveListener(OnSaveListen onSaveListen) {
        this.onSaveListen = onSaveListen;
    }

    //扫码
    public interface OnScanListen {
        void onClick(View view);
    }

    public void setScanListener(OnScanListen onScanListen) {
        this.onScanListen = onScanListen;
    }


}
