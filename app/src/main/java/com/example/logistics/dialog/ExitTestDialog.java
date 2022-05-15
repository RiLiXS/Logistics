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
public class ExitTestDialog extends Dialog {
    private TextView tv_sure, tv_false;
    private Context context;
    private onClickListen onClickListen;

    public ExitTestDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public ExitTestDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.dialog);
    }

    protected ExitTestDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test_exit);
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
                if (onClickListen != null) {
                    onClickListen.onClick(v);
                    dismiss();
                }
            }
        });

    }
    //保存
    public interface onClickListen {
        void onClick(View view);
    }

    public void setOnClickListen(onClickListen onClickListen) {
        this.onClickListen = onClickListen;
    }

}
