package com.framework.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密
 * Created by HR on 2017/6/29.
 */
public class MD5Util {

    private static final String ALGORITHM = "MD5";

    /**
     * md5加密返回字节数组
     * @param str
     * @return
     */
    public static byte[] MD5Origin(String str){
        MessageDigest messageDigest;
        byte[] md5Bytes = null;

        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
           md5Bytes = messageDigest.digest(str.getBytes());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5Bytes;
    }

    /**
     * MD5 32位加密返回string
     * 参数是 MD5Origin 加密后的字节数组
     * @return
     */
    public static String MD532(byte[] b){
        String str = null;

        try {

            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                int val = ((int) b[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            str = hexValue.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * MD5 16位加密
     * @param encryptStr
     * @return
     */
    public static String MD516(String encryptStr) {
        return MD532(MD5Origin(encryptStr)).substring(8, 24);
    }

    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte b) {

        int iRet = b;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;

        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 32位 md5加密 通过字节数组转换;
     *  结果等同于上面的算法
     * @param str
     * @return
     */
    public static String  MD5String(String str){
        return byteToString(MD5Origin(str));
    }

}
