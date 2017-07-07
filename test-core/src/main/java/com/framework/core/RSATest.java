package com.framework.core;

import com.framework.Util.Base64Util;
import com.framework.Util.RSAUtil;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * Created by HR on 2017/6/29.
 */
public class RSATest {

    Map<String, Object> keyMap = null;
    String str = null;
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeSn2BBNGWUs4DAxNlSzTn2GxxMXNhEl5cSr8Sx4NfV/SXeoC6V+Wp6i4vTac/+LJ8g2SqJ1CVM5W3GaWMz+hCdElMEgfH65gF9P40yaOX6LHbnKBWxb6tgYHj45hVJygdoX4Jx1bvsHBOq01VYKeSxedIEEg9/vRB9RcBg83s/QIDAQAB";
    String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ5KfYEE0ZZSzgMDE2VLNOfYbHExc2ESXlxKvxLHg19X9Jd6gLpX5anqLi9Npz/4snyDZKonUJUzlbcZpYzP6EJ0SUwSB8frmAX0/jTJo5fosducoFbFvq2BgePjmFUnKB2hfgnHVu+wcE6rTVVgp5LF50gQSD3+9EH1FwGDzez9AgMBAAECgYA6buMvupioNn8oa31AemfYYnN9wbSHFCHTTiCFDTO5AZ4gQS++Gm6EsBdGjRL9sZAqGNMTYRoFShqtkgEkHyfss4Htg7XMrbJ2JQ/pRdhJfvqGv0S1xzzZNsmmcPAc2ptYnHl+r1EVLF3ehfi29WalXUPe6MCMI0rbstq5rGXkvQJBANHktlLFDwy0KP5CbIuDJhqLtVp9N71CoR1q195dCVYHB29cotRs22mlHdw7Kowb9bzaWlvbUz8bv8BP5sNaQDMCQQDBD+6gJcGbFPabpZFj8HmENGqTPXgGVHKHNjJZ67F0tSm1y74cfei/8RLoMx5690iNklbTfPW2rqB49f8XGS4PAkBCE9aySAds4GrnYNH7OdaNFN6LFlCt7IP8Vt6Oyotd4eidTnSUm5CXubuk0ZubcFH0mmlH8cxLWdkMGhtz9L2xAkEAtFeQnZKHVrlvX5d6x7Njn6ZjA6WAz9Dkpv9ua2bqfk2YZ9Spgu3ulBW6hqLxzwXdhTl019u7n2liR3vrTIVd6wJAS2sG3eqg1Fa7i2TAwLfv0iv6crsrv9uvMc5qZkKJfbVgppw4yeZweL1uuyq4mPg4YxDLL4y3d5f4DhkpUwDXNg==";

    @Before
    public void initKeys() throws NoSuchAlgorithmException {
        keyMap = RSAUtil.initKey();
        str = "username:password";

//        publicKey = RSAUtil.getPublicKey(keyMap);
//        privateKey = RSAUtil.getPrivateKey(keyMap);
    }

    @Test
    public void testGetKeys(){

        publicKey = RSAUtil.getPublicKey(keyMap);
        privateKey = RSAUtil.getPrivateKey(keyMap);

        System.out.println("publicKey: " + publicKey);
        System.out.println("privateKey: " + privateKey);
    }

    /**
     * 测试公钥加密私钥解密，base64传输String
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    @Test
    public void testPubEnPriDe() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {

        System.out.println("--------------公钥加密私钥解密过程-------------------");
        byte[] enBytes = RSAUtil.encryptByPublicKey(str.getBytes(),publicKey);

        System.out.println("公钥加密：" + Base64Util.encryptBASE64(enBytes));

        byte[] deBytes = RSAUtil.decryptByPrivateKey(Base64Util.decryptBASE64(Base64Util.encryptBASE64(enBytes)),privateKey);

        System.out.println("私钥解密：" + new String(deBytes));

    }

    @Test
    public void testPriEnPubDe() throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {
        System.out.println("--------------私钥加密公钥解密过程-------------------");
        byte[] enBytes = RSAUtil.encryptByPrivateKey(str.getBytes(),privateKey);

        System.out.println("私钥加密：" + Base64Util.encryptBASE64(enBytes));

        byte[] deBytes = RSAUtil.decryptByPublicKey(enBytes,publicKey);

        System.out.println("公钥解密：" + new String(deBytes));
    }

    @Test
    public void testSign() throws InvalidKeySpecException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {

        System.out.println("---------------私钥签名过程------------------");
        String sign = RSAUtil.sign(str.getBytes(),privateKey);
        //私钥加密数据
        //byte[] enBytes = RSAUtil.encryptByPrivateKey(str.getBytes(),privateKey);

        System.out.println("私钥签名：" + sign);

        boolean verify = RSAUtil.verify(str.getBytes(),publicKey,sign);

        System.out.println(verify);

    }
}
