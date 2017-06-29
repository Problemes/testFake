package com.framework.Util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Base64加密解密
 * Created by HR on 2017/6/29.
 */
public class Base64Util {


    public static BASE64Encoder base64Encoder = new BASE64Encoder();
    public static BASE64Decoder base64DEcoder = new BASE64Decoder();

    /**
     * BASE64加密
     * @param key
     * @return
     */
    public static String encryptBASE64(byte[] key) {
        return base64Encoder.encodeBuffer(key);
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws IOException
     */
    public static byte[] decryptBASE64(String key) throws IOException {
        return base64DEcoder.decodeBuffer(key);
    }
}
