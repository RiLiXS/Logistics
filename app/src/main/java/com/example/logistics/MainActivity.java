package com.example.logistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.cretin.tools.fanpermission.FanPermissionListener;
import com.cretin.tools.fanpermission.FanPermissionUtils;
import com.example.logistics.base.BaseActivity;
import com.example.logistics.bean.AppUpdateBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestAppUpdateBean;
import com.example.logistics.bean.RequestDeviceListBean;
import com.example.logistics.bean.RequestUserListBean;
import com.example.logistics.bean.TaskReasonBean;
import com.example.logistics.bean.TaskSourceBean;
import com.example.logistics.bean.UserInfoBean;

import com.example.logistics.bean.UserRoleBean;
import com.example.logistics.dialog.ExitDialog;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.fragment.HomeFragment;
import com.example.logistics.fragment.MachiningFragment;
import com.example.logistics.fragment.MatterFragment;
import com.example.logistics.fragment.MineFragment;
import com.example.logistics.fragment.PlanFragment;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.utils.AppUtil;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.next.easynavigation.view.EasyNavigationBar;
import com.open.hule.library.entity.AppUpdate;
import com.open.hule.library.utils.UpdateManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigation_bar)
    EasyNavigationBar navigationBar;
    private static final String TAG = "MainActivity";
    private String[] tabText = {"首页", "计划",
            "生产", "质量", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.home_unselect_icon, R.mipmap.task_unselect_icon,
            R.mipmap.matter_unselect_icon, R.mipmap.machin_unselect_icon, R.mipmap.mine_unselect_icon};
    //选中时icon
    private int[] selectIcon = {R.mipmap.home_select_icon, R.mipmap.task_select_icon,
            R.mipmap.matter_select_icon, R.mipmap.machin_select_icon, R.mipmap.mine_select_icon};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //添加fragment集合
        fragments.add(new MatterFragment());
        fragments.add(new PlanFragment());
        fragments.add(new MachiningFragment());
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());
        getUserInfo();//获取用户信息
        getUserRole();//获取用户角色列表
        getPermission();//获取动态权限
        getUserList();//获取用户列表
        getDeviceList();//获取设备列表
        getAppUpdate();//版本更新
    }

    /**
     * 获取设备列表
     */
    private void getDeviceList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/mdmeqpt/eqpt/list")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestDeviceListBean requestDeviceListBean = new Gson().fromJson(data, RequestDeviceListBean.class);
                            if (requestDeviceListBean.getCode() == 1) {
                                Toast.makeText(MainActivity.this, "" + requestDeviceListBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                UserLocalData.setDeviceList(MainActivity.this, requestDeviceListBean);//保存登录信息
                                Log.d(TAG, requestDeviceListBean.getData() + "");

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }

    }

    /**
     * 获取用户列表
     */
    private void getUserList() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/user/list")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestUserListBean requestUserListBean = new Gson().fromJson(data, RequestUserListBean.class);
                            if (requestUserListBean.getCode() == 1) {
                                Toast.makeText(MainActivity.this, "" + requestUserListBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                UserLocalData.setUserList(MainActivity.this, requestUserListBean);//保存登录信息
                                Log.d(TAG, requestUserListBean.getData() + "");

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getPermission() {
        FanPermissionUtils.with(MainActivity.this)
                //添加所有你需要申请的权限
                .addPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .addPermissions(Manifest.permission.CAMERA)
                //添加权限申请回调监听 如果申请失败 会返回已申请成功的权限列表，用户拒绝的权限列表和用户点击了不再提醒的永久拒绝的权限列表
                .setPermissionsCheckListener(new FanPermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                        //所有权限授权成功才会回调这里
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                        //当有权限没有被授权就会回调这里
                        //会返回已申请成功的权限列表（grantedPermissions）
                        //用户拒绝的权限列表（deniedPermissions）
                        //用户点击了不再提醒的永久拒绝的权限列表（forceDeniedPermissions）
                    }
                })
                //生成配置
                .createConfig()
                //配置是否强制用户授权才可以使用，当设置为true的时候，如果用户拒绝授权，会一直弹出授权框让用户授权
                .setForceAllPermissionsGranted(true)
                //配置当用户点击了不再提示的时候，会弹窗指引用户去设置页面授权，这个参数是弹窗里面的提示内容
                .setForceDeniedPermissionTips("请前往设置->应用->【" + FanPermissionUtils.getAppName(MainActivity.this) + "】->权限中打开相关权限，否则功能无法正常运行！")
                //构建配置并生效
                .buildConfig()
                //开始授权
                .startCheckPermission();

    }

    @SuppressLint("ResourceType")
    @Override
    protected void initData() {
        //底部tab栏切换
        navigationBar
                .titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .selectTextColor(Color.parseColor(getString(R.color.tab_text_select)))
                .normalTextColor(Color.parseColor(getString(R.color.tab_text_unselect)))
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .build();
    }

    /**
     * 版本更新
     */
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
                                Toast.makeText(MainActivity.this, "" + appUpdateBean.getMsg(), Toast.LENGTH_SHORT).show();


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
        if (!AppUtil.getVersionCode(this).equals(requestAppUpdateBean.getAppVersion())) {
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
        new UpdateManager().startUpdate(MainActivity.this, appUpdate);

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
        new UpdateManager().startUpdate(MainActivity.this, appUpdate);

    }


    private void getSource() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/dict/type/task_source")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            TaskSourceBean taskSourceBean = new Gson().fromJson(data, TaskSourceBean.class);
                            if (taskSourceBean.getCode() == 1) {
                                Toast.makeText(MainActivity.this, "" + taskSourceBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                UserLocalData.setSource(MainActivity.this, taskSourceBean);//保存结转原因信息
                                Log.d(TAG, taskSourceBean.getData() + "");

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getReason() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/dict/type/transfer_reason")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            TaskReasonBean taskReasonBean = new Gson().fromJson(data, TaskReasonBean.class);
                            if (taskReasonBean.getCode() == 1) {
                                Toast.makeText(MainActivity.this, "" + taskReasonBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                UserLocalData.setReason(MainActivity.this, taskReasonBean);//保存结转原因信息
                                Log.d(TAG, taskReasonBean.getData() + "");

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getUserRole() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/role/list")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            UserRoleBean userRoleBean = new Gson().fromJson(data, UserRoleBean.class);
                            if (userRoleBean.getCode() == 1) {
                                Toast.makeText(MainActivity.this, "" + userRoleBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                UserLocalData.setRole(MainActivity.this, userRoleBean);//保存登录信息
                                Log.d(TAG, userRoleBean.getData() + "");

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }
    }

    private void getUserInfo() {
        LoginBean login = UserLocalData.getLogin();//获取登录信息
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/user/info")
                    // 也可以添加多个
                    .addHeader("Authorization", C.Setting.author_id + access_token)

//                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                    .addHeader("Content-Length", "<calculated when request is sent>")

                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            UserInfoBean userInfoBean = new Gson().fromJson(data, UserInfoBean.class);
                            if (userInfoBean.getCode() == 1) {
                                Toast.makeText(MainActivity.this, "" + userInfoBean.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                UserLocalData.setUser(MainActivity.this, userInfoBean);//保存登录信息
                                Log.d(TAG, userInfoBean.getData().getSysUser().getPhone() + "");
                                EventBus.getDefault().post(new MessageEvent(EventBusAction.UPDATED_MINE));

                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {

                        }


                    });
        }


    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ExitDialog exitDialog = new ExitDialog(this);
                exitDialog.show();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}