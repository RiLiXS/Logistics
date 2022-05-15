package com.example.logistics.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.logistics.LoginActivity;
import com.example.logistics.R;
import com.example.logistics.base.BaseFragment;
import com.example.logistics.bean.AppUpdateBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestAppUpdateBean;
import com.example.logistics.bean.UserInfoBean;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.ActivityLifeHelper;
import com.example.logistics.utils.AppUtil;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.open.hule.library.entity.AppUpdate;
import com.open.hule.library.utils.UpdateManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import okhttp3.Call;

/**
 * 我的
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private ConstraintLayout conLayout_line;//我的线边库

    private ConstraintLayout conLayout_mine;//个人中心

    private ConstraintLayout conLayout_kpi;//上班打卡

    private ConstraintLayout conLayout_skill;//我的设备

    private ConstraintLayout conLayout_logout;//退出登录
    private ConstraintLayout conLayout_update;//APP更新
    private ImageView img_avatar;//头像
    private TextView tv_name;//姓名
    private TextView tv_phone;//电话
    private boolean isFirstLoading = true;
    private  TextView tv_version_update;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int initLayout() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        conLayout_line = view.findViewById(R.id.conLayout_line);
        conLayout_mine = view.findViewById(R.id.conLayout_mine);
        conLayout_kpi = view.findViewById(R.id.conLayout_kpi);
        conLayout_skill = view.findViewById(R.id.conLayout_skill);
        conLayout_logout = view.findViewById(R.id.conLayout_logout);
        conLayout_update = view.findViewById(R.id.conLayout_update);//APP更新
        conLayout_update.setOnClickListener(this);
        conLayout_line.setOnClickListener(this);
        conLayout_mine.setOnClickListener(this);
        conLayout_kpi.setOnClickListener(this);
        conLayout_skill.setOnClickListener(this);
        conLayout_logout.setOnClickListener(this);
        tv_version_update=view.findViewById(R.id.tv_version_update);
        img_avatar = view.findViewById(R.id.img_avatar);
        tv_name = view.findViewById(R.id.tv_name);
        tv_phone = view.findViewById(R.id.tv_phone);
        if (C.BASE_HOST.equals("http://218.29.152.122:9998")){
            tv_version_update.setText("版本更新 "+"v"+AppUtil.getVersionCode(getActivity())+"(测试)");
        }else{
            tv_version_update.setText("版本更新 "+"v"+AppUtil.getVersionCode(getActivity()));
        }

    }

    @Override
    protected void initData(Context mContext) {

    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.conLayout_mine://个人中心
                break;
            case R.id.conLayout_line://我的线边库
                break;

            case R.id.conLayout_kpi://上班打卡
                break;
            case R.id.conLayout_skill://我的设备
                break;
            case R.id.conLayout_logout://退出登录
                ActivityLifeHelper.getInstance().finishAllActivities();//销毁所有的Activity
                UserLocalData.closeUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));//跳转登录页面
                break;

            case R.id.conLayout_update://APP更新
                getAppUpdate();
                break;


        }
    }

    private void getAppUpdate() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/appversion/appinfo/AppVersion/"+C.BASE_APPCODE)
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            AppUpdateBean appUpdateBean = new Gson().fromJson(data, AppUpdateBean.class);
                            if (appUpdateBean.getCode() == 0) {
                                RequestAppUpdateBean requestAppUpdateBean = appUpdateBean.getData();
                                if (requestAppUpdateBean == null) return;
                                isAppUpdate(requestAppUpdateBean);

                            } else {
                                Toast.makeText(getActivity(), "" + appUpdateBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }


    }

    /**
     * 判断是否更新
     *
     * @param requestAppUpdateBean
     */
    private void isAppUpdate(RequestAppUpdateBean requestAppUpdateBean) {
        if (AppUtil.getVersionCode(getActivity()).equals(requestAppUpdateBean.getAppVersion())) {
            Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_SHORT).show();
        } else {
            //  Toast.makeText(getActivity(),"请下载最新版本",Toast.LENGTH_SHORT).show();

           /* AppUpdate appUpdate = new AppUpdate.Builder()
                    //更新地址（必须）
                    .newVersionUrl("http://download.api.chongpar.com/android.apk")
                    // 版本号（非必须）
//                    .newVersionCode("v1.7")
                    // 文件大小（非必须）
//                    .fileSize("5.8M")
                    // 更新内容（非必填，默认“1.用户体验优化\n2.部分问题修复”）
                    .updateInfo("1.用户体验优化\n2.部分问题修复")
                    .build();
            new UpdateManager().startUpdate(getActivity(), appUpdate);*/

            //是否强制更新
            if (requestAppUpdateBean.getForced().equals("0")) {
                plainUpdate(requestAppUpdateBean);
            } else {
                forceUpdate(requestAppUpdateBean);
            }


        }

    }

    /**
     * 强制更新
     *
     * @param requestAppUpdateBean
     */
    private void forceUpdate(RequestAppUpdateBean requestAppUpdateBean) {
        // 更新的数据参数
        AppUpdate appUpdate = new AppUpdate.Builder()
                //更新地址（必传）
                .newVersionUrl(C.BASE_HOST + requestAppUpdateBean.getFileUrl())
                // 版本号（非必填）
                //  .newVersionCode("v1.6")
                //【建议】：自定义布局在静默下载模式下使用
                // 通过传入资源id来自定义更新对话框，注意取消更新的id要定义为btnUpdateLater，立即更新的id要定义为btnUpdateNow（非必填）
                .updateResourceId(R.layout.dialog_update)
                // 更新的标题，弹框的标题（非必填，默认为应用更新）
                .updateTitle(R.string.update_title)
                // 更新内容的提示语，内容的标题（非必填，默认为更新内容）
                .updateContentTitle(R.string.update_content_lb)
                // 更新内容（非必填，默认“1.用户体验优化\n2.部分问题修复”）
                .updateInfo(requestAppUpdateBean.getRemark().replace("\\n", "\n"))
                // 文件大小（非必填）
                // .fileSize("5.8M")
                // 保存文件路径（默认前缀：Android/data/包名/files/ 文件名：download）
//                    .savePath("/WinsMine")
                //是否采取静默下载模式（非必填，只显示更新提示，后台下载完自动弹出安装界面），否则，显示下载进度，显示下载失败
                .isSilentMode(false)
                //是否强制更新（非必填，默认不采取强制更新，否则，不更新无法使用）
                .forceUpdate(1)
                //文件的MD5值，默认不传，如果不传，不会去验证md5(非静默下载模式生效，若有值，且验证不一致，会启动浏览器去下载)
                //  .md5("2d9feb595d3aa093e9ee49412d2c8805")
                .build();
        new UpdateManager().startUpdate(getActivity(), appUpdate);

    }

    /**
     * 普通更新
     *
     * @param requestAppUpdateBean
     */
    private void plainUpdate(RequestAppUpdateBean requestAppUpdateBean) {
        // 更新的数据参数
        AppUpdate appUpdate = new AppUpdate.Builder()
                //更新地址（必传）
                .newVersionUrl(C.BASE_HOST + requestAppUpdateBean.getFileUrl())
                // 版本号（非必填）
                //  .newVersionCode("v1.6")
                //【建议】：自定义布局在静默下载模式下使用
                // 通过传入资源id来自定义更新对话框，注意取消更新的id要定义为btnUpdateLater，立即更新的id要定义为btnUpdateNow（非必填）
                .updateResourceId(R.layout.dialog_update)
                // 更新的标题，弹框的标题（非必填，默认为应用更新）
                .updateTitle(R.string.update_title)
                // 更新内容的提示语，内容的标题（非必填，默认为更新内容）
                .updateContentTitle(R.string.update_content_lb)
                // 更新内容（非必填，默认“1.用户体验优化\n2.部分问题修复”）
                .updateInfo(requestAppUpdateBean.getRemark().replace("\\n", "\n"))
                // 文件大小（非必填）
                //   .fileSize("5.8M")
                // 保存文件路径（默认前缀：Android/data/包名/files/ 文件名：download）
//                    .savePath("/WinsMine")
                //是否采取静默下载模式（非必填，只显示更新提示，后台下载完自动弹出安装界面），否则，显示下载进度，显示下载失败
                .isSilentMode(false)
                //是否强制更新（非必填，默认不采取强制更新，否则，不更新无法使用）
                .forceUpdate(0)
                //文件的MD5值，默认不传，如果不传，不会去验证md5(非静默下载模式生效，若有值，且验证不一致，会启动浏览器去下载)
                //  .md5("2d9feb595d3aa093e9ee49412d2c8805")
                .build();
        new UpdateManager().startUpdate(getActivity(), appUpdate);

    }


    @Subscribe //订阅事件 回调
    public void onEventMainThread(MessageEvent event) {
        switch (event.getAction()) {
            case EventBusAction.UPDATED_MINE:
                initRefresh();
                break;
        }
    }

    private void initRefresh() {
        UserInfoBean user = UserLocalData.getUser();
        String username = user.getData().getSysUser().getUsername();
        String phone = user.getData().getSysUser().getPhone();
        if (!TextUtils.isEmpty(username)) {
            tv_name.setText(username);
        }
        if (!TextUtils.isEmpty(phone)) {
            tv_phone.setText(phone);
        }
        //tv_job.setText("总经理");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoading) {
//如果不是第一次加载，刷新数据
            initRefresh();
        }

        isFirstLoading = false;
    }

}

