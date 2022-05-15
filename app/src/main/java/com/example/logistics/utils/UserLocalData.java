package com.example.logistics.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.logistics.App;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.RequestDeviceListBean;
import com.example.logistics.bean.RequestUserListBean;
import com.example.logistics.bean.TaskReasonBean;
import com.example.logistics.bean.TaskSourceBean;
import com.example.logistics.bean.UserInfoBean;
import com.example.logistics.bean.UserRoleBean;

/**
 * @author : jyx
 * @description:
 * @date :12.10.21
 */
public class UserLocalData {
    public static UserInfoBean sUserInfo;
    public static LoginBean sLoginInfo;
    public static UserRoleBean sUserRole;
    public static TaskReasonBean sTaskReason;
    public static TaskSourceBean sTaskSource;
    public static RequestUserListBean requestUserListBean;
    public static RequestDeviceListBean requestDeviceListBean;


    /**
     * 获取设备列表
     *
     * @return
     */
    public static RequestDeviceListBean getDeviceList() {
        return getDeviceList(App.getAppContext());
    }

    public static RequestDeviceListBean getDeviceList(Context activity) {
        if (requestDeviceListBean != null) {
            return requestDeviceListBean;
        }
        try {
            String msgJson = (String) SharedPreferencesUtils.get(activity, C.sp.deviceList, "");
            if (msgJson != null && !"".equals(msgJson)) {
                RequestDeviceListBean userInfo = MyGsonUtils.jsonToBean(msgJson, RequestDeviceListBean.class);
                if (userInfo == null) {
                    requestDeviceListBean = (RequestDeviceListBean) App.getACache().getAsObject(C.sp.deviceList);
                } else {
                    requestDeviceListBean = userInfo;
                }
            } else {
                requestDeviceListBean = (RequestDeviceListBean) App.getACache().getAsObject(C.sp.deviceList);
            }
            if (requestDeviceListBean == null) {
                requestDeviceListBean = new RequestDeviceListBean();
            }
            return requestDeviceListBean;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new RequestDeviceListBean();
    }
    /**
     * 获取用户列表
     *
     * @return
     */
    public static RequestUserListBean getUserList() {
        return getUserList(App.getAppContext());
    }

    public static RequestUserListBean getUserList(Context activity) {
        if (requestUserListBean != null) {
            return requestUserListBean;
        }
        try {
            String msgJson = (String) SharedPreferencesUtils.get(activity, C.sp.userList, "");
            if (msgJson != null && !"".equals(msgJson)) {
                RequestUserListBean userInfo = MyGsonUtils.jsonToBean(msgJson, RequestUserListBean.class);
                if (userInfo == null) {
                    requestUserListBean = (RequestUserListBean) App.getACache().getAsObject(C.sp.userList);
                } else {
                    requestUserListBean = userInfo;
                }
            } else {
                requestUserListBean = (RequestUserListBean) App.getACache().getAsObject(C.sp.userList);
            }
            if (requestUserListBean == null) {
                requestUserListBean = new RequestUserListBean();
            }
            return requestUserListBean;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new RequestUserListBean();
    }

    /**
     * 获取任务来源信息
     *
     * @return
     */
    public static TaskSourceBean getSource() {
        return getSource(App.getAppContext());
    }

    public static TaskSourceBean getSource(Context activity) {
        if (sTaskSource != null) {
            return sTaskSource;
        }
        try {
            String msgJson = (String) SharedPreferencesUtils.get(activity, C.sp.taskSource, "");
            if (msgJson != null && !"".equals(msgJson)) {
                TaskSourceBean userInfo = MyGsonUtils.jsonToBean(msgJson, TaskSourceBean.class);
                if (userInfo == null) {
                    sTaskSource = (TaskSourceBean) App.getACache().getAsObject(C.sp.taskSource);
                } else {
                    sTaskSource = userInfo;
                }
            } else {
                sTaskSource = (TaskSourceBean) App.getACache().getAsObject(C.sp.taskSource);
            }
            if (sTaskSource == null) {
                sTaskSource = new TaskSourceBean();
            }
            return sTaskSource;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new TaskSourceBean();
    }
    /**
     * 获取用户原因
     *
     * @return
     */
    public static TaskReasonBean getReason() {
        return getReason(App.getAppContext());
    }

    public static TaskReasonBean getReason(Context activity) {
        if (sTaskReason != null) {
            return sTaskReason;
        }
        try {
            String msgJson = (String) SharedPreferencesUtils.get(activity, C.sp.userReason, "");
            if (msgJson != null && !"".equals(msgJson)) {
                TaskReasonBean userInfo = MyGsonUtils.jsonToBean(msgJson, TaskReasonBean.class);
                if (userInfo == null) {
                    sTaskReason = (TaskReasonBean) App.getACache().getAsObject(C.sp.userReason);
                } else {
                    sTaskReason = userInfo;
                }
            } else {
                sTaskReason = (TaskReasonBean) App.getACache().getAsObject(C.sp.userReason);
            }
            if (sTaskReason == null) {
                sTaskReason = new TaskReasonBean();
            }
            return sTaskReason;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new TaskReasonBean();
    }


    /**
     * 获取User角色信息
     *
     * @return
     *//*
    public static UserRoleBean getRole() {
        return getRole(App.getAppContext());
    }

    public static UserRoleBean getRole(Context activity) {
        if (sUserRole != null) {
            return sUserRole;
        }
        try {
            String msgJson = (String) SharedPreferencesUtils.get(activity, C.sp.userRole, "");
            if (msgJson != null && !"".equals(msgJson)) {
                UserRoleBean userInfo = MyGsonUtils.jsonToBean(msgJson, UserRoleBean.class);
                if (userInfo == null) {
                    sUserRole = (UserRoleBean) App.getACache().getAsObject(C.sp.userRole);
                } else {
                    sUserRole = userInfo;
                }
            } else {
                sUserRole = (UserRoleBean) App.getACache().getAsObject(C.sp.userRole);
            }
            if (sUserRole == null) {
                sUserRole = new UserRoleBean();
            }
            return sUserRole;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new UserRoleBean();
    }*/

    /**
     * 获取User信息
     *
     * @return
     */
    public static UserInfoBean getUser() {
        return getUser(App.getAppContext());
    }

    public static UserInfoBean getUser(Context activity) {
        if (sUserInfo != null) {
            return sUserInfo;
        }
        try {
            String msgJson = (String) SharedPreferencesUtils.get(activity, C.sp.userInfo, "");
            if (msgJson != null && !"".equals(msgJson)) {
                UserInfoBean userInfo = MyGsonUtils.jsonToBean(msgJson, UserInfoBean.class);
                if (userInfo == null) {
                    sUserInfo = (UserInfoBean) App.getACache().getAsObject(C.sp.userInfo);
                } else {
                    sUserInfo = userInfo;
                }
            } else {
                sUserInfo = (UserInfoBean) App.getACache().getAsObject(C.sp.userInfo);
            }
            if (sUserInfo == null) {
                sUserInfo = new UserInfoBean();
            }
            return sUserInfo;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new UserInfoBean();
    }

    /**
     * 获取User信息
     *
     * @return
     */
    public static LoginBean getLogin() {
        return getLogin(App.getAppContext());
    }

    public static LoginBean getLogin(Context activity) {
        if (sLoginInfo != null) {
            return sLoginInfo;
        }
        try {
            String msgJson = (String) SharedPreferencesUtils.get(activity, C.sp.loginInfo, "");
            if (msgJson != null && !"".equals(msgJson)) {
                LoginBean userInfo = MyGsonUtils.jsonToBean(msgJson, LoginBean.class);
                if (userInfo == null) {
                    sLoginInfo = (LoginBean) App.getACache().getAsObject(C.sp.loginInfo);
                } else {
                    sLoginInfo = userInfo;
                }
            } else {
                sLoginInfo = (LoginBean) App.getACache().getAsObject(C.sp.loginInfo);
            }
            if (sLoginInfo == null) {
                sLoginInfo = new LoginBean();
            }
            return sLoginInfo;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new LoginBean();
    }

    public static void closeUser() {
//        mMyHeadBitmap = null;
//        mUserhead = "";
//        App.getACache().put(C.sp.token, "");
        App.getACache().put(C.sp.userInfo, "");
        App.getACache().put(C.sp.loginInfo, "");
        App.getACache().put(C.sp.userRole, "");
        App.getACache().put(C.sp.userReason, "");
        App.getACache().put(C.sp.taskSource, "");
        App.getACache().put(C.sp.userList, "");
        App.getACache().put(C.sp.deviceList, "");
//        SharedPreferencesUtils.put(App.getAppContext(), C.sp.token, "");
        SharedPreferencesUtils.put(App.getAppContext(), C.sp.userInfo, "");
        SharedPreferencesUtils.put(App.getAppContext(), C.sp.loginInfo, "");
        SharedPreferencesUtils.put(App.getAppContext(), C.sp.userRole, "");
        SharedPreferencesUtils.put(App.getAppContext(), C.sp.userReason, "");
        SharedPreferencesUtils.put(App.getAppContext(), C.sp.taskSource, "");
        SharedPreferencesUtils.put(App.getAppContext(), C.sp.userList, "");
        SharedPreferencesUtils.put(App.getAppContext(), C.sp.deviceList, "");
//        sToken = "";
        sUserInfo = null;
        sLoginInfo = null;
        sUserRole=null;
        sTaskReason=null;
        sTaskSource=null;
        requestUserListBean=null;
        requestDeviceListBean=null;

    }

    /**
     * 保存任务来源信息
     *
     * @param activity
     * @param userInfo
     */
    public static void setSource(final Activity activity, TaskSourceBean userInfo) {
        if (userInfo == null) {
//            mMyHeadBitmap = null;
//            mUserhead = "";
            return;
        }
//        SensorsDataUtil.getInstance().setUserInfo();
        try {
            String jsonInfo = MyGsonUtils.beanToJson(userInfo);
            if (!TextUtils.isEmpty(jsonInfo)) {
                SharedPreferencesUtils.put(activity, C.sp.taskSource, jsonInfo);
            }
            App.getACache().put(C.sp.taskSource, userInfo);
            sTaskSource = userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存user原因信息
     *
     * @param activity
     * @param userInfo
     */
    public static void setReason(final Activity activity, TaskReasonBean userInfo) {
        if (userInfo == null) {
//            mMyHeadBitmap = null;
//            mUserhead = "";
            return;
        }
//        SensorsDataUtil.getInstance().setUserInfo();
        try {
            String jsonInfo = MyGsonUtils.beanToJson(userInfo);
            if (!TextUtils.isEmpty(jsonInfo)) {
                SharedPreferencesUtils.put(activity, C.sp.userReason, jsonInfo);
            }
            App.getACache().put(C.sp.userReason, userInfo);
            sTaskReason = userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存user角色信息
     *
     * @param activity
     * @param userInfo
     */
    public static void setRole(final Activity activity, UserRoleBean userInfo) {
        if (userInfo == null) {
//            mMyHeadBitmap = null;
//            mUserhead = "";
            return;
        }
//        SensorsDataUtil.getInstance().setUserInfo();
        try {
            String jsonInfo = MyGsonUtils.beanToJson(userInfo);
            if (!TextUtils.isEmpty(jsonInfo)) {
                SharedPreferencesUtils.put(activity, C.sp.userRole, jsonInfo);
            }
            App.getACache().put(C.sp.userRole, userInfo);
            sUserRole = userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存user信息
     *
     * @param activity
     * @param userInfo
     */
    public static void setUser(final Activity activity, UserInfoBean userInfo) {
        if (userInfo == null) {
//            mMyHeadBitmap = null;
//            mUserhead = "";
            return;
        }
//        SensorsDataUtil.getInstance().setUserInfo();
        try {
            String jsonInfo = MyGsonUtils.beanToJson(userInfo);
            if (!TextUtils.isEmpty(jsonInfo)) {
                SharedPreferencesUtils.put(activity, C.sp.userInfo, jsonInfo);
            }
            App.getACache().put(C.sp.userInfo, userInfo);
            sUserInfo = userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存设备列表
     *
     * @param activity
     * @param userInfo
     */
    public static void setDeviceList(final Activity activity, RequestDeviceListBean userInfo) {
        if (userInfo == null) {
//            mMyHeadBitmap = null;
//            mUserhead = "";
            return;
        }
//        SensorsDataUtil.getInstance().setUserInfo();
        try {
            String jsonInfo = MyGsonUtils.beanToJson(userInfo);
            if (!TextUtils.isEmpty(jsonInfo)) {
                SharedPreferencesUtils.put(activity, C.sp.deviceList, jsonInfo);
            }
            App.getACache().put(C.sp.deviceList, userInfo);
            requestDeviceListBean = userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存user列表信息
     *
     * @param activity
     * @param userInfo
     */
    public static void setUserList(final Activity activity, RequestUserListBean userInfo) {
        if (userInfo == null) {
//            mMyHeadBitmap = null;
//            mUserhead = "";
            return;
        }
//        SensorsDataUtil.getInstance().setUserInfo();
        try {
            String jsonInfo = MyGsonUtils.beanToJson(userInfo);
            if (!TextUtils.isEmpty(jsonInfo)) {
                SharedPreferencesUtils.put(activity, C.sp.userList, jsonInfo);
            }
            App.getACache().put(C.sp.userList, userInfo);
            requestUserListBean = userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存登录信息
     *
     * @param activity
     * @param userInfo
     */
    public static void setLogin(final Activity activity, LoginBean userInfo) {
        if (userInfo == null) {
//            mMyHeadBitmap = null;
//            mUserhead = "";
            return;
        }
//        SensorsDataUtil.getInstance().setUserInfo();
        try {
            String jsonInfo = MyGsonUtils.beanToJson(userInfo);
            if (!TextUtils.isEmpty(jsonInfo)) {
                SharedPreferencesUtils.put(activity, C.sp.loginInfo, jsonInfo);
            }
            App.getACache().put(C.sp.loginInfo, userInfo);
            sLoginInfo = userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
