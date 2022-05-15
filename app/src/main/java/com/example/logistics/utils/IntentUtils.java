package com.example.logistics.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.logistics.home.activity.defects.DefectProductActivity;
import com.example.logistics.home.activity.finall_test.FinalInspectionActivity;
import com.example.logistics.home.activity.first_test.FirstInspectionActivity;
import com.example.logistics.home.activity.first_test.FirstInspectionListActivity;
import com.example.logistics.home.activity.onsite_test.OnsiteInspectionActivity;
import com.example.logistics.home.activity.onsite_test.OnsiteInspectionListActivity;
import com.example.logistics.home.activity.special_test.SpecialInspectionActivity;
import com.example.logistics.home.activity.special_test.SpecialInspectionListActivity;
import com.example.logistics.machining.activity.FeedingAddActivity;
import com.example.logistics.machining.activity.MergeActivity;
import com.example.logistics.machining.activity.ProduceJobActivity;
import com.example.logistics.machining.activity.SplitActivity;
import com.example.logistics.machining.activity.TransCodeMergeActivity;
import com.example.logistics.machining.activity.assist.OutAssistActivity;
import com.example.logistics.machining.activity.turnover.TurnOverActivity;
import com.example.logistics.plan.activity.WorkShopTaskActivity;

/**
 * @author : jyx
 * @description:跳转工具类
 * @date :1.12.21
 */
public class IntentUtils {
    public static void gotoAction(Activity activity, int id) {
        switch (id) {
            case C.IntentionType.FEED://投料
                activity.startActivity(new Intent(activity, FeedingAddActivity.class));
                break;
            case C.IntentionType.PRODUCE://产出
                activity.startActivity(new Intent(activity, ProduceJobActivity.class));
                break;
            case C.IntentionType.SPLIT://加工
                activity.startActivity(new Intent(activity, SplitActivity.class));
                break;
            case C.IntentionType.MERGE://合并
                activity.startActivity(new Intent(activity, MergeActivity.class));
                break;
            case C.IntentionType.FIRST_INSPECTION://质检上报
                activity.startActivity(new Intent(activity, FinalInspectionActivity.class));
                break;
            case C.IntentionType.ONSITE_INSPECTION://不合格品
             activity.startActivity(new Intent(activity, DefectProductActivity.class));
                break;
            case C.IntentionType.TURNOVER://周转
                activity.startActivity(new Intent(activity, TurnOverActivity.class));
                break;
            case C.IntentionType.CLEAN_TASK://清洗
                Intent intent = new Intent(activity, WorkShopTaskActivity.class);
                intent.putExtra(C.plan.PLAN_TYPE,1);
                activity.startActivity(intent);
                break;
            case C.IntentionType.COLD_WORK_TASK://冷加工
                Intent intent2 = new Intent(activity, WorkShopTaskActivity.class);
                intent2.putExtra(C.plan.PLAN_TYPE,2);
                activity.startActivity(intent2);
                break;
            case C.IntentionType.COATING_TASK://镀膜
                Intent intent3 = new Intent(activity, WorkShopTaskActivity.class);
                intent3.putExtra(C.plan.PLAN_TYPE,3);
                activity.startActivity(intent3);
                break;
            case C.IntentionType.LITHOGRAPHY_TASK://光刻
                Intent intent4 = new Intent(activity, WorkShopTaskActivity.class);
                intent4.putExtra(C.plan.PLAN_TYPE,4);
                activity.startActivity(intent4);
                break;
            case C.IntentionType.SAFE_TASK://全检
                Intent intent5 = new Intent(activity, WorkShopTaskActivity.class);
                intent5.putExtra(C.plan.PLAN_TYPE,5);
                activity.startActivity(intent5);
                break;
            case C.IntentionType.PRISM_TASK://棱镜
                Intent intent6 = new Intent(activity, WorkShopTaskActivity.class);
                intent6.putExtra(C.plan.PLAN_TYPE,6);
                activity.startActivity(intent6);
                break;
            case C.IntentionType.OUT_ASSIST://外协
                activity.startActivity(new Intent(activity, OutAssistActivity.class));
                break;
            case C.IntentionType.MERGE_CODE://转码合并
                activity.startActivity(new Intent(activity, TransCodeMergeActivity.class));
                break;
            default:
                ViewShowUtils.showShortToast(activity, "功能暂未开放");
                break;

        }
    }

}
