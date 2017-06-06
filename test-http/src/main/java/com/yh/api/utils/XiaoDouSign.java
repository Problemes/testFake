package com.yh.api.utils;

import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by HR on 2017/1/12.
 */
public class XiaoDouSign {

    // 建议写到配置文件中
    private static final String app_key = "e83ddc3e63883ef2";
    private static final String app_secret_key = "0655e766e83ddc3e63883ef280a2fb44";

    /**
     * 获取数据接口签名
     *
     * @param data
     *            LinkedHashMap数据，保证顺序
     * @return
     * @throws Exception
     */
    public static String getSign(LinkedHashMap<String, String> data) throws Exception {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sb.append(data.get(entry.getKey()));
        }
        // 密钥
        sb.append(app_secret_key);
        System.out.println("MD5加密之前的字符串:" + sb.toString());

        // 转换字节数组为16进制字串
        byte[] bytes = MessageDigest.getInstance("MD5").digest(sb.toString().getBytes("UTF-8")); // 家校宝接口为UTF-8编码
        sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(byteToArrayString(b));
        }
        System.out.println("MD5加密之后的签名：" + sb.toString());
        return sb.toString();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}


