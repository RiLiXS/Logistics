package com.example.logistics.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author : jyx
 * @description:
 * @date :4/1/21
 */
public class MathUtils {


    /**
     * 去掉小数点后多余位数
     *
     * @param
     * @return
     */
    public static String getNum(String rmb) {
        if (TextUtils.isEmpty(rmb)) {
            return "";
        }
        if (rmb.indexOf(".") > 0) {
            rmb = rmb.replaceAll("0+?$", "");//去掉多余的0
            rmb = rmb.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return rmb;

    }

    /**
     * 获取环比
     *
     * @param current
     * @param last
     * @return
     */
    public static String getChain(String current, String last) {
        if (TextUtils.isEmpty(current) && TextUtils.isEmpty(last)) return "";
        double currentDouble = Double.parseDouble(current);
        double lastDouble = Double.parseDouble(last);
        double subtract = getSubtract(currentDouble, lastDouble);
        double chainDouble = (subtract / lastDouble) * 100;
        return roundByScale(chainDouble, 2) + "%";
    }

    /**
     * string 转化为百分比
     *
     * @param a
     * @return
     */
    public static String getStringRate(String a) {
        double currentDouble = Double.parseDouble(a) * 100;
        return roundByScale(currentDouble, 2) + "%";
    }

    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     *
     * @param v     需要格式化的数字
     * @param scale 小数点后保留几位
     * @return
     */
    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

    /**
     * double相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double getSum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * double相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String getSum2(String d1, String d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        double doubleValue = bd1.add(bd2).doubleValue();
        return getNum(String.valueOf(doubleValue));
    }


    /**
     * double相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double getSubtract(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String getSubtract2(String d1, String d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        double doubleValue = bd1.subtract(bd2).doubleValue();
        return getNum(String.valueOf(doubleValue));
    }


    /**
     * double相乘
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double getMultiply(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double相除
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double getDivide(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double相除，保留scale位小数
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double getDivide(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 字符串数字为null处理
     * @param s1
     * @return
     */
    public static String getString(String s1) {
        if (TextUtils.isEmpty(s1)) {
            return "0";
        } else {
            return s1;
        }
    }


    /**
     * 字符串为null处理
     * @param s1
     * @return
     */
    public static String getStr(String s1) {
        if (TextUtils.isEmpty(s1)) {
            return "";
        } else {
            return s1;
        }
    }


    /**
     * 小数字符串数字转百分比
     * @param s1
     * @return
     */
    public static String getPercentage(String s1) {
        if (TextUtils.isEmpty(s1)) {
            return "0%";
        } else {
            double currentDouble = Double.parseDouble(s1) * 100;
            return roundByScale(currentDouble, 2) + "%";
        }
    }
    /**
     * 把金额用，区隔开来
     * 千位分隔符 保留两位小数 没有也不补0
     * @param s1
     * @return
     */
    public static String getPrice(String s1){
        if (TextUtils.isEmpty(s1)) {
            return "0";
        }else{
            DecimalFormat df2 = new DecimalFormat("#,##0.##") ;
            return df2.format(new BigDecimal(s1));
        }

    }


    /**
     * string相除 加百分号
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String getScale(String d1, String d2) {
        double currentDouble = Double.parseDouble(d1);
        double lastDouble = Double.parseDouble(d2);
       double chainDouble = (currentDouble / lastDouble) * 100;
        return roundByScale(chainDouble, 2) + "%";
    }


    /**
     * string相除 不加百分号
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String getScale2(String d1, String d2) {
        double currentDouble = Double.parseDouble(d1);
        double lastDouble = Double.parseDouble(d2);
        double chainDouble = (currentDouble / lastDouble) * 100;
        return roundByScale(chainDouble, 2);
    }

    /**
     * string相除 不加百分号
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String getScale3(String d1, String d2) {
        double currentDouble = Double.parseDouble(d1);
        double lastDouble = Double.parseDouble(d2);
        double chainDouble = (currentDouble / lastDouble);
        return roundByScale(chainDouble, 2);
    }


    /**
     * 判断TextView的内容宽度是否超出其可用宽度
     * @param tv
     * @return
     */
    public static boolean isOverFlowed(TextView tv) {
        int availableWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();
        Paint textViewPaint = tv.getPaint();
        float textWidth = textViewPaint.measureText(tv.getText().toString());
        if (textWidth > availableWidth) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 调用系统浏览器打开网页
     * @param context
     * @param url
     */
    public static void getSystemBrowse(Context context,String url){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);

    }

}
