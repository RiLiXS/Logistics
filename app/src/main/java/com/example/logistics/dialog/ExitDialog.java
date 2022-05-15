package com.example.logistics.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.logistics.R;
import com.example.logistics.utils.ActivityLifeHelper;


/**
 * @author : jyx
 * @description:
 * @date :29.11.21
 */
public class ExitDialog extends Dialog {
    private TextView tv_sure, tv_false;

    public ExitDialog(@NonNull Context context) {
        super(context);
    }

    public ExitDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.dialog);
    }

    protected ExitDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        tv_sure = findViewById(R.id.tv_sure);
        tv_false = findViewById(R.id.tv_false);
        tv_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityLifeHelper.getInstance().finishAllActivities();//销毁所有的Activity
            }
        });

    }
}
