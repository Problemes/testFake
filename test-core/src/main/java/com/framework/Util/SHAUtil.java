package com.framework.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * SHA加密工具类
 * Created by HR on 2017/10/9.
 */
public class SHAUtil {

    public static String shaEncode(String str) throws UnsupportedEncodingException {

        MessageDigest sha = null;
        try {

            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "encode wrong";
        }

        byte[] byteArray = str.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        String str = "shaencodeisamessage,sososososotrydoit 3d42a25b7ed307f7fb2dcd2a08dbbffc962eb4d9";
        String str1 = "3d42a25b7ed307f7fb2dcd2a08dbbffc962eb4d9";
        String str2 = "f0a557d2f495b77dcb320ce8b465d5396259187c";

        String shaEncode = shaEncode(str);

        System.out.println(shaEncode);
        System.out.println(str1.length());
    }

}
