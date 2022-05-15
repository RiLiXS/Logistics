package com.example.logistics;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.AesUtils;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.example.logistics.utils.ViewShowUtils;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.img_show)
    ImageView img_show;
    private static final String TAG = "LoginActivity";
    private LoadingDialog loadingDialog;
    private boolean isShow = false;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);

    }

    @Override
    protected void initData() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                isNull();
//                String encrypt = AesUtils.encrypt(et_password.getText().toString());
                //mPresenter.getLogin(LoginActivity.this,et_account.getText().toString(),encrypt);
                //ViewShowUtils.showShortToast(LoginActivity.this,"登录成功");


            }
        });
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        img_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    img_show.setImageResource(R.drawable.password_hide);
                    //如果选中，显示密码
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShow = false;
                } else {
                    isShow = true;
                    img_show.setImageResource(R.drawable.password_show);
                    //如果选中，隐藏密码
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getLogin() {
        OkHttpUtils.builder().url(C.BASE_HOST + "/auth/oauth/token?grant_type=password")
                // 有参数的话添加参数，可多个
                .addParam("username", et_account.getText().toString())
                .addParam("password", AesUtils.encrypt(et_password.getText().toString()))
                // 也可以添加多个
                .addHeader("Authorization", C.Setting.authorization)
                .addHeader("TENANT-ID", C.Setting.tenant_id)
                .addHeader("isToken", C.Setting.isToken)
//                .addHeader("Content-Type","application/x-www-form-urlencoded")
//                .addHeader("Content-Length","<calculated when request is sent>")

                // 如果是true的话，会类似于postman中post提交方式的raw，用json的方式提交，不是表单
                // 如果是false的话传统的表单提交
                .post(false)
                .async(new OkHttpUtils.ICallBack() {
                    @Override
                    public void onSuccessful(Call call, String data) {

                        LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                        if (loginBean.getCode() == 1) {
                            loadingDialog.loadFailed();
                            Toast.makeText(LoginActivity.this, "" + loginBean.getMsg(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            String access_token = loginBean.getAccess_token();
                            Log.e(TAG, access_token + "");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            loadingDialog.loadSuccess();
                            UserLocalData.setLogin(LoginActivity.this, loginBean);//保存登录信息

                        }


                    }

                    @Override
                    public void onFailure(Call call, String errorMsg) {
                        loadingDialog.loadFailed();
                        Toast.makeText(LoginActivity.this, errorMsg+"", Toast.LENGTH_SHORT).show();
                    }


                });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void isNull() {
        if (TextUtils.isEmpty(et_account.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString())) {
            ViewShowUtils.showShortToast(this, "账户或密码不能为空");

        } else {

            loadingDialog.setLoadingText("加载中...")//设置loading时显示的文字
                    .show();
            getLogin();
        }

    }


}