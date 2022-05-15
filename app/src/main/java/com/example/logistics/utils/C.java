package com.example.logistics.utils;

import retrofit2.http.PUT;

/**
 * @author jyx
 * @description:
 * @date :2020-10-14
 */
public class C {
   //测试  演示专用  http://36.111.167.120:8888
    public static final String BASE_MOREBIT_DEV = "http://dev-openapi.urs.nextop.cc";//开发
    public static final String BASE_MOREBIT_TEST = "http://test-openapi.urs.nextop.cc";//测试
    public static final String BASE_MOREBIT_FORMAL = "https://api.morebit.com.cn";//正式
    public static final String BASE_MOREBIT_PRE = "http://urs-api.vantop.cc/";//预发布
    public static final String BASE_HOST = "http://223.76.222.128:9199"; //BuildConfig.BASE_HOST; http://223.76.222.128:9199 正式    http://218.29.152.122:9998 测试
    public static final String BASE_APPCODE = "logistics_ems"; // http://192.168.10.251:80   logistics_ems  内网  logistics
    public static class Setting {

        public static String tenant_id = "1";//
        public static String isToken = "false";//token
        public static String authorization = "Basic cGlnOnBpZw==";
        public static String gray = ""; //灰度控制参数
        public static final int deviceType = 1;
        public static final int os = 1;
        public static String author_id = "Bearer ";

    }

    public static class sp {
        public static final String TOKEN = "token";
        public static final String USERINFO = "userinfo"; // 用户缓存
        public static final String SERVICETIME = "servicetime";//服务端时间
        public static final String userInfo = "sUserInfo"; // 用户缓存
        public static final String loginInfo = "loginInfo"; // 用户缓存
        public static final String userRole = "userRole"; // 用户角色缓存
        public static final String userReason = "userReason"; // 用户原因缓存
        public static final String taskSource = "taskSource"; // 任务来源缓存
        public static final String userList = "userList"; // 用户列表
        public static final String deviceList = "deviceList"; // 用户列表
    }


    public static class requestCode {
        // 自己用
        public static final String dataListEmpty = "dataListEmpty";//  listdata为null
        public static final String dataNull = "dataNull";//   data为null

        public final static String SUCCESS = "0";//成功


    }

    public static class task {

        public static final String PATROL_TASK_LIST = "patrol_task_list";
        public static final String POINT_LIST = "point_list";
        public static final String POINT_ID = "point_id";
        public static final String TASK_ID = "task_id";
        public static final String TASK_INFO_ID = "task_info_id";
        public static final String TASK_NUM = "task_num";
        public static final String TASK_NAME = "task_name";
        public static final String TASK_BEAN = "task_bean";
        public static final String DEFECT_BEAN = "defect_bean";

    }

    public static class patrol {

        public static final String QUALITY_BEAN = "quality_bean";


    }

    public static class home {

        public static final String QUALITY_BEAN = "quality_bean";


    }
    public static class mach {

        public static final String PROCESS_ID = "process_id";
        public static final String PROCESS_MULTIPLE = "process_multiple";
        public static final String OUT_WORK_ID = "out_work_id";
        public static final String PRODUCT_JOB_BEAN = "product_job_bean";
        public static final String PRODUCT_CODE_BEAN = "product_code_bean";
        public static final String WORK_CENTER_ID = "work_center_id";
        public static final String PROCESS_CODE = "process_code";
    }

    public static class plan {

        public static final String PLAN_TYPE = "plan_type";//车间任务type


    }


    public static class IntentionType {


        public static final int FEED = 1;// 投料
        public static final int PRODUCE = 2; // 产出
        public static final int SPLIT = 3;//拆分
        public static final int MERGE = 4;//合并
        public static final int FIRST_INSPECTION = 5;// 首检
        public static final int ONSITE_INSPECTION = 6; //不合格品
        public static final int TURNOVER = 7;//周转
        public static final int CLEAN_TASK = 8;//清洗
        public static final int COLD_WORK_TASK = 9;//冷加工
        public static final int COATING_TASK = 10;//镀膜
        public static final int LITHOGRAPHY_TASK = 11;//光刻
        public static final int SAFE_TASK = 12;//全检
        public static final int PRISM_TASK = 13;//棱镜
        public static final int OUT_ASSIST = 14;//外协
        public static final int MERGE_CODE = 15;//转码合并


    }
}
