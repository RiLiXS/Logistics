package com.example.logistics.utilsf;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.example.logistics.bean.TimeBean;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * data:2019/11/4
 * author:jyx
 * function:月份时间选择器
 */
public class SelfPlanTimeUtil {
    private volatile static SelfPlanTimeUtil timeCheckUtil;
    private static String time;
    private Context context;

    private boolean[] type = {true, true, true, false, false, false};

    public SelfPlanTimeUtil(Context context) {
        this.context = context;
    }

    public static SelfPlanTimeUtil getInstance(Context context) {
        if (timeCheckUtil == null) {
            timeCheckUtil = new SelfPlanTimeUtil(context);
        }
        return timeCheckUtil;
    }

    /**
     * 时间选择器
     *
     * @param
     * @param title 选择器弹窗的标题
     * @param flag  返回时间的格式
     */
    public void showBirthdayDate(Context context, String title, final int flag,int num) {


        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH, +100);// 选择时间的间隔
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);//设置起始年份
        Calendar endDate = Calendar.getInstance();
        endDate.set(3000,12,30);
        new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date, flag);// 返回的时间
                long stringToDate = getStringToDate(time);
                requestBirthday(stringToDate);
                getTimeString(time,num);
            }
        })

                .setType(type)
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("完成")//确认按钮文字
                .setTitleSize(16)//标题文字大小
                .setContentTextSize(20)//内容位子大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#F05557"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#999999"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build()
                .show();

    }

    private void getTimeString(String time,int type) {
        EventBus.getDefault().post(new TimeBean(time+"",type));
    }


    private void requestBirthday(long textView) {

       //  EventBus.getDefault().post(new TimeBean(textView+""));


    }


    /**
     * 时间选择器
     *
     * @param
     * @param title 选择器弹窗的标题
     * @param flag  返回时间的格式
     */
    public void showBirthdayDate2(Context context, String title, final TextView textView, final int flag) {


        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH, +100);// 选择时间的间隔
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2020, 3, 1);//设置起始年份
        Calendar endDate = Calendar.getInstance();
        new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date, flag);// 返回的时间
                textView.setText(time);
                String time2 = getTime(date, 0);// 返回的时间
                long stringToDate = getStringToDate(time2);
                requestBirthday2(stringToDate);
            }
        })

                .setType(type)
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("完成")//确认按钮文字
                .setTitleSize(16)//标题文字大小
                .setContentTextSize(20)//内容位子大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#F05557"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#999999"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build()
                .show();

    }

    private void requestBirthday2(long stringToDate) {
        //  EventBus.getDefault().post(new TimeBean(stringToDate+""));
    }

    /**
     * 格式化时间
     *
     * @param date 时间数据源
     * @param type 时间返回格式
     * @return
     */
    public String getTime(Date date, int type) {//可根据需要自行截取数据显示
        SimpleDateFormat format = null;
        if (type == 1) {
            format = new SimpleDateFormat("yyyy年MM月" + " 月报");
        } else if (type == 0) {
            format = new SimpleDateFormat("yyyy-MM");
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return format.format(date);
    }

    //字符串转化为时间戳
    public static long getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

}
