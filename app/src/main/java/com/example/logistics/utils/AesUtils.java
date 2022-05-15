package com.example.logistics.utils;

/**
 * @author : jyx
 * @description: AES对称加密
 * @date :17.9.21
 */

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zachary on 18/6/27.
 * 安卓端AES加密
 */
public class AesUtils {

    //加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
    private static String sKey="pigxpigxpigxpigx";//与后端秘钥统一
    //向量
    private static String ivParameter = "pigxpigxpigxpigx";
    private static AesUtils instance = null;


    /**
     * 加密
     * @param
     * * @param sKey 私钥 ，可以用字母和数字组成,AES固定格式为128/192/256 bits.即：16/24/32bytes。此处使用AES-128-CBC加密模式
     * @return
     * ZhaoLi
     */
    public static String encrypt(String data) {

        //加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
        String sKey="pigxpigxpigxpigx";//与后端秘钥统一
        String ivString = sKey;
        //偏移量
        byte[] iv = ivString.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int length = dataBytes.length;
            //计算需填充长度
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            //填充
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(sKey.getBytes(), "AES");
            //设置偏移量参数
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            return base64Encode(cipher.doFinal(plaintext));

        } catch (Exception e) {
            handleException("encrypt", e);
        }
        return null;
    }

    /**
     * 将 字节数组 转换成 Base64 编码
     * 用Base64.DEFAULT模式会导致加密的text下面多一行（在应用中显示是这样）
     */
    public static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    /**
     * 将 Base64 字符串 解码成 字节数组
     */
    public static byte[] base64Decode(String data) {
        return Base64.decode(data, Base64.NO_WRAP);
    }

    /**
     * 处理异常
     */
    private static void handleException(String methodName, Exception e) {
        e.printStackTrace();
    }




}